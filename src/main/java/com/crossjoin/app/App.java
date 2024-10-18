package com.crossjoin.app;

import java.sql.*;
import java.time.LocalDateTime;

public class App {
    /*private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/labdb1";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "crossjoin";*/

    private static final String JDBC_URL = System.getenv("JDBC_URL");
    private static final String JDBC_USER = System.getenv("JDBC_USER");
    private static final String JDBC_PASSWORD = System.getenv("JDBC_PASSWORD"); 

    public static void main(String[] args) {
        
        System.out.println("Hello World!");

        try {

            Class.forName("org.postgresql.Driver");

            if (JDBC_URL == null || JDBC_USER == null || JDBC_PASSWORD == null) {
                System.out.println("ERROR: Missing environment variables for database connection. Stopping...");
                return;
            } else {
                System.out.println("Initializing with configurations: ");
                System.out.println("- URL: " + JDBC_URL);
                System.out.println("- USER: " + JDBC_USER);
                System.out.println("- PASSWORD: " + JDBC_PASSWORD);
            }

            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                    + "id serial PRIMARY KEY,"
                    + "name VARCHAR(255))";

            statement.execute(createTableSQL);

            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products");

                while (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println(LocalDateTime. now() + " | Number of entries in table PRODUCTS: " + count);
                }

                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
