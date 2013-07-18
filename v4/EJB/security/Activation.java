/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author admin
 */
@Entity
public class Activation implements Serializable {

    @Id
    @Column(unique = true, length = 32)
    private String activationKey;
    @OneToOne
    private Account account;

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
