/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import ontopad.data.ClassInfo;
import ontopad.data.Env;

/**
 *
 * @author admin
 */
@ApplicationScoped
public class ConfirmationData implements Serializable {

    @Inject
    @Env
    private Environment environment;
    @Inject
    @ClassInfo
    private StoredClassCatalog classCatalog;
    private Database values;
    private static final String name = "Confirmation";
    private static final Logger log = Logger.getLogger(ConfirmationData.class.getName());

    @PostConstruct
    public void open() {
        DatabaseConfig myDbConfig = new DatabaseConfig();
        myDbConfig.setAllowCreate(true);
        myDbConfig.setTransactional(true);
        try {
            values = environment.openDatabase(null, name, myDbConfig);
            log.info(name + " opened successfully");
        } catch (DatabaseException de) {
            log.log(Level.SEVERE, name + " could not be opened: {0}", de.getMessage());
        }
    }

    public void reset() {
        if (values != null) {
            try {
                values.close();
                environment.removeDatabase(null, name);
                DatabaseConfig myDbConfig = new DatabaseConfig();
                myDbConfig.setAllowCreate(true);
                myDbConfig.setTransactional(true);
                values = environment.openDatabase(null, name, myDbConfig);
                log.info(name + " was reset successfully");
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, name + " could not be reset: {0}", de.getMessage());
            }
        } else {
            log.warning(name + " could not be reset because it is not open");
        }
    }

    @PreDestroy
    public void close() {
        if (values != null) {
            try {
                values.close();
                log.info(name + " closed successfully");
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, name + " could not be closed: {0}", de.getMessage());
            }
        } else {
            log.warning(name + " could not be closed because it is not open");
        }
    }

    public boolean put(String key, Confirmation value) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        StringBinding.stringToEntry(key, keyEntry);

        DatabaseEntry valueEntry = new DatabaseEntry();
        EntryBinding valueBinding = new SerialBinding(classCatalog, Confirmation.class);
        valueBinding.objectToEntry(value, valueEntry);

        if (values.put(null, keyEntry, valueEntry) == OperationStatus.SUCCESS) {
            return true;
        }
        return false;
    }

    public Confirmation get(String key) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        StringBinding.stringToEntry(key, keyEntry);

        DatabaseEntry valueEntry = new DatabaseEntry();
        if (values.get(null, keyEntry, valueEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            EntryBinding valueBinding = new SerialBinding(classCatalog, Confirmation.class);
            return (Confirmation) valueBinding.entryToObject(valueEntry);
        }
        return null;
    }

    public boolean delete(String key) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        StringBinding.stringToEntry(key, keyEntry);
        if (values.delete(null, keyEntry) == OperationStatus.SUCCESS) {
            return true;
        }
        return false;
    }
}
