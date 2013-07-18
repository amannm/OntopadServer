/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class AuthenticationServiceException extends Exception {

    public AuthenticationServiceException(String message) {
        super("Registration Exception: " + message);
    }
}
