/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Amann
 */
@Entity
public class TriplePO {

    @ManyToOne
    private Thing s;
    @OneToOne
    private Thing p;
    @OneToOne
    private Thing o;
}
