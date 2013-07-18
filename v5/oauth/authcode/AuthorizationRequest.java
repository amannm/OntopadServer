/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.authcode;

import java.util.Map;

public class AuthorizationRequest {

    private static final String responseType = "code";
    private final String clientID;
    private String redirectURI;
    private String scope;
    private String state;

    public AuthorizationRequest(String clientID) {
        this.clientID = clientID;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
