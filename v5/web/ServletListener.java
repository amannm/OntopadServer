/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.web;

import com.ontopad.identity.IdentityFilter;
import com.ontopad.identity.RegistrationServlet;
import com.ontopad.authorization.AuthorizationFilter;
import com.ontopad.identity.ConfirmationServlet;
import com.ontopad.identity.IdentityServlet;
import com.ontopad.view.HomeServlet;
import com.ontopad.view.OntologyServlet;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    static final String[] securePaths = {"/*"};

    private static void registerServlet(ServletContext context, String resourcePath, Class servletClass) {
        ServletRegistration.Dynamic homeServletRegistration = context.addServlet(servletClass.getCanonicalName(), servletClass);
        homeServletRegistration.addMapping(resourcePath);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        SessionCookieConfig scc = context.getSessionCookieConfig();
        scc.setName("ontopad_session");
        scc.setHttpOnly(true);
        scc.setPath("/");

        registerServlet(context, "/", HomeServlet.class);
        registerServlet(context, "/identity", IdentityServlet.class);
        registerServlet(context, "/identity/registration", RegistrationServlet.class);
        registerServlet(context, "/identity/confirmation", ConfirmationServlet.class);
        registerServlet(context, "/ontology", OntologyServlet.class);

        FilterRegistration.Dynamic authenticationFilterRegistration = context.addFilter("Authentication", IdentityFilter.class);
        authenticationFilterRegistration.addMappingForUrlPatterns(null, true, securePaths);
        FilterRegistration.Dynamic authorizationFilterRegistration = context.addFilter("Authorization", AuthorizationFilter.class);
        authorizationFilterRegistration.addMappingForUrlPatterns(null, true, securePaths);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
