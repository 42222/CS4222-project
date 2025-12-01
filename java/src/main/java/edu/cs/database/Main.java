package edu.cs.database;

import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Trying to connect...");

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to cs1.calstatela.edu successfully!");

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== UNIVERSITY DB MENU =====");
                System.out.println("1. Add Project");
                System.out.println("2. Remove Project");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine(); 
                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (choice == 1) {
                    System.out.print("Enter Project Number: ");
                    int pnum = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Sponsor Name: ");
                    String sponsor = sc.nextLine();

                    System.out.print("Start Date (YYYY-MM-DD): ");
                    String sdate = sc.nextLine();

                    System.out.print("End Date (YYYY-MM-DD): ");
                    String edate = sc.nextLine();

                    System.out.print("Budget: ");
                    double budget = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("PI SSN: ");
                    String pi = sc.nextLine();

                    ProjectDAO.addProject(
                            pnum, sponsor,
                            Date.valueOf(sdate),
                            Date.valueOf(edate),
                            budget, pi
                    );
                }

                else if (choice == 2) {
                    System.out.print("Enter Project Number to delete: ");
                    int pnum = sc.nextInt();
                    ProjectDAO.removeProject(pnum);
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Failed to connect or run command:");
            e.printStackTrace();
        }
    }
}
