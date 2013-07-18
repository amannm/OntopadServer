/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security.service;

import java.security.Principal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import ontopad.security.Account;
import ontopad.security.Activation;
import ontopad.security.Group;

/**
 *
 * @author admin
 *
 * 0 Site 1 Admin 2 Moderator 3 Member, 4 Unactivated Member 5 Guest 6 Banned
 * Unverified Account 5 Public Visitor
 */
@Stateless
public class AccountService {

    @EJB
    private EmailService es;
    @PersistenceContext(unitName = "OntopadPU")
    private EntityManager em;
    @Resource
    private SessionContext sc;

    @RolesAllowed("guest")
    public void register(String emailAddress, String password) throws EntityExistsException, PersistenceException, MessagingException {
        Account acc = new Account();
        acc.setPassword(password);
        acc.setRegistratedOn(Calendar.getInstance());
        em.persist(acc);
        //em.find(Group.class, 5L).addMember(acc);
        //em.find(Group.class, 4L).addMember(acc);

        Activation act = new Activation();
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        act.setActivationKey(key);
        act.setAccount(acc);
        em.persist(act);
        es.send(emailAddress, "Ontopad Account Registration", "Please click on the following link to finish registering your account: <a href=\"http://ontopad.com/registration/" + key + "\"/>");
    }

    @RolesAllowed("unactivated")
    public boolean activate(String key) {
        Principal p = sc.getCallerPrincipal();
        Activation act = em.find(Activation.class, key);
        if (act != null) {
            Account acc = act.getAccount();
        }
        return false;
    }

    @RolesAllowed("administrator")
    public Account find(String email) {
        return em.find(Account.class, email);
    }

    @RolesAllowed("administrator")
    public void save(Account acc) {
        em.persist(acc);
    }

    @RolesAllowed("administrator")
    public void edit(Account acc) {
        em.merge(acc);
    }

    @RolesAllowed("administrator")
    public void delete(Account acc) {
        em.remove(acc);
    }
}
