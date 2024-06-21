package test;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("remail");
        String password = request.getParameter("renterPass");

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Load the MySQL driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "database");

            // Prepare the SQL statement
            pst = conn.prepareStatement("SELECT * FROM registration WHERE email = ? AND password = ?");
            pst.setString(1, email);
            pst.setString(2, password);

            // Execute the query
            rs = pst.executeQuery();
            response.setContentType("text/html");  
            PrintWriter out = response.getWriter();  
                  

           /* if (rs.next()) {
                // Login successful, redirect to the welcome servlet
                response.sendRedirect("LoginServlet");
            } else {
                // Login failed, show an error message
                PrintWriter out = response.getWriter();
                response.sendRedirect("Get.html");
                out.println("Login failed. Invalid username or password");
            
            }*/
            if(rs.next()) {
            //RequestDispatcher rd=request.getRequestDispatcher("/LoginServlet");  
            //rd.forward(request, response);  
            	response.sendRedirect("LoginServlet");
        }  
        else{  
            out.print("<html><h3><center>Sorry UserName or Password Error!</h3></html>");  
            RequestDispatcher rd=request.getRequestDispatcher("/Get.html");  
            rd.include(request, response);  
           // response.sendRedirect("Get.html");
        } 
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}