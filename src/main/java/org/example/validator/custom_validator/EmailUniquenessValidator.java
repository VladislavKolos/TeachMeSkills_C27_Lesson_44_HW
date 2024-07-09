package org.example.validator.custom_validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.validator.ExistenceStudentValidator;
import org.example.validator.custom_annotation.EmailUniquenessCheck;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Validator for checking the uniqueness of email.
 */
@Component
@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<EmailUniquenessCheck, String> {
    private final ExistenceStudentValidator existenceValidator;

    /**
     * Validator initialization.
     * @param constraintAnnotation the annotation that the validator processes
     */
    @Override
    public void initialize(EmailUniquenessCheck constraintAnnotation) {
    }

    /**
     * Validation logic to check email uniqueness.
     * @param email email field value to be checked
     * @param constraintValidatorContext validator context providing additional data and operations
     * @return true if email is unique; false if email is already in use
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String sqlQuery = "SELECT COUNT(*) FROM students WHERE email = ?";
        boolean exist = false;

        try {
            exist = existenceValidator.userExists(sqlQuery, email);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return !exist;
    }
}
