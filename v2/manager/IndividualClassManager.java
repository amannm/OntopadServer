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
import ontopad.domain.ThingClass;
import ontopad.producer.OntoPadDatabase;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class IndividualClassManager implements ManagerBean<ThingClass> {

    @Inject
    @OntoPadDatabase
    private EntityManager em;

    @Override
    public ThingClass create(ThingClass t) {
        em.persist(t);
        return t;    }

    @Override
    public void delete(ThingClass t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThingClass modify(ThingClass t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThingClass findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ThingClass> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
