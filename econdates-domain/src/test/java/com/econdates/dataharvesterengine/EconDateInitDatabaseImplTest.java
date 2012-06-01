package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.persistance.EdCountryDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EconDateInitDatabaseImplTest {
	@Autowired
	EconDateInitDatabase edInitDbImpl;

	@Autowired
	HarvestLocation forexPro;

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Test
	public void initCountryData() {
		if (!edInitDbImpl.isCountryDataInit()) {
			edInitDbImpl.initCountryData();
		}
		assertTrue(edInitDbImpl.isCountryDataInit());
	}

	@Test
	public void initEuroZone() {
		if (!edInitDbImpl.isEuroZoneAsCountryDataInit()
				&& edInitDbImpl.isCountryDataInit()) {
			edInitDbImpl.initEuroZoneAsCountry();
		}
		assertTrue(edInitDbImpl.isEuroZoneAsCountryDataInit());
	}

	@Test
	public void initRegionData() {
		if (!edInitDbImpl.isRegionDataInit()) {
			edInitDbImpl.initRegionData();
		}
		assertTrue(edInitDbImpl.isRegionDataInit());
	}

	@Test
	public void initIndicatorAndHistoryData() throws IOException {
		// LocalDate startDate = new LocalDate(2012, 4,
		// 8, GregorianChronology.getInstanceUTC());
		// LocalDate endDate = new LocalDate(2012, 4,
		// 7, GregorianChronology.getInstanceUTC());

		LocalDate startDate = new LocalDate(2012, 5, 19,
				GregorianChronology.getInstanceUTC());
		LocalDate endDate = new LocalDate(2012, 5, 18,
				GregorianChronology.getInstanceUTC());

		if (edInitDbImpl.isEuroZoneAsCountryDataInit()
				&& !edInitDbImpl.isIndicatorAndHistoryDataInit()) {
			edInitDbImpl.initIndicatorAndHistoryData(startDate, endDate);
		}
		assertTrue(edInitDbImpl.isIndicatorAndHistoryDataInit());
	}

	@Test
	public void testValidateAndPersisHistoricalData() throws IOException {

		Set<EdHistory> edHistories = forexPro.getHistoricalDetailsByEventId(
				new EdIndicator(), "17226");
		edInitDbImpl.validateAndPersistHistoricalData(edHistories);

	}

	@Test
	public void validateHistoricalData() throws IOException {
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
	@Rollback(true)
	public void initHolidaysData() throws IOException {
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
