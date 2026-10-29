package org.example.repository;


import org.example.model.Student;
import io.github.cdimascio.dotenv.Dotenv;

import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    Dotenv dotenv = Dotenv.load();

    String url = dotenv.get("DB_URL");
    String username = dotenv.get("DB_USER");
    String password = dotenv.get("DB_PASSWORD");


    // Create User
    public void createStudent(Student student) {
        // 2. Write the SQL query
        String sqlQuery = """
                INSERT INTO students (name, email, age)
                VALUES(?,?,?)
                """;
        // Use try with resources to automatically close the connection
        try (
                // 1. Establish the connection
                Connection conn = DriverManager.getConnection(url, username, password);


                // 3. Create PreparedStatement
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {
            // 4. Pass the parameter using preparedStatement like two-way binding
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());

            // 5. Execute the query using methods like executeUpdate() and executeQuery()
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("Create Operation Successful");
            } else {
                System.out.println("Creation failed");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    // Update User
    public void updateStudent(Student student, Long id) {
        String sqlQuery = """
                UPDATE students
                SET name = ?,
                    email = ?,
                    age = ?
                WHERE id = ?
                """;

        try (
                Connection conn = DriverManager.getConnection(url, username, password);

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(1, student.getEmail());
            preparedStatement.setInt(1, student.getAge());
            preparedStatement.setLong(4, id);

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("Update Operation Successfull");
            } else {
                System.out.println("Updation failed");
            }


        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    // Delete User
    public void deleteStudent(Long id) {
        String sqlQuery = """
                DELETE from students WHERE id = ?
                """;
        try (
                Connection conn = DriverManager.getConnection(url, username, password);

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {

            preparedStatement.setLong(1, id);

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("Delete Operation Successfully");
            } else {
                System.out.println("Deletion failed");
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    // Get User
    public void getStudentById(Long id) {
        String sqlQuery = """
                SELECT name, email,age FROM students
                WHERE id = ?
                """;
        try (
                Connection conn = DriverManager.getConnection(url, username, password);

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = getRow(resultSet);
                    System.out.println(student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    public void getStudent() {
        String sqlQuery = """
                SELECT name, email,age FROM students
                """;
        try (
                Connection conn = DriverManager.getConnection(url, username, password);

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Student> studentList = new ArrayList<>();

                while (resultSet.next()) {
                    Student student = getRow(resultSet);
                    studentList.add(student);
                    System.out.println(student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    // Complete CRUD using the method called execute() Note: not mainly used.
    public void completeCrud() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();

            String sqlQuery = "SELECT id, name, email, age FROM students" +
                    "WHERE id = 2";

            boolean result = statement.execute(sqlQuery);

            if (result) {
                ResultSet resultSet = statement.getResultSet();
            } else {
                int rowAffected = statement.getUpdateCount();
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    private Student getRow(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setAge(resultSet.getInt("age"));

        return student;
    }

}
