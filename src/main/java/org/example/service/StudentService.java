package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Student;
import org.example.util.PostgresUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for student management.
 * Contains methods for creating, getting and deleting students.
 */
@Service
@RequiredArgsConstructor
public class StudentService {
    private final PostgresUtil postgresUtil;

    /**
     * Creates a new student in the database.
     * @param student a Student object containing information about the student
     * @throws SQLException if the SQL-query failed
     */
    public void createStudent(Student student) throws SQLException {
        Connection connection = postgresUtil.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = null;

        String sqlQuery = "INSERT INTO students (name, email, \"group\") VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getGroup());

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Exception!!!");

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connection.setAutoCommit(true);
            connection.close();
        }
    }

    /**
     * Returns a list of students grouped by group.
     * @return Map, where the key is the name of the group and the value is the list of students in this group
     * @throws SQLException if the SQL-query failed
     */
    public Map<String, List<Student>> getStudents() throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet preparedResultSet = null;

        String sqlQuery = "SELECT * FROM students ORDER BY \"group\" ASC, name ASC";

        List<Student> students = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedResultSet = preparedStatement.executeQuery();
            while (preparedResultSet.next()) {
                Student student = Student.builder()
                        .id(preparedResultSet.getInt("id"))
                        .name(preparedResultSet.getString("name"))
                        .email(preparedResultSet.getString("email"))
                        .group(preparedResultSet.getString("group"))
                        .build();

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception!!!");

        } finally {
            if (preparedResultSet != null) {
                preparedResultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return students.stream().collect(Collectors.groupingBy(Student::getGroup));
    }

    /**
     * Removes a student from the database by his ID.
     * @param id ID of the student to be deleted
     * @throws SQLException if the SQL-query failed
     */
    public void deleteStudent(int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = null;

        String sqlQuery = "DELETE FROM students WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Exception!!!");

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
