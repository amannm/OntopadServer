/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amann
 */
public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "OntoPad")
    @OntoPadDatabase
    private EntityManager em;
}
