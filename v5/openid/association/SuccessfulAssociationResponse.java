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
public abstract class SuccessfulAssociationResponse extends AssociationResponse {

    protected String associationHandle;
    protected AssociationSessionType associationSessionType;
    protected AssociationType associationType;
    protected Integer expiresIn;

    public String getAssociationHandle() {
        return associationHandle;
    }

    public void setAssociationHandle(String associationHandle) {
        this.associationHandle = associationHandle;
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

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
    
}
