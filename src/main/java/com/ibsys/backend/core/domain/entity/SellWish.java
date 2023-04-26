package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SellWish {
    @Id
    private Long id;

    private Integer article;

    private Integer quantity;
}
