package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hirondelle.date4j.DateTime;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.econdates.domain.entities.EdIndicator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataHarvesterConfig.class, loader = AnnotationConfigContextLoader.class)
public class ForexProTest {

	@Autowired
	@Qualifier("forexPro")
	private HarvestLocation forexPro;
	
	@Before
	public void setConnObj() throws IOException {
		forexPro.setConnObj(ForexPro.BASE_URL);
	}

	@Test
	public void testGetDocument() throws IOException {
		Document doc = forexPro.getDocument();
		assertNotNull(doc);
	}

	@Test
	public void testGetResponse() throws IOException {
		Response response = forexPro.getResponse();
		assertEquals(HttpStatus.OK.value(), response.statusCode());
	}

	@Test
	public void testisValidConnection() throws IOException {
		assertTrue(forexPro.isValidConnection(forexPro.getResponse()));
	}
	
	@Test
	public void testIsValidEconomicDocument() throws IOException{
		assertTrue(forexPro.isValidEconomicDocument(forexPro.getDocument()));
	}
	
	
	@Test
	public void testParseDocumentToRetrieveIndicatorsForAParticulalDay() throws IOException{
		DateTime day = new DateTime("2011-02-06");
		List<EdIndicator> indicators = forexPro.getEconomicIndicatorsForSingleDay(day);
	}

}
