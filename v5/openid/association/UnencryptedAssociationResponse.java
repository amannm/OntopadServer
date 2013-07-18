/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid.association;

import com.ontopad.message.HttpKeyValuePrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class UnencryptedAssociationResponse extends SuccessfulAssociationResponse {

    private String associationHandle;
    private AssociationSessionType associationSessionType;
    private AssociationType associationType;
    private Integer expiresIn;
    private String macKey;

    @Override
    public void commit(OutputStream out) {
        HttpKeyValuePrinter printer = new HttpKeyValuePrinter(out);
        printer.put("ns", namespace);
        printer.put("assoc_handle", associationHandle);
        printer.put("session_type", associationSessionType.toString());
        printer.put("assoc_type", associationType.toString());
        printer.put("expires_in", expiresIn.toString());
        printer.put("mac_key", macKey);
        printer.close();
    }
}
