package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "orderitem")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer article;

    @Positive
    private Integer quantity;

    /**
    1 = special order
    2 = cheap vendor
    3 = Just In Time
    4 = fast
    5 = normal
     */
    @Size(min = 1, max = 5, message = "Please choose an order type between 1 and 5.")
    private Integer modus;
}
