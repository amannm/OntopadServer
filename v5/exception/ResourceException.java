/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class ResourceException extends Exception {

    public ResourceException(String message) {
        super("Resource Exception: " + message);
    }
}
