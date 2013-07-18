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
@NamedQuery(name = "ontopad.domain.Account.findAll", query = "SELECT a FROM Account a")

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(nullable = false, length = 15)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 128)
    private char[] password;
    
    @ManyToOne
    private AccountGroup accountGroup;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Ontology> ontologies;
    
    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable(name = "ACCOUNTGROUP", joinColumns = { @JoinColumn(name = "USERNAME", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "GROUPID", nullable = false) })
    private Set<AccountGroup> groups;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ontopad.domain.Account[ id=" + username + " ]";
    }
}
