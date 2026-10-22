package com.itsc;

import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/StudentsDB";
        String username = "root"; // your username
        String password = "root"; // your password

        try {
            Class.forName(driver); // optional since JDBC 4.0
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Established Connection");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
