/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.facebook;

import com.ontopad.oauth.ResponseType;
import java.io.IOException;
import java.math.BigInteger;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class FacebookAuthorizationRequest {

    private final BigInteger clientID;
    private final String redirectURI;
    private EnumSet<FacebookPermission> scope = EnumSet.noneOf(FacebookPermission.class);
    private String state;
    private ResponseType responseType;
    private FacebookDialogDisplayMode display;

    public FacebookAuthorizationRequest(BigInteger clientID, String redirectURI) {
        this.clientID = clientID;
        this.redirectURI = redirectURI;
    }

    public String getURL() {
        StringBuilder urlBuilder = new StringBuilder("https://www.facebook.com/dialog/oauth?client_id=" + clientID + "&redirect_iri=" + redirectURI);
        if (!scope.isEmpty()) {
            Iterator<FacebookPermission> scopeIter = scope.iterator();
            urlBuilder.append("&scope=").append(scopeIter.next().toString());
            while (scopeIter.hasNext()) {
                urlBuilder.append(',').append(scopeIter.next().toString());
            }
        }
        if (state != null) {
            urlBuilder.append("&state=").append(state);
        }
        if (responseType != null) {
            urlBuilder.append("&response_type=").append(responseType.toString());
        }
        if (display != null) {
            urlBuilder.append("&display=").append(display.toString());
        }
        return urlBuilder.toString();
    }
}
