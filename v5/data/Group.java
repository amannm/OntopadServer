/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author admin
 */
public class Group implements Serializable {

    private String name;
    //group ids
    private Set<Long> superGroups = new HashSet<>();

    public Set<Long> getSuperGroups() {
        return superGroups;
    }

    public void setSuperGroups(Set<Long> superGroups) {
        this.superGroups = superGroups;
    }

    public void addSuperGroup(Long superGroupID) {
        superGroups.add(superGroupID);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeSuperGroup(Long groupID) {
        superGroups.remove(groupID);
    }
}
