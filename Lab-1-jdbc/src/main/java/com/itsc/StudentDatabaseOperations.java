package com.itsc;

import java.sql.*;

public class StudentDatabaseOperations {

    static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_URL = "jdbc:mysql://localhost:3306/StudentsDB";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            // Task 1: Connect to MySQL, create database and table
            createDatabase();

            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createTable(connection);

            // Task 2: Insert data
            insertSampleData(connection);

            // Task 3: Retrieve data
            retrieveData(connection);

            // Task 4: Update data
            updateStudentName(connection, 1, "UpdatedFirstName");

            // Task 5: Delete data
            deleteStudent(connection, 2);

            // Task 6: Calculate average grade
            calculateAverageGrade(connection);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE DATABASE IF NOT EXISTS StudentsDB");
        System.out.println("Database 'StudentsDB' is ready.");
        statement.close();
        connection.close();
    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // Drop first so the program can be run again without duplicate-key errors
        statement.executeUpdate("DROP TABLE IF EXISTS students");
        statement.executeUpdate("CREATE TABLE students ("
                + "id INT PRIMARY KEY, "
                + "firstname VARCHAR(255), "
                + "lastname VARCHAR(255), "
                + "grade INT)");
        System.out.println("Table 'students' created.");
        statement.close();
    }

    private static void insertSampleData(Connection connection) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(
                "INSERT INTO students (id, firstname, lastname, grade) VALUES (?, ?, ?, ?)");

        // Insert a single row
        insert.setInt(1, 1);
        insert.setString(2, "John");
        insert.setString(3, "Doe");
        insert.setInt(4, 90);
        insert.executeUpdate();
        System.out.println("Inserted 1 row.");

        // Insert ten more rows
        String[][] students = {
                {"Abebe", "Kebede", "85"},
                {"Aster", "Nega", "92"},
                {"Jemal", "Edris", "78"},
                {"Haile", "Anaol", "88"},
                {"Teddy", "Anbesaw", "73"},
                {"Teklay", "Gebru", "95"},
                {"Selam", "Tesfaye", "81"},
                {"Meron", "Alemu", "67"},
                {"Dawit", "Bekele", "90"},
                {"Hana", "Girma", "84"}
        };
        int id = 2;
        for (String[] s : students) {
            insert.setInt(1, id++);
            insert.setString(2, s[0]);
            insert.setString(3, s[1]);
            insert.setInt(4, Integer.parseInt(s[2]));
            insert.executeUpdate();
        }
        System.out.println("Inserted " + students.length + " more rows.");
        insert.close();
    }

    private static void retrieveData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students LIMIT 5");

        System.out.println("\nFirst five students:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            int grade = resultSet.getInt("grade");
            System.out.println("ID: " + id + ", Name: " + firstname + " " + lastname + ", Grade: " + grade);
        }
        resultSet.close();
        statement.close();
    }

    private static void updateStudentName(Connection connection, int id, String newFirstName) throws SQLException {
        PreparedStatement update = connection.prepareStatement(
                "UPDATE students SET firstname = ? WHERE id = ?");
        update.setString(1, newFirstName);
        update.setInt(2, id);
        int rows = update.executeUpdate();
        System.out.println("\nUpdated " + rows + " row(s): id " + id + " -> firstname = " + newFirstName);
        update.close();
    }

    private static void deleteStudent(Connection connection, int id) throws SQLException {
        PreparedStatement delete = connection.prepareStatement("DELETE FROM students WHERE id = ?");
        delete.setInt(1, id);
        int rows = delete.executeUpdate();
        System.out.println("Deleted " + rows + " row(s) with id " + id);
        delete.close();
    }

    private static void calculateAverageGrade(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT AVG(grade) AS average_grade FROM students");
        if (resultSet.next()) {
            double averageGrade = resultSet.getDouble("average_grade");
            System.out.printf("%nAverage Grade: %.2f%n", averageGrade);
        }
        resultSet.close();
        statement.close();
    }
}
