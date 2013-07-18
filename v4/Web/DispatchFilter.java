/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.annotation.security.DeclareRoles;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebFilter(urlPatterns = {"/*"})
public class DispatchFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
//        switch (request.getServletPath()) {
//            case "/email":
//                response.sendRedirect("/Ontopad-war/email");
//                break;
//            case "/login":
//                if (request.getUserPrincipal() == null) {
//                    response.sendRedirect("/Ontopad-war/login");
//                } else {
//                    response.sendRedirect("/Ontopad-war/");
//                }
//                break;
//            case "/logout":
//                if (request.getUserPrincipal() != null) {
//                    response.sendRedirect("/Ontopad-war/logout");
//                } else {
//                    response.sendRedirect("/Ontopad-war/");
//                }
//                break;
//        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
