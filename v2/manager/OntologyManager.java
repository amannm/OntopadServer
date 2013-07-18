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
import javax.persistence.TypedQuery;
import ontopad.domain.Ontology;
import ontopad.producer.OntoPadDatabase;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class OntologyManager implements ManagerBean<Ontology>{

    @Inject
    @OntoPadDatabase
    private EntityManager em;

    public OntologyManager() {
    }

    @Override
    public Ontology create(Ontology ontology) {
        em.persist(ontology);
        return ontology;
    }

    @Override
    public Ontology modify(Ontology ontology) {
        em.find(Ontology.class, ontology.getId());
        Ontology mergedOntology = em.merge(ontology);
        return mergedOntology;
    }

    @Override
    public void delete(Ontology Ontology) {
        Ontology mergedOntology = em.merge(Ontology);
        em.remove(mergedOntology);
    }

    @Override
    public List<Ontology> findAll() {
        TypedQuery<Ontology> findAllUsersQuery = em.createNamedQuery("ontopad.domain.Ontology.findAll", Ontology.class);
        List<Ontology> resultList = findAllUsersQuery.getResultList();
        return resultList;
    }

    @Override
    public Ontology findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
