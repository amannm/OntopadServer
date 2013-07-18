/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Amann
 */
@Entity
public class TripleSO implements Serializable {

    @Id
    @OneToOne
    private SubjectObject so;
    @ManyToOne
    private Thing p;
}
