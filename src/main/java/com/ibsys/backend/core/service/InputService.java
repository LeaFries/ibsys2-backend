package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.StuecklistenGruppe;
import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import com.ibsys.backend.core.domain.entity.Workplace;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.OrdersInWorkWorkplaceRepository;
import com.ibsys.backend.core.repository.WaitinglistWorkplaceRepository;
import com.ibsys.backend.core.repository.WorkplaceRepository;
import com.ibsys.backend.web.dto.InputDTO;
import com.ibsys.backend.web.dto.mapper.ArticleMapper;
import com.ibsys.backend.web.dto.mapper.ForecastMapper;
import com.ibsys.backend.web.dto.mapper.OrdersInWorkWorkplaceMapper;
import com.ibsys.backend.web.dto.mapper.WaitinglistWorkplaceMapper;
import com.ibsys.backend.web.dto.mapper.WorkplaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InputService {

    private final ForecastRepository forecastRepository;
    private final ArticleRepository articleRepository;
    private final WaitinglistWorkplaceRepository waitinglistWorkplaceRepository;
    private final WorkplaceRepository workplaceRepository;
    private final OrdersInWorkWorkplaceRepository ordersInWorkWorkplaceRepository;

    private final ArticleMapper articleMapper;
    private final ForecastMapper forecastMapper;
    private final WaitinglistWorkplaceMapper waitinglistWorkplaceMapper;
    private final WorkplaceMapper workplaceMapper;
    private final OrdersInWorkWorkplaceMapper ordersInWorkWorkplaceMapper;

    @Transactional
    public void importForcast(Forecast forecast) {
        forecastRepository.saveAndFlush(forecast);
    }

    @Transactional
    public void importInput(InputDTO inputDTO) {
        List<Article> articles = inputDTO.getWarehousestock().getArticle().stream()
                .map(articleMapper::toArticle)
                .toList();

        importArticles(articles);

        importForcast(forecastMapper.toForecast(inputDTO.getForecast(), inputDTO.getPeriod()));

        importWaitinglistWorkstation(inputDTO);

        importOrdersInWorkWorkplace(inputDTO);

    }

    @Transactional
    public void importWaitinglistWorkstation(InputDTO inputDTO) {
        inputDTO.getWaitinglistworkstations().stream().forEach(
                workplaceDTO -> {
                    log.debug(workplaceDTO.toString());
                    Workplace workplace = workplaceRepository.saveAndFlush(workplaceMapper.toWorkplace(workplaceDTO));
                    if(workplaceDTO.getWaitinglist() == null) {
                        return;
                    }
                    List<WaitinglistWorkplace> waitinglistWorkplace = workplaceDTO.getWaitinglist().stream()
                            .map(waitinglistDTO -> {
                                waitinglistDTO.setWorkplace(workplace);
                                return waitinglistWorkplaceMapper.toWaitingWorkplacelist(waitinglistDTO);
                            })
                            .toList();
                    waitinglistWorkplaceRepository.saveAllAndFlush(waitinglistWorkplace);
                }
        );
    }

    @Transactional
    public void importOrdersInWorkWorkplace(InputDTO inputDTO) {
        ordersInWorkWorkplaceRepository.saveAllAndFlush(inputDTO.getOrdersinwork().stream()
                .map(ordersInWorkWorkplaceMapper::toOrdersInWorkWorkplace)
                .toList());
    }

    @Transactional
    public void importArticles(List<Article> articles) {
        articles = articles.stream().map(article -> {
            switch (article.getId()) {
                case 1, 4, 7, 10, 13, 18, 51, 50, 49 -> article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_1);
                case 2, 5, 8, 11, 14, 19, 65, 55, 54 -> article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_2);
                case 3, 20, 6, 9, 12, 15, 31, 30, 29 -> article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_3);
                case 26, 16, 17 -> article.setStuecklistenGruppe(StuecklistenGruppe.ALL);
                default -> article.setStuecklistenGruppe(null);
            }
            return article;
        }).toList();

        articleRepository.saveAllAndFlush(articles);
    }

    @Transactional
    public void importWaitinglist(List<WaitinglistWorkplace> waitinglistWorkplace) {
        waitinglistWorkplaceRepository.saveAllAndFlush(waitinglistWorkplace);
    }

}
