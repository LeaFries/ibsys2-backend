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

        @Column(name = "article_number")
        private int articleNumber;

        @Column(name = "workstation_number")
        private int workstationNumber;

        @Column(name = "working_time")
        private int workingTime;

        @Column(name = "set_up_time")
        private int setUpTime;


        // additional properties
        // standard constructors, getters, and setters
}
