package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.KQuantityNeed;
import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import com.ibsys.backend.core.domain.entity.PurchasePartDisposition;
import com.ibsys.backend.core.domain.status.OrderColor;
import com.ibsys.backend.core.repository.KQuantityNeedRepository;
import com.ibsys.backend.core.repository.ProductionInPeriodRepository;
import com.ibsys.backend.core.repository.PurchasePartDispositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchasePartDispositionService {
    private final PurchasePartDispositionRepository purchasePartDispositionRepository;
    private final ProductionInPeriodRepository productionInPeriodRepository;
    private final KQuantityNeedRepository kQuantityNeedRepository;

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

        for(PurchasePartDisposition ppD : purchasePartDispositions) {
            if ((ppD.getInitialStock() - ppD.getRequirementN() > 0)) {
                lastingPeriod++;
                if ((ppD.getInitialStock() - ppD.getRequirementN() - ppD.getRequirementNplusOne() > 0)) {
                    lastingPeriod++;
                    if((ppD.getInitialStock()
                            - ppD.getRequirementN()
                            - ppD.getRequirementNplusOne()
                            - ppD.getRequirementNplusTwo() > 0)) {
                        lastingPeriod++;
                        if((ppD.getInitialStock()
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
            else {
                ppD.setOrderQuantity(ppD.getDiscountQuantity());
                ppD.setOrderType(5);
                ppD.setOrderColor(OrderColor.yellow);
            }

            lastingPeriod = 0;
        }

        purchasePartDispositionRepository.saveAllAndFlush(purchasePartDispositions);
    }

}
