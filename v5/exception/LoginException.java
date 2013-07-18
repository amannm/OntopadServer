/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class LoginException extends Exception {

    public LoginException(String message) {
        super("Login Exception: " + message);
    }
}
