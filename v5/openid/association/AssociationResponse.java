/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import java.io.OutputStream;

/**
 *
 * @author admin
 */
public abstract class AssociationResponse {

    protected static final String namespace = "http://specs.openid.net/auth/2.0";

    public abstract void commit(OutputStream out);
}
