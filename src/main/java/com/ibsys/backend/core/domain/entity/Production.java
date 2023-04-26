package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Production {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer article;

    private Integer quantity;
}
