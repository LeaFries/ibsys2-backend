package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.*;
import com.ibsys.backend.core.repository.*;
import com.ibsys.backend.web.dto.DispositionEigenfertigungArticleResultDTO;
import com.ibsys.backend.web.dto.DispositionEigenfertigungInputDTO;
import com.ibsys.backend.web.dto.DispositionEigenfertigungResultDTO;
import com.ibsys.backend.web.dto.mapper.DispositionResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DispositionEigenfertigungService {

    private final ArticleRepository articleRepository;
    private final ForecastRepository forecastRepository;
    private final WaitinglistWorkplaceRepository waitinglistWorkplaceRepository;
    private final OrdersInWorkWorkplaceRepository ordersInWorkWorkplaceRepository;
    private final WaitingliststockWaitlinglistRepository waitingliststockWaitlinglistRepository;
    private final ProductionRepository productionRepository;
    private final ProductionInPeriodRepository productionInPeriodRepository;
    private final DispositionEigenfertigungRepository dispositionEigenfertigungRepository;

    private final DispositionResultMapper dispositionResultMapper;

    @Transactional
    public void updateArticles(final Map<Integer, Integer> geplanterSicherheitsbestand, final Map<Integer, Integer> setZuesaetlicheProduktionsauftaege) {
        log.debug(geplanterSicherheitsbestand.toString());
        List<Article> articles = articleRepository.findAll();
        List<Article> updatedArticles = articles.stream()
                .map(article -> {
                    if(geplanterSicherheitsbestand.containsKey(article.getId())) {
                        article.setGeplanterSicherheitsbestand(geplanterSicherheitsbestand.get(article.getId()));
                        article.setZuesaetlicheProduktionsauftaege(setZuesaetlicheProduktionsauftaege.get(article.getId()));
                    }
                    return article;
                })
                .toList();
        articleRepository.saveAllAndFlush(updatedArticles);
    }

    @Transactional
    public List<DispositionEigenfertigungResultDTO> dispositionEigenfertigungStart(final DispositionEigenfertigungInputDTO inputDTO) {
        updateArticles(inputDTO.getGeplanterSicherheitsbestand(), inputDTO.getZuesaetlicheProduktionsauftaege());
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO1 = dispositionEigenfertigung(List.of(1,26,51,16,17,50,4,10,49,7,13,18), StuecklistenGruppe.GRUPPE_1);
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO2 = dispositionEigenfertigung(List.of(2,26,56,16,17,55,5,11,54,8,14,19), StuecklistenGruppe.GRUPPE_2);
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO3 = dispositionEigenfertigung(List.of(3,26,31,16,17,30,6,12,29,9,15,20), StuecklistenGruppe.GRUPPE_3);

        // Put the production for bike 1, 2, 3 into the production-in-period table, for the matrix multiplication
        Production firstBike = productionRepository.findProductionByArticle(1).get();
        Production secondBike = productionRepository.findProductionByArticle(2).get();
        Production thirdBike = productionRepository.findProductionByArticle(3).get();

        List<ProductionInPeriod> productionInPeriods = productionInPeriodRepository.findAllById(Arrays.asList(1L, 2L, 3L));

        productionInPeriods.get(0).setPeriodN(firstBike.getQuantity());
        productionInPeriods.get(1).setPeriodN(secondBike.getQuantity());
        productionInPeriods.get(2).setPeriodN(thirdBike.getQuantity());

        productionInPeriodRepository.saveAllAndFlush(productionInPeriods);

        return List.of(dispositionEigenfertigungResultDTO1, dispositionEigenfertigungResultDTO2, dispositionEigenfertigungResultDTO3);
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigung(final List<Integer> reihenfolge, final StuecklistenGruppe stuecklistenGruppe) {
        List<DispositionEigenfertigungArticleResultDTO> dispositionResult = new ArrayList<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Production> productions = new ArrayList<>();
        List<Article> resultArticles = new ArrayList<>();
        List<OrdersInWorkWorkplace> allOrdersInWorkWorkplaces = ordersInWorkWorkplaceRepository.findAll();
        List<DispositionEigenfertigungResult> dispositionEigenfertigungResults = new ArrayList<>();

        reihenfolge
                .forEach(
                        index -> {
                            Article article = allArticles.stream().filter(allArticle -> allArticle.getId() == index).findFirst().orElse(null);

                            if(article == null) {
                                throw new RuntimeException("Dieser Artikel ist noch nicht in der Datenbank verhanden: " + index);
                            }

                            int vertriebswunschCurrentArticle = getVertriebswunsch(stuecklistenGruppe, vertriebswunsch);

                            int auftraegeInWarteschlange = sumUpAuftraegeInWarteschlange(article.getId());

                            OrdersInWorkWorkplace ordersInWorkWorkplace = allOrdersInWorkWorkplaces.stream()
                                    .filter(workplace -> workplace.getItem() == article.getId())
                                    .findFirst()
                                    .orElse(null);

                            int auftraegeInBearbeitung = 0;

                            if (ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }

                            article.setWarteschlange(auftraegeInWarteschlange);

                            int warteschlange = 0;

                            if(!IntStream.rangeClosed(1,3).boxed().toList().contains(article.getId())) {
                                int vorgaengerArtikelId = getVorgaengerArtikelId(article, stuecklistenGruppe);

                                vertriebswunschCurrentArticle = dispositionResult.stream()
                                        .filter(resultArticle -> resultArticle.getArticleNumber() == vorgaengerArtikelId)
                                        .findFirst()
                                        .get().getProduktionFuerKommendePeriode();

                                Article lastArticle = articleRepository.findById(vorgaengerArtikelId).orElse(null);

                                if (lastArticle.getWarteschlange() > 0) {
                                    warteschlange = lastArticle.getWarteschlange();
                                }
                            }

                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            int zusaetzlicheProduktionsauftraege = article.getZuesaetlicheProduktionsauftaege();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                int ausgleich = determineCompensation(warteschlange, stuecklistenGruppe);
                                warteschlange /= 3;
                                warteschlange += ausgleich;

                                ausgleich = determineCompensation(geplanterSicherheitsbestand, stuecklistenGruppe);
                                geplanterSicherheitsbestand /= 3;
                                geplanterSicherheitsbestand += ausgleich;

                                ausgleich = determineCompensation(lagerbestandEndeVorperiode, stuecklistenGruppe);
                                lagerbestandEndeVorperiode /= 3;
                                lagerbestandEndeVorperiode += ausgleich;

                                ausgleich = determineCompensation(auftraegeInWarteschlange, stuecklistenGruppe);
                                auftraegeInWarteschlange /= 3;
                                auftraegeInWarteschlange += ausgleich;

                                ausgleich = determineCompensation(auftraegeInBearbeitung, stuecklistenGruppe);
                                auftraegeInBearbeitung /= 3;
                                auftraegeInBearbeitung += ausgleich;
                            }

                            int produktionsauftraege = vertriebswunschCurrentArticle
                                    + warteschlange
                                    + geplanterSicherheitsbestand
                                    + zusaetzlicheProduktionsauftraege
                                    - lagerbestandEndeVorperiode
                                    - auftraegeInWarteschlange
                                    - auftraegeInBearbeitung;
                            String rechnung = "Article "
                                    + article.getId()
                                    + " | "
                                    + vertriebswunschCurrentArticle
                                    + " + "
                                    + warteschlange
                                    + " + "
                                    + geplanterSicherheitsbestand
                                    + " + "
                                    + zusaetzlicheProduktionsauftraege
                                    + " - "
                                    + lagerbestandEndeVorperiode
                                    + " - "
                                    + auftraegeInWarteschlange
                                    + " - "
                                    + auftraegeInBearbeitung
                                    + " = "
                                    + produktionsauftraege;
                            log.debug(rechnung);
                            dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                                    .articleNumber(article.getId())
                                                    .stuecklistenGruppe(stuecklistenGruppe)
                                                    .build())
                                            .vertriebswunsch(vertriebswunschCurrentArticle)
                                            .warteschlange(warteschlange)
                                            .geplanterSicherheitsbestand(geplanterSicherheitsbestand)
                                            .lagerbestandEndeVorperiode(lagerbestandEndeVorperiode)
                                            .auftraegeInWarteschlange(auftraegeInWarteschlange)
                                            .auftraegeInBearbeitung(auftraegeInBearbeitung)
                                            .produktionFuerKommendePeriode(produktionsauftraege)
                                            .zusaetzlicheProduktionsauftraege(zusaetzlicheProduktionsauftraege)
                                    .build());
                            dispositionResult.add(DispositionEigenfertigungArticleResultDTO.builder()
                                            .articleNumber(article.getId())
                                            .vertriebswunsch(vertriebswunschCurrentArticle)
                                            .warteschlange(warteschlange)
                                            .geplanterSicherheitsbestand(geplanterSicherheitsbestand)
                                            .zusaetzlicheProduktionsauftraege(zusaetzlicheProduktionsauftraege)
                                            .lagerbestandEndeVorperiode(lagerbestandEndeVorperiode)
                                            .auftraegeInWarteschlange(auftraegeInWarteschlange)
                                            .auftraegeInBearbeitung(auftraegeInBearbeitung)
                                            .produktionFuerKommendePeriode(produktionsauftraege)
                                    .build());
                            resultArticles.add(article);
                            Production production = Production.builder()
                                    .article(article.getId())
                                    .quantity(produktionsauftraege)
                                    .build();
                            productions.add(production);
                        }
                );
        articleRepository.saveAll(resultArticles);
        productionRepository.saveAll(productions);
        dispositionEigenfertigungRepository.saveAll(dispositionEigenfertigungResults);
        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(stuecklistenGruppe.toString())
                .articles(dispositionResult)
                .build();
    }

    @Transactional
    public int getVertriebswunsch(StuecklistenGruppe stuecklistenGruppe, Forecast vertriebswunsch) {
        if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_1) {
            return vertriebswunsch.getP1();
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_2) {
            return vertriebswunsch.getP2();
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_3) {
            return vertriebswunsch.getP3();
        }
        throw new RuntimeException("Vertriebswunsch fehlt");
    }

    @Transactional
    public int getVorgaengerArtikelId(Article article, StuecklistenGruppe stuecklistenGruppe) {
        if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_1) {
            switch (article.getId()) {
                case 26, 51 -> { return 1; }
                case 16, 17, 50 -> { return 51; }
                case 4, 10, 49 -> { return 50; }
                case 7, 13, 18 -> { return 49; }
            }
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_2) {
            switch (article.getId()) {
                case 26, 56 -> { return 2; }
                case 16, 17, 55 -> { return 56; }
                case 5, 11, 54 -> { return 55; }
                case 8, 14, 19 -> { return 54; }
            }
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_3) {
            switch (article.getId()) {
                case 26, 31 -> { return 3; }
                case 16, 17, 30 -> { return 31; }
                case 6, 12, 29 -> { return 30; }
                case 9, 15, 20 -> { return 29; }
            }
        }
        throw new RuntimeException("Article ID gibt es nicht: " + article.getId());
    }

    @Transactional
    public int sumUpAuftraegeInWarteschlange(int id) {
        ArrayList<WaitinglistWorkplace> waitinglistWorkplaces = waitinglistWorkplaceRepository.findByItem(id);
        ArrayList<WaitingliststockWaitinglist> waitingliststockWaitinglists = waitingliststockWaitlinglistRepository.findByItem(id);
        int waitinglistWorkplaceSum = waitinglistWorkplaces.stream()
                .mapToInt(WaitinglistWorkplace::getAmount)
                .sum();
        int waitingliststockSum = waitingliststockWaitinglists.stream()
                .mapToInt(WaitingliststockWaitinglist::getAmount)
                .sum();
        return waitinglistWorkplaceSum + waitingliststockSum;
    }

    @Transactional
    public int determineCompensation(int value, StuecklistenGruppe stuecklistenGruppe) {
        int ausgleich = 0;
        if(value % 3 != 0 && (stuecklistenGruppe == StuecklistenGruppe.GRUPPE_1 || stuecklistenGruppe == StuecklistenGruppe.GRUPPE_2)) {
            ausgleich = 1;
            if(value % 3 == 1 && stuecklistenGruppe == StuecklistenGruppe.GRUPPE_2) {
                ausgleich = 0;
            }
        }
        return ausgleich;
    }

    @Transactional(readOnly = true)
    public List<DispositionEigenfertigungResultDTO> get() {
        List<DispositionEigenfertigungResult> dispositionEigenfertigungResults = dispositionEigenfertigungRepository.findAll();

        List<DispositionEigenfertigungArticleResultDTO> dispositionP1Articles = dispositionEigenfertigungResults.stream()
                .filter(article -> article.getDispositinEigenfertigungResultId().getStuecklistenGruppe() == StuecklistenGruppe.GRUPPE_1)
                .map(dispositionResultMapper::toDTO)
                .toList();
        DispositionEigenfertigungResultDTO resultDTOP1 = DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(StuecklistenGruppe.GRUPPE_1.toString())
                .articles(dispositionP1Articles)
                .build();

        List<DispositionEigenfertigungArticleResultDTO> dispositionP2Articles = dispositionEigenfertigungResults.stream()
                .filter(article -> article.getDispositinEigenfertigungResultId().getStuecklistenGruppe() == StuecklistenGruppe.GRUPPE_2)
                .map(dispositionResultMapper::toDTO)
                .toList();
        DispositionEigenfertigungResultDTO resultDTOP2 = DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(StuecklistenGruppe.GRUPPE_2.toString())
                .articles(dispositionP2Articles)
                .build();

        List<DispositionEigenfertigungArticleResultDTO> dispositionP3Articles = dispositionEigenfertigungResults.stream()
                .filter(article -> article.getDispositinEigenfertigungResultId().getStuecklistenGruppe() == StuecklistenGruppe.GRUPPE_3)
                .map(dispositionResultMapper::toDTO)
                .toList();
        DispositionEigenfertigungResultDTO resultDTOP3 = DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(StuecklistenGruppe.GRUPPE_3.toString())
                .articles(dispositionP3Articles)
                .build();

        return List.of(resultDTOP1, resultDTOP2, resultDTOP3);
    }

}
