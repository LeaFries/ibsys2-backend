package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Workplace;
import com.ibsys.backend.web.dto.WorkplaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {
    Workplace toWorkplace(WorkplaceDTO workplaceDTO);
}
