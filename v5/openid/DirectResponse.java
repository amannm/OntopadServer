/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class DirectResponse {
    private StringBuilder sb = new StringBuilder("ns:http://specs.openid.net/auth/2.0\n");
    public void put(String key, String value) {
        sb.append(key).append(":").append(value).append("\n");
    }
    @Override
    public String toString() {
        return sb.toString();
    }
}
