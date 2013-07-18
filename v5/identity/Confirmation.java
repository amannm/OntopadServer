/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 *
 * @author admin
 */
public class Confirmation implements Serializable {

    private Long groupID;
    private Calendar expiresOn = Calendar.getInstance();

    public Confirmation() {
        this.expiresOn.roll(Calendar.DAY_OF_MONTH, true);
    }
    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long group) {
        this.groupID = group;
    }

    public Calendar getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Calendar expiresOn) {
        this.expiresOn = expiresOn;
    }
}
