/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.openid.OpenIDKeyValuePrinter;
import com.ontopad.openid.OpenIDURLEncodedPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

/**
 *
 * @author admin
 */
public class AssociationService {
    private static final BigInteger macKey = new BigInteger("028E692B68415CAB392D0E4905972210D967ABC1E68B5DDCEF0792545C4B92E2", 16);
    private static final BigInteger defaultModulus = new BigInteger("DCF93A0B883972EC0E19989AC5A2CE310E1D37717E8D9571BB7623731866E61EF75A2E27898B057F9891C2E27A639C3F29B60814581CD3B2CA3986D2683705577D45C2E7E52DC81C7A171876E5CEA74B1448BFDFAF18828EFD2519F14E45E3826634AF1949E5B535CC829A483B8A76223E5D490A257F05BDFF16F2FB22C583AB", 16);
    private static final BigInteger defaultGenerator = BigInteger.valueOf(2L);
   
    private DHParameterSpec dhParams;
    private KeyPair pair;
    private MessageDigest messageDigest;

    public void init() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public AssociationRequest generateRequest() {
        try {
            EncryptedAssociationRequest request = new EncryptedAssociationRequest();
            request.setAssociationSessionType(AssociationSessionType.DH_SHA256);
            request.setAssociationType(AssociationType.HMAC_SHA256);
            request.setModulus(defaultModulus.toByteArray());
            request.setGenerator(defaultGenerator.toByteArray());
            KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DiffieHellman");
            dhParams = new DHParameterSpec(defaultModulus, defaultGenerator);
            pairGen.initialize(dhParams);
            pair = pairGen.generateKeyPair();
            DHPublicKey publicKey = (DHPublicKey) pair.getPublic();
            request.setPublicKey(publicKey.getY().toByteArray());
            return request;
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public AssociationResponse processRequest(EncryptedAssociationRequest request) {
        try {
            EncryptedAssociationResponse response = new EncryptedAssociationResponse();
            response.setAssociationHandle("asss");
            response.setAssociationSessionType(AssociationSessionType.DH_SHA256);
            response.setAssociationType(AssociationType.HMAC_SHA256);
            response.setExpiresIn(Integer.MAX_VALUE);
            BigInteger modulus = new BigInteger(request.getModulus());
            BigInteger generator = new BigInteger(request.getGenerator());
            dhParams = new DHParameterSpec(modulus, generator);
            KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DiffieHellman");
            pairGen.initialize(dhParams);
            pair = pairGen.generateKeyPair();
            DHPrivateKey privateKey = (DHPrivateKey) pair.getPrivate();
            BigInteger otherPublicKey = new BigInteger(request.getPublicKey());
            BigInteger sharedSecret = otherPublicKey.modPow(privateKey.getX(), modulus);
            byte[] digestedSharedSecret = messageDigest.digest(sharedSecret.toByteArray());
            byte[] keyData = macKey.toByteArray();
            for (int i = 0; i < keyData.length; i++) {
                keyData[i] ^= digestedSharedSecret[i];
            }
            response.setMacKey(keyData);
            DHPublicKey publicKey = (DHPublicKey) pair.getPublic();
            response.setPublicKey(publicKey.getY().toByteArray());
            return response;
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void processResponse(EncryptedAssociationResponse response) {
        BigInteger otherPublicKey = new BigInteger(response.getPublicKey());
        DHPrivateKey privateKey = (DHPrivateKey) pair.getPrivate();
        BigInteger sharedSecret = otherPublicKey.modPow(privateKey.getX(), dhParams.getP());
        byte[] digestedSharedSecret = messageDigest.digest(sharedSecret.toByteArray());
        byte[] macData = response.getMacKey();
        for (int i = 0; i < macData.length; i++) {
            macData[i] ^= digestedSharedSecret[i];
        }
    }
}
