package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.WaitingliststockWaitinglist;
import com.ibsys.backend.web.dto.WaitingliststockWaitinglistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaitingliststockWaitinglistMapper {

    WaitingliststockWaitinglist toWaitingliststockWaitlinglist(final WaitingliststockWaitinglistDTO waitinglistDTO);

}
