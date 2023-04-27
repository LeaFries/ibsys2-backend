package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SumDTO {

    private int setupevents;
    private long idletime;
    private String wageidletimecosts;
    private String wagecosts;
    private String machineidletimecosts;

}
