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
import ontopad.domain.Account;
import ontopad.domain.Ontology;
import ontopad.producer.Database;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class OntologyManager implements GenericManager<Ontology> {

    @Inject
    @Database
    private EntityManager em;

    public OntologyManager() {
    }

    OntologyManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Ontology create(Ontology ontology) {
        em.persist(ontology);
        return ontology;
    }

    @Override
    public void delete(Ontology Ontology) {
        Ontology mergedOntology = em.merge(Ontology);
        em.remove(mergedOntology);
    }

    @Override
    public Ontology modify(Ontology ontology) {
        em.find(Ontology.class, ontology.getOntologyName());
        Ontology mergedOntology = em.merge(ontology);
        return mergedOntology;
    }

    @Override
    public List<Ontology> findAll() {
        TypedQuery<Ontology> findAllUsersQuery = em.createNamedQuery("ontopad.domain.Ontology.findAll", Ontology.class);
        List<Ontology> resultList = findAllUsersQuery.getResultList();
        return resultList;
    }

    @Override
    public Ontology findById(String ontologyId) {
        Ontology ontology = em.find(Ontology.class, ontologyId);
        return ontology;
    }
}
