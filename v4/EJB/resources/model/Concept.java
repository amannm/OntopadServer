/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.resources.model;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author admin
 */
public abstract class Concept {
    private Long id;
    private Set<Concept> superConcepts;
    private Set<Concept> subConcepts;
    private Map<Role, Concept> predecessors;
    private Map<Role, Concept> successors;
}
