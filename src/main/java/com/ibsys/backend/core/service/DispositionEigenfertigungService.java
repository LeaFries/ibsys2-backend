package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.StuecklistenGruppe;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.web.dto.DispositionEigenfertigungInputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DispositionEigenfertigungService {

    private final ArticleRepository articleRepository;
    private final ForecastRepository forecastRepository;

    @Transactional
    public void updateArticles(final Map<Integer, Integer> geplanterSicherheitsbestand) {
        List<Article> articles = articleRepository.findAll();
        List<Article> updatedArticles = articles.stream()
                .map(article -> {
                    article.setGeplanterSicherheitsbestand(
                            geplanterSicherheitsbestand.get(article.getId())
                    );
                    return article;
                })
                .toList();
        articleRepository.saveAllAndFlush(updatedArticles);
    }

    @Transactional
    public void dispositionEigenfertigungStart(final DispositionEigenfertigungInputDTO inputDTO) {
        updateArticles(inputDTO.getGeplanterSicherheitsbestand());
    }

    public void dispositionEigenfertigung() {
        HashMap<Integer, Integer> dispositionResult = new HashMap<>();
        Forecast forecast = forecastRepository.findById(1L).orElse(null);
        List<Article> articles = articleRepository.findAll();
        articles.stream()
                .filter(article -> article.getStuecklistenGruppe() == StuecklistenGruppe.GRUPPE_1)
                .forEach(
                        article -> {


                        }
                );

    }

}
