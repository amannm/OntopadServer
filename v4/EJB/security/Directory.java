/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "directories")
public class Directory implements Serializable {

    @Id
    private String directoryName;
    @OneToMany
    private List<Ontology> ontologies;
}
