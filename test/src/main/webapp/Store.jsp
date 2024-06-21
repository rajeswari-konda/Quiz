<%@ page import="java.sql.*"%>
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="java.io.PrintWriter" %>
<%
String name=request.getParameter("rname");
String email=request.getParameter("remail");
String gender=request.getParameter("rgender");
String pass=request.getParameter("renterPass");
String pass1=request.getParameter("rconfirmPass");
response.setContentType("text/html");  

PrintWriter out1 = response.getWriter();  
if(pass.equals(pass1)){
try{
	Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","database");
	PreparedStatement ps=conn.prepareStatement("insert into registration  values(?,?,?,?)");
	ps.setString(1,name);
	ps.setString(2,email);
	ps.setString(3,gender);
	ps.setString(4,pass);
	int x=ps.executeUpdate();
	
	  //response.setContentType("text/html");  
      //PrintWriter out1 = response.getWriter();  
	if(x!=0){
		out1.print("<html><h3><center>Signup Done Successfully!</h3></html>");
		RequestDispatcher rd=request.getRequestDispatcher("/Reg 2.html");  
        rd.include(request, response);  
	}else{
		out1.print("Something went Wrong...."); 
		// RequestDispatcher rd=request.getRequestDispatcher("/Reg 2.html");  
	      //  rd.include(request, response);
	}
}
	catch(Exception e){
		//out.print("Check the Email Once again");
		out1.print("<html><h3><center>Check the Email Once Again!</h3></html>");  
        RequestDispatcher rd=request.getRequestDispatcher("/Reg 2.html");  
        rd.include(request, response);
	}
}
else{
	out1.print("<html><h3><center>Password not matching</h3></html>");
	 RequestDispatcher rd=request.getRequestDispatcher("/Reg 2.html");  
     rd.include(request, response);

}

%>

