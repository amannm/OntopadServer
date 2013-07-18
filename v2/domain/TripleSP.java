/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Amann
 */
@Entity
public class TripleSP implements Serializable {

    @EmbeddedId
    private SubjectPredicate sp;
    @ManyToOne
    private Thing o;
}
