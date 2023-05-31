package com.ibsys.backend.core.service;

import com.ibsys.backend.core.repository.ArticleWorkstationPlanRepository;
import com.ibsys.backend.web.dto.CapacityPlanningDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapacityPlanningService {

    private final ArticleWorkstationPlanRepository articleWorkstationPlanRepository;

    @Transactional
    public void calculateCapacityPlan(List<CapacityPlanningDTO> capacityPlanningDTO) {

        //articleWorkstationPlanRepository.findBy()

    }

}
