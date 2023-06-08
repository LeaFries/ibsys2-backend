package com.ibsys.backend.core.service;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.ibsys.backend.core.domain.aggregate.CapacityPlanningResult;
import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import com.ibsys.backend.core.domain.entity.Waitinglist;
import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.core.repository.CapacityPlanColumnRepository;
import com.ibsys.backend.core.repository.WaitinglistRepository;
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
    private final WaitinglistRepository waitinglistRepository;

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


        for (int i = 1;i<=15;i++) {
            if (i == 5) continue;
            List<CapacityPlanColumn> workstationWorkingsTimes =
                    capacityPlanColumnRepository.findByWorkstationNumber(i);

            Optional<Waitinglist> waitinglist = waitinglistRepository.findById(i);

            int sumNewCapacityReg = getSumWorkingTimeFromWorkstation(workstationWorkingsTimes);
            int sumNewSetUpTime = getSumSetUpTimeFromWorkstation(workstationWorkingsTimes);

            capacityReqsMap.put(i, sumNewCapacityReg);
            newSetUpTimeMap.put(i, sumNewSetUpTime);
            if (waitinglist.isPresent()) {
                behindScheduleCapacityMap.put(i, waitinglist.get().getTimeneed());
            }else {
                log.debug("waitinglist was not present");
            }


            //capacityReqsMap.add(sumNewCapacityReg);

        }
        CapacityPlanningResult result = new CapacityPlanningResult();

        //result.setNewCapacity_reqs(capacityReqsMap);

        result.setNewCapacity_reqs(capacityReqsMap);
        result.setNewSetUpTime(newSetUpTimeMap);
        result.setBehindScheduleCapacity(behindScheduleCapacityMap);


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
            sum+=cp.getSetUpTime();
        }
        return sum;
    }

}
