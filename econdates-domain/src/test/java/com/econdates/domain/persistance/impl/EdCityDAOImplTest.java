package com.econdates.domain.persistance.impl;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.dataharvesterengine.ImportStaticData;
import com.econdates.domain.entities.EdCity;
import com.econdates.domain.persistance.EdCityDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EdCityDAOImplTest {

	@Autowired
	EdCityDAO edCityDAOImpl;

	@Autowired
	EconDateInitDatabase edInitDbImpl;

	@Before
	public void setUp() {
		Collection<EdCity> edCities = edCityDAOImpl.findAll();
		if (edCities.isEmpty()) {
			edInitDbImpl.initCityData();
		}
	}

	@Test
	public void testFindAll(){
		assertEquals(ImportStaticData.TOTAL_CITIES, edCityDAOImpl.findAll().size());
	}
}
