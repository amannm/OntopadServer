/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ontopad.identity.Identity;

/**
 *
 * @author admin
 */
public class HTMLService {

    private static final Logger log = Logger.getLogger(HTMLService.class.getName());

    public static void printMenuBar(PrintWriter writer, Identity identity) {
        if (identity != null) {
            Long nominalGroup = identity.getNominalGroupID();
            if (nominalGroup != 0L) {
                writer.print("<div id=\"nav\">Logged in with ID = " + nominalGroup
                        + " <a href=\"/OntopadWeb/\">Home</a>"
                        + " <a href=\"/OntopadWeb/logout\">Logout</a></div>");
                return;
            }
        }
        writer.print("<div id=\"nav\">Not logged in"
                + " <a href=\"/OntopadWeb/\">Home</a>"
                + " <a href=\"/OntopadWeb/login\">Login</a>"
                + " <a href=\"/OntopadWeb/registration\">Register</a></div>");
    }

    public static void printPlainHeader(PrintWriter writer, String title) {
        writer.write("<html><head><title>" + title + "</title></head><body><h1>" + title + "</h1>");
    }

    public static void printPlainFooter(PrintWriter writer) {
        writer.write("</body></html>");
    }

    public static void printLoginForm(PrintWriter writer) {
        writer.write("<h2>Login</h2> "
                + "<form action=\"\" method=\"post\">"
                + "<input type=\"text\" name=\"login\" />"
                + "<input type=\"password\" name=\"password\" />"
                + "<input type=\"submit\" />"
                + "</form>");

    }

    public static void printButton(PrintWriter writer, String text) {
        writer.write("<h2>Login</h2> "
                + "<form action=\"\" method=\"post\">"
                + "<input type=\"submit\" value=\"" + text + "\"/>"
                + "</form>");
    }

    public static void printRedirectHeader(PrintWriter writer, String title, String url, Integer delay) {
        writer.write("<html><head><title>" + title + "</title><meta http-equiv=\"Refresh\" content=\"" + delay + ";url=/OntopadWeb" + url + "\" /></head><body>");
    }

    public static void printRegistrationForm(PrintWriter writer) {
        writer.print("<h2>Enter your deets</h2> "
                + "<form action=\"\" method=\"post\">"
                + "Email Address: <input type=\"text\" name=\"email\"/>"
                + "Password: <input type=\"password\" name=\"password\"/>"
                + "<input type=\"submit\" value=\"Submit\"/>"
                + "</form>");
    }

    public static void printMessageBox(PrintWriter writer, String title, String message) {
        writer.write("<div>"
                + "<h2>" + title + "</h2> "
                + "<p>" + message + "</p>"
                + "</div>");

    }

    public static void printLink(PrintWriter writer, String url, String text) {
        writer.write("<a href=\"/OntopadWeb" + url + "\">" + text + "</a>");
    }

    public static void printSingleInputForm(PrintWriter writer, String text) {
        writer.write("<form action=\"\" method=\"post\">"
                + "<input type=\"text\" name=\"code\"/>"
                + "<input type=\"submit\" value=\"" + text + "\"/>"
                + "</form>");
    }
}
