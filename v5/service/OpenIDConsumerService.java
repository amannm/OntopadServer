/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

/**
 *
 * @author admin
 */
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;

@ApplicationScoped
public class OpenIDConsumerService implements Serializable {

    private ConsumerManager manager;
    private static final String returnToUrl = "http://ontopad.com/openid";
    private static final String fetchAttribute = "email";
    private static final String openidAttribute = "openid-disc";
    private static final String fetchURL = "http://schema.openid.net/contact/email";
    private static final String OPTIONAL_VALUE = "0";
    private static final String REQUIRED_VALUE = "1";

    @PostConstruct
    public void init() {
        manager = new ConsumerManager();
    }
    
    public ConsumerManager getConsumerManager() {
        return manager;
    }
//    public String authRequest(String userSuppliedString, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
//        try {
//            List discoveries = manager.discover(userSuppliedString);
//            DiscoveryInformation discovered = manager.associate(discoveries);
//            httpRequest.getSession().setAttribute(openidAttribute, discovered);
//            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
//            FetchRequest fetch = FetchRequest.createFetchRequest();
//            fetch.addAttribute(fetchAttribute, fetchURL, true);
//            authReq.addExtension(fetch);
//            if (!discovered.isVersion2()) {
//                httpResponse.sendRedirect(authReq.getDestinationUrl(true));
//                return null;
//            } else {
//                httpRequest.setAttribute("parameterMap", authReq.getParameterMap());
//                httpRequest.setAttribute("destinationUrl", authReq.getDestinationUrl(false));
//                httpRequest.getServletContext().getRequestDispatcher("formredirection.jsp").forward(httpRequest, httpResponse);
//            }
//        } catch (OpenIDException e) {
//        }
//        return null;
//    }
//
//    public Identifier verifyResponse(HttpServletRequest httpReq) {
//        try {
//            ParameterList response = new ParameterList(httpReq.getParameterMap());
//            DiscoveryInformation discovered = (DiscoveryInformation) httpReq.getSession().getAttribute("openid-disc");
//            StringBuffer receivingURL = httpReq.getRequestURL();
//            String queryString = httpReq.getQueryString();
//            if (queryString != null && queryString.length() > 0) {
//                receivingURL.append("?").append(httpReq.getQueryString());
//            }
//            VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);
//            Identifier verified = verification.getVerifiedId();
//            if (verified != null) {
//                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
//                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
//                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
//                    List emails = fetchResp.getAttributeValues(fetchAttribute);
//                    String email = (String) emails.get(0);
//                }
//                return verified;
//            }
//        } catch (OpenIDException e) {
//        }
//        return null;
//    }
}