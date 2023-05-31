package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "article_workstation_plan")
public class ArticleWorkstationPlan {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        Long id;

        private int articleNumber;

        private int workstationNumber;

        private int workingTime;

        private int setUpTime;


        // additional properties
        // standard constructors, getters, and setters
}
