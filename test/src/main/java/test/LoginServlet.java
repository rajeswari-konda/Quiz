package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String[][] questions = {
        {"Who found java programming?","Guido van Rossum","James Gosling","Dennis Ritchie","Bjarne Stroustrup","2"},
        {"What part is used to compile, debug and execute java programs?","JRE","JIT","JDK","JVM","3"},
        {"Which of the following is the super class of all java classes?","Array List","Abstract class","Object class","String","3"},
        {"Which permits the removal of elements from a collection?", "Enumeration", "Iterator", "Both", "None of above", "2"},
        {"The comparator() interface contains the method?", "compareWith", "compareTo()", "compare()", "comparison()", "3"},
        {"What is the life cycle method of a servlet is called when the servlet is first loaded into memory?","init()","doGet()","doPost()","service","1"},
        {"Which keyword is used for Debugging in java?","Abstract","Insert","Assert","Boolean","3"},
        {"Parent class of all java classes is?","Java.lang.system","Java.lang.object","java.lang.class","java.lang.reflect.object","2"},
        {"Which of the following is NOT a java primitive type?","short","long","long double","boolean","3"},
        {"Which section provides for the implementation of the private server side of the system?","Server","Server Reader","Socket","ServerSocket","4"},
    
        // Add more questions here
    };
    
    private int score = 0;
    private int currentQuestion = 0;  // changed to start at 0

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        
        out.println("<html><head><title>Quiz</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; }");
        out.println(".timer { position: absolute; top: 50px; left: 500px; font-size: 20px; color: blue; font-weight: bold; background-color: yellow; padding: 5px; border: 2px solid blue; border-radius: 5px; }");
        out.println("h1 { text-align: center; }");
       // out.println("form { text-align: center; }");
        out.println("</style>");
        out.println("<script type='text/javascript'>");
        out.println("var timeLeft = 15;");  // 30 seconds timer
        out.println("function countdown() {");
        out.println("    if (timeLeft == 0) {");
        out.println("        document.getElementById('quizForm').submit();");
        out.println("    } else {");
        out.println("        document.getElementById('timer').innerHTML = timeLeft + ' seconds remaining';");
        out.println("        timeLeft--;");
        out.println("        setTimeout(countdown, 1000);");
        out.println("    }");
        out.println("}");
        out.println("window.onload = function() { countdown(); };");
        out.println("</script>");
        out.println("</head><body>");
        
        if (currentQuestion < questions.length) {
            String[] question = questions[currentQuestion];
            out.println("<div class='timer' id='timer'>15 seconds remaining</div>");  // Timer display
            out.println("<h1>Mind Quiz</h1>");
            out.println("<h2>Hi!!Welcome To Our Quiz</h2>");
            out.println("<form id='quizForm' method='post'>");
            out.println("<h3>" + question[0] + "</h3>");
            for (int i = 1; i < question.length - 1; i++) {
                out.println("<input type='radio' name='answer' value='" + i + "'>" + question[i] + "<br>");
            }
            out.println("<input type='hidden' name='action' value='next'>");
            out.println("<input type='submit' value='Next'>");
           // if(currentQuestion>0&&) {
            //	out.println("<input type='submit' name='action' value='Back'>");
            //}
            if(currentQuestion>9) {
            	out.println("<input type='submit' name='action' value='Submit'>");
            }
            out.println("</form>");
        } else {
            out.println("<h1>Quiz Result</h1>");
            out.println("<h2>Your score : " + score + "/" + questions.length + "</h2>");
        }
        
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userAnswer = request.getParameter("answer");
        String action =request.getParameter("action");
        if("next".equals(action)) {
        if (userAnswer != null && userAnswer.equals(questions[currentQuestion][questions[currentQuestion].length - 1])) {
            score++;
        }
        currentQuestion ++;
        }
        else if("Back".equals(action)) {
        currentQuestion--;
        if(currentQuestion<0) {
        	currentQuestion=0;
        }
        }
        doGet(request, response);
    }
}