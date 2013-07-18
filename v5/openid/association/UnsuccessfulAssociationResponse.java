/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.message.HttpKeyValuePrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class UnsuccessfulAssociationResponse extends AssociationResponse {

    private static final String errorCode = "unsupported-type";
    private String errorMessage;
    private AssociationSessionType supportedAssociationSessionType;
    private AssociationType supportedAssociationType;

    @Override
    public void commit(OutputStream out) {
        HttpKeyValuePrinter printer = new HttpKeyValuePrinter(out);
        printer.put("ns", namespace);
        printer.put("error", errorMessage);
        printer.put("error_code", errorCode);
        printer.put("session_type", supportedAssociationSessionType.toString());
        printer.put("assoc_type", supportedAssociationType.toString());
        printer.close();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AssociationSessionType getSupportedAssociationSessionType() {
        return supportedAssociationSessionType;
    }

    public void setSupportedAssociationSessionType(AssociationSessionType supportedAssociationSessionType) {
        this.supportedAssociationSessionType = supportedAssociationSessionType;
    }

    public AssociationType getSupportedAssociationType() {
        return supportedAssociationType;
    }

    public void setSupportedAssociationType(AssociationType supportedAssociationType) {
        this.supportedAssociationType = supportedAssociationType;
    }
    
}
