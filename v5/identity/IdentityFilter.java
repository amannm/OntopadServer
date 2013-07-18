/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.ontopad.identity.AuthenticationService;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.ontopad.identity.Identity;
import com.ontopad.web.Attributes;

/**
 *
 * @author Amann Malik
 *
 * Function: Identity the resource requester
 *
 * Filter Application: Chains that utilize knowledge of a requester identity
 *
 * Dependencies: AuthenticationService: Required for acquiring a *  * valid <code>Identity</code> corresponding to whatever value stored in the
 * domain cookie name COOKIE_NAME
 *
 * Side-effects: For all downstream request handlers:
 * <code>((HttpServletRequest)request).getSession.getAttribute(Attribute.IDENTITY_ATTRIBUTE)</code>
 * returns a valid Identity object
 *
 */
public class IdentityFilter implements Filter {

    private static final Logger log = Logger.getLogger(IdentityFilter.class.getName());
    @Inject
    private AuthenticationService authenticationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[IdentityFilter]: Checking for identity...");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        Identity identity = (Identity) httpSession.getAttribute(Attributes.IDENTITY_ATTRIBUTE);
        if (identity == null) {
            log.info("[IdentityFilter]: No identity currently associated with HttpSession");
            String token = null;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie != null) {
                        if (cookie.getName().equals(Attributes.TOKEN_COOKIE_NAME)) {
                            token = cookie.getValue();
                            log.info("[IdentityFilter]: Reestablishing identity from token...");
                        }
                    }
                }
            }
            identity = authenticationService.authenticate(token);
            httpSession.setAttribute(Attributes.IDENTITY_ATTRIBUTE, identity);
        }
        log.log(Level.INFO, "[IdentityFilter]: Current identity {0}", identity.getNominalGroupID());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
