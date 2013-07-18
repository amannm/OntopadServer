/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.message.HttpURLEncodedPrinter;
import java.io.OutputStream;
import java.math.BigInteger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author admin
 */
public class EncryptedAssociationRequest extends AssociationRequest {

    private String modulus;
    private String generator;
    private String publicKey;

    @Override
    public void commit(OutputStream out) {
        HttpURLEncodedPrinter printer = new HttpURLEncodedPrinter(out, "openid.ns=" + namespace);
        printer.put("openid.mode", mode);
        printer.put("openid.session_type", associationSessionType.toString());
        printer.put("openid.assoc_type", associationType.toString());
        printer.put("openid.dh_modulus", modulus);
        printer.put("openid.dh_gen", generator);
        printer.put("openid.dh_consumer_public", publicKey);
        printer.close();
    }

    public byte[] getModulus() {
        return Base64.decodeBase64(modulus);
    }

    public void setModulus(byte[] modulus) {
        this.modulus = Base64.encodeBase64String(modulus);
    }

    public byte[] getGenerator() {
        return Base64.decodeBase64(generator);
    }

    public void setGenerator(byte[] generator) {
        this.generator = Base64.encodeBase64String(generator);
    }

    public byte[] getPublicKey() {
        return Base64.decodeBase64(publicKey);
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = Base64.encodeBase64String(publicKey);
    }
    
    
}
