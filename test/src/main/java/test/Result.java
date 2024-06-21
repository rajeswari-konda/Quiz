package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Result")
public class Result extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
    private String jdbcUsername = "system";
    private String jdbcPassword = "database";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        int score = Integer.parseInt(request.getParameter("score"));

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","database");

            // Prepare SQL statement
            String sql = "INSERT INTO quiz_score (email, score) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2, score);

            // Execute the insert
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                out.println("<h1>Score recorded successfully!</h1>");
            } else {
                out.println("<h1>Error recording score.</h1>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>Database driver not found.</h1>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Database error.</h1>");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}