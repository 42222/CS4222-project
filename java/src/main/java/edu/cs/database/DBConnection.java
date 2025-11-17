package edu.cs.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:postgresql://cs1.calstatela.edu:5432/YOUR_DB_NAME";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASS = "YOUR_PASSWORD";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
