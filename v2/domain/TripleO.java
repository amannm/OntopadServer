/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Amann
 */
@Entity
public class TripleO implements Serializable {

    @OneToMany
    private TripleSP tsp;
    @Id
    @OneToOne
    private Thing o;
}
