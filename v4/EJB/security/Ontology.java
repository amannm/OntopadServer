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

/**
 *
 * @author admin
 */
@Entity
public class Ontology implements Serializable {

    @Id
    private Long id;
    private String ontologyName;
    private String ontologyData;
    //CREATE, READ, MODIFY, DELETE, COPY, REFERENCE

}
