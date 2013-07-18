/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ontopad.security.service.EmailService;

/**
 *
 * @author admin
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Login</h2> ");
            out.println("<form action=\"/Ontopad-war/login\" method=\"post\">");
            out.println("<input type=\"text\" name=\"login\" />");
            out.println("<input type=\"text\" name=\"password\" />");
            out.println("<input type=\"submit\" />");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            Principal currentPrincipal = request.getUserPrincipal();
            if (currentPrincipal != null) {
                out.println("You are already logged in as: " + currentPrincipal.getName());
            } else {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                try {
                    request.login(login, password);
                    out.println("Login Successful");
                } catch (ServletException se) {
                    out.println("Login Failed");
                }
            }
            out.println("</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
