package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
public class SellDirect {
    @Id
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 3, message = "Bike must be between 1 and 3.")
    private Integer article;

    private Integer quantity;

    private Double price;

    private Double penalty;
}
