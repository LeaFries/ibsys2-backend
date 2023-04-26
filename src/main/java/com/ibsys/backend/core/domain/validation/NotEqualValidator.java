package com.ibsys.backend.core.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEqualValidator implements ConstraintValidator<NotEqual, Integer> {
    private int valueToExclude;

    public void initialize(NotEqual constraint) {
        this.valueToExclude = constraint.value();
    }

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value != valueToExclude;
    }
}
