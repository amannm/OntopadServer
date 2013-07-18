/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.AccessLocalException;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ontopad.security.service.EmailService;

/**
 *
 * @author admin
 */
@WebServlet(urlPatterns = {"/email"})
public class EmailTestServlet extends HttpServlet {

    @EJB
    private EmailService emailService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mailing Application</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Send Email</h2> ");
            out.println("<form action=\"/Ontopad-war/email\" method=\"post\">");
            out.println("<input type=\"text\" name=\"address\" />");
            out.println("<input type=\"text\" name=\"subject\" />");
            out.println("<input type=\"text\" name=\"body\" />");
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
            out.println("<title>Mailing Application</title>");
            out.println("</head>");
            out.println("<body>");
            String address = request.getParameter("address");
            String subject = request.getParameter("subject");
            String body = request.getParameter("body");
            try {
                emailService.send(address, subject, body);
                out.println("<h1>Email sent successfully</h1>");
            } catch (MessagingException ex) {
                out.println("<h1>Error while trying to send email</h1>");
            } catch (EJBAccessException ex) {
                out.println("<h1>You aren't authorized to use this service</h1>");
            } catch (EJBException ex) {
                out.println("<h1>Unknown error</h1>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
}
