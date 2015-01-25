package info.openmultinet.ontology.translators.tosca;

import info.openmultinet.ontology.exceptions.InvalidModelException;
import info.openmultinet.ontology.translators.tosca.Tosca2OMN.UnsupportedException;
import info.openmultinet.ontology.vocabulary.Omn;
import info.openmultinet.ontology.vocabulary.Osco;
import info.openmultinet.ontology.vocabulary.Tosca;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class Tosca2OMNTest {


	@Test
	public void testGetTopology() throws JAXBException, InvalidModelException, UnsupportedException {
	  InputStream input = this.getClass().getResourceAsStream("/tosca-request.xml");
	  
	  final Model model = Tosca2OMN.getModel(input);

		final String serializedModel = Tosca2OMNTest.serializeModel(model,
				"TTL");
		System.out.println(serializedModel);

		Assert.assertTrue("Should contain a topology resource",
        model.containsResource(Omn.Topology));
		Assert.assertTrue("Should contain the dummy node resource",
				model.contains(Osco.dummy, RDFS.subClassOf, Tosca.Node));
		Assert.assertTrue("Should contain state resources",
				model.containsResource(Tosca.State));
		Assert.assertTrue("Should contain state resources",
				model.containsResource(Osco.Active));
		Assert.assertTrue("Should contain parameter resources",
				model.containsResource(Osco.parameter1));
		Assert.assertTrue("port should be of range int",
				model.contains(Osco.port, RDFS.range, XSD.xint));
		Assert.assertTrue("parameter1 should be of range string",
				model.contains(Osco.parameter1, RDFS.range, XSD.xstring));
		Assert.assertTrue("Should contain the service type",
				model.containsResource(Osco.dummy));
		Assert.assertTrue("Should contain the service properties",
				model.containsResource(Osco.test_param));
	}

	public static String serializeModel(final Model rdfModel,
			final String serialization) {
		final StringWriter writer = new StringWriter();
		rdfModel.write(writer, serialization);
		return writer.toString();
	}
}
