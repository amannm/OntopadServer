/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class SessionException extends Exception {

    public SessionException(String message) {
        super("Session Exception: " + message);
    }
}
