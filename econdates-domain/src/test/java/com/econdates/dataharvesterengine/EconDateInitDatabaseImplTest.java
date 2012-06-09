package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicatorValue;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.domain.persistance.impl.AbstractPersistanceUnitTestCase;
import com.econdates.fixtures.EdIndicatorValueFixture;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EconDateInitDatabaseImplTest extends
		AbstractPersistanceUnitTestCase {

	@Autowired
	EconDateInitDatabase edInitDbImpl;

	@Autowired
	HarvestLocation forexPro;

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	@Mock
	ForexPro mockForexPro;

	@Before
	public void setup() {
		setupIndicator();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Rollback(false)
	public void initCountryData() {
		if (!edInitDbImpl.isCountryDataInit()) {
			edInitDbImpl.initCountryData();
		}
		assertTrue(edInitDbImpl.isCountryDataInit());
	}

	@Test
	@Rollback(false)
	public void initEuroZone() {
		if (!edInitDbImpl.isEuroZoneAsCountryDataInit()
				&& edInitDbImpl.isCountryDataInit()) {
			edInitDbImpl.initEuroZoneAsCountry();
		}
		assertTrue(edInitDbImpl.isEuroZoneAsCountryDataInit());
	}

	@Test
	@Rollback(false)
	public void initRegionData() {
		if (!edInitDbImpl.isRegionDataInit()) {
			edInitDbImpl.initRegionData();
		}
		assertTrue(edInitDbImpl.isRegionDataInit());
	}

	@Test
	@Rollback(false)
	public void initIndicatorAndHistoryData() throws IOException,
			InterruptedException {

		// Below is for date which only has holiday:
		// LocalDate startDate = new LocalDate(2012, 4, 8,
		// GregorianChronology.getInstanceUTC());
		// LocalDate endDate = new LocalDate(2012, 4, 7,
		// GregorianChronology.getInstanceUTC());

		LocalDate startDate = new LocalDate(2012, 5, 18,
				GregorianChronology.getInstanceUTC());
		LocalDate endDate = new LocalDate(2012, 5, 17,
				GregorianChronology.getInstanceUTC());

		if (edInitDbImpl.isEuroZoneAsCountryDataInit()
				&& !edInitDbImpl.isIndicatorAndHistoryDataInit()) {
			edInitDbImpl.initIndicatorAndHistoryData(startDate, endDate);
		}

		throw new RuntimeException();
		// assertTrue(edInitDbImpl.isIndicatorAndHistoryDataInit());
	}

	@Test
	public void isIndicatorAndHistoryDataInit() {
		assertTrue(edInitDbImpl.isIndicatorAndHistoryDataInit());
	}

	@Test
	public void testValidateAndPersistHistoricalData() throws IOException,
			InterruptedException {

		Set<EdHistory> edHistories = forexPro.getHistoricalDetailsByEventId(
				aigIndicator, "17226");
		edInitDbImpl.validateAndPersistHistoricalData(edHistories);

	}

	@Test
	public void testMockValidateAndPersistHistoricalData() throws IOException,
			InterruptedException {
		// Setup
		LocalDate startDate = new LocalDate(2099, 1, 30);
		LocalDate endDate = new LocalDate(2099, 1, 20);
		org.mockito.Mockito.when(
				mockForexPro.getHistoricalDetailsByEventId(
						(EdIndicator) Matchers.anyObject(),
						Matchers.anyString())).thenReturn(
				EdIndicatorValueFixture.getSetOfEdHistory(startDate, endDate));

		// Test
		Set<EdHistory> edHistories = mockForexPro
				.getHistoricalDetailsByEventId(new EdIndicator(), "17226");
		Set<EdHistory> edHistoriesDuplicates = mockForexPro
				.getHistoricalDetailsByEventId(new EdIndicator(), "17226");

		edInitDbImpl.validateAndPersistHistoricalData(edHistories);

		// duplicates should not be persisted
		edInitDbImpl.validateAndPersistHistoricalData(edHistoriesDuplicates);

		// Validate
		List<EdIndicatorValue> edHistoriesFromDAO = edIndicatorValueDAOImpl
				.findByGreaterThanOrEqualToDateValue(endDate, EdHistory.class);

		List<EdIndicatorValue> edScheduledFromDAO = edIndicatorValueDAOImpl
				.findByGreaterThanOrEqualToDateValue(endDate, EdScheduled.class);

		assertEquals(10, edHistoriesFromDAO.size());

		// This validates that parsed IndicatorValues are saved as scheduled if
		// their actual is null
		assertEquals(1, edScheduledFromDAO.size());
		assertEquals(11, edHistories.size());
	}

	@Test
	public void validateHistoricalData() throws IOException,
			InterruptedException {
		LocalDate startDate = new LocalDate(2012, 4, 8,
				GregorianChronology.getInstanceUTC());
		LocalDate endDate = new LocalDate(2012, 4, 7,
				GregorianChronology.getInstanceUTC());

		if (edInitDbImpl.isEuroZoneAsCountryDataInit()
				&& !edInitDbImpl.isIndicatorAndHistoryDataInit()) {
			edInitDbImpl.initIndicatorAndHistoryData(startDate, endDate);
		}
		edInitDbImpl.initIndicatorAndHistoryData(startDate, endDate);

		assertTrue(true);
	}

	@Test
	public void initHolidaysData() throws IOException, InterruptedException {
		if (!edInitDbImpl.isHolidayDataInit()) {
			LocalDate startDate = new LocalDate(
					GregorianChronology.getInstanceUTC());
			LocalDate endDate = new LocalDate(2012, 04, 29,
					GregorianChronology.getInstanceUTC());
			edInitDbImpl.initHolidayData(startDate, endDate);
		}
		assertTrue(edInitDbImpl.isHolidayDataInit());
	}

}
