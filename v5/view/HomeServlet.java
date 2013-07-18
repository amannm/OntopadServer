/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.view;

import com.ontopad.service.HTMLService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ontopad.identity.Identity;
import com.ontopad.web.Attributes;

/**
 *
 * @author admin
 */
public class HomeServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(HomeServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Identity id = (Identity) session.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        log.log(Level.INFO, "Home browsing identity: {0}", id.getNominalGroupID());

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HTMLService.printPlainHeader(out, "Ontopad - Home");
            HTMLService.printMenuBar(out, id);
            out.print("<p>This is the site root. enjoy</p>");
            HTMLService.printPlainFooter(out);
        }
    }
}
