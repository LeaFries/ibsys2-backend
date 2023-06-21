package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Table(name = "disposition_eigenfertigung_result")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DispositionEigenfertigungResult {

    @EmbeddedId
    DispositinEigenfertigungResultId dispositinEigenfertigungResultId;

    @Column(name = "vertriebswunsch")
    private int vertriebswunsch;

    @Column(name = "warteschlange")
    private int warteschlange;

    @Column(name = "geplanter_sicherheitsbestand")
    private int geplanterSicherheitsbestand;

    @Column(name = "lagerbestand_ende_vorperiode")
    private int lagerbestandEndeVorperiode;

    @Column(name = "auftraege_in_wartschlange")
    private int auftraegeInWarteschlange;

    @Column(name = "auftraege_in_bearbeitung")
    private int auftraegeInBearbeitung;

    @Column(name = "produktion_fuer_kommende_periode")
    private int produktionFuerKommendePeriode;

    @Column(name = "zusaetzliche_produktionsauftraege")
    private int zusaetzlicheProduktionsauftraege;

}
