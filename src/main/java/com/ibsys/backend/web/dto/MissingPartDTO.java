package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class MissingPartDTO {

    private int id;
    private List<WaitingliststockWorkplaceDTO> workplace;

}
