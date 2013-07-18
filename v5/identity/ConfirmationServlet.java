/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.ontopad.exception.AuthenticationServiceException;
import com.ontopad.exception.LoginException;
import com.ontopad.service.EmailService;
import com.ontopad.service.HTMLService;
import com.ontopad.web.Attributes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.MessagingException;
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
public class ConfirmationServlet extends HttpServlet {

    @Inject
    private EmailService emailService;
    @Inject
    private AuthenticationService authenticationService;
    private static final Logger log = Logger.getLogger(ConfirmationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Authorization: Identity in group 1L with GET permission
        HttpSession httpSession = request.getSession();
        Identity identity = (Identity) httpSession.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        if (identity.getGroupIDs().contains(1L)) {
            //in group of unconfirmed identities
            if (identity.getConfirmationCode() != null) {
                //unconfirmed identity with outstanding confirmation over 24h of age 
                response.setStatus(404);
            } else {
                //unconfirmed identity with outstanding confirmation less than 24h of age
                response.setStatus(204);
            }
        } else {
            //confirmed identity browsing their identity information
            response.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //receive POST data of confirmation codes
        //Precondition: only identities in group 1L make it through the authorization filter
        String confirmationCode = request.getParameter("code");
        if (confirmationCode != null) {
            try {
                authenticationService.activate(confirmationCode);
                log.info("[POST /identity/confirmation]: Confirmation Success.");
            } catch (AuthenticationServiceException ex) {
                Logger.getLogger(ConfirmationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Identity identity = (Identity) httpSession.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        if (identity != null) {
            if (identity.getGroupIDs().contains(1L)) {
                identity.getNominalGroupID();
            }
        }
        response.setStatus(202);
    }
}
