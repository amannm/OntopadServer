/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.message.HttpKeyValuePrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author admin
 */
public class EncryptedAssociationResponse extends SuccessfulAssociationResponse {

    private String publicKey;
    private String macKey;

    @Override
    public void commit(OutputStream out) {
        HttpKeyValuePrinter printer = new HttpKeyValuePrinter(out);
        printer.put("ns", namespace);
        printer.put("assoc_handle", associationHandle);
        printer.put("session_type", associationSessionType.toString());
        printer.put("assoc_type", associationType.toString());
        printer.put("expires_in", expiresIn.toString());
        printer.put("dh_server_public", publicKey);
        printer.put("enc_mac_key", macKey);
        printer.close();
    }

    public byte[] getPublicKey() {
        return Base64.decodeBase64(publicKey);
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = Base64.encodeBase64String(publicKey);
    }

    public byte[] getMacKey() {
        return Base64.decodeBase64(macKey);
    }

    public void setMacKey(byte[] macKey) {
        this.macKey = Base64.encodeBase64String(macKey);
    }
    
    
}
