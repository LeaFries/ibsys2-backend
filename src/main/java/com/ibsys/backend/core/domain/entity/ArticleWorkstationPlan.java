package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "article_workstation_plan")
public class ArticleWorkstationPlan {

        @Id
        Long id;

        private int articleNumber;

        private int workstationNumber;

        private int workingTime;

        private int setUpTime;


        // additional properties
        // standard constructors, getters, and setters
}
