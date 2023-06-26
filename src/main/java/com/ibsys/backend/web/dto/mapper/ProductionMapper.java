package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Production;
import com.ibsys.backend.web.dto.ProductionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductionMapper {

    ProductionDTO toProductionDTO(final Production production);

}
