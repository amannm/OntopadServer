/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.PreloadConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author admin
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class EnvironmentService {

    private Environment env;
    private final String dataPath = "E:/data";

    @PostConstruct
    private void startup() {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setReadOnly(false);
        envConfig.setAllowCreate(true);
        try {
            env = new Environment(new File(dataPath), envConfig);
        } catch (DatabaseException de) {
        }
    }

    @PreDestroy
    private void shutdown() {
        if (env != null) {
            try {
                env.close();
            } catch (DatabaseException dbe) {
                System.err.println("Error closing environment" + dbe.toString());
            }
        }
    }

    public EntityStore getEntityStore(String storeName, StoreConfig storeConfig) {
        return new EntityStore(env, storeName, storeConfig);
    }
}
