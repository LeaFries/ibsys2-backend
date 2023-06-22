package com.ibsys.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DispositionEigenfertigungInputDTO {
    private Map<Integer,Integer> geplanterSicherheitsbestand;
    private Map<Integer, Integer> zuesaetzlicheProduktionsauftraege;
}
