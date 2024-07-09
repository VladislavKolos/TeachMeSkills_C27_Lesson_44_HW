package org.example.validator.custom_annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.custom_validator.EmailUniquenessValidator;

import java.lang.annotation.*;

/**
 * Annotation for checking the uniqueness of email.
 */
@Documented
@Constraint(validatedBy = EmailUniquenessValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUniquenessCheck {
    String message() default "Email is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
