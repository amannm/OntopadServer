/**
 * Created on 2007-4-14 00:54:50
 */
package com.ontopad.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

public class OpenIDConsumerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String verificationURL = "http://ontopad.com/identity/verification";
    private static final Logger log = Logger.getLogger(OpenIDConsumerServlet.class.getName());
    private ConsumerManager manager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String host = config.getInitParameter("proxy.host");
        if (host != null) {
            ProxyProperties proxyProps = new ProxyProperties();
            String port = config.getInitParameter("proxy.port");
            String username = config.getInitParameter("proxy.username");
            String password = config.getInitParameter("proxy.password");
            String domain = config.getInitParameter("proxy.domain");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userSuppliedString = request.getParameter("identity");
        try {
            List discoveries = manager.discover(userSuppliedString);
            DiscoveryInformation discovered = manager.associate(discoveries);
            request.getSession().setAttribute("openid-disc", discovered);
            AuthRequest authRequest = manager.authenticate(discovered, verificationURL);
            if (!discovered.isVersion2()) {
                response.sendRedirect(authRequest.getDestinationUrl(true));
            } else {
                request.setAttribute("parameterMap", request.getParameterMap());
                request.setAttribute("message", authRequest);
                request.getRequestDispatcher("/formredirection.jsp").forward(request, response);
            }
        } catch (DiscoveryException | MessageException | ConsumerException ex) {
            Logger.getLogger(OpenIDConsumerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
