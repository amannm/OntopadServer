/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import javax.naming.NamingException;
import javax.naming.Reference;

/**
 *
 * @author admin
 */
public class BDBUnmanagedConnectionFactory implements BDBConnectionFactory {

    @Override
    public void setReference(Reference reference) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Reference getReference() throws NamingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
