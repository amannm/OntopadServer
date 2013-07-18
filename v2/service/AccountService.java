/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.service;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import ontopad.domain.Thing;
import ontopad.manager.*;

/**
 *
 * @author Amann
 */
@Named
@Stateless
@LocalBean
public class AccountService {

    @Resource
    private SessionContext context;
    @Inject
    private IndividualManager individualManager;
    @Inject
    private IndividualClassManager individualClassManager;
    @Inject
    private IndividualPropertyManager individualPropertyManager;
    @Inject
    private DataClassManager dataClassManager;
    @Inject
    private DataValueManager dataValueManager;
    @Inject
    private DataPropertyManager dataPropertyManager;

    @PermitAll
    public Thing createAccount(Thing individual) throws Exception {
        Thing newIndividual = individualManager.create(individual);
        
        return null;
    }

    @RolesAllowed({"RegisteredUsers"})
    public void modifyPassword(String pass) throws Exception {
    }

    @RolesAllowed({"RegisteredUsers"})
    public void deleteAccount() throws Exception {
    }
}
