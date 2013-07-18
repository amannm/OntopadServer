/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author admin
 */
public class IndirectErrorResponse  {
    String ns;
    String mode;
    String error;
    String contact;
    String reference;
    public IndirectErrorResponse() {
    }
}
