package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EconDateInitDatabaseImplTest {
	@Autowired
	EconDateInitDatabase edInitDbImpl;

	@Test
	public void initEuroZone() {
		edInitDbImpl.initEuroZoneAsCountry();
		assertTrue(edInitDbImpl.isEuroZoneAsCountryDataInit());
	}

	@Test
	public void initIndicatorAndHistoryData() throws IOException {
		if (edInitDbImpl.isEuroZoneAsCountryDataInit()) {
			edInitDbImpl.initIndicatorAndHistoryData();
		}
	}

}
