/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.service;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import ontopad.domain.Ontology;
import ontopad.manager.OntologyManager;

/**
 *
 * @author Amann
 */
@MessageDriven(mappedName = "jms/LoginQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@RolesAllowed({"RegisteredUsers"})
public class OntologyService implements MessageListener {

    @Resource
    private SessionContext context;
    @Inject
    private OntologyManager ontologyManager;

    public Ontology createOntology() {
        return null;
    }

    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
