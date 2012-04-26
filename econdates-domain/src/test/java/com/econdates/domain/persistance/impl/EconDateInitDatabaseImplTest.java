package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdRegionDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EconDateInitDatabaseImplTest {

	private static final long AUSTRALIA_ID = 14;
	private static final String COUNTRY_NAME = "Australia";
	

	@Autowired
	EdCountryDAO edCountryDAOImpl;
	
	@Autowired
	EdRegionDAO edRegionDAOImpl;
	
	List<EdCountry> edCountries;

	
	
	@Test
	public void testIsCountryDAOInit(){
		

	}

}
