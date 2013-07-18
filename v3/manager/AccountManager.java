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
import ontopad.producer.Database;

/**
 *
 * @author Amann
 */
@Stateless
@LocalBean
public class AccountManager implements GenericManager<Account> {

    @Inject
    @Database
    private EntityManager em;

    public AccountManager() {
    }

    AccountManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Account create(Account account) {
        em.persist(account);
        return account;
    }

    @Override
    public void delete(Account account) {
        Account mergedAccount = em.merge(account);
        em.remove(mergedAccount);
    }

    @Override
    public Account modify(Account account) {
        em.find(Account.class, account.getUsername());
        Account mergedAccount = em.merge(account);
        return mergedAccount;
    }

    @Override
    public List<Account> findAll() {
        TypedQuery<Account> findAllAccountsQuery = em.createNamedQuery("ontopad.domain.Account.findAllAccounts", Account.class);
        List<Account> resultList = findAllAccountsQuery.getResultList();
        return resultList;
    }

    @Override
    public Account findById(String accountId) {
        Account account = em.find(Account.class, accountId);
        return account;
    }
}
