package com.ibsys.backend.core.domain.entity;

import com.ibsys.backend.core.domain.validation.NotEqual;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Data
public class WorkingTime {
    @Id
    private Long id;

    @NotNull
    @NotEqual(value = 5, message = "Working station 5 doesn't exist.")
    @Size(min = 1, max = 15, message = "Working station must be between 1 and 15.")
    private Integer station;

    @NotNull
    @Size(min = 1, max = 3, message = "Shift must be between 1 and 3.")
    private Integer shift;

    @NotNull
    private Integer overtime;
}
