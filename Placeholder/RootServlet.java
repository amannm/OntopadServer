package ontopad.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class RootServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("<!doctype html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Ontopad</title>"
                    + "<style>"
                    + "html, body {height:100%; width: 100%; margin:0; padding:0; background: #303030}"
                    + "#outer {position: absolute; width: 100%; height: 100%; display: table;}"
                    + "#inner {display: table-cell; vertical-align: middle; text-align: center;}"
                    + "#definition {background: #404040; border-top: solid 0.2em #505050; border-bottom: solid 0.2em #505050; padding-left: 1em; padding-right: 1em; font-family: georgia, arial, verdana, sans-serif; font-size: 180%;}"
                    + "#definition h1 {color: #a0a0e0;}"
                    + "#definition p {color: #e0e0e0;}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div id=\"outer\">"
                    + "<div id=\"inner\">"
                    + "<div id=\"definition\">"
                    + "<h1>on·tol·o·gy</h1>"
                    + "<p>a particular theory about the nature of being or the kinds of things that have existence</p>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>");
        }
    }
}
