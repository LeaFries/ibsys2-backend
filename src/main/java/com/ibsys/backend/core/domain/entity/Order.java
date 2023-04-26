package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity(name = "orderitem")
@Data
public class Order {
    @Id
    private Long id;

    private Integer article;

    @Positive
    private Integer quantity;

    private Integer modus;
}
