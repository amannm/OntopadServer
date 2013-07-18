/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.authcode;

/**
 *
 * @author admin
 */
public class TokenRequest {
    //Access Token Request

    private static final String grantType = "authorization_code";
    private final String code;
    private final String redirectURI;
    private final String clientID;
    private final String clientSecret;

    //no 
    public TokenRequest(String code) {
        this.code = code;
        this.redirectURI = null;
        this.clientID = null;
        this.clientSecret = null;
    }

    //if the authorization request included redirect uri
    public TokenRequest(String code, String redirect_uri) {
        this.code = code;
        this.redirectURI = redirect_uri;
        this.clientID = null;
        this.clientSecret = null;

    }

    //if the client isnt authenticating  like somem way...
    public TokenRequest(String code, String redirect_uri, String client_id) {
        this.code = code;
        this.redirectURI = redirect_uri;
        this.clientID = client_id;
        this.clientSecret = null;
    }

    public TokenRequest(String code, String redirect_uri, String client_id, String clientSecret) {
        this.code = code;
        this.redirectURI = redirect_uri;
        this.clientID = client_id;
        this.clientSecret = clientSecret;
    }
    //code
    //client_id
    //redirect_uri
}
