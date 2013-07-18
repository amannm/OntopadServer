/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Amann
 */
@Entity
public class TripleSPO {

    @OneToOne
    private Thing s;
    @OneToOne
    private Thing p;
    @OneToOne
    private Thing o;
}
