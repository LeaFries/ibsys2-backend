package com.ibsys.backend.core.service;

import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.web.dto.InputCapacityPlanningDTO;
import com.ibsys.backend.web.dto.OutputCapacityPlanningDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapacityPlanningService {

    private final ArticleWorkstationPlanRepository articleWorkstationPlanRepository;

    @Transactional
    public void calculateCapacityPlan(List<InputCapacityPlanningDTO> inputCapacityPlanningDTO) {

        //articleWorkstationPlanRepository.findBy()

    }

    public OutputCapacityPlanningDTO getOutput() {
        throw new NotYetImplementedException();
    }

}
