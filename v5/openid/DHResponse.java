/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

/**
 *
 * @author admin
 */
public class DHResponse {

    public void get() {
        /*
         * dh_server_public
         Value: base64(btwoc(g ^ xb mod p))

         Description: The OP's Diffie-Hellman public key.

         enc_mac_key
         Value: base64(H(btwoc(g ^ (xa * xb) mod p)) XOR MAC key)

         Description: The MAC key (shared secret), encrypted with the secret Diffie-Hellman value. H is either "SHA1" or "SHA256" depending on the session type.
         */
    }
}
