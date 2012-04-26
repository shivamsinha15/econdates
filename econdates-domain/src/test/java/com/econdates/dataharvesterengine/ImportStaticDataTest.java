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

	@Test
	public void testGetEdCountries() {
		List<EdCountry> edCountries = importStaticDataImpl.getCountries();
		assertEquals(ImportStaticData.TOTAL_COUNTRIES, edCountries.size());
	}

	@Test
	public void testGetEdRegions() {
		List<EdRegion> edRegions = importStaticDataImpl.getRegions();
		assertEquals(ImportStaticData.TOTAL_REGIONS, edRegions.size());
	}

	@Test
	public void testGetEDCities() {
		List<EdCity> edCities = importStaticDataImpl.getCities();
		assertEquals(ImportStaticData.TOTAL_CITIES, edCities.size());
	}

}
