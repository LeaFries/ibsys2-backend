package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.CapacityPlanningResult;
import com.ibsys.backend.core.domain.entity.*;
import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.core.repository.CapacityPlanColumnRepository;
import com.ibsys.backend.core.repository.WaitinglistWorkplaceRepository;
import com.ibsys.backend.core.repository.WorkplaceRepository;
import com.ibsys.backend.web.dto.InputCapacityPlanningDTO;
import com.ibsys.backend.core.domain.aggregate.OutputCapacityPlanning;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapacityPlanningService {

    private final ArticleWorkstationPlanRepository articleWorkstationPlanRepository;
    private final CapacityPlanColumnRepository capacityPlanColumnRepository;
    private final WorkplaceRepository WorkplaceRepository;
    private final WaitinglistWorkplaceRepository waitinglistWorkplaceRepository;

    @Transactional
    public void calculateCapacityPlan(List<InputCapacityPlanningDTO> inputCapacityPlanningDTO) {

      for (InputCapacityPlanningDTO cpDTO: inputCapacityPlanningDTO){
         List<ArticleWorkstationPlan> awpList =
                 articleWorkstationPlanRepository.findByArticleNumber(cpDTO.getArticleNumber());
         for (ArticleWorkstationPlan awpItem: awpList) {
             int workingTime = cpDTO.getOrderQuantity() * awpItem.getWorkingTime();
             int setUpTime = 0;
             if(workingTime!=0) {
                 setUpTime = awpItem.getSetUpTime();
             }

             CapacityPlanColumn cp = new CapacityPlanColumn(
                     cpDTO.getArticleNumber(),
                     awpItem.getWorkstationNumber(),
                     workingTime,
                     setUpTime
                     );
             capacityPlanColumnRepository.saveAndFlush(cp);
             log.debug("Calculate Test Result: " + cp);
         }
      }
    }

    public OutputCapacityPlanning getOutput() {
        OutputCapacityPlanning output = new OutputCapacityPlanning();
        output.setWorkingTimePlan(capacityPlanColumnRepository.findAll());
        output.setCapacityPlanningResult(calculateResult());
       return output;
    }

    public CapacityPlanningResult calculateResult() {

        //List<Integer> capacityReqsMap = new ArrayList<>();
        Map<Integer, Integer> capacityReqsMap = new HashMap<>();
        Map<Integer, Integer> newSetUpTimeMap = new HashMap<>();
        Map<Integer, Integer> behindScheduleCapacityMap = new HashMap<>();
        Map<Integer, Integer> behindScheduleSetUpTimeMap = new HashMap<>();


        for (int i = 1;i<=15;i++) {
            if (i == 5) continue;
            List<CapacityPlanColumn> workstationWorkingsTimes =
                    capacityPlanColumnRepository.findByWorkstationNumber(i);


            int sumNewCapacityReg = getSumWorkingTimeFromWorkstation(workstationWorkingsTimes);
            int sumNewSetUpTime = getSumSetUpTimeFromWorkstation(workstationWorkingsTimes);

            capacityReqsMap.put(i, sumNewCapacityReg);
            newSetUpTimeMap.put(i, sumNewSetUpTime);

            Workplace waitinglistForWorkstation = WorkplaceRepository.findById(i).orElse(null);
            if(waitinglistForWorkstation!=null) {
                behindScheduleCapacityMap.put(i, waitinglistForWorkstation.getTimeneed());
            }else {
                behindScheduleCapacityMap.put(i, 0);
            }

            behindScheduleSetUpTimeMap.put(i, 0);

            //capacityReqsMap.add(sumNewCapacityReg);
        }

        //behindScheduleCapcitySetUpTime
        List<WaitinglistWorkplace> waitinglistWorkplaces = waitinglistWorkplaceRepository.findAll();
        for (WaitinglistWorkplace waitinglistWorkplace: waitinglistWorkplaces) {
            int workstationNumber = waitinglistWorkplace.getWorkplace().getId();
            int articleNumber = waitinglistWorkplace.getItem();
            ArticleWorkstationPlan awpSetUpTime =
                    articleWorkstationPlanRepository.findByWorkstationNumberAndArticleNumber(
                            workstationNumber,
                            articleNumber);

            //sumBehindScheduleSetUpTime +=awpSetUpTime.getSetUpTime();
            behindScheduleSetUpTimeMap.put(workstationNumber,
                    behindScheduleSetUpTimeMap.get(workstationNumber)+awpSetUpTime.getSetUpTime());

        }

        CapacityPlanningResult result = new CapacityPlanningResult();

        //result.setNewCapacity_reqs(capacityReqsMap);

        result.setNewCapacity_reqs(capacityReqsMap);
        result.setNewSetUpTime(newSetUpTimeMap);
        result.setBehindScheduleCapacity(behindScheduleCapacityMap);
        result.setBehindScheduleSetUpTime(behindScheduleSetUpTimeMap);



        return result;

    }

    private int getSumWorkingTimeFromWorkstation(List<CapacityPlanColumn> workstationWorkingsTimes) {
        int sum = 0;
        for(CapacityPlanColumn cp: workstationWorkingsTimes) {
            sum+=cp.getWorkingTime();
        }
        return sum;
    }

    private int getSumSetUpTimeFromWorkstation(List<CapacityPlanColumn> workstationSetUpTimes) {
        int sum = 0;
        for(CapacityPlanColumn cp: workstationSetUpTimes) {
            if(cp.getWorkingTime()!=0)
                sum+=cp.getSetUpTime();
        }
        return sum;
    }

}
