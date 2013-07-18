/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.ontopad.exception.LoginException;
import com.ontopad.service.HTMLService;
import com.ontopad.web.Attributes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 *
 * /identity/{confirmation} i can PUT an identity confirming singleton to this
 * location, returns 202 processing 1. send a confirmation email (reads from
 * registration data) 2. attach a timer to the partial request subsequent GETs
 * to this location while unprocessed/unconfirmed will continue to give 204 No
 * content until 1. after 24 hours the queued request is destroyed and the
 * location starts returning 404 again 2. an activation code is POSTed before 24
 * hours has elapsed, causing the timer to be removed the singleton then becomes
 * a permanent resource at that location, returning 201 after the activating
 * POST and 200 for subsequent requests forever after that
 *
 */
public class IdentityServlet extends HttpServlet {

    
    @Inject
    private AuthenticationService authenticationService;
    private static final Logger log = Logger.getLogger(IdentityServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get all information, or summary of your identity
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        Identity identity = (Identity) httpSession.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        //Generate an HTML response body
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (identity.getNominalGroupID() == 0L) {
                //Login page allowing those without identities to POST credentials here
                HTMLService.printPlainHeader(out, "Login");
                HTMLService.printLoginForm(out);
                HTMLService.printPlainFooter(out);
            } else {
                //Show personal page containing information about this identity and various functions
                HTMLService.printPlainHeader(out, "Logged in user page");
                HTMLService.printButton(out, "Logout");
                HTMLService.printPlainFooter(out);
            }
        }
        response.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //receive all data that adds/modifies the identity data. includes session logins and registrations
        String emailAddress = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            String token = authenticationService.login(emailAddress, password);
            Cookie cookie = new Cookie(Attributes.TOKEN_COOKIE_NAME, token);
            cookie.setMaxAge(Attributes.TOKEN_COOKIE_LIFETIME);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            Identity identity = authenticationService.authenticate(token);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(Attributes.IDENTITY_ATTRIBUTE, identity);
            log.info("[POST /identity]: Login Success. Redirecting to /");
            response.sendRedirect("/");
        } catch (LoginException | IOException ex) {
            log.info("[POST /identity]: Login Failure. Refreshing page");
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Logout by invalidating the HttpSession and overwriting the token cookie
        request.getSession().invalidate();
        Cookie cookie = new Cookie("ontopad_token", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.info("[DELETE /identity]: Deleting the identity session.");
        response.setStatus(200);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
