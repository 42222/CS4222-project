package edu.cs.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ProjectDAO {

    public static void addProject(int pnum, String sponsor,
                                  Date startDate, Date endDate,
                                  double budget, String piSsn) throws Exception {
        String sql = "INSERT INTO project (pnum, sponser, startdate, enddate, budget, pi_ssn) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pnum);
            ps.setString(2, sponsor);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.setDouble(5, budget);
            ps.setString(6, piSsn);

            int rows = ps.executeUpdate();
            System.out.println("Inserted " + rows + " project(s).");
        }
    }

    public static void removeProject(int pnum) throws Exception {
        String sql = "DELETE FROM project WHERE pnum = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pnum);
            int rows = ps.executeUpdate();
            System.out.println("Deleted " + rows + " project(s).");
        }
    }
}
