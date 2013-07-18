/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.message;

import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author admin
 */
public class HttpFormPostMessage extends TextMessage {

    public HttpFormPostMessage(String destinationURL) {
        super(destinationURL);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action=\"").append(destination).append("\" method=\"post\" accept-charset=\"utf-8\">");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append("<input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\"/>");
        }
        sb.append("<button type=\"submit\">Submit</button></form>");
        return sb.toString();
    }
}
