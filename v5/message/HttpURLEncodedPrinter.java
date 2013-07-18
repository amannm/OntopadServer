/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class HttpURLEncodedPrinter {

    protected PrintWriter out;

    public HttpURLEncodedPrinter(OutputStream out, String initial) {
        try {
            this.out = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            this.out.write(initial);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void put(String key, String value) {
        try {
            out.append('&').append(URLEncoder.encode(key, "UTF-8")).append('=').append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void close() {
        out.close();
    }
}
