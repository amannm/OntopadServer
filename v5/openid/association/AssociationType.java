/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

/**
 *
 * @author admin
 */
public enum AssociationType {
    HMAC_SHA1("HMAC-SHA1"),
    HMAC_SHA256("HMAC-SHA256");

    private final String name;       

    private AssociationType(String s) {
        name = s;
    }

    @Override
    public String toString(){
       return name;
    }
}
