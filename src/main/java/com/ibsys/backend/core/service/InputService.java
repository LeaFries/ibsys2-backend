package com.ibsys.backend.core.service;


import com.ibsys.backend.core.domain.entity.*;
import com.ibsys.backend.core.repository.*;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.StuecklistenGruppe;
import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import com.ibsys.backend.core.domain.entity.WaitingliststockWaitinglist;
import com.ibsys.backend.core.domain.entity.Workplace;
import com.ibsys.backend.core.repository.ArticleRepository;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.OrdersInWorkWorkplaceRepository;
import com.ibsys.backend.core.repository.WaitinglistWorkplaceRepository;
import com.ibsys.backend.core.repository.WaitingliststockWaitlinglistRepository;
import com.ibsys.backend.core.repository.WorkplaceRepository;
import com.ibsys.backend.web.dto.InputDTO;
import com.ibsys.backend.web.dto.mapper.ArticleMapper;
import com.ibsys.backend.web.dto.mapper.ForecastMapper;
import com.ibsys.backend.web.dto.mapper.OrdersInWorkWorkplaceMapper;
import com.ibsys.backend.web.dto.mapper.WaitinglistWorkplaceMapper;
import com.ibsys.backend.web.dto.mapper.WaitingliststockWaitinglistMapper;
import com.ibsys.backend.web.dto.mapper.WorkplaceMapper;
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
    private final WaitinglistWorkplaceRepository waitinglistWorkplaceRepository;
    private final WorkplaceRepository workplaceRepository;
    private final OrdersInWorkWorkplaceRepository ordersInWorkWorkplaceRepository;
    private final PurchasePartDispositionRepository purchasePartDispositionRepository;
    private final WaitingliststockWaitlinglistRepository waitingliststockWaitlinglistRepository;
    private final FutureInwardStockmovementRepository futureInwardStockmovementRepository;
    private final DispositionEigenfertigungRepository dispositionEigenfertigungRepository;

    private final DispositionEigenfertigungService dispositionEigenfertigungService;

    private final ArticleMapper articleMapper;
    private final ForecastMapper forecastMapper;
    private final WaitinglistWorkplaceMapper waitinglistWorkplaceMapper;
    private final WorkplaceMapper workplaceMapper;
    private final OrdersInWorkWorkplaceMapper ordersInWorkWorkplaceMapper;
    private final WaitingliststockWaitinglistMapper waitingliststockWaitinglistMapper;

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

        importWaitingliststockWaitinglist(inputDTO);

        importFutureInwardStockmovement(inputDTO);

        importDispositionEigenfertigung();
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
    public void importWaitingliststockWaitinglist(InputDTO inputDTO) {
        List<WaitingliststockWaitinglist> waitinglists = new ArrayList<>();
        inputDTO.getWaitingliststock().stream()
                .forEach(
                        missingPartDTO ->
                                missingPartDTO.getWorkplace()
                                    .forEach( workplaceDTO ->
                                            workplaceDTO.getWaitinglist().forEach(
                                                waitinglistDTO -> waitinglists.add(waitingliststockWaitinglistMapper.toWaitingliststockWaitlinglist(waitinglistDTO))
                                            )
                                    )
                );
        waitingliststockWaitlinglistRepository.saveAllAndFlush(waitinglists);
    }

    @Transactional
    public void importOrdersInWorkWorkplace(InputDTO inputDTO) {
        ordersInWorkWorkplaceRepository.saveAllAndFlush(inputDTO.getOrdersinwork().stream()
                .map(ordersInWorkWorkplaceMapper::toOrdersInWorkWorkplace)
                .toList());
    }

    @Transactional
    public void importDispositionEigenfertigung() {
        List<OrdersInWorkWorkplace> allOrdersInWorkWorkplaces = ordersInWorkWorkplaceRepository.findAll();
        List<DispositionEigenfertigungResult> dispositionEigenfertigungResults = dispositionEigenfertigungRepository.findAll();
        dispositionEigenfertigungResults.forEach(result -> {
            result.setAuftraegeInWarteschlange(dispositionEigenfertigungService.sumUpAuftraegeInWarteschlange(
                    result.getDispositinEigenfertigungResultId().getArticleNumber())
            );
            OrdersInWorkWorkplace ordersInWorkWorkplace = allOrdersInWorkWorkplaces.stream()
                    .filter(workplace -> workplace.getItem() == result.getDispositinEigenfertigungResultId().getArticleNumber())
                    .findFirst()
                    .orElse(null);

            int auftraegeInBearbeitung = 0;

            if (ordersInWorkWorkplace != null) {
                auftraegeInBearbeitung = ordersInWorkWorkplace.getAmount();
            }
            result.setAuftraegeInBearbeitung(auftraegeInBearbeitung);
        });
        dispositionEigenfertigungRepository.saveAll(dispositionEigenfertigungResults);
    }

    @Transactional
    public void importArticles(List<Article> articles) {
        List<DispositionEigenfertigungResult> dispositionEigenfertigungResults = new ArrayList<>();
        articles = articles.stream().map(article -> {
            switch (article.getId()) {
                case 1, 4, 7, 10, 13, 18, 51, 50, 49 -> {
                    article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_1);
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                                    .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                            .articleNumber(article.getId())
                                            .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_1)
                                            .build())
                                    .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                }
                case 2, 5, 8, 11, 14, 19, 65, 55, 54 -> {
                    article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_2);
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                    .articleNumber(article.getId())
                                    .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_2)
                                    .build())
                            .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                }
                case 3, 20, 6, 9, 12, 15, 31, 30, 29 -> {
                    article.setStuecklistenGruppe(StuecklistenGruppe.GRUPPE_3);
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                    .articleNumber(article.getId())
                                    .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_3)
                                    .build())
                            .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                }
                case 26, 16, 17 -> {
                    article.setStuecklistenGruppe(StuecklistenGruppe.ALL);
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                    .articleNumber(article.getId())
                                    .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_1)
                                    .build())
                            .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                    .articleNumber(article.getId())
                                    .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_2)
                                    .build())
                            .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                    dispositionEigenfertigungResults.add(DispositionEigenfertigungResult.builder()
                            .dispositinEigenfertigungResultId(DispositinEigenfertigungResultId.builder()
                                    .articleNumber(article.getId())
                                    .stuecklistenGruppe(StuecklistenGruppe.GRUPPE_3)
                                    .build())
                            .lagerbestandEndeVorperiode(article.getAmount())
                            .build());
                }
                default -> article.setStuecklistenGruppe(null);
            }
            return article;
        }).toList();

        // Feed the stock values for the purchase parts into the purchase-part-disposition table
        List<PurchasePartDisposition> purchasePartDispositions = purchasePartDispositionRepository.findAll();

        for (int i = 0; i < purchasePartDispositions.size(); i++) {
            int itemNumber = purchasePartDispositions.get(i).getItemNumber();

            purchasePartDispositions.get(i).setInitialStock(articles.get(itemNumber-1).getAmount());
        }

        purchasePartDispositionRepository.saveAllAndFlush(purchasePartDispositions);
        articleRepository.saveAllAndFlush(articles);
        dispositionEigenfertigungRepository.saveAll(dispositionEigenfertigungResults);
    }

    @Transactional
    public void importWaitinglist(List<WaitinglistWorkplace> waitinglistWorkplace) {
        waitinglistWorkplaceRepository.saveAllAndFlush(waitinglistWorkplace);
    }

    @Transactional
    public void importFutureInwardStockmovement(InputDTO inputDTO) {
        futureInwardStockmovementRepository.deleteAll();

        List<FutureInwardStockmovement> futureInwardStockmovements = inputDTO
                .getFutureinwardstockmovement()
                .stream()
                .toList();

        futureInwardStockmovementRepository.saveAllAndFlush(futureInwardStockmovements);
    }

}
