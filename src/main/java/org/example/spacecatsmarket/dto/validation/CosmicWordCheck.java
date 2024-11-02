package org.example.spacecatsmarket.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWordCheck {
    String COSMIC_WORD_CHECK_SHOULD_BE_VALID = "The product name must contain space words: star, galaxy, comet, planet, nebula and others.";

    String message() default COSMIC_WORD_CHECK_SHOULD_BE_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
