package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "lagerbestand_vorperiode")
    private int lagerbestandVorperiode;

}
