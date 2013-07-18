/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.authorization;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author admin
 */
public class ServletPolicy implements Serializable {
    public Set<Long> allowedGET = new HashSet<>();
    public Set<Long> allowedPOST = new HashSet<>();
    public Set<Long> allowedPUT = new HashSet<>();
    public Set<Long> allowedDELETE = new HashSet<>();
    public Set<Long> allowedHEAD = new HashSet<>();
    public Set<Long> allowedOPTIONS = new HashSet<>();
    public Set<Long> allowedTRACE = new HashSet<>();
}
