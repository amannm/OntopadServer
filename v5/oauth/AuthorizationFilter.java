/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth;

import com.ontopad.service.HTMLService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class AuthorizationFilter implements Filter {

    String ass = "https://www.facebook.com/dialog/oauth?"
            + "response_type=code&"
            + "redirect_uri=https://www.ontopad.com/authorization&"
            + "client_id=";
//Access Token Endpoint URL: https://graph.facebook.com/oauth/access_token
//User Profile Service URL: https://graph.facebook.com/me
//OAuth 2.0 Provider logout service: http://www.facebook.com/logout.php

    //Authorization Endpoint URL: https://accounts.google.com/o/oauth2/auth?response_type=code
//Access Token Endpoint URL: https://accounts.google.com/o/oauth2/token?grant_type=authorization_code
//User Profile Service URL: https://www.googleapis.com/oauth2/v1/userinfo
//OAuth 2.0 Provider logout service: https://mail.google.com/mail/?logout&hl=en
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //if unauthorized
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.append("<html><head><title>Choose</title><head><body>")
                    .append("<a href=\"").append("string").append("\">").append("Facebook").append("</a>")
                    .append("<a href=\"").append("string").append("\">").append("Google").append("</a>")
                    .append("</body></html>");
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect("http://www.fart.com/authorize?response_type=code&client_id=s6BhdRkqt3&state=xyz&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb");
    }

    @Override
    public void destroy() {
    }
}
