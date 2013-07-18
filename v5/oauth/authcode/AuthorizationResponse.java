/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.authcode;

/**
 *
 * @author admin
 */
public class AuthorizationResponse {

    public final String code;
    public final String state;

    public AuthorizationResponse(String code) {
        this.code = code;
        this.state = null;
    }

    public AuthorizationResponse(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
