/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database;

import ontopad.web.Account;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import ontopad.web.Cryptography;

/**
 *
 * @author admin
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@DependsOn({"EnvironmentService"})
public class AccountDatastoreService {

    @EJB
    private EnvironmentService envService;
    private EntityStore store;
    PrimaryIndex<Long, Account> idIndex;
    SecondaryIndex<String, Long, Account> emailIndex;

    @PostConstruct
    private void startup() {
        StoreConfig sc = new StoreConfig();
        sc.setAllowCreate(true);
        sc.setReadOnly(false);
        store = envService.getEntityStore("AccountData", sc);
        idIndex = store.getPrimaryIndex(Long.class, Account.class);
        emailIndex = store.getSecondaryIndex(idIndex, String.class, "emailAddress");
    }

    public Account find(String email) {
        return emailIndex.get(email);
    }

    public boolean isValidCredential(String email, String password) {
        Account acc = emailIndex.get(email);
        if (acc == null) {
            return false;
        }
        try {
            return Base64.encode(Cryptography.applySHA256(password.getBytes("UTF-8"))).equals(acc.getPassword());
        } catch (UnsupportedEncodingException ex) {
            return false;
        }
    }

    @PreDestroy
    private void shutdown() {
        if (store != null) {
            try {
                store.close();
            } catch (DatabaseException dbe) {
                System.err.println("Error closing entity store" + dbe.toString());
            }
        }
    }
}
