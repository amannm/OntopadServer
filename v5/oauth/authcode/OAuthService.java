/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.authcode;

import java.util.Map;

/**
 *
 * @author admin
 */
public class OAuthService {
    private Map<String, String> clientIDMap;
    private static final String returnURL = "http://www.ontopad.com/return";
    public AuthorizationRequest getAuthorizationRequest(String authorizationServer, String[] scope) {
        AuthorizationRequest request = new AuthorizationRequest(clientIDMap.get(authorizationServer));
        return request;
    }
    public TokenRequest handleAuthorizationResponse(AuthorizationResponse response) {
        TokenRequest request = new TokenRequest(response.getCode(), returnURL);
        return request;
    }
    public void handleTokenResponse(TokenResponse response) {
        response.getAccessToken();
    }
    
    
}
