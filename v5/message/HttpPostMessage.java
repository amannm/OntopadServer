/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class HttpPostMessage extends TextMessage {

    public HttpPostMessage(String destinationURL) {
        super(destinationURL);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = data.entrySet();
        try {
            for (Map.Entry<String, String> entry : entrySet) {

                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HttpPostMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (entrySet.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
