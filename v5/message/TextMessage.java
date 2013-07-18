/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.message;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class TextMessage {

    protected final String destination;
    protected final Map<String, String> data = new HashMap<>();

    public TextMessage(String destination) {
        this.destination = destination;
    }

    public String getDestinationURL() {
        return destination;
    }

    public void put(String key, String value) {
        data.put(key, value);
    }


//<html><head><title>Ontopad OpenID Authentication Request</title></head><body onload=\"document.forms[0].submit();\">


}
