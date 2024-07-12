package org.example.validator.custom_validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.util.PostgresUtil;
import org.example.validator.custom_annotation.EmailUniquenessCheck;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Validator for checking the uniqueness of email.
 */
@Component
@RequiredArgsConstructor
public class StudentValidator implements ConstraintValidator<EmailUniquenessCheck, String> {
    private final PostgresUtil postgresUtil;

    /**
     * Validation logic to check email uniqueness.
     *
     * @param email                      email field value to be checked
     * @param constraintValidatorContext validator context providing additional data and operations
     * @return true if email is unique; false if email is already in use
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String sqlQuery = "SELECT COUNT(*) FROM students WHERE email = ?";
        boolean exist = false;

        try {
            exist = userExists(sqlQuery, email);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return !exist;
    }

    public boolean isValidID(int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlQuery = "SELECT COUNT(*) FROM students WHERE id = ?";

        boolean exists = false;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return exists;
    }

    /**
     * Checks the existence of a user with the specified email in the database.
     *
     * @param sqlQuery SQL-query to check the existence of a record
     * @param email    user email to check
     * @return true if a user with the specified email exists; false otherwise
     * @throws SQLException if a SQL-error occurs when executing a query
     */
    private boolean userExists(String sqlQuery, String email) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean exists = false;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return exists;
    }
}
