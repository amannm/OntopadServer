/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.openid4java.OpenIDException;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegResponse;

/**
 *
 * @author admin
 */
public class VerificationServlet extends HttpServlet {

    private ConsumerManager manager;
    private static final String verificationURL = "http://www.ontopad.com/identity/verification";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ParameterList parameterList = new ParameterList(request.getParameterMap());
        HttpSession session = request.getSession();
        DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("openid-disc");
        try {
            VerificationResult verification = manager.verify(verificationURL, parameterList, discovered);
            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                session.setAttribute("identity", verified.getIdentifier());
            }
        } catch (MessageException | DiscoveryException | AssociationException ex) {
            Logger.getLogger(VerificationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String destination = (String) session.getAttribute("destination");
        if (destination != null) {
            getServletContext().getRequestDispatcher(destination).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/").forward(request, response);
        }
    }
}
