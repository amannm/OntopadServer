/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.util.HttpClientFactory;
import org.openid4java.util.ProxyProperties;

/**
 *
 * @author admin
 */
public class IdentityFilter implements Filter {

    private ConsumerManager manager;
    private static final String verificationURL = "http://www.ontopad.com/identity/verification";
    private static final Logger log = Logger.getLogger(IdentityFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String host = filterConfig.getInitParameter("proxy.host");
        if (host != null) {
            ProxyProperties proxyProps = new ProxyProperties();
            String port = filterConfig.getInitParameter("proxy.port");
            String username = filterConfig.getInitParameter("proxy.username");
            String password = filterConfig.getInitParameter("proxy.password");
            String domain = filterConfig.getInitParameter("proxy.domain");
            proxyProps.setProxyHostName(host);
            proxyProps.setProxyPort(Integer.parseInt(port));
            proxyProps.setUserName(username);
            proxyProps.setPassword(password);
            proxyProps.setDomain(domain);
            HttpClientFactory.setProxyProperties(proxyProps);
        }
        manager = new ConsumerManager();
        manager.setAssociations(new InMemoryConsumerAssociationStore());
        manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
        manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getAttribute("identity") == null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if ("POST".equals(httpRequest.getMethod())) {
                //Is this POST an attempt to initiate authentication using OpenID?
                String userSuppliedIdentifier = httpRequest.getParameter("openid_identifier");
                if (userSuppliedIdentifier != null) {
                    //This POST is an attempt to initiate authentication using OpenID.
                    try {
                        List discoveries = manager.discover(userSuppliedIdentifier);
                        DiscoveryInformation discovered = manager.associate(discoveries);
                        httpRequest.getSession().setAttribute("openid-disc", discovered);
                        AuthRequest authRequest = manager.authenticate(discovered, verificationURL);
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        if (!discovered.isVersion2()) {
                            //GET redirect to identity provider
                            httpResponse.sendRedirect(authRequest.getDestinationUrl(true));
                        } else {
                            //POST redirect to identity provider
                            try (PrintWriter out = httpResponse.getWriter()) {
                                out.print("<html><head><title>Ontopad OpenID Authentication Request</title></head><body onload=\"document.forms[0].submit();\"><form action=\"" + authRequest.getOPEndpoint() + "\" method=\"post\" accept-charset=\"utf-8\">");
                                Map<String, String> parameterMap = authRequest.getParameterMap();
                                for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                                        out.print("<input type=\"hidden\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
                                }
                                out.print("<button type=\"submit\">Authenticate</button></form></body></html>");
                            }
                        }
                    } catch (DiscoveryException | MessageException | ConsumerException ex) {
                        log.log(Level.SEVERE, null, ex);
                    }
                }
            }
            //print authorization required or login page
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(401);
            httpResponse.setContentType("text/html; charset=UTF-8");
            try (PrintWriter out = httpResponse.getWriter()) {
            }
        } else {
            //continue to destination
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
