package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdHoliday;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.persistance.EdIndicatorDAO;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = DataHarvesterConfig.class, loader =
// AnnotationConfigContextLoader.class)
@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
public class ForexProTest {

	private final Logger logger = LoggerFactory.getLogger(ForexPro.class);
	private static final String NAME = "AIG Construction Index";
	private static final LocalTime LOCAL_TIME = new LocalTime(22, 30);
	private static final int RELEASE_DAY_WEEK = 7;
	private static final int RELEASE_DAY_MONTH = 6;
	private static final String URL = "http://www.aigroup.asn.au/";
	private static final String SOURCE_REPORT = "Australian Industry Group";
	private static final String COUNTRY = "AUSTRALIA";
	private static final String EVENT_ID = "10828";

	private static final int YEAR = 2011;
	private static final int MONTH = 2;
	private static final int DAY_OF_MONTH = 6;
	private static final int HOUR_OF_DAY = 0;
	private static final int MINUTE_OF_HOUR = 0;
	private static final int TIMEZONE = 0;

	@Autowired
	@Qualifier("forexPro")
	private HarvestLocation forexPro;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Before
	public void setConnObj() throws IOException {
		forexPro.setConnObj(ForexPro.BASE_URL);
	}

	@Test
	public void testGetDocument() throws IOException {
		Document doc = forexPro.getDocument();
		assertNotNull(doc);
	}

	@Test
	public void testGetResponse() throws IOException {
		Response response = forexPro.getResponse();
		assertEquals(HttpStatus.OK.value(), response.statusCode());
	}

	@Test
	public void testisValidConnection() throws IOException {
		assertTrue(forexPro.isValidConnection(forexPro.getResponse()));
	}

	@Test
	public void testIsValidEconomicDocument() throws IOException {
		assertTrue(forexPro.isValidEconomicDocument(forexPro.getDocument()));
	}

	public void testDateFormat() {
		int year = 2011;
		int month = 2;
		int dayOfMonth = 6;
		int hourOfDay = 0;
		int minuteOfHour = 0;
		int secondsOfMinute = 0;

		DateTime day = new DateTime(year, month, dayOfMonth, hourOfDay,
				minuteOfHour, secondsOfMinute, DateTimeZone.forID("Etc/UTC"));
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		;
		logger.info("Date format test: " + day.toString(fmt));

		assertEquals("2011-02-06", day.toString(fmt));
	}

	@Test
	public void testGetMoreDetailsByEventId() throws IOException {
		EdIndicator moreDetailsAboutEdIndicator = forexPro
				.getMoreDetailsByEventId(new EdIndicator(), EVENT_ID);
		EdIndicator expectedEdIndicator = new EdIndicator();
		expectedEdIndicator.setSourceReport(SOURCE_REPORT);
		expectedEdIndicator.setReleaseUrl(URL);
		assertEquals(expectedEdIndicator, moreDetailsAboutEdIndicator);
	}

	@Test
	public void testGetHistoricalDetailsByEventId() throws IOException {
		Set<EdHistory> edHistories = forexPro.getHistoricalDetailsByEventId(
				new EdIndicator(), EVENT_ID);
		assertEquals(48, edHistories.size());
	}

	@Test
	public void testGetEconomicIndicatorsForSingleDay() throws IOException {

		DateTime day = new DateTime(YEAR, MONTH, DAY_OF_MONTH, HOUR_OF_DAY,
				MINUTE_OF_HOUR, TIMEZONE);

		List<EdIndicator> indicators = forexPro
				.getEconomicIndicatorsForSingleDay(day);

		logger.info("INDICATOR SIZE: " + indicators.size());
		assertEquals(1, indicators.size());
		assertEquals(49, indicators.get(0).getEdHistories().size());
	}

	@Test
	public void testGetEdHolidaysForASingleDay() throws IOException {
		DateTime day = new DateTime(2011, 04, 26, 0, 0, 0);
		List<EdHoliday> edHolidays = forexPro.getEdHolidaysForASingleDay(day);
		logger.info("HOLIDAYs SIZE: " + edHolidays.size());
		assertEquals(1, edHolidays.size());
	}

}
