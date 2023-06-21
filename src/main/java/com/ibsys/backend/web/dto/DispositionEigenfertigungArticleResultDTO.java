package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class DispositionEigenfertigungArticleResultDTO {

    private int articleNumber;
    private int vertriebswunsch;
    private int warteschlange;
    private int geplanterSicherheitsbestand;
    private int lagerbestandEndeVorperiode;
    private int auftraegeInWarteschlange;
    private int auftraegeInBearbeitung;
    private int produktionFuerKommendePeriode;
    private int zusaetzlicheProduktionsauftraege;

}
