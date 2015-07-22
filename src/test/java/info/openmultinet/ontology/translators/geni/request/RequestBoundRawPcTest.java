package info.openmultinet.ontology.translators.geni.request;

import info.openmultinet.ontology.exceptions.DeprecatedRspecVersionException;
import info.openmultinet.ontology.exceptions.InvalidModelException;
import info.openmultinet.ontology.exceptions.MissingRspecElementException;
import info.openmultinet.ontology.translators.AbstractConverter;
import info.openmultinet.ontology.translators.geni.RSpecValidation;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RequestBoundRawPcTest {

	@Test
	public void requestRoundtrip() throws JAXBException, InvalidModelException,
			IOException, XMLStreamException, MissingRspecElementException,
			DeprecatedRspecVersionException {
		final String filename = "/geni/request/request_bound_rawpc.xml";
		final String inputRspec = AbstractConverter.toString(filename);

		System.out.println("Converting this input from '" + filename + "':");
		System.out.println("===============================");
		System.out.println(inputRspec);
		System.out.println("===============================");

		final String outputRspec = RSpecValidation
				.completeRoundtrip(inputRspec);

		System.out.println("Generated this rspec:");
		System.out.println("===============================");
		System.out.println(outputRspec);
		System.out.println("===============================");

		System.out.println("Get number of diffs and nodes:");
		System.out.println("===============================");
		int[] diffsNodes = RSpecValidation.getDiffsNodes(inputRspec);

		Assert.assertTrue("type", outputRspec.contains("type=\"request\""));
		Assert.assertTrue("client id",
				outputRspec.contains("client_id=\"my-raw-pc-1\""));

		Document xmlDoc = RSpecValidation.loadXMLFromString(outputRspec);

		// check that output has one rspec element
		NodeList rspec = xmlDoc.getElementsByTagNameNS(
				"http://www.geni.net/resources/rspec/3", "rspec");
		Assert.assertTrue(rspec.getLength() == 1);

		NodeList installServices = xmlDoc.getElementsByTagNameNS(
				"http://www.geni.net/resources/rspec/3", "install");
		Assert.assertTrue(installServices.getLength() == 2);

		NodeList executeServices = xmlDoc.getElementsByTagNameNS(
				"http://www.geni.net/resources/rspec/3", "execute");
		Assert.assertTrue(executeServices.getLength() == 2);

		// TODO: This test does not consistently return 0, only sometimes. Need
		// to debug.
		// Assert.assertTrue("No differences between input and output files",
		// diffsNodes[0] == 0);
	}

}
