package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ArticleDTO {

    private int id;
    private int amount;
    private int startamount;
    private String pct;
    private String price;
    private String stockvalue;

}
