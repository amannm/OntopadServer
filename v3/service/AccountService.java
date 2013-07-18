/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.service;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import ontopad.domain.Account;
import ontopad.manager.AccountManager;

/**
 *
 * @author Amann
 */
@MessageDriven(mappedName = "jms/LoginQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class AccountService {

    @Resource
    private SessionContext context;
    @Inject
    private AccountManager accountManager;

    @PermitAll
    public Account createAccount(Account account) throws Exception {

        Account newAccount = accountManager.create(account);

        return null;
    }

    @RolesAllowed({"RegisteredUsers"})
    public void modifyPassword(String pass) throws Exception {
    }

    @RolesAllowed({"RegisteredUsers"})
    public void deleteAccount() throws Exception {
    }
}
