package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.FutureInwardStockmovement;
import com.ibsys.backend.core.domain.entity.KQuantityNeed;
import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import com.ibsys.backend.core.domain.entity.PurchasePartDisposition;
import com.ibsys.backend.core.domain.status.OrderColor;
import com.ibsys.backend.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchasePartDispositionService {
    private final PurchasePartDispositionRepository purchasePartDispositionRepository;
    private final ProductionInPeriodRepository productionInPeriodRepository;
    private final KQuantityNeedRepository kQuantityNeedRepository;
    private final ForecastRepository forecastRepository;
    private final FutureInwardStockmovementRepository futureInwardStockmovementRepository;

    public List<PurchasePartDisposition> findPurchasePartDisposition() {

        List<KQuantityNeed> kQuantityNeeds = kQuantityNeedRepository.findAll();
        List<ProductionInPeriod> productionInPeriods= productionInPeriodRepository.findAll();

        for(int i = 0; i < kQuantityNeeds.size(); i++) {

            for (int j = 0; j < 4; j++) {
                PurchasePartDisposition temp = purchasePartDispositionRepository.findById((long)(i+1)).get();
                if (j == 0) {
                    temp.setRequirementN(
                            kQuantityNeeds.get(i).getP1() * productionInPeriods.get(0).getPeriodN() +
                            kQuantityNeeds.get(i).getP2() * productionInPeriods.get(1).getPeriodN() +
                            kQuantityNeeds.get(i).getP3() * productionInPeriods.get(2).getPeriodN());
                }
                else if (j == 1) {
                    temp.setRequirementNplusOne(
                            kQuantityNeeds.get(i).getP1() * productionInPeriods.get(0).getPeriodNplusOne() +
                            kQuantityNeeds.get(i).getP2() * productionInPeriods.get(1).getPeriodNplusOne() +
                            kQuantityNeeds.get(i).getP3() * productionInPeriods.get(2).getPeriodNplusOne());
                }
                else if (j == 2) {
                    temp.setRequirementNplusTwo(
                            kQuantityNeeds.get(i).getP1() * productionInPeriods.get(0).getPeriodNplusTwo() +
                            kQuantityNeeds.get(i).getP2() * productionInPeriods.get(1).getPeriodNplusTwo() +
                            kQuantityNeeds.get(i).getP3() * productionInPeriods.get(2).getPeriodNplusTwo());
                }
                else if (j == 3) {
                    temp.setRequirementNplusThree(
                            kQuantityNeeds.get(i).getP1() * productionInPeriods.get(0).getPeriodNplusThree() +
                            kQuantityNeeds.get(i).getP2() * productionInPeriods.get(1).getPeriodNplusThree() +
                            kQuantityNeeds.get(i).getP3() * productionInPeriods.get(2).getPeriodNplusThree());
                }

                purchasePartDispositionRepository.saveAndFlush(temp);
            }
        }

        List<PurchasePartDisposition> purchasePartDispositions = purchasePartDispositionRepository.findAll();

        determineOrderNecessity(purchasePartDispositions);

        return purchasePartDispositions;
    }

    private void determineOrderNecessity(List<PurchasePartDisposition> purchasePartDispositions) {
        double lastingPeriod = 0;

        int currentPeriod = forecastRepository.findById(1L).get().getPeriod();

        int periodN = currentPeriod + 1;
        int periodNplusOne = currentPeriod + 2;
        int periodNplusTwo = currentPeriod + 3;
        int periodNplusThree = currentPeriod + 4;

        for(PurchasePartDisposition ppD : purchasePartDispositions) {

            Optional<FutureInwardStockmovement> futureInwardStockmovement = futureInwardStockmovementRepository
                    .findFutureInwardStockmovementByArticle(ppD.getItemNumber());

            // placeholder value
            int truncatedPeriodArrival = -1;

            int incomingAmountN = 0;
            int incomingAmountNplusOne = 0;
            int incomingAmountNplusTwo = 0;
            int incomingAmountNplusThree = 0;

            // If a future inwards stockmovement exists for that specific article
            if (!futureInwardStockmovement.isEmpty()) {
                FutureInwardStockmovement incomingOrder = futureInwardStockmovement.get();

                double alreadyElapsedDeliveryTime = incomingOrder.getOrderperiod() - currentPeriod;
                double remainingDeliveryTime = 0.0;

                // Check if the order mode is normal or fast
                if (incomingOrder.getMode() == 5) {
                    remainingDeliveryTime = ppD.getDeliveryTimeWithDeviation() + alreadyElapsedDeliveryTime;
                }
                else if (incomingOrder.getMode() == 4) {
                    remainingDeliveryTime = ppD.getDeliveryTimeFast() + alreadyElapsedDeliveryTime;
                }
                else if (incomingOrder.getMode() == 3) {
                    remainingDeliveryTime = ppD.getDeliveryTimeJITwithDeviation() + alreadyElapsedDeliveryTime;
                }

                double periodArrival = currentPeriod + remainingDeliveryTime;

                ppD.setFuturePeriodArrival(periodArrival);
                ppD.setFuturePeriodAmount(incomingOrder.getAmount());

                truncatedPeriodArrival = (int)Math.floor(periodArrival);

                if (periodN == truncatedPeriodArrival) {
                    incomingAmountN = incomingOrder.getAmount();
                }
                else if (periodNplusOne == truncatedPeriodArrival) {
                    incomingAmountNplusOne = incomingOrder.getAmount();
                }
                else if (periodNplusTwo == truncatedPeriodArrival) {
                    incomingAmountNplusTwo = incomingOrder.getAmount();
                }
                else if (periodNplusThree == truncatedPeriodArrival) {
                    incomingAmountNplusThree = incomingOrder.getAmount();
                }
            }

            if ((ppD.getInitialStock() + incomingAmountN - ppD.getRequirementN() > 0)) {
                lastingPeriod++;
                if ((ppD.getInitialStock() + incomingAmountNplusOne - ppD.getRequirementN() - ppD.getRequirementNplusOne() > 0)) {
                    lastingPeriod++;
                    if((ppD.getInitialStock() + incomingAmountNplusTwo
                            - ppD.getRequirementN()
                            - ppD.getRequirementNplusOne()
                            - ppD.getRequirementNplusTwo() > 0)) {
                        lastingPeriod++;
                        if((ppD.getInitialStock() + incomingAmountNplusThree
                                - ppD.getRequirementN()
                                - ppD.getRequirementNplusOne()
                                - ppD.getRequirementNplusTwo()
                                - ppD.getRequirementNplusThree() > 0)) {
                            lastingPeriod++;
                        }
                    }
                }
            }

            if(lastingPeriod - ppD.getDeliveryTimeWithDeviation() >= 1) {
                ppD.setOrderColor(OrderColor.green);
            }
            else if (lastingPeriod - ppD.getDeliveryTimeWithDeviation() <= -1) {
                ppD.setOrderQuantity(ppD.getDiscountQuantity());
                ppD.setOrderType(4);
                ppD.setOrderColor(OrderColor.red);
            }
            else if (lastingPeriod - ppD.getDeliveryTimeWithDeviation() > -1
                    && lastingPeriod - ppD.getDeliveryTimeWithDeviation() <= 0) {
                ppD.setOrderQuantity(ppD.getDiscountQuantity());
                ppD.setOrderType(5);
                ppD.setOrderColor(OrderColor.yellow);
            }
            else {
                ppD.setOrderQuantity(ppD.getDiscountQuantity());
                ppD.setOrderColor(OrderColor.yellow);
            }

            lastingPeriod = 0;
        }

        purchasePartDispositionRepository.saveAllAndFlush(purchasePartDispositions);
    }

}
