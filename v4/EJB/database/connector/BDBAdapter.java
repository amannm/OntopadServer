/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.database.connector;

import com.sleepycat.je.Environment;
import com.sleepycat.je.XAEnvironment;
import java.io.File;
import java.util.List;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.AuthenticationMechanism;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.XATerminator;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAResource;

/**
 *
 * @author admin
 */
@Connector(
        reauthenticationSupport = false,
transactionSupport = TransactionSupport.TransactionSupportLevel.LocalTransaction,
eisType = "MAIL",
authMechanisms = {
    @AuthenticationMechanism(
          authMechanism = "BasicPassword",
    credentialInterface = AuthenticationMechanism.CredentialInterface.PasswordCredential)
})
public class BDBAdapter implements ResourceAdapter, java.io.Serializable {

    private List<XAEnvironment> envs;
    private TransactionSynchronizationRegistry txnsr;
    private WorkManager wm;
    private XATerminator xat;

    @Override
    public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
        this.txnsr = ctx.getTransactionSynchronizationRegistry();
        this.wm = ctx.getWorkManager();
        this.xat = ctx.getXATerminator();
    }

    @Override
    public void stop() {
        for(XAEnvironment e : envs) {
            e.close();
        }
    }

    @Override
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
        //endpointFactory.createEndpoint(new XAEnvironment(new File(""), null));
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
        //endpointFactory.createEndpoint(null)
    }

    @Override
    public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
        return (XAResource[]) envs.toArray();
    }
}
