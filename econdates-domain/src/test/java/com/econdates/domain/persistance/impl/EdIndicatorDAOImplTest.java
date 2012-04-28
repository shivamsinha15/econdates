package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EdIndicatorDAOImplTest {

	private static final long COUNTRY_ID = 14;

	private static final String COUNTRY_NAME = "Australia";

	private static final String NAME = "AIG Construction Index";

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;


	@Autowired
	EconDateInitDatabase econDateInitDbImpl;
	
	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Before
	public void setUp() {

		if (!econDateInitDbImpl.isCountryDataInit()) {
			econDateInitDbImpl.initCountryData();
		}
		
		EdIndicator aigIndicator = edIndicatorDAOImpl.findByNameCountryAndImportance(NAME, COUNTRY_ID, Importance.Low);
		
		if(aigIndicator==null){
			econDateInitDbImpl.setUpExampleAIGEdIndicator();
		}

	}

	@Test
	public void testFindByNameCountryAndImportance() {
		EdIndicator expectedEdIndicator = new EdIndicator();
		expectedEdIndicator.setImportance(Importance.Low);
		expectedEdIndicator.setName(NAME);
		expectedEdIndicator.setEdCountry(edCountryDAOImpl
				.findByName(COUNTRY_NAME));
		EdIndicator actualEdIndicator = edIndicatorDAOImpl
				.findByNameCountryAndImportance(NAME, COUNTRY_ID, Importance.Low);
		assertEquals(expectedEdIndicator, actualEdIndicator);
	}

}
