package edu.cs.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfessorDAO {

    public static void showFacultyProjects(String profSsn) {

        String sql =
            "SELECT DISTINCT p.pnum, p.sponser, p.startdate, p.enddate, p.budget, role " +
            "FROM ( " +
            "   SELECT p.pnum, p.sponser, p.startdate, p.enddate, p.budget, 'PI' AS role " +
            "   FROM project p " +
            "   WHERE p.pi_ssn = ? " +
            "   UNION " +
            "   SELECT p.pnum, p.sponser, p.startdate, p.enddate, p.budget, 'Co-PI' AS role " +
            "   FROM project p " +
            "   JOIN works_on_co_pis w ON p.pnum = w.pnum " +
            "   WHERE w.professor_ssn = ? " +
            ") AS p " +
            "ORDER BY p.pnum";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profSsn);
            stmt.setString(2, profSsn);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\nProjects for professor SSN = " + profSsn + ":");
            boolean hasAny = false;

            while (rs.next()) {
                hasAny = true;
                int pnum = rs.getInt("pnum");
                String sponsor = rs.getString("sponser");
                java.sql.Date sdate = rs.getDate("startdate");
                java.sql.Date edate = rs.getDate("enddate");
                double budget = rs.getDouble("budget");
                String role = rs.getString("role");

                System.out.println("-------------------------------------");
                System.out.println("Project #: " + pnum);
                System.out.println("Sponsor : " + sponsor);
                System.out.println("Start   : " + sdate);
                System.out.println("End     : " + edate);
                System.out.println("Budget  : " + budget);
                System.out.println("Role    : " + role);
            }

            if (!hasAny) {
                System.out.println("No projects found for this professor.");
            }

        } catch (Exception e) {
            System.out.println("Error while fetching faculty projects:");
            e.printStackTrace();
        }
    }
}
