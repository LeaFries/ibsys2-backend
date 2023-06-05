package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.CapacityPlanningResult;
import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.core.repository.CapacityPlanColumnRepository;
import com.ibsys.backend.web.dto.InputCapacityPlanningDTO;
import com.ibsys.backend.core.domain.aggregate.OutputCapacityPlanning;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapacityPlanningService {

    private final ArticleWorkstationPlanRepository articleWorkstationPlanRepository;
    private final CapacityPlanColumnRepository capacityPlanColumnRepository;

    @Transactional
    public void calculateCapacityPlan(List<InputCapacityPlanningDTO> inputCapacityPlanningDTO) {

      for (InputCapacityPlanningDTO cpDTO: inputCapacityPlanningDTO){
         List<ArticleWorkstationPlan> awpList =
                 articleWorkstationPlanRepository.findByArticleNumber(cpDTO.getArticleNumber());
         for (ArticleWorkstationPlan awpItem: awpList) {
             int workingTime = cpDTO.getOrderQuantity() * awpItem.getWorkingTime();
             CapacityPlanColumn cp = new CapacityPlanColumn(
                     cpDTO.getArticleNumber(),
                     awpItem.getWorkstationNumber(),
                     workingTime,
                     awpItem.getSetUpTime()
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

        //List<Integer> capacity_reqs = new ArrayList<>();
        Map<Integer, Integer> capacity_reqs = new HashMap<>();

        for (int i = 1;i<=15;i++) {
            if (i == 5) continue;
            List<CapacityPlanColumn> workstationWorkingsTimes =
                    capacityPlanColumnRepository.findByWorkstationNumber(i);

            int sum = getSumWorkingTimeFromWorkstation(workstationWorkingsTimes);

            capacity_reqs.put(i, sum);

            //capacity_reqs.add(sum);

        }
        CapacityPlanningResult result = new CapacityPlanningResult();

        //result.setNewCapacity_reqs(capacity_reqs);

        result.setNewCapacity_reqs(capacity_reqs);


        return result;

    }

    private int getSumWorkingTimeFromWorkstation(List<CapacityPlanColumn> workstationWorkingsTimes) {
        int sum = 0;
        for(CapacityPlanColumn cp: workstationWorkingsTimes) {
            sum+=cp.getWorkingTime();
        }
        return sum;
    }

}
