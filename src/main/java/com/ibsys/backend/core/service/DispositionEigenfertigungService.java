package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.OrdersInWorkWorkplace;
import com.ibsys.backend.core.domain.entity.StuecklistenGruppe;
import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.OrdersInWorkWorkplaceRepository;
import com.ibsys.backend.core.repository.WaitinglistWorkplaceRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class DispositionEigenfertigungService {

    private final ArticleRepository articleRepository;
    private final ForecastRepository forecastRepository;
    private final WaitinglistWorkplaceRepository waitinglistWorkplaceRepository;
    private final OrdersInWorkWorkplaceRepository ordersInWorkWorkplaceRepository;

    @Transactional
    public void updateArticles(final Map<Integer, Integer> geplanterSicherheitsbestand) {
        log.debug(geplanterSicherheitsbestand.toString());
        List<Article> articles = articleRepository.findAll();
        List<Article> updatedArticles = articles.stream()
                .map(article -> {
                    if(geplanterSicherheitsbestand.containsKey(article.getId())) {
                        article.setGeplanterSicherheitsbestand(geplanterSicherheitsbestand.get(article.getId()));
                    }
                    return article;
                })
                .toList();
        articleRepository.saveAllAndFlush(updatedArticles);
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigungStart(final DispositionEigenfertigungInputDTO inputDTO) {
        updateArticles(inputDTO.getGeplanterSicherheitsbestand());
        return dispositionEigenfertigung(List.of(1,26,51,16,17,50,4,10,49,7,13,18));
    }

    @Transactional
    public DispositionEigenfertigungResultDTO dispositionEigenfertigung(final List<Integer> reihenfolge) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();

        reihenfolge.stream()
                .forEach(
                        index -> {
                            List<Article> articles = allArticles.stream().filter(allArticle -> allArticle.getId() == index).toList();
                            Article article = articles.get(0);
                            int vertriebswunschCurrentArticle = vertriebswunsch.getP1();
                            int auftraegeInWarteschlange = sumUpAuftraegeInBearbeitung(article.getId());
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            int auftraegeInBearbeitung = 0;
                            if(ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }
                            int vorgaengerArtikelId = 0;
                            switch (article.getId()) {
                                case 26, 51 -> vorgaengerArtikelId = 1;
                                case 16, 17, 50 -> vorgaengerArtikelId = 51;
                                case 4, 10, 49 -> vorgaengerArtikelId = 50;
                                case 7, 13, 18 -> vorgaengerArtikelId = 49;
                            }
                            int warteschlange = 0;
                            OrdersInWorkWorkplace ordersInWorkWorkplaceLastArticle = ordersInWorkWorkplaceRepository.findByItem(vorgaengerArtikelId).orElse(null);
                            if(ordersInWorkWorkplaceLastArticle != null) {
                                warteschlange = ordersInWorkWorkplaceLastArticle.getAmount();
                            }
                            if(article.getId() != 1) {
                                vertriebswunschCurrentArticle = dispositionResult.get(vorgaengerArtikelId);
                            }
                            int geplanterSicherheitsbestand = article.getGeplanterSicherheitsbestand();
                            int lagerbestandEndeVorperiode = article.getAmount();
                            if(article.getStuecklistenGruppe() == StuecklistenGruppe.ALL) {
                                warteschlange /= 3;
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
                                    + produktionsauftraege
                                    + " = "
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
                                    + auftraegeInBearbeitung;
                            log.debug(rechnung);
                            dispositionResult.put(article.getId(), produktionsauftraege);
                        }
                );
        return DispositionEigenfertigungResultDTO.builder()
                .produktGruppe(1)
                .articlesProduktionsmenge(dispositionResult)
                .build();
    }

    public int sumUpAuftraegeInBearbeitung(int id) {
        ArrayList<WaitinglistWorkplace> waitinglistWorkplaces = waitinglistWorkplaceRepository.findByItem(id);
        return waitinglistWorkplaces.stream()
                .mapToInt(WaitinglistWorkplace::getAmount)
                .sum();
    }

}
