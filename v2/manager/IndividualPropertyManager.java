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
import ontopad.domain.ThingProperty;
import ontopad.producer.OntoPadDatabase;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class IndividualPropertyManager implements ManagerBean<ThingProperty> {

    @Inject
    @OntoPadDatabase
    private EntityManager em;

    @Override
    public ThingProperty create(ThingProperty t) {
        em.persist(t);
        return t;
    }

    @Override
    public void delete(ThingProperty t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThingProperty modify(ThingProperty t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThingProperty findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ThingProperty> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
