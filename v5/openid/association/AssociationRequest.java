/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.message.HttpKeyValuePrinter;
import com.ontopad.message.HttpURLEncodedPrinter;
import java.io.OutputStream;

/**
 *
 * @author admin
 */
public class AssociationRequest {
    
    protected static final String namespace = "http://specs.openid.net/auth/2.0";
    protected static final String mode = "association";
    protected AssociationSessionType associationSessionType;
    protected AssociationType associationType;

    public void commit(OutputStream out) {
        HttpURLEncodedPrinter printer = new HttpURLEncodedPrinter(out, "openid.ns=" + namespace);
        printer.put("openid.mode", mode);
        printer.put("openid.session_type", associationSessionType.toString());
        printer.put("openid.assoc_type", associationType.toString());
        printer.close();
    }

    public AssociationSessionType getAssociationSessionType() {
        return associationSessionType;
    }

    public void setAssociationSessionType(AssociationSessionType associationSessionType) {
        this.associationSessionType = associationSessionType;
    }

    public AssociationType getAssociationType() {
        return associationType;
    }

    public void setAssociationType(AssociationType associationType) {
        this.associationType = associationType;
    }
    
    
}
