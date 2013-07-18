/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "groups")
public class Group implements Serializable {

    @Id
    private Long id;
    private String groupName;
    @ManyToMany(mappedBy = "groups")
    private Set<Account> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
