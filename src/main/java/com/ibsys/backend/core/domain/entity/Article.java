package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany(mappedBy = "article")
    Set<Article_Workstation_Plan> article_workstation_plan;
}
