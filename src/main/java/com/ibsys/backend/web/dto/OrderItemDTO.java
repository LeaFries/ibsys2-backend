package com.ibsys.backend.web.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderItemDTO {
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
    private Integer modus;
}
