/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Amann
 */
@Entity
public class Thing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Basic
    private String baseIRI;
    @Basic
    private String name;
}