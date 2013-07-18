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
@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Logout Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            try {
                request.logout();
                out.println("Logout Successful");
            } catch (ServletException se) {
                out.println("Logout failed");
            }
            out.println("</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
