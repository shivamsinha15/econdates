package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.dataharvesterengine.ImportStaticData;
import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdRegionDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EdCountryDAOImplTest {

	private static final long AUSTRALIA_ID = 14;
	private static final String COUNTRY_NAME = "Australia";

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Autowired
	EdRegionDAO edRegionDAOImpl;

	@Autowired
	EconDateInitDatabase econDateInitDatabaseImpl;

	@Before
	public void setUp() {
		if (!econDateInitDatabaseImpl.isCountryDataInit()) {
			econDateInitDatabaseImpl.initCountryData();
		}
	}

	@Test
	public void testFindAll() {
		assertEquals(edCountryDAOImpl.findAll().size(),
				ImportStaticData.TOTAL_COUNTRIES);
	}

	@Test
	public void testFindID() {
		EdCountry edAustralia = edCountryDAOImpl.findById(AUSTRALIA_ID);
		assertEquals(COUNTRY_NAME, edAustralia.getCountryName());
	}

	@Test
	public void testFindByName() {
		EdCountry edAustralia = edCountryDAOImpl.findByName(COUNTRY_NAME);
		assertEquals(COUNTRY_NAME, edAustralia.getCountryName());
	}

}
