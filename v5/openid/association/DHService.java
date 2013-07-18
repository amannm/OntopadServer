/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

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
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author admin
 */
public class DHService { 
    //32 byte length MAC
    //Konstantynopolitańczykowianeczka
    private static final String macKey = "Konstantynopolitańczykowianeczka";
    private static BigInteger defaultMod = new BigInteger("DCF93A0B883972EC0E19989AC5A2CE310E1D37717E8D9571BB7623731866E61EF75A2E27898B057F9891C2E27A639C3F29B60814581CD3B2CA3986D2683705577D45C2E7E52DC81C7A171876E5CEA74B1448BFDFAF18828EFD2519F14E45E3826634AF1949E5B535CC829A483B8A76223E5D490A257F05BDFF16F2FB22C583AB", 16);
    private static BigInteger defaultGen = new BigInteger("2");
    private DHParameterSpec dhParams;
    private KeyPair pair;
    private MessageDigest messageDigest;
    private KeyAgreement keyAgree;

    public void init() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    public String[] createAssociation() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidParameterSpecException, InvalidKeyException {
        AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DiffieHellman");
        paramGen.init(new DHGenParameterSpec(512, 512));
        AlgorithmParameters params = paramGen.generateParameters();
        dhParams = params.getParameterSpec(DHParameterSpec.class);

        KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DiffieHellman");
        pairGen.initialize(dhParams);
        pair = pairGen.generateKeyPair();
        keyAgree = KeyAgreement.getInstance("DiffieHellman");
        keyAgree.init(pair.getPrivate());

        String[] result = new String[3];
        result[0] = Base64.encodeBase64String(dhParams.getP().toByteArray());
        result[1] = Base64.encodeBase64String(dhParams.getG().toByteArray());
        result[2] = Base64.encodeBase64String(pair.getPublic().getEncoded());
        return result;
    }

    public String[] receiveAssociation(String encodedModulus, String encodedGenerator, String encodedPublicKey) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
        byte[] decodedModulus = Base64.decodeBase64(encodedModulus);
        byte[] decodedGenerator = Base64.decodeBase64(encodedGenerator);
        byte[] decodedPublicKey = Base64.decodeBase64(encodedPublicKey);
        BigInteger modulus = new BigInteger(decodedModulus);
        BigInteger generator = new BigInteger(decodedGenerator);
        dhParams = new DHParameterSpec(modulus, generator);
        KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DiffieHellman");
        pairGen.initialize(dhParams);
        pair = pairGen.generateKeyPair();
        keyAgree = KeyAgreement.getInstance("DiffieHellman");
        keyAgree.init(pair.getPrivate());

        KeyFactory publicKeyFactory = KeyFactory.getInstance("DiffieHellman");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decodedPublicKey);
        PublicKey otherPublicKey = publicKeyFactory.generatePublic(x509KeySpec);
        keyAgree.doPhase(otherPublicKey, true);

        String[] result = new String[2];
        byte[] thisPublicKey = pair.getPublic().getEncoded();
        result[0] = Base64.encodeBase64String(thisPublicKey);

        byte[] sharedSecret = keyAgree.generateSecret();
        byte[] unencryptedMacKey = macKey.getBytes("UTF-8");
        byte[] hashedSharedSecret = messageDigest.digest(sharedSecret);
        for (int i = 0; i < unencryptedMacKey.length; i++) {
            unencryptedMacKey[i] = (byte) (unencryptedMacKey[i] ^ hashedSharedSecret[i]);
        }
        result[1] = Base64.encodeBase64String(unencryptedMacKey);
        return result;
    }

    public void receiveData(String publicKey, String data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        byte[] publicKeyDecoded = Base64.decodeBase64(publicKey);
        KeyFactory bobKeyFac = KeyFactory.getInstance("DiffieHellman");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyDecoded);
        PublicKey receivedPublicKey = bobKeyFac.generatePublic(x509KeySpec);
        keyAgree.doPhase(receivedPublicKey, true);
        byte[] sharedSecret = keyAgree.generateSecret();
        byte[] digestedSharedSecret = messageDigest.digest(sharedSecret);
        byte[] encryptedData = Base64.decodeBase64(data);
        for (int i = 0; i < digestedSharedSecret.length; i++) {
            encryptedData[i] = (byte) (encryptedData[i] ^ digestedSharedSecret[i]);
        }
    }
}
