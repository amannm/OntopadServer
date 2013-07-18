/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.XAEnvironment;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

/**
 *
 * @author admin
 */
public class BDBManagedConnection implements ManagedConnection, LocalTransaction {

    private XAEnvironment env;
    private BDBConnection connection;
    private PrintWriter log;
    private List<ConnectionEventListener> listeners;
    private transient File rootDir;
    private transient EnvironmentConfig envConfig;
    private transient TransactionConfig transConfig;
    public BDBRequestInfo getInfo() {
        return new BDBRequestInfo(rootDir, envConfig, transConfig);
    }
    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return this.connection;
    }

    @Override
    public void destroy() throws ResourceException {
        env.close();
        ConnectionEvent connEvent = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
        connEvent.setConnectionHandle(connection);
        for (ConnectionEventListener cel : listeners) {
            cel.connectionClosed(connEvent);
        }
    }

    @Override
    public void cleanup() throws ResourceException {
    }

    @Override
    public void associateConnection(Object connection) throws ResourceException {
        this.connection = (BDBConnection) connection;
    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public XAResource getXAResource() throws ResourceException {
        return env;
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return this;
    }

    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return new BDBConnectionMetaData();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.log = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return log;
    }

    @Override
    public void begin() throws ResourceException {
        if (env == null) {
            throw new ResourceException("env is null in begin()");
        }
        try {
            Transaction txn = env.beginTransaction(null, transConfig);
            env.setThreadTransaction(txn);
        } catch (DatabaseException de) {
            throw new ResourceException("During begin: " + de.toString());
        }

        ConnectionEvent connEvent = new ConnectionEvent(this, ConnectionEvent.LOCAL_TRANSACTION_STARTED);
        connEvent.setConnectionHandle(this);
        for (ConnectionEventListener cel : listeners) {
            cel.localTransactionStarted(connEvent);
        }
    }

    @Override
    public void commit() throws ResourceException {

        if (env == null) {
            throw new ResourceException("env is null in commit()");
        }
        try {
            env.getThreadTransaction().commit();
        } catch (DatabaseException DE) {
            throw new ResourceException(DE);
        } finally {
            env.setThreadTransaction(null);
        }

        ConnectionEvent connEvent = new ConnectionEvent(this, ConnectionEvent.LOCAL_TRANSACTION_COMMITTED);
        connEvent.setConnectionHandle(this);
        for (ConnectionEventListener cel : listeners) {
            cel.localTransactionCommitted(connEvent);
        }
    }

    @Override
    public void rollback() throws ResourceException {
        if (env == null) {
            throw new ResourceException("env is null in rollback()");
        }
        try {
            env.getThreadTransaction().abort();
        } catch (DatabaseException de) {
            throw new ResourceException(de);
        } finally {
            env.setThreadTransaction(null);
        }

        ConnectionEvent connEvent = new ConnectionEvent(this, ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK);
        connEvent.setConnectionHandle(this);
        for (ConnectionEventListener cel : listeners) {
            cel.localTransactionRolledback(connEvent);
        }
    }
}
