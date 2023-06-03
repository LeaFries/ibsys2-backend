package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.core.repository.CapacityPlanColumnRepository;
import com.ibsys.backend.web.dto.InputCapacityPlanningDTO;
import com.ibsys.backend.core.domain.aggregate.OutputCapacityPlanning;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ibsys.backend.core.domain.aggregate.OutputCapacityPlanning;

import java.util.List;

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
             log.debug("Calculate Test Result: " + String.valueOf(cp));
         }



      }


    }

    public String getOutput() {
       return "it works";
    }

}
