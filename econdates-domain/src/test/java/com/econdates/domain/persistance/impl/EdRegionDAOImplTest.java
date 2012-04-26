package com.econdates.domain.persistance.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.dataharvesterengine.ImportStaticData;
import com.econdates.domain.entities.EdRegion;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdRegionDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EdRegionDAOImplTest {

	private static final long NSW_STATE_ID = 4;
	private static final String STATE_NAME = "New South Wales";

	@Autowired
	EdRegionDAO edRegionDAOImpl;

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Autowired
	EconDateInitDatabase econDateInitDbImpl;

	@Before
	public void setUp() {
		if (!econDateInitDbImpl.isCountryDataInit()) {
			econDateInitDbImpl.initCountryData();
		}

		if (!econDateInitDbImpl.isRegionDataInit()) {
			econDateInitDbImpl.initRegionData();
		}
	}

	@Test
	public void testFindAll() {
		assertEquals(ImportStaticData.TOTAL_REGIONS, edRegionDAOImpl.findAll()
				.size());
	}

	public void testFindID() {
		EdRegion edStateNSW = edRegionDAOImpl.findById(NSW_STATE_ID);
		assertEquals(STATE_NAME, edStateNSW.getName());
	}

}
