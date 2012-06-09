package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.persistance.EdCountryDAO;

public class EdIndicatorDAOImplTest extends AbstractPersistanceUnitTestCase {

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Before
	public void setUp() {
		setupCountryData();
		setupIndicator();
	}

	@Test
	@Transactional
	public void testFindByNameCountryAndImportance() {
		EdIndicator expectedEdIndicator = new EdIndicator();
		expectedEdIndicator.setImportance(Importance.Low);
		expectedEdIndicator.setName(NAME);
		expectedEdIndicator.setEdCountry(edCountryDAOImpl
				.findByName(COUNTRY_NAME));
		EdIndicator actualEdIndicator = edIndicatorDAOImpl
				.findByNameCountryAndImportance(NAME, COUNTRY_ID,
						Importance.Low);
		assertEquals(expectedEdIndicator, actualEdIndicator);
	}

}
