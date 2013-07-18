/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

/**
 *
 * @author admin
 */
public enum AssociationSessionType {
    UNENCRYPTED("no-encryption"),
    DH_SHA1("DH-SHA1"),
    DH_SHA256("DH-SHA256");

    private final String name;       

    private AssociationSessionType(String s) {
        name = s;
    }

    @Override
    public String toString(){
       return name;
    }
}
