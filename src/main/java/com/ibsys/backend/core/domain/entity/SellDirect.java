package com.ibsys.backend.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class SellDirect {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore()
    private Long id;

    @NotNull
    @Column(unique = true)
    @Min(value = 1, message = "Bike can be at lowest 1.")
    @Max(value = 3, message = "Bike can be at highest 3")
    private Integer article;

    private Integer quantity;

    private Double price;

    private Double penalty;
}
