/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.identity;

import ontopad.web.Cryptography;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
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
import ontopad.database.EnvironmentService;

/**
 *
 * @author admin
 */
@Singleton
@Startup
@DependsOn({"DatabaseService"})
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class AccountService2 {

    private Database passwords;
    private Database memberships;
    private Database groupnames;
    @EJB
    private EnvironmentService ds;

    @PostConstruct
    private void startup() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setReadOnly(false);
        dbConfig.setAllowCreate(true);
        passwords = ds.open("password", dbConfig);
        memberships = ds.open("groups", dbConfig);
        groupnames = ds.open("groupnames", dbConfig);
        putPassword("amannmalik@gmail.com", "zygosperm");
    }

    public Long getAccountID(String email) {
        DatabaseEntry em = new DatabaseEntry();
        DatabaseEntry accountid = new DatabaseEntry();
        StringBinding.stringToEntry(email, em);
        if (passwords.get(null, em, accountid, null) == OperationStatus.SUCCESS) {
            return LongBinding.entryToLong(accountid);
        } else {
            return -1L;
        }
    }

    public String getAccountPassword(Long id) {
        DatabaseEntry accountid = new DatabaseEntry();
        DatabaseEntry password = new DatabaseEntry();
        LongBinding.longToEntry(id, accountid);
        if (passwords.get(null, accountid, password, null) == OperationStatus.SUCCESS) {
            return Cryptography.decode(password.getData());
        } else {
            return null;
        }
    }

    private void putPassword(String email, String password) {
        String passw = Cryptography.decode(Cryptography.hashPassword(Cryptography.encode(password)));
        try {
            DatabaseEntry em = new DatabaseEntry(email.getBytes("UTF-8"));
            DatabaseEntry pa = new DatabaseEntry(passw.getBytes("UTF-8"));
            passwords.put(null, em, pa);
        } catch (UnsupportedEncodingException | DatabaseException e) {
            // Exception handling goes here
        }
    }

    private Set<Long> getGroupMemberships(Long id) {
        DatabaseEntry accountid = new DatabaseEntry();
        DatabaseEntry groupid = new DatabaseEntry();
        LongBinding.longToEntry(id, accountid);
        Cursor cursor = memberships.openCursor(null, null);
        Set<Long> groupids = null;
        while (cursor.getNext(accountid, groupid, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            groupids.add(LongBinding.entryToLong(groupid));
        }
        return groupids;
    }

    //Long groupid -> String groupname
    private String getGroupName(Long id) {
        DatabaseEntry groupid = new DatabaseEntry();
        DatabaseEntry groupname = new DatabaseEntry();
        LongBinding.longToEntry(id, groupid);
        if (groupnames.get(null, groupid, groupname, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            return StringBinding.entryToString(groupname);
        } else {
            return null;
        }
    }

    public void remove(String key) {
        try {
            DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
            db.delete(null, theKey);
        } catch (UnsupportedEncodingException | DatabaseException | UnsupportedOperationException | IllegalArgumentException e) {
        }
    }

    @PreDestroy
    private void shutdown() {
        ds.close(db);
    }
}
