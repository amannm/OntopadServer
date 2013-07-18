/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

import com.ontopad.message.HttpURLEncodedPrinter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author admin
 */
public class OpenIDURLEncodedPrinter extends HttpURLEncodedPrinter {

    public OpenIDURLEncodedPrinter(OutputStream out, String mode) throws IOException {
        super(out);
        this.out.append("openid.ns=http://specs.openid.net/auth/2.0&openid.mode=").write(mode);
    }
    
}
