package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import com.ibsys.backend.web.dto.WaitinglistWorkplaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaitinglistWorkplaceMapper {

    WaitinglistWorkplace toWaitingWorkplacelist(WaitinglistWorkplaceDTO waitinglistWorkplaceDTO);

}
