/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.TransactionConfig;
import java.io.File;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author admin
 */
public class BDBRequestInfo implements ConnectionRequestInfo {

    private File rootDir;
    private EnvironmentConfig envConfig;
    private TransactionConfig transConfig;

    public BDBRequestInfo(File rootDir, EnvironmentConfig envConfig, TransactionConfig transConfig) {
        this.rootDir = rootDir;
        this.envConfig = envConfig;
        this.transConfig = transConfig;
    }

    File getRootDir() {
        return rootDir;
    }

    EnvironmentConfig getEnvConfig() {
        return envConfig;
    }

    TransactionConfig getTransactionConfig() {
        return transConfig;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BDBRequestInfo) {
            BDBRequestInfo info = (BDBRequestInfo) obj;
            return rootDir.equals(info.getRootDir()) && envConfig.equals(info.getEnvConfig()) && transConfig.equals(info.getTransactionConfig());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return rootDir.hashCode();
    }

    @Override
    public String toString() {
        return "</BDBRequestInfo rootDir=" + rootDir.getAbsolutePath() + "/>";
    }
}