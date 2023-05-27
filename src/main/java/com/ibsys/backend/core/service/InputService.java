package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.Waitinglist;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.WaitinglistRepository;
import com.ibsys.backend.web.dto.InputDTO;
import com.ibsys.backend.web.dto.mapper.ArticleMapper;
import com.ibsys.backend.web.dto.mapper.ForecastMapper;
import com.ibsys.backend.web.dto.mapper.WaitinglistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InputService {

    private final ForecastRepository forecastRepository;
    private final ArticleRepository articleRepository;
    private final WaitinglistRepository waitinglistRepository;

    private final ArticleMapper articleMapper;
    private final ForecastMapper forecastMapper;
    private final WaitinglistMapper waitinglistMapper;

    @Transactional
    public void importForcast(Forecast forecast) {
        forecastRepository.saveAndFlush(forecast);
    }

    @Transactional
    public void importInput(InputDTO inputDTO) {
        List<Article> articles = inputDTO.getWarehousestock().getArticle().stream()
                .map(articleDTO -> articleMapper.toArticle(articleDTO, inputDTO.getPeriod()))
                .toList();

        importArticles(articles);

        importForcast(forecastMapper.toForecast(inputDTO.getForecast(), inputDTO.getPeriod()));

        ArrayList<Waitinglist> waitinglists = new ArrayList<>();
        inputDTO.getWaitinglistworkstations()
                        .forEach(workplaceDTO -> {
                            if(workplaceDTO.getWaitinglist() == null) {
                                return;
                            }
                            workplaceDTO.getWaitinglist().forEach(
                                    waitinglistDTO -> waitinglists.add(waitinglistMapper.toWaitinglist(
                                            waitinglistDTO, inputDTO.getPeriod()
                                    ))
                            );
                        });
        importWaitinglist(waitinglists);

    }

    @Transactional
    public void importArticles(List<Article> articles) {
        articles.forEach(
                articleRepository::saveAndFlush
        );
    }

    @Transactional
    public void importWaitinglist(List<Waitinglist> waitinglist) {
        waitinglistRepository.saveAllAndFlush(waitinglist);
    }

}
