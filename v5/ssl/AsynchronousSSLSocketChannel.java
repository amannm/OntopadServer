/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.ssl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;

/**
 *
 * @author admin
 */
public class AsynchronousSSLSocketChannel {

    private static final ConcurrentLinkedQueue<ByteBuffer> bufferPool = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            bufferPool.add(ByteBuffer.allocateDirect(4096));
        }
    }

    static {
    }
//    private final AsynchronousSocketChannel channel;
//    private final SSLEngine engine;
//    private ByteBuffer inPublicData;
//    private ByteBuffer outPublicData;
//    private ByteBuffer inPrivateData;
//    private ByteBuffer outPrivateData;

    public AsynchronousSSLSocketChannel(AsynchronousSocketChannel channel, SSLEngine engine) {
        this.channel = channel;
        this.engine = engine;
    }

    public void associate(String address, int port) throws SSLException, InterruptedException, ExecutionException, TimeoutException, IOException {

        InetSocketAddress add = (InetSocketAddress) channel.getRemoteAddress();
        Future<Void> connectionResult = channel.connect(new InetSocketAddress(address, port));
        inPublicData = bufferPool.poll();
        outPublicData = bufferPool.poll();
        inPrivateData = bufferPool.poll();
        outPrivateData = bufferPool.poll();
        engine.beginHandshake();
        HandshakeStatus handshakeStatus = engine.getHandshakeStatus();
        connectionResult.get(3, TimeUnit.SECONDS);
        do {
            switch (handshakeStatus) {
                case NEED_UNWRAP:
                    channel.read(inPublicData);
                    inPublicData.flip();
                    SSLEngineResult result = engine.unwrap(inPublicData, inPrivateData);
                    inPublicData.compact();
                    handshakeStatus = result.getHandshakeStatus();
                    if (result.getStatus() == SSLEngineResult.Status.OK) {
                        break;
                    }
                    throw new SSLException("\"" + result.getStatus().toString() + "\" result from unwrap operation during handshake");
                case NEED_WRAP:
                    outPublicData.clear();
                    result = engine.wrap(outPrivateData, outPublicData);
                    handshakeStatus = result.getHandshakeStatus();
                    if (result.getStatus() == SSLEngineResult.Status.OK) {
                        outPublicData.flip();
                        channel.write(outPublicData);
                        break;
                    }
                    throw new SSLException("\"" + result.getStatus().toString() + "\" result from wrap operation during handshake");
                case NEED_TASK:
                    Runnable task = engine.getDelegatedTask();
                    while (task != null) {
                        task.run();
                    }
                    handshakeStatus = engine.getHandshakeStatus();
                    break;
                case NOT_HANDSHAKING:
                    throw new SSLException("\"Not Handshaking\" status during handshake");
            }
        } while (handshakeStatus != HandshakeStatus.FINISHED);
    }

    public Future<Integer> write(ByteBuffer buffer) {
        ByteBuffer outboundEncryptedData = bufferPool.poll();
        try {
            engine.wrap(buffer, outboundEncryptedData);
        } catch (SSLException ex) {
        }
        return channel.write(outboundEncryptedData);
    }
    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public Future<ByteBuffer> decrypt(ByteBuffer data) {
        return service.submit(new DecryptionTask(data, engine));
    }

    public Future<ByteBuffer> encrypt(ByteBuffer data) {
        return service.submit(new EncryptionTask(data, ));
    }

    public class DecryptionTask implements Callable<ByteBuffer> {

        private ByteBuffer encrypted;
        private SSLEngine engine;

        public DecryptionTask(ByteBuffer encrypted, SSLEngine engine) {
            this.encrypted = encrypted;
            this.engine = engine;
        }

        @Override
        public ByteBuffer call() {
            ByteBuffer decrypted = bufferPool.poll();
            SSLEngineResult result;
            try {
                result = engine.unwrap(encrypted, decrypted);
                if (result.getStatus() != SSLEngineResult.Status.OK) {
                    decrypted = null;
                }
            } catch (SSLException ex) {
                decrypted.clear();
                bufferPool.add(decrypted);
                decrypted = null;
            } finally {
                encrypted.clear();
                bufferPool.add(encrypted);
                return decrypted;
            }
        }
    }


}
