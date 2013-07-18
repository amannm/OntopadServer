/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.xdb.DirectToXMLTypeMapping;

public class XMLDataCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(final ClassDescriptor descriptor) throws Exception {
        descriptor.removeMappingForAttributeName("xmlData");
        DirectToXMLTypeMapping mapping = new DirectToXMLTypeMapping();
        mapping.setAttributeName("xmlData"); //name of the atribute on the Entity Bean
        mapping.setFieldName("XMLDATA"); //name of the database column
        descriptor.addMapping(mapping);
    }
}
