package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.CapacityPlanningResult;
import com.ibsys.backend.core.domain.entity.*;
import com.ibsys.backend.core.repository.*;
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
    private final WorkingTimeRepository overtimeRepository;

    @Transactional
    public void calculateCapacityPlan(List<InputCapacityPlanningDTO> inputCapacityPlanningDTO) {

        capacityPlanColumnRepository.deleteAll();

      for (InputCapacityPlanningDTO cpDTO: inputCapacityPlanningDTO){
         List<ArticleWorkstationPlan> awpList =
                 articleWorkstationPlanRepository.findByArticleNumber(cpDTO.getArticleNumber());
         for (ArticleWorkstationPlan awpItem: awpList) {
             int orderQuantity = cpDTO.getOrderQuantity();
             int workingTime = 0;
             if (orderQuantity >= 0) {
                 workingTime = cpDTO.getOrderQuantity() * awpItem.getWorkingTime();
             }

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
             log.debug("Calculated: " + cp);
         }
      }
    }

    public OutputCapacityPlanning getOutput() {
        OutputCapacityPlanning output = new OutputCapacityPlanning();
        output.setWorkingTimePlan(capacityPlanColumnRepository.findAll());
        output.setCapacityPlanningResult(calculateResult());
        log.debug("build " + output);
       return output;
    }

    public CapacityPlanningResult calculateResult() {

        Map<Integer, Integer> newCapacityReqsMap = new HashMap<>();
        Map<Integer, Integer> newSetUpTimeMap = new HashMap<>();
        Map<Integer, Integer> behindScheduleCapacityMap = new HashMap<>();
        Map<Integer, Integer> behindScheduleSetUpTimeMap = new HashMap<>();
        Map<Integer, Integer> totalCapacityReqsMap = new HashMap<>();
        List<OverTime> workstationWithOvertime = new ArrayList<>();


        for (int i = 1;i<=15;i++) {
            if (i == 5) continue;
            List<CapacityPlanColumn> workstationWorkingsTimes =
                    capacityPlanColumnRepository.findByWorkstationNumber(i);


            int sumNewCapacityReg = getSumWorkingTimeFromWorkstation(workstationWorkingsTimes);
            int sumNewSetUpTime = getSumSetUpTimeFromWorkstation(workstationWorkingsTimes);

            newCapacityReqsMap.put(i, sumNewCapacityReg);
            newSetUpTimeMap.put(i, sumNewSetUpTime);

            //behindScheduleCapacity
            Workplace waitinglistForWorkstation = WorkplaceRepository.findById(i).orElse(null);
            if(waitinglistForWorkstation!=null) {
                behindScheduleCapacityMap.put(i, waitinglistForWorkstation.getTimeneed());
            }else {
                behindScheduleCapacityMap.put(i, 0);
            }

            //filling for building sum
            behindScheduleSetUpTimeMap.put(i, 0);
            totalCapacityReqsMap.put(i, 0);

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

        //totalCapacityReqs
        for (Map.Entry<Integer, Integer> entry: totalCapacityReqsMap.entrySet()) {
            int key = entry.getKey();
            int sum = newCapacityReqsMap.get(key) +
                    newSetUpTimeMap.get(key) +
                    behindScheduleCapacityMap.get(key) +
                    behindScheduleSetUpTimeMap.get(key);
            totalCapacityReqsMap.put(key, totalCapacityReqsMap.get(key) + sum);

            //Overtime
            OverTime overTime = new OverTime();
            int overtime = 0;
            overTime.setStation(key);
            if (sum <= 2400) {
                overTime.setShift(1);
                overTime.setOvertime(overtime);
            } else if (sum <= 3600) {
                overTime.setShift(1);
                overtime = (sum - 2400)/5;
                overTime.setOvertime(overtime);
            } else if (sum <= 4800) {
                overTime.setShift(2);
                overTime.setOvertime(overtime);
            } else if (sum <= 6000) {
                overTime.setShift(2);
                overtime = (sum - 4800)/5;
                overTime.setOvertime(overtime);
            } else {
                overTime.setShift(3);
                overTime.setOvertime(overtime);
            }

            OverTime overTimeFromDB = overtimeRepository.findByStation(key);
            overTimeFromDB.setShift(overTime.getShift());
            overTimeFromDB.setOvertime(overTime.getOvertime());
            overtimeRepository.saveAndFlush(overTimeFromDB);
            //workstationWithOvertime = overtimeRepository.findAll();

        }



        CapacityPlanningResult result = new CapacityPlanningResult();

        //result.setNewCapacity_reqs(newCapacityReqsMap);

        result.setNewCapacity_reqs(newCapacityReqsMap);
        result.setNewSetUpTime(newSetUpTimeMap);
        result.setBehindScheduleCapacity(behindScheduleCapacityMap);
        result.setBehindScheduleSetUpTime(behindScheduleSetUpTimeMap);
        result.setTotalCapacityRequirement(totalCapacityReqsMap);
        result.setWorkstationsWithOverTime(overtimeRepository.findAll());

        log.debug("build " + result);

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
