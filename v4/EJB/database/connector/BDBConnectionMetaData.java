/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import javax.resource.spi.ManagedConnectionMetaData;

/**
 *
 * @author admin
 */
public class BDBConnectionMetaData implements ManagedConnectionMetaData {

    @Override
    public String getEISProductName() {
        return "Berkeley DB Java Edition JCA";
    }

    @Override
    public String getEISProductVersion() {
        return "2.0";
    }

    @Override
    public int getMaxConnections() {
        /* Make a je.* parameter? */
        return 100;
    }

    @Override
    public String getUserName() {
        return null;
    }
}
