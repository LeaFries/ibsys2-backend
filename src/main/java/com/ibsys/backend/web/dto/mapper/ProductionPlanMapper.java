package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductionPlanMapper {
    ProductionPlan toProductionPlan(ProductionPlanDTO productionPlanDTO);

    List<ProductionPlan> toProductionPlanList(List<ProductionPlanDTO> productionPlanDTOList);
}
