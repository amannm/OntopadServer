/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.service;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import ontopad.manager.OntologyManager;

/**
 *
 * @author Amann
 */
@Named
@Stateless
@LocalBean
@RolesAllowed({"RegisteredUsers"})
public class OntologyService {

    @Resource
    private SessionContext context;
    @Inject
    private OntologyManager ontologyManager;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
