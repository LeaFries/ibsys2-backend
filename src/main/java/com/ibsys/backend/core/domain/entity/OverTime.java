package com.ibsys.backend.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibsys.backend.core.domain.validation.NotEqual;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "working_time")
public class OverTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore()
    private Long id;

    @NotNull
    @NotEqual(value = 5, message = "Working station 5 doesn't exist.")
    //@Size(min = 1, max = 15, message = "Working station must be between 1 and 15.")
    @Min(value = 1)
    @Max(value = 15)
    @Column(unique = true)
    private int station;

    @NotNull
    //@Size(min = 1, max = 3, message = "Shift must be between 1 and 3.")
    @Min(value = 1, message = "Shift must be at least 1.")
    @Max(value = 3, message = "Shift must be between 1 and 3")
    private int shift;

    private int overtime;
}
