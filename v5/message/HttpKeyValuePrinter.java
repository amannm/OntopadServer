/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.message;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author admin
 */
public class HttpKeyValuePrinter {

    protected PrintWriter out;

    public HttpKeyValuePrinter(OutputStream out) {
        try {
            this.out = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void put(String key, String value) {
        out.append(key).append(':').append(value).append('\n');
    }

    public void close() {
        out.close();
    }
}
