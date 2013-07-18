/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.misc;

/**
 *
 * @author Amann
 */
public enum BuiltInDatatype {

    RDFS_LITERAL("rdfs:Literal"),
    OWL_RATIONAL("owl:rational"), OWL_REAL("owl:real"),
    XSD_DOUBLE("xsd:double"), XSD_FLOAT("xsd:float"), XSD_DECIMAL("xsd:decimal"), XSD_INTEGER("xsd:integer"),
    XSD_LONG("xsd:long"), XSD_INT("xsd:int"), XSD_SHORT("xsd:short"), XSD_BYTE("xsd:byte"),
    XSD_NONNEGATIVEINTEGER("xsd:nonNegativeInteger"), XSD_NONPOSITIVEINTEGER("xsd:nonPositiveInteger"),
    XSD_POSITIVEINTEGER("xsd:positiveInteger"), XSD_NEGATIVEINTEGER("xsd:negativeInteger"),
    XSD_UNSIGNEDLONG("xsd:unsignedLong"), XSD_UNSIGNEDINT("xsd:unsignedInt"),
    XSD_UNSIGNEDSHORT("xsd:unsignedShort"), XSD_UNSIGNEDBYTE("xsd:unsignedByte"),
    RDF_PLAINLITERAL("rdf:PlainLiteral"),
    XSD_STRING("xsd:string"), XSD_NCNAME("xsd:NCName"), XSD_NAME("xsd:Name"), XSD_NMTOKEN("xsd:NMTOKEN"), 
    XSD_TOKEN("xsd:token"), XSD_LANGUAGE("xsd:language"), XSD_NORMALIZEDSTRING("xsd:normalizedString"),
    XSD_BOOLEAN("xsd:boolean"),
    XSD_BASE64BINARY("xsd:base64Binary"), XSD_HEXBINARY("xsd:hexBinary"),
    XSD_ANYURI("xsd:anyURI"),
    XSD_DATETIME("xsd:dateTime"),
    XSD_DATETIMESTAMP("xsd:dateTimeStamp"),
    RDF_XMLLITERAL("rdf:XMLLiteral");
    
    private final String text;

    private BuiltInDatatype(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
