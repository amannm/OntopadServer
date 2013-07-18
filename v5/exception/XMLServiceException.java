/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.exception;

/**
 *
 * @author admin
 */
public class XMLServiceException extends Exception {

    public XMLServiceException(String message) {
        super("XMLService Exception: " + message);
    }
}