package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.OrdersInWorkWorkplace;
import com.ibsys.backend.core.domain.entity.Production;
import com.ibsys.backend.core.domain.entity.StuecklistenGruppe;
import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import com.ibsys.backend.core.domain.entity.WaitingliststockWaitinglist;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.OrdersInWorkWorkplaceRepository;
import com.ibsys.backend.core.repository.ProductionRepository;
import com.ibsys.backend.core.repository.WaitinglistWorkplaceRepository;
import com.ibsys.backend.core.repository.WaitingliststockWaitlinglistRepository;
import com.ibsys.backend.web.dto.DispositionEigenfertigungInputDTO;
import com.ibsys.backend.web.dto.DispositionEigenfertigungResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTOP1 = dispositionEigenfertigungP1(List.of(1,26,51,16,17,50,4,10,49,7,13,18));
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTOP2 = dispositionEigenfertigungP2(List.of(2,26,56,16,17,55,5,11,54,8,14,19));
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTOP3 = dispositionEigenfertigungP3(List.of(3,26,31,16,17,30,6,12,29,9,15,20));
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO = dispositionEigenfertigung(List.of(1,26,51,16,17,50,4,10,49,7,13,18), StuecklistenGruppe.GRUPPE_1);
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO12 = dispositionEigenfertigung(List.of(2,26,56,16,17,55,5,11,54,8,14,19), StuecklistenGruppe.GRUPPE_2);
        log.debug("");
        log.debug("");
        log.debug("");
        DispositionEigenfertigungResultDTO dispositionEigenfertigungResultDTO122 = dispositionEigenfertigung(List.of(3,26,31,16,17,30,6,12,29,9,15,20), StuecklistenGruppe.GRUPPE_3);

        return List.of(dispositionEigenfertigungResultDTOP1, dispositionEigenfertigungResultDTOP2, dispositionEigenfertigungResultDTOP3);
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigungP1(final List<Integer> reihenfolge) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Production> productions = new ArrayList<>();
        List<Article> resultArticles = new ArrayList<>();

        reihenfolge.stream()
                .forEach(
                        index -> {
                            List<Article> articles = allArticles.stream().filter(allArticle -> allArticle.getId() == index).toList();
                            Article article = articles.get(0);
                            int vertriebswunschCurrentArticle = vertriebswunsch.getP1();
                            int auftraegeInWarteschlange = sumUpAuftraegeInWarteschlange(article.getId());
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            int auftraegeInBearbeitung = 0;
                            if(ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }
                            article.setWarteschlange(auftraegeInWarteschlange);
                            int vorgaengerArtikelId = 0;
                            switch (article.getId()) {
                                case 26, 51 -> vorgaengerArtikelId = 1;
                                case 16, 17, 50 -> vorgaengerArtikelId = 51;
                                case 4, 10, 49 -> vorgaengerArtikelId = 50;
                                case 7, 13, 18 -> vorgaengerArtikelId = 49;
                            }
                            int warteschlange = 0;
                            if(article.getId() != 1) {
                                Article lastArticle = articleRepository.findById(vorgaengerArtikelId).orElse(null);
                                if (lastArticle.getWarteschlange() > 0) {
                                    warteschlange = lastArticle.getWarteschlange();
                                }
                            }
                            if(article.getId() != 1) {
                                vertriebswunschCurrentArticle = dispositionResult.get(vorgaengerArtikelId);
                            }
                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                warteschlange /= 3;
                                geplanterSicherheitsbestand /= 3;
                                lagerbestandEndeVorperiode /= 3;
                                auftraegeInWarteschlange /= 3;
                                auftraegeInBearbeitung /= 3;
                            }
                            int produktionsauftraege = vertriebswunschCurrentArticle
                                    + warteschlange
                                    + geplanterSicherheitsbestand
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
                                    + " - "
                                    + lagerbestandEndeVorperiode
                                    + " - "
                                    + auftraegeInWarteschlange
                                    + " - "
                                    + auftraegeInBearbeitung
                                    + " = "
                                    + produktionsauftraege;
                            log.debug(rechnung);
                            dispositionResult.put(article.getId(), produktionsauftraege);
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

        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(1)
                .articlesProduktionsmenge(dispositionResult)
                .build();
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigungP2(final List<Integer> reihenfolge) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Production> productions = new ArrayList<>();
        List<Article> resultArticles = new ArrayList<>();

        reihenfolge.stream()
                .forEach(
                        index -> {
                            List<Article> articles = allArticles.stream().filter(allArticle -> allArticle.getId() == index).toList();
                            Article article = articles.get(0);
                            int vertriebswunschCurrentArticle = vertriebswunsch.getP2();
                            int auftraegeInWarteschlange = sumUpAuftraegeInWarteschlange(article.getId());
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            int auftraegeInBearbeitung = 0;
                            if(ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }
                            article.setWarteschlange(auftraegeInWarteschlange);
                            int vorgaengerArtikelId = 0;
                            switch (article.getId()) {
                                case 26, 56 -> vorgaengerArtikelId = 2;
                                case 16, 17, 55 -> vorgaengerArtikelId = 56;
                                case 5, 11, 54 -> vorgaengerArtikelId = 55;
                                case 8, 14, 19 -> vorgaengerArtikelId = 54;
                            }
                            int warteschlange = 0;
                            if(article.getId() != 2) {
                                Article lastArticle = articleRepository.findById(vorgaengerArtikelId).orElse(null);
                                if (lastArticle.getWarteschlange() > 0) {
                                    warteschlange = lastArticle.getWarteschlange();
                                }
                            }
                            if(article.getId() != 2) {
                                vertriebswunschCurrentArticle = dispositionResult.get(vorgaengerArtikelId);
                            }
                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                warteschlange /= 3;
                                geplanterSicherheitsbestand /= 3;
                                lagerbestandEndeVorperiode /= 3;
                                auftraegeInWarteschlange /= 3;
                                auftraegeInBearbeitung /= 3;
                            }
                            int produktionsauftraege = vertriebswunschCurrentArticle
                                    + warteschlange
                                    + geplanterSicherheitsbestand
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
                                    + " - "
                                    + lagerbestandEndeVorperiode
                                    + " - "
                                    + auftraegeInWarteschlange
                                    + " - "
                                    + auftraegeInBearbeitung
                                    + " = "
                                    + produktionsauftraege;
                            log.debug(rechnung);
                            dispositionResult.put(article.getId(), produktionsauftraege);
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
        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(2)
                .articlesProduktionsmenge(dispositionResult)
                .build();
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigungP3(final List<Integer> reihenfolge) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Production> productions = new ArrayList<>();
        List<Article> resultArticles = new ArrayList<>();

        reihenfolge.stream()
                .forEach(
                        index -> {
                            List<Article> articles = allArticles.stream().filter(allArticle -> allArticle.getId() == index).toList();
                            Article article = articles.get(0);
                            int vertriebswunschCurrentArticle = vertriebswunsch.getP3();
                            int auftraegeInWarteschlange = sumUpAuftraegeInWarteschlange(article.getId());
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            int auftraegeInBearbeitung = 0;
                            if(ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }
                            article.setWarteschlange(auftraegeInWarteschlange);
                            int vorgaengerArtikelId = 0;
                            switch (article.getId()) {
                                case 26, 31 -> vorgaengerArtikelId = 3;
                                case 16, 17, 30 -> vorgaengerArtikelId = 31;
                                case 6, 12, 29 -> vorgaengerArtikelId = 30;
                                case 9, 15, 20 -> vorgaengerArtikelId = 29;
                            }
                            int warteschlange = 0;
                            if(article.getId() != 3) {
                                Article lastArticle = articleRepository.findById(vorgaengerArtikelId).orElse(null);
                                if (lastArticle.getWarteschlange() > 0) {
                                    warteschlange = lastArticle.getWarteschlange();
                                }
                            }
                            if(article.getId() != 3) {
                                vertriebswunschCurrentArticle = dispositionResult.get(vorgaengerArtikelId);
                            }
                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                warteschlange /= 3;
                                geplanterSicherheitsbestand /= 3;
                                lagerbestandEndeVorperiode /= 3;
                                auftraegeInWarteschlange /= 3;
                                auftraegeInBearbeitung /= 3;
                            }
                            int produktionsauftraege = vertriebswunschCurrentArticle
                                    + warteschlange
                                    + geplanterSicherheitsbestand
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
                                    + " - "
                                    + lagerbestandEndeVorperiode
                                    + " - "
                                    + auftraegeInWarteschlange
                                    + " - "
                                    + auftraegeInBearbeitung
                                    + " = "
                                    + produktionsauftraege;
                            log.debug(rechnung);
                            dispositionResult.put(article.getId(), produktionsauftraege);
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
        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(3)
                .articlesProduktionsmenge(dispositionResult)
                .build();
    }

    public DispositionEigenfertigungResultDTO dispositionEigenfertigung(final List<Integer> reihenfolge, final StuecklistenGruppe stuecklistenGruppe) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Production> productions = new ArrayList<>();
        List<Article> resultArticles = new ArrayList<>();

        reihenfolge
                .forEach(
                        index -> {
                            Article article = allArticles.stream().filter(allArticle -> allArticle.getId() == index).findFirst().orElse(null);

                            int vertriebswunschCurrentArticle = getVertriebswunsch(stuecklistenGruppe, vertriebswunsch);

                            int auftraegeInWarteschlange = sumUpAuftraegeInWarteschlange(article.getId());

                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);

                            int auftraegeInBearbeitung = 0;

                            if (ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }

                            article.setWarteschlange(auftraegeInWarteschlange);

                            int vorgaengerArtikelId = getVorgaengerArtikelId(article, stuecklistenGruppe);

                            int warteschlange = 0;

                            if(!IntStream.rangeClosed(1,3).boxed().toList().contains(article.getId())) {
                                Article lastArticle = articleRepository.findById(vorgaengerArtikelId).orElse(null);
                                if (lastArticle.getWarteschlange() > 0) {
                                    warteschlange = lastArticle.getWarteschlange();
                                }
                            }

                            if(!IntStream.rangeClosed(1,3).boxed().toList().contains(article.getId())) {
                                vertriebswunschCurrentArticle = dispositionResult.get(vorgaengerArtikelId);
                            }

                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                warteschlange /= 3;
                                geplanterSicherheitsbestand /= 3;
                                lagerbestandEndeVorperiode /= 3;
                                auftraegeInWarteschlange /= 3;
                                auftraegeInBearbeitung /= 3;
                            }
                            int produktionsauftraege = vertriebswunschCurrentArticle
                                    + warteschlange
                                    + geplanterSicherheitsbestand
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
                                    + " - "
                                    + lagerbestandEndeVorperiode
                                    + " - "
                                    + auftraegeInWarteschlange
                                    + " - "
                                    + auftraegeInBearbeitung
                                    + " = "
                                    + produktionsauftraege;
                            log.debug(rechnung);
                            dispositionResult.put(article.getId(), produktionsauftraege);
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
        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(3)
                .articlesProduktionsmenge(dispositionResult)
                .build();
    }

    private int getVertriebswunsch(StuecklistenGruppe stuecklistenGruppe, Forecast vertriebswunsch) {
        if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_1) {
            return vertriebswunsch.getP1();
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_2) {
            return vertriebswunsch.getP2();
        } else if(stuecklistenGruppe == StuecklistenGruppe.GRUPPE_3) {
            return vertriebswunsch.getP3();
        }
        throw new Error("Vertriebswunsch fehlt");
    }

    private int getVorgaengerArtikelId(Article article, StuecklistenGruppe stuecklistenGruppe) {
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
        throw new Error("Article ID gibt es nicht!");
    }

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

}
