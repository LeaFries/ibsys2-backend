package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "workstation")
public class Workstation {

    @Id
    private int number;

    @OneToMany(mappedBy = "workstation")
    Set<ArticleWorkstationPlan> article_workstation_plan;
}
