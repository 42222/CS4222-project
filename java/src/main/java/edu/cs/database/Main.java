package edu.cs.database;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        System.out.println("Trying to connect…");

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to cs1.calstatela.edu successfully!");
        } catch (Exception e) {
            System.out.println("Failed to connect:");
            e.printStackTrace();
        }
    }
}
