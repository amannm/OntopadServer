/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class JSONServiceException extends Exception {

    public JSONServiceException(String message) {
        super("JSONService Exception: " + message);
    }
}