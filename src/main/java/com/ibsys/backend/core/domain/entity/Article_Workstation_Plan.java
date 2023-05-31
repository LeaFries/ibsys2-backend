package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "article_workstation_plan")
public class Article_Workstation_Plan {

        @Id
        Long id;

        @ManyToOne
        @JoinColumn(name = "article_number")
        Article article;

        @ManyToOne
        @JoinColumn(name = "workstation_number")
        Workstation workstation;

        int workingTime;

        int setUpTime;


        // additional properties
        // standard constructors, getters, and setters
}
