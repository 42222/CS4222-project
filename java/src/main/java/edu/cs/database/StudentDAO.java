package edu.cs.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    public static void showStudentInfo(String ssn) {
        String sql =
            "SELECT s.ssn, s.name, s.age, s.gender, s.degreeprogram, " +
            "       d.dname AS major_dept, " +
            "       adv.name AS advisor_name " +
            "FROM student s " +
            "LEFT JOIN department d ON s.majordeptnum = d.dnum " +
            "LEFT JOIN student adv ON s.advisorssn = adv.ssn " +
            "WHERE s.ssn = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ssn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("===== Student Info =====");
                    System.out.println("SSN:       " + rs.getString("ssn"));
                    System.out.println("Name:      " + rs.getString("name"));
                    System.out.println("Age:       " + rs.getInt("age"));
                    System.out.println("Gender:    " + rs.getString("gender"));
                    System.out.println("Program:   " + rs.getString("degreeprogram"));
                    System.out.println("Major Dept:" + rs.getString("major_dept"));

                    String advisorName = rs.getString("advisor_name");
                    if (advisorName != null) {
                        System.out.println("Advisor:   " + advisorName);
                    } else {
                        System.out.println("Advisor:   (no advisor assigned)");
                    }
                    System.out.println("========================");
                } else {
                    System.out.println("No student found with SSN: " + ssn);
                }
            }

        } catch (Exception e) {
            System.out.println("Error while fetching student info.");
            e.printStackTrace();
        }
    }
}
