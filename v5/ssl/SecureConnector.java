/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author admin
 */
public class SecureConnector {

    private SSLContext sslContext;

    public void doHandshake(AsynchronousSocketChannel socketChannel, SSLEngine engine, ByteBuffer outPublicData, ByteBuffer inPublicData, ByteBuffer outPrivateData, ByteBuffer inPrivateData) throws SSLException {
    }

    public void doUnwrap(SSLEngine engine, ByteBuffer encrypted, ByteBuffer unencrypted) {
        try {
            SSLEngineResult result = engine.unwrap(encrypted, unencrypted);
            switch (result.getStatus()) {
                case OK:
                    unencrypted.flip();
                    break;
                case BUFFER_UNDERFLOW:
                case BUFFER_OVERFLOW:
                case CLOSED:
            }
        } catch (SSLException ex) {
        }
    }

    public void start() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException, Exception {
        char[] passphrase = "passphrase".toCharArray();
        KeyStore ksKeys = KeyStore.getInstance("JKS");
        ksKeys.load(new FileInputStream("testKeys"), passphrase);
        KeyStore ksTrust = KeyStore.getInstance("JKS");
        ksTrust.load(new FileInputStream("testTrust"), passphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ksKeys, passphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ksTrust);
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    }
    private static final String hostname = "ontopad.com";
    private static final String server = "graph.facebook.com";
    private static final int port = 243;
    //64 megabytes
    private static final ByteBuffer pool = ByteBuffer.allocateDirect(67108864);

    public void work() {
        //ExecutorService pool = Executors.newFixedThreadPool(2);
        //AsynchronousChannelGroup.withThreadPool(pool)

        //Kernel Default Thread Pool
        try (final AsynchronousSocketChannel channel = AsynchronousSocketChannel.open()) {
            SSLEngine engine = sslContext.createSSLEngine(hostname, port);
            engine.setUseClientMode(true);
            AsynchronousSSLSocketChannel connection = new AsynchronousSSLSocketChannel(channel, engine);
            connection.associate("graph.facebook.com", 443);
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
        }
// Use as client

    }
}
