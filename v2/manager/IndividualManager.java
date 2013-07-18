/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.manager;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import ontopad.domain.Thing;
import ontopad.producer.OntoPadDatabase;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class IndividualManager implements ManagerBean<Thing> {

    @Inject
    @OntoPadDatabase
    private EntityManager em;

    @Override
    public Thing create(Thing t) {
        em.persist(t);
        return t;    }

    @Override
    public void delete(Thing t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Thing modify(Thing t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Thing findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Thing> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
