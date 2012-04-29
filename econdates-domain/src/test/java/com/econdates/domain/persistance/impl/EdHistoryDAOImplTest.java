package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.persistance.EdHistoryDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EdHistoryDAOImplTest {

	private static final long COUNTRY_ID = 14;
	private static final String NAME = "AIG Construction Index";
	
	private EdIndicator aigIndicator;
	private EdHistory edHistory;
	private EdHistory expectedEdHistory;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	EdHistoryDAO edHistoryDAOImpl;

	@Autowired
	EconDateInitDatabase econDateInitDbImpl;

	@Before
	public void setUp() {

		if (!econDateInitDbImpl.isCountryDataInit()) {
			econDateInitDbImpl.initCountryData();
		}

		aigIndicator = edIndicatorDAOImpl
				.findByNameCountryAndImportance(NAME, COUNTRY_ID,
						Importance.Low);

		if (aigIndicator == null) {
			econDateInitDbImpl.setUpExampleAIGEdIndicator();
		}
		
		expectedEdHistory = new EdHistory();
		expectedEdHistory.setActual("40.2");
		expectedEdHistory.setAnalysis(null);
		expectedEdHistory.setConsensus(null);
		expectedEdHistory.setPrevious("43.8");
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		expectedEdHistory.setReleaseDate(new LocalDate(2011, 2, 6).toDate());
		expectedEdHistory.setRevised(null);
		expectedEdHistory.setEdIndicator(aigIndicator);

		edHistory = edHistoryDAOImpl.findByEdHistory(expectedEdHistory);
		if(edHistory==null){
			edHistoryDAOImpl.saveOrUpdate(expectedEdHistory);
		}
	}

	@Test
	public void testFindByEdHistory() {
		EdHistory actualEdHistory = edHistoryDAOImpl.findByEdHistory(edHistory);
		assertEquals(actualEdHistory,expectedEdHistory);
	}
	
	@Test
	public void edHistoryEquals(){
		
		EdHistory other = new EdHistory();
		other.setActual("40.2");
		other.setAnalysis(null);
		other.setConsensus("");
		other.setPrevious("43.8");
		other.setReleaseDate(new DateTime(2011, 2, 6, 0, 0, 0).toDate());
		other.setRevised(null);
		other.setEdIndicator(aigIndicator);

		
		assertEquals(expectedEdHistory,other);
		
	}
	
}