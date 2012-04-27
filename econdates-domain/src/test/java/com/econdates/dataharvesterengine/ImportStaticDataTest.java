package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.domain.entities.EdCity;
import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdRegion;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ImportStaticDataTest {
	@Autowired
	ImportStaticData importStaticDataImpl;
	
	private static final int EDR_INDEX=129;
	private static final int EDR_ID = 539;
	private static final int EDR_COUNTRY_ID=2;
	private static final String EDR_NAME = "Albania";
	private static final String EDR_CODE ="AL";
	

	@Test
	public void testGetEdCountries() {
		List<EdCountry> edCountries = importStaticDataImpl.getCountries();
		assertEquals(ImportStaticData.TOTAL_COUNTRIES, edCountries.size());
	}

	@Test
	public void testGetEdRegions() {
		List<EdRegion> edRegions = importStaticDataImpl.getRegions();
		assertEquals(ImportStaticData.TOTAL_REGIONS, edRegions.size());
		assertEquals(EDR_ID,edRegions.get(EDR_INDEX).getId());
		assertEquals(EDR_COUNTRY_ID,edRegions.get(EDR_INDEX).getCountryId());
		assertEquals(EDR_NAME,edRegions.get(EDR_INDEX).getName());
		assertEquals(EDR_CODE,edRegions.get(EDR_INDEX).getCode());
	}

	@Test
	public void testGetEDCities() {
		List<EdCity> edCities = importStaticDataImpl.getCities();
		assertEquals(ImportStaticData.TOTAL_CITIES, edCities.size());
	}

}
