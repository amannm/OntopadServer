/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import java.io.PrintWriter;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

/**
 *
 * @author admin
 */
@ConnectionDefinition(
        connectionFactory = BDBConnectionFactory.class, connectionFactoryImpl = BDBUnmanagedConnectionFactory.class,
connection = BDBConnection.class, connectionImpl = BDBUnmanagedConnection.class)
public class BDBManagedConnectionFactory implements ManagedConnectionFactory {

    private PrintWriter log;

    @Override
    public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
        return new BDBUnmanagedConnectionFactory();
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        return new BDBUnmanagedConnectionFactory();
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new BDBManagedConnection();
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        for (Object o : connectionSet) {
            if (o instanceof BDBManagedConnection) {
                BDBManagedConnection mc = (BDBManagedConnection) o;
                if (mc.getInfo().equals(cxRequestInfo)) {
                    return mc;
                }
            }
        }
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.log = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return log;
    }
}
