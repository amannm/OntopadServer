/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.authcode;

import java.io.OutputStream;

/**
 *
 * @author admin
 */
public class OAuthException extends Throwable {
    private final Error error;
    private String errorDescription;
    private String errorURI;
    
    public OAuthException(Error error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorURI() {
        return errorURI;
    }

    public void setErrorURI(String errorURI) {
        this.errorURI = errorURI;
    }
    public void commitURLEncoded(OutputStream out) {
        
    }
}
