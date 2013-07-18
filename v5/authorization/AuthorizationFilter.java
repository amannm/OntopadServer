/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.authorization;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ontopad.identity.Identity;
import com.ontopad.web.Attributes;

/**
 *
 * @author Amann Malik
 *
 *
 * 
 */
public class AuthorizationFilter implements Filter {

    @Inject
    private AuthorizationService authorizationService;
    private static final Logger log = Logger.getLogger(AuthorizationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        Identity identity = (Identity) httpSession.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        String method = httpRequest.getMethod();
        String location = httpRequest.getServletPath();
        if (authorizationService.authorize(identity, method, location)) {
            log.log(Level.INFO, "Client with ID: {0} has gained authorization for a {1} request on resource at {2}", new Object[]{identity.getNominalGroupID(), method, location});
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (identity.getNominalGroupID() == 0L) {
                httpResponse.setStatus(401);
                httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Ontopad\"");
            } else {
                httpResponse.setStatus(403);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
