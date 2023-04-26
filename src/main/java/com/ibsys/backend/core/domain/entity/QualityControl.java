package com.ibsys.backend.core.domain.entity;

import com.ibsys.backend.core.domain.status.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class QualityControl {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Type type;

    private Integer loseQuantity;

    private Integer delay;
}
