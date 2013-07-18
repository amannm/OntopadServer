/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Amann
 */
@Entity
public class AccountGroup implements Serializable {

    public static enum GroupId {

        ADMIN, USER, GUEST
    }
    private static final long serialVersionUID = 1L;
    @Id
    @Enumerated(EnumType.STRING)
    private GroupId groupid;
    @ManyToMany(mappedBy = "accountgroup", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Account> accounts;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupid != null ? groupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccountGroup)) {
            return false;
        }
        AccountGroup other = (AccountGroup) object;
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ontopad.domain.AccountGroup[ id=" + groupid + " ]";
    }
}
