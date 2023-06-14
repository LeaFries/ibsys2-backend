package com.ibsys.backend.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore()
    private Long id;

    private Type type;

    private Integer loseQuantity;

    private Integer delay;
}
