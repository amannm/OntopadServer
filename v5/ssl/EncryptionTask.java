/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.ssl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;

/**
 *
 * @author admin
 */
    public class EncryptionTask implements Callable<ByteBuffer> {

        private ByteBuffer unencrypted;
        private SSLEngine engine;
        private AsynchronousSocketChannel channel;

        public EncryptionTask(ByteBuffer unencrypted, SSLEngine engine, AsynchronousSocketChannel channel) {
            this.unencrypted = unencrypted;
            this.engine = engine;
            this.channel = channel;
        }

        @Override
        public ByteBuffer call() {
            ByteBuffer encrypted = bufferPool.poll();
            SSLEngineResult result;
            try {
                result = engine.wrap(unencrypted, encrypted);
                if (result.getStatus() != SSLEngineResult.Status.OK) {
                    encrypted = null;
                }
            } catch (SSLException ex) {
                encrypted.clear();
                bufferPool.add(encrypted);
                encrypted = null;
            } finally {
                unencrypted.clear();
                bufferPool.add(unencrypted);
                return encrypted;
            }
        }
    }