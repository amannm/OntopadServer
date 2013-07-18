/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.resources.model;

import java.util.Set;

/**
 *
 * @author admin
 */
public abstract class Role {
    private Long id;
    private Set<Role> superRoles;
    private Set<Role> subRoles;
}
