package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "article")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {

    @Id
    private int id;
    @Column(name = "amount")
    private int amount;
    @Column(name = "startamount")
    private int startamount;
    @Column(name = "pct")
    private String pct;
    @Column(name = "price")
    private String price;
    @Column(name = "stockvalue")
    private String stockvalue;
    @Column(name = "period")
    private int period;
    @Column(name = "geplanter_sicherheitsbestand")
    private int geplanterSicherheitsbestand;
    @Column(name = "stuecklisten_gruppe")
    @Enumerated(EnumType.STRING)
    private StuecklistenGruppe stuecklistenGruppe;
    @Column(name = "warteschlange")
    private int warteschlange;
    @Column(name = "zusaetzliche_produktionsauftraege")
    private int zuesaetlicheProduktionsauftaege;

}