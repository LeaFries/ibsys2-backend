package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class WarehousestockDTO {

    private List<ArticleDTO> articleDTOS;
    private String totalstockvalue;

}