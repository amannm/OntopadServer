/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import com.sleepycat.je.Environment;

/**
 *
 * @author admin
 */
public class BDBUnmanagedConnection implements BDBConnection {
    Environment env;
    @Override
    public void close() throws Exception {
        env.close();
    }
}
