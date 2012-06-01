package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
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
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = DataHarvesterConfig.class, loader =
// AnnotationConfigContextLoader.class)
@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
public class ForexProTest {

	private final Logger logger = LoggerFactory.getLogger(ForexPro.class);
	private static final String EVENT_ID = "17226";

	@Autowired
	@Qualifier("forexPro")
	private HarvestLocation forexPro;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	EdCountryDAO edCountryDAOImpl;

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
		expectedEdIndicator.setSourceReport("Australian Industry Group");
		expectedEdIndicator.setReleaseUrl("http://www.aigroup.asn.au/");
		assertEquals(expectedEdIndicator, moreDetailsAboutEdIndicator);
	}

	@Test
	public void testGetHistoricalDetailsByEventId() throws IOException {
		Set<EdHistory> edHistories = forexPro.getHistoricalDetailsByEventId(
				new EdIndicator(), EVENT_ID);
		assertEquals(50, edHistories.size());
	}

	@Test
	public void testGetEconomicIndicatorsForSingleDay() throws IOException {

		LocalDate day = new LocalDate(2011, 2, 6);

		List<EdIndicator> indicators = forexPro
				.getEconomicIndicatorsForSingleDay(day);

		logger.info("INDICATOR SIZE: " + indicators.size());
		assertEquals(1, indicators.size());
		assertEquals(49, indicators.get(0).getEdHistories().size());
	}

	// http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=filter&elemntsValues=dateFrom%3D2012-04-08%2CdateTo%3D2012-04-08%2Ccurrency%3D29%2Ccurrency%3D25%2Ccurrency%3D54%2Ccurrency%3D145%2Ccurrency%3D34%2Ccurrency%3D32%2Ccurrency%3D70%2Ccurrency%3D6%2Ccurrency%3D27%2Ccurrency%3D37%2Ccurrency%3D122%2Ccurrency%3D113%2Ccurrency%3D55%2Ccurrency%3D24%2Ccurrency%3D59%2Ccurrency%3D72%2Ccurrency%3D71%2Ccurrency%3D22%2Ccurrency%3D17%2Ccurrency%3D51%2Ccurrency%3D39%2Ccurrency%3D93%2Ccurrency%3D14%2Ccurrency%3D48%2Ccurrency%3D33%2Ccurrency%3D23%2Ccurrency%3D10%2Ccurrency%3D35%2Ccurrency%3D92%2Ccurrency%3D68%2Ccurrency%3D42%2Ccurrency%3D7%2Ccurrency%3D105%2Ccurrency%3D21%2Ccurrency%3D43%2Ccurrency%3D60%2Ccurrency%3D87%2Ccurrency%3D125%2Ccurrency%3D45%2Ccurrency%3D53%2Ccurrency%3D38%2Ccurrency%3D56%2Ccurrency%3D52%2Ccurrency%3D36%2Ccurrency%3D110%2Ccurrency%3D11%2Ccurrency%3D26%2Ccurrency%3D9%2Ccurrency%3D12%2Ccurrency%3D46%2Ccurrency%3D41%2Ccurrency%3D202%2Ccurrency%3D63%2Ccurrency%3D61%2Ccurrency%3D143%2Ccurrency%3D4%2Ccurrency%3D5%2Ccurrency%3D138%2Ccurrency%3D178%2CtimeZone%3D55%2Cdst%3Doff&timeFrame=weekly
	@Test
	public void testEdIndicatorValuesByDate() throws IOException {
		LocalDate releaseDate = new LocalDate(2012, 4, 8);

		EdIndicator adjCurrentAccount = new EdIndicator();
		adjCurrentAccount.setName("Adjusted Current Account");
		adjCurrentAccount.setImportance(Importance.Low);
		adjCurrentAccount.setEdCountry(edCountryDAOImpl.findByName("Japan"));

		EdHistory valuesForIndicator = forexPro.getEdIndicatorValuesByDate(
				adjCurrentAccount, releaseDate);

		assertEquals("0.85T", valuesForIndicator.getActual());
		assertEquals("0.66T", valuesForIndicator.getConsensus());
		assertEquals("0.14T", valuesForIndicator.getPrevious());
		assertEquals("0.120T", valuesForIndicator.getRevised());

	}

	@Test
	public void testGetEdHolidaysForASingleDay() throws IOException {
		LocalDate day = new LocalDate(2011, 04, 26,
				GregorianChronology.getInstanceUTC());
		List<EdHoliday> edHolidays = forexPro.getEdHolidaysForASingleDay(day);
		logger.info("HOLIDAYs SIZE: " + edHolidays.size());
		assertEquals(1, edHolidays.size());
	}

	@Test
	public void testPopulateIndicatorValuesForLatestData() throws IOException {

		EdIndicator sthKoreanPPI = new EdIndicator();
		sthKoreanPPI.setName("South Korean PPI (MoM)");
		sthKoreanPPI.setImportance(Importance.Low);
		sthKoreanPPI.setEdCountry(edCountryDAOImpl.findByName("South Korea"));

		EdScheduled toBeReleasedPPI = new EdScheduled();
		toBeReleasedPPI.setEdIndicator(sthKoreanPPI);
		toBeReleasedPPI.setPrevious("0.70%");
		toBeReleasedPPI.setReleaseDate(new LocalDate(2012, 04, 8));
		forexPro.populateIndicatorValuesForLatestData(toBeReleasedPPI);
		assertEquals("0.60%", toBeReleasedPPI.getActual());

	}

}
