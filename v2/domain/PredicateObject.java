/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Amann
 */
@Embeddable
public class PredicateObject implements Serializable {

    private Thing p;
    private Thing o;
}
