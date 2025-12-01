package edu.cs.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL  =
        "jdbc:postgresql://cs1.calstatela.edu:5432/hp422212f2516p";
    private static final String USER = "hp422212f2516p";
    private static final String PASS = "K0Wp4oaqmJbb";   

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
