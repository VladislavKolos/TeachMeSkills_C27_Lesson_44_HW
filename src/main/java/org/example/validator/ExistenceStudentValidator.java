package org.example.validator;

import lombok.RequiredArgsConstructor;
import org.example.util.PostgresUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Validator for checking the existence of a student in the database.
 * Checks whether the student with the specified email exists in the database.
 */
@Component
@RequiredArgsConstructor
public class ExistenceStudentValidator {
    private final PostgresUtil postgresUtil;

    /**
     * Checks the existence of a user with the specified email in the database.
     *
     * @param sqlQuery SQL-query to check the existence of a record
     * @param email    user email to check
     * @return true if a user with the specified email exists; false otherwise
     * @throws SQLException if a SQL-error occurs when executing a query
     */
    public boolean userExists(String sqlQuery, String email) throws SQLException {
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
}
