/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author admin
 */
public class AuthenticationInfo implements Serializable {

    String password;
    String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
