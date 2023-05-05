package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DispositionEigenfertigungService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void updateArticles(List<Article> requestArticles) {
        List<Article> allArticles = articleRepository.findAll();

        Article maxPeriodArticle = allArticles.stream().max(Comparator.comparing(Article::getPeriod))
                .stream().findFirst()
                .orElseThrow(null);

        int currentPeriod = maxPeriodArticle.getPeriod();

        List<Article> articles = allArticles.stream()
                .filter(article1 -> Objects.equals(article1.getPeriod(), currentPeriod))
                .map(article -> {
                    Optional<Article> requestArticle = requestArticles.stream().filter(articleRequest -> Objects.equals(articleRequest.getId(), article.getId())).findAny();
                    requestArticle.ifPresent(value -> article.setLagerbestandVorperiode(value.getLagerbestandVorperiode()));
                    return article;
                })
                .toList();

        articleRepository.saveAllAndFlush(articles);
    }

}
