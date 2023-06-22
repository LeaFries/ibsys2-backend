package com.ibsys.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SumDTO {

    private int setupevents;
    private long idletime;
    private String wageidletimecosts;
    private String wagecosts;
    private String machineidletimecosts;

}
