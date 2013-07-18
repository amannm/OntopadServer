/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.data;

import com.ontopad.identity.IdentityFilter;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author admin
 */
@ApplicationScoped
public class DataEnvironment implements Serializable {

    private Environment environment;
    private Database classDB;
    private StoredClassCatalog classCatalog;
    private static final Logger log = Logger.getLogger(DataEnvironment.class.getName());

    @PostConstruct
    public void init() {
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        myEnvConfig.setAllowCreate(true);
        myEnvConfig.setTransactional(true);
        DatabaseConfig myDbConfig = new DatabaseConfig();
        myDbConfig.setSortedDuplicates(false);
        myDbConfig.setAllowCreate(true);
        try {
            this.environment = new Environment(new File("E:/data"), myEnvConfig);
            this.classDB = environment.openDatabase(null, "classDatabase", myDbConfig);
            this.classCatalog = new StoredClassCatalog(classDB);
            log.info("Data environment initialized succesfully");
        } catch (DatabaseException de) {
            log.log(Level.SEVERE, "Exception during init: {0}", de.getMessage());
        }
    }

    @PreDestroy
    public void close() {
        if (classCatalog != null) {
            try {
                classCatalog.close();
                log.info("Class catalog closed succesfully");
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, "Class Catalog failed to close properly: {0}", de.getMessage());
            }
        }
        if (classDB != null) {
            try {
                classDB.close();
                log.info("Class database closed succesfully");
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, "Class Database failed to close properly: {0}", de.getMessage());
            }
        }
        if (environment != null) {
            try {
                environment.close();
                log.info("Environment closed succesfully");
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, "Environment failed to close properly: {0}", de.getMessage());
            }
        }
    }

    @Produces
    @Env
    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Produces
    @ClassInfo
    public StoredClassCatalog getClassCatalog() {
        return classCatalog;
    }

    public void setClassCatalog(StoredClassCatalog classCatalog) {
        this.classCatalog = classCatalog;
    }
}
