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
public class TripleS implements Serializable {
    
    @Id
    @OneToOne
    private Thing s;
    @OneToMany
    private TriplePO tpo;
}
