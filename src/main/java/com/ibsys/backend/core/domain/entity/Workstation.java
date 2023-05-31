package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "workstation")
public class Workstation {

    @Id
    private int number;

    @OneToMany(mappedBy = "workstation")
    Set<Article_Workstation_Plan> article_workstation_plan;
}
