import java.sql.*;
import java.util.Scanner;

//this is the java client application
//it conencts to pstgresql database and lets the user add/remove and tests the triggers
public class UniversityDatabaseClient {

    //connect to database
    private static final String DB_URL = "jdbc:postgresql://cs1.calstatela.edu:5432/hp422212f25285p";
    private static final String USER = "hp422212f2528p";
    private static final String PASS = "eVX4TKgi80oF";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to database!");

            Scanner in = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- University Database Menu ---");
                System.out.println("1. Add a project");
                System.out.println("2. Remove a project");
                System.out.println("3. Display student info (student, advisor, major dept)");
                System.out.println("4. Display projects a faculty works on");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");

                int choice = in.nextInt();
                in.nextLine();

                if (choice == 1) {
                    System.out.print("Enter project number: ");
                    int pnum = in.nextInt();
                    in.nextLine();

                    System.out.print("Enter sponsor: ");
                    String sponser = in.nextLine();

                    addProject(conn, pnum, sponser);

                } else if (choice == 2) {
                // List all projects so the user knows which to remove
                    displayAllProjects(conn);

                    System.out.print("Enter project number to delete: ");
                    int pnum = in.nextInt();
                    removeProject(conn, pnum);
                } else if (choice == 3) {
                    listAllStudents(conn); // show all students
                    System.out.print("Enter student SSN from the list: ");
                    String ssn = in.nextLine();
                    displayStudentInfo(conn, ssn);

                } else if (choice == 4) {
                    listAllFaculty(conn); // show all faculty
                    System.out.print("Enter faculty SSN from the list: ");
                    String ssn = in.nextLine();
                    displayFacultyProjects(conn, ssn);

                } else if (choice == 5) {
                    System.out.println("exited");
                    break;
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    //add a project
    private static void addProject(Connection conn, int pnum, String sponser) throws SQLException {
    String sql =
        "INSERT INTO Project (PNum, Sponser, StartDate, EndDate, Budget, PI_SSN) " +
        "VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '1 year', 100000.00, '111111111')";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, pnum);
        stmt.setString(2, sponser);
        int rows = stmt.executeUpdate();
        
        if (rows > 0) {
             System.out.println("SUCCESS: Project " + pnum + " (" + sponser + ") added.");
        }
    } catch (SQLException e) {
        if (e.getSQLState().startsWith("23")) { 
            System.out.println("Error: Project number " + pnum + " already exists or a foreign key constraint failed.");
        } else {
            System.out.println("Error adding project: " + e.getMessage());
        }
    }
}
    // remove a project
    private static void removeProject(Connection conn, int pnum) throws SQLException {
        String sql = "DELETE FROM Project WHERE PNum = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pnum);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Project removed.");
            } else {
                System.out.println("Project not found.");
            }
        }
    }
    private static void listAllStudents(Connection conn) throws SQLException {
    String sql = "SELECT SSN, Name FROM Student ORDER BY Name";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        System.out.println("\n--- Students ---");
        while (rs.next()) {
            System.out.println("SSN: " + rs.getString("SSN") + " | Name: " + rs.getString("Name"));
        }
    }
    }
    private static void listAllFaculty(Connection conn) throws SQLException {
    String sql = "SELECT SSN, Name FROM Professor ORDER BY Name";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        System.out.println("\n--- Faculty Members ---");
        while (rs.next()) {
            System.out.println("SSN: " + rs.getString("SSN") + " | Name: " + rs.getString("Name"));
        }
    }
    }

// display student information
private static void displayStudentInfo(Connection conn, String ssn) throws SQLException {
    String sql =
        "SELECT S.Name AS studentName, " +
        "Adv.Name AS advisorName, " + 
        "D.DName AS majorDept " +
        "FROM Student S " +
        "LEFT JOIN Student Adv ON S.AdvisorSSN = Adv.SSN " + 
        "JOIN Department D ON S.MajorDeptNum = D.DNum " + 
        "WHERE S.SSN = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, ssn);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("\n--- Student Info ---");
                System.out.println("Student: " + rs.getString("studentName"));
                System.out.println("Advisor: " + rs.getString("advisorName"));
                System.out.println("Major Department: " + rs.getString("majorDept"));
            } else {
                System.out.println("ERROR: Student with SSN " + ssn + " not found or advisor not set.");
            }
        }
    }
}
// display faculty members (shows role copi or pi)
private static void displayFacultyProjects(Connection conn, String ssn) throws SQLException {
    String sql =
        "SELECT PNum, Sponser, Budget, 'PI' AS role " +
        "FROM Project " +
        "WHERE PI_SSN = ? " +
        "UNION " +
        "SELECT P2.PNum, P2.Sponser, P2.Budget, 'Co-PI' AS role " +
        "FROM works_on_co_pis W " +
        "JOIN Project P2 ON W.PNum = P2.PNum " +
        "WHERE W.Professor_SSN = ? " +
        "ORDER BY PNum";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, ssn);
        stmt.setString(2, ssn);

        try (ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nProjects for faculty SSN " + ssn + ":");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf(
                    "Project %d | Sponsor: %s | Budget: $%.2f | Role: %s\n",
                    rs.getInt("PNum"),
                    rs.getString("Sponser"),
                    rs.getDouble("Budget"),
                    rs.getString("role")
                );
            }

            if (!found) {
                System.out.println("No projects found for this faculty member");
            }
        }
    }
}
// displays all projects so user can know which to delete
private static void displayAllProjects(Connection conn) throws SQLException {
    String sql = "SELECT PNum, Sponser, Budget, PI_SSN FROM Project ORDER BY PNum";

    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        System.out.println("\n.    Current Projects      ");
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s\n", "PNum", "Sponsor", "Budget", "PI SSN");
        System.out.println("-----------------------------------------------------------------");
        
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf(
                "%-10d | %-20s | $%-9.2f | %-10s\n", 
                rs.getInt("PNum"), 
                rs.getString("Sponser"), 
                rs.getDouble("Budget"), 
                rs.getString("PI_SSN")
            );
        }
        System.out.println("-----------------------------------------------------------------");
        if (!found) {
            System.out.println("No projects found in the database.");
        }
    }
}

}
