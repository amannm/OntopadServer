/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author admin
 */
public class Identity implements Serializable {
    private Long nominalGroupID = 0L;
    private String confirmationCode;
    private Set<Long> groupIDs = new HashSet<>(Arrays.asList(0L));

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Set<Long> getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(Set<Long> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public Long getNominalGroupID() {
        return nominalGroupID;
    }

    public void setNominalGroupID(Long individualID) {
        this.nominalGroupID = individualID;
    }
}

