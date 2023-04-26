package com.ibsys.backend.core.domain.entity;

import com.ibsys.backend.core.domain.status.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QualityControl {
    @Id
    private Long id;

    private Type type;

    private Integer loseQuantity;

    private Integer delay;
}
