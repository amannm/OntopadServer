/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.ontopad.exception.AuthenticationServiceException;
import com.ontopad.service.HTMLService;
import com.ontopad.service.JSONService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class RegistrationServlet extends HttpServlet {

    @Inject
    private AuthenticationService authenticationService;
    private static final Logger log = Logger.getLogger(RegistrationServlet.class.getName());
    @Inject
    private JSONService jsonService;

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            log.info("Generating Registration Form");
            HTMLService.printPlainHeader(out, "Registration");
            HTMLService.printRegistrationForm(out);
            HTMLService.printPlainFooter(out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String emailAddress = request.getParameter("email");
        String password = request.getParameter("password");

        String result;
        try {
            authenticationService.register(emailAddress, password);
            log.info("Registration successful: activation email sent");
            response.setStatus(202);
            //result = HTMLUtil.getMessageBox("Registration Success", "An email has been sent to the email address you provided during registration. Please click on the link inside this email in order to fully activate your membership. Thanks.");
        } catch (AuthenticationServiceException ex) {
            log.log(Level.WARNING, "Registration failed: {0}", ex.getMessage());
            response.setStatus(409);
            //result = HTMLUtil.getMessageBox("Registration Failed", "You can't register with those details");

        }
    }
}
