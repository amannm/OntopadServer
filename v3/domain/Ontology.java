package ontopad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import ontopad.customizer.XMLDataCustomizer;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author Amann
 */
@Entity
@NamedQuery(name = "ontopad.domain.Ontology.findAll", query = "SELECT o FROM Ontology o")
@Customizer(XMLDataCustomizer.class)
public class Ontology implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false, length = 15)
    private String ontologyName;
    
    public Ontology() {
    }

    public Ontology(String xmlData) {
        this.xmlData = xmlData;
    }
    
    public String getOntologyName() {
        return ontologyName;
    }

    public void setOntologyName(String ontologyName) {
        this.ontologyName = ontologyName;
    }
    
    private String xmlData;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ontologyName != null ? ontologyName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ontology)) {
            return false;
        }
        Ontology other = (Ontology) object;
        if ((this.ontologyName == null && other.ontologyName != null) || (this.ontologyName != null && !this.ontologyName.equals(other.ontologyName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ontopad.domain.Ontology[ id=" + ontologyName + " ]";
    }
}
