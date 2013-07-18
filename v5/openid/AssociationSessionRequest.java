/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import java.math.BigInteger;

import com.ontopad.message.HttpURLEncodedPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author admin
 */
public class AssociationSessionRequest {

    private HttpURLEncodedPrinter out;
    private MessageDigest messageDigest;
    private BigInteger secret;
    private BigInteger p;
    
    public AssociationSessionRequest(OutputStream stream, String associationType, String sessionType) throws UnsupportedEncodingException, IOException {
        out = new HttpURLEncodedPrinter(stream, "openid.ns=http://specs.openid.net/auth/2.0&openid.mode=associate");
        out.put("openid.assoc_Type", associationType);
        out.put("openid.session_Type", sessionType);
    }

    public void start(OutputStream stream, String modulusBase64, String generatorBase64, String privateKeyBase64) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, IOException {
        byte[] modulus   = Base64.decodeBase64(modulusBase64.getBytes());
        byte[] generator = Base64.decodeBase64(generatorBase64.getBytes());
        byte[] privateKey = Base64.decodeBase64(privateKeyBase64.getBytes());
        p = new BigInteger(modulus);
        BigInteger g = new BigInteger(generator);
        BigInteger x = new BigInteger(privateKey);
        secret = g.modPow(x, p);
        DHParameterSpec dhParameterSpec = new DHParameterSpec(p, g);
        DHPrivateKeySpec dhPrivateKeySpec = new DHPrivateKeySpec(x, p, g);
        out.put("openid.dh_modulus", modulusBase64);
        out.put("openid.dh_gen", generatorBase64);
        String shit = Base64.encodeBase64String(secret.toByteArray());
        out.put("openid.dh_consumer_public", shit);
        //send p and g
    }

    public void receive(String remoteSecret) {
        byte[] remoteData= Base64.decodeBase64(remoteSecret.getBytes());
        BigInteger otherPublicSecret = new BigInteger(remoteData);
        BigInteger sharedSecret = otherPublicSecret.modPow(secret, p);
    }
    /*
     * openid.dh_modulus
     Value: base64(btwoc(p))

     Default: See Appendix B

     openid.dh_gen
     Value: base64(btwoc(g))

     Default: g = 2

     openid.dh_consumer_public
     Value: base64(btwoc(g ^ xa mod p))
     */

    public void setDiffieHellman() {
        //new String(Base64.encodeBase64(BigInteger.getBytes());

        out.put("openid.dh_modulus", Base64.encodeBase64String(0));
    }
}
