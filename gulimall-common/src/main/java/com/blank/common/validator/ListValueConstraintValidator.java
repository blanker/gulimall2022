package com.blank.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    int[] values;

    @Override
    public void initialize(ListValue constraintAnnotation) {
        values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return Arrays.stream(values).anyMatch(val -> val == value);
    }
}
