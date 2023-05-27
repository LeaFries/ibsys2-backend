package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Waitinglist;
import com.ibsys.backend.web.dto.WaitinglistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WaitinglistMapper {

    @Mapping(source = "period", target = "period")
    Waitinglist toWaitinglist(WaitinglistDTO waitinglistDTO, Integer period);

}
