/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Home</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=\"/Ontopad-war/login\"><h2>Login</h2></a>");
            out.println("<a href=\"/Ontopad-war/logout\"><h2>Logout</h2></a>");
            out.println("<a href=\"/Ontopad-war/email\"><h2>Email Application</h2></a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
