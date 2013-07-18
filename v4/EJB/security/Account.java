/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    private Long id;
    @Column(unique = true, nullable = false, length = 128)
    private String emailAddress;
    @OneToOne
    private Directory directory;
    @Column(nullable = false, length = 128)
    private String password;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar registratedOn;
    @ManyToMany
    private Set<Group> groups;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getRegistratedOn() {
        return registratedOn;
    }

    public void setRegistratedOn(Calendar registratedOn) {
        this.registratedOn = registratedOn;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
