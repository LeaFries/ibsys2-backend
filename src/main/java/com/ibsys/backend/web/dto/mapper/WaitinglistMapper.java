package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Waitinglist;
import com.ibsys.backend.web.dto.WaitinglistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaitinglistMapper {

    Waitinglist toWaitinglist(WaitinglistDTO waitinglistDTO);

}
