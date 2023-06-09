package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.web.dto.ProductionInPeriodDTO;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductionInPeriodMapper {
    ProductionInPeriod toProductionPlan(ProductionInPeriodDTO productionInPeriodDTO);

    List<ProductionInPeriod> toProductionInPeriodList(List<ProductionInPeriodDTO> ProductionInPeriodDTOList);
}
