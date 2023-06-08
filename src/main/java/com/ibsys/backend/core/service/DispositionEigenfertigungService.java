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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    public void dispositionEigenfertigungStart(final DispositionEigenfertigungInputDTO inputDTO) {
        updateArticles(inputDTO.getGeplanterSicherheitsbestand());
        dispositionEigenfertigung(Collections.emptyList());
    }

    @Transactional
    public void dispositionEigenfertigung(final List<Integer> reihenfolge) {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast vertriebswunsch = forecastRepository.findById(1L).orElse(null);
        List<Article> allArticles = articleRepository.findAll();
        List<Integer> reihenfolgeP1 = new ArrayList<>(List.of(1,26,51,16,17,50,4,10,49,7,13,18));
        allArticles.stream()
                .filter(article -> article.getStuecklistenGruppe() == StuecklistenGruppe.GRUPPE_3)
                .forEach(
                        article -> {
                            log.debug(article.toString());
                            int auftraegeInWarteschlange = sumUpAuftraegeInBearbeitung(article.getId());
                            int lagerbestandEndeVorperiode = article.getLagerbestandVorperiode();
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            if(ordersInWorkWorkplace == null) {
                                return;
                            }
                            int warteschlange = 0;
                            if(article.getId() != 1) {
                            }
                            int auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                        }
                );

        reihenfolge.stream()
                .forEach(
                        index -> {
                            List<Article> articles = allArticles.stream().filter(allArticle -> allArticle.getId() == index).toList();
                            Article article = articles.get(0);
                            int auftraegeInWarteschlange = sumUpAuftraegeInBearbeitung(article.getId());
                            int lagerbestandEndeVorperiode = article.getLagerbestandVorperiode();
                            OrdersInWorkWorkplace ordersInWorkWorkplace = ordersInWorkWorkplaceRepository.findByItem(article.getId()).orElse(null);
                            int auftraegeInBearbeitung = 0;
                            if(ordersInWorkWorkplace != null) {
                                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
                            }

                        }
                );

    }

    public int sumUpAuftraegeInBearbeitung(int id) {
        ArrayList<WaitinglistWorkplace> waitinglistWorkplaces = waitinglistWorkplaceRepository.findByItem(id);
        return waitinglistWorkplaces.stream()
                .mapToInt(WaitinglistWorkplace::getAmount)
                .sum();
    }

}
