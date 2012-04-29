package com.econdates.dataharvesterengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdHoliday;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdHistoryDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.google.common.base.CharMatcher;

@Service
public class ForexPro implements HarvestLocation {

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Autowired
	EdHistoryDAO edHistoryDAOImpl;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	public static final String BASE_URL = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=filter&elemntsValues=dateFrom%3D2012-2-5%2CdateTo%3D2012-2-11%2Ccurrency%3D29%2Ccurrency%3D25%2Ccurrency%3D54%2Ccurrency%3D145%2Ccurrency%3D34%2Ccurrency%3D32%2Ccurrency%3D70%2Ccurrency%3D6%2Ccurrency%3D27%2Ccurrency%3D37%2Ccurrency%3D122%2Ccurrency%3D113%2Ccurrency%3D55%2Ccurrency%3D24%2Ccurrency%3D59%2Ccurrency%3D72%2Ccurrency%3D71%2Ccurrency%3D22%2Ccurrency%3D17%2Ccurrency%3D51%2Ccurrency%3D39%2Ccurrency%3D93%2Ccurrency%3D14%2Ccurrency%3D48%2Ccurrency%3D33%2Ccurrency%3D23%2Ccurrency%3D10%2Ccurrency%3D35%2Ccurrency%3D92%2Ccurrency%3D68%2Ccurrency%3D42%2Ccurrency%3D7%2Ccurrency%3D105%2Ccurrency%3D21%2Ccurrency%3D43%2Ccurrency%3D60%2Ccurrency%3D87%2Ccurrency%3D125%2Ccurrency%3D45%2Ccurrency%3D53%2Ccurrency%3D38%2Ccurrency%3D56%2Ccurrency%3D52%2Ccurrency%3D36%2Ccurrency%3D110%2Ccurrency%3D11%2Ccurrency%3D26%2Ccurrency%3D9%2Ccurrency%3D12%2Ccurrency%3D46%2Ccurrency%3D41%2Ccurrency%3D202%2Ccurrency%3D63%2Ccurrency%3D61%2Ccurrency%3D143%2Ccurrency%3D4%2Ccurrency%3D5%2Ccurrency%3D138%2Ccurrency%3D178%2CtimeZone%3D55%2Cdst%3Doff&timeFrame=weekly";

	private final Logger logger = LoggerFactory.getLogger(ForexPro.class);
	private final static int ARRAY_OFFSET = 1;
	private Connection connObj;

	private static final int HOUR_OF_DAY = 0;
	private static final int MINUTE_OF_HOUR = 0;
	private static final int TIMEZONE = 0;

	enum Month {
		JAN(), FEB(), MAR(), APR(), MAY(), JUN(), JUL(), AUG(), SEP(), OCT(), NOV(), DEC();
	}

	public boolean isValidConnection(Response response) throws IOException {
		boolean isValidConnection = false;
		if (response.statusCode() == HttpStatus.OK.value()) {
			logger.info("Correct Http Response from ForexPro Website: "
					+ HttpStatus.OK.toString());
			isValidConnection = true;
		}
		return isValidConnection;
	}

	public Response getResponse() throws IOException {
		Response forexProConnectionResponse = getConnObj().execute();
		return forexProConnectionResponse;
	}

	public Document getDocument() throws IOException {
		return getConnObj().get();
	}

	public boolean isValidEconomicDocument(Document doc) {
		boolean isValidDocument = false;
		Elements time = doc.select("td[class*= align_center]").select(
				":contains(Time)");
		Elements currency = doc.select("td[class*= align_center]").select(
				":contains(Cur.)");
		Elements importance = doc.select("td[class*= align_center]").select(
				":contains(Imp.)");
		Elements event = doc.select("td[class*= align_center]").select(
				":contains(Event)");
		Elements actual = doc.select("td[class*= align_center]").select(
				":contains(Actual)");
		Elements forecast = doc.select("td[class*= align_center]").select(
				":contains(Forecast)");
		Elements previous = doc.select("td[class*= align_center]").select(
				":contains(Previous)");
		if (!time.isEmpty() && !currency.isEmpty() && !importance.isEmpty()
				&& !event.isEmpty() && !actual.isEmpty() && !forecast.isEmpty()
				&& !previous.isEmpty()) {
			isValidDocument = true;
			logger.info("Document for ForexPro is Valid");
		}
		return isValidDocument;
	}

	public List<EdIndicator> getEconomicIndicatorsForSingleDay(DateTime day)
			throws IOException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		logger.info("Retriving economic indicators for the date: "
				+ day.toString(fmt));

		setConnObj(constructUrlStringToGetIndicatorsAParticularDay(day));
		return parseDocumentToRetrieveIndicatorsForAParticularDay(
				getDocument(), day);
	}

	public List<EdHoliday> getEdHolidaysForASingleDay(DateTime day)
			throws IOException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		logger.info("Retriving holidays for the date: " + day.toString(fmt));

		setConnObj(constructUrlStringToGetHolidays(day));
		return parseDocToRetrieveHolidaysForAParticularDay(getDocument(), day);
	}

	private List<EdHoliday> parseDocToRetrieveHolidaysForAParticularDay(
			Document document, DateTime day) {
		Elements holidayRows = document.select("tbody").select("tr");
		Iterator holidayRowIterator = holidayRows.iterator();
		List<EdHoliday> edHolidays = new ArrayList<EdHoliday>();

		while (holidayRowIterator.hasNext()) {
			EdHoliday edHoliday = new EdHoliday();
			Element elem = (Element) holidayRowIterator.next();
			Elements holidayRowCells = holidayRows.select("td");

			int index = 0;
			for (Element holidayCell : holidayRowCells) {

				index++;
				switch (index) {
				case 1:
					// This cell contains holiday date, however the date from
					// the element is not required, since we already have it.
					edHoliday.setDate(day.toDate());
					break;
				case 2:
					// This cell contains padding, hence do nothing.
					break;
				case 3:
					// This cell contains the country.
					String countryName = holidayCell.select("a").text();
					EdCountry edCountry = edCountryDAOImpl
							.findByName(countryName);
					edHoliday.setEdCountry(edCountry);
					break;
				case 4:
					// This cell contain stock exchange name.
					String stockExchange = holidayCell.text();
					edHoliday.setMarketName(stockExchange);
					break;
				case 5:
					// This cell contains the holidayName
					edHoliday.setName(holidayCell.text());
				}
			}
			edHolidays.add(edHoliday);
		}
		return edHolidays;
	}

	private List<EdIndicator> parseDocumentToRetrieveIndicatorsForAParticularDay(
			Document document, DateTime day) throws IOException {
		Elements indicatorDetails = document.select("tr[id*=eventRowId]");

		Iterator indicatorIterator = indicatorDetails.iterator();

		List<EdIndicator> edIndicatorsForADay = new ArrayList<EdIndicator>();
		Set<EdHistory> edHistories = new HashSet<EdHistory>();

		while (indicatorIterator.hasNext()) {
			EdIndicator edIndicator = new EdIndicator();
			Element elem = (Element) indicatorIterator.next();
			String eventId = getEventIdAsString(elem.select(
					"tr[id*=eventRowId]").attr("id"));
			String eventTime = elem.select("td[id*=eventTime]").text();
			String eventCurrency = elem.select("td[id*=eventCurrency]").text();
			String eventCountry = elem.select("td[id*=eventCurrency]")
					.select("span[title]").attr("title");
			Elements entImpElement = elem.select("td[id*=eventImportance]")
					.select("span[title]");
			String eventImportance = elem.select("td[id*=eventImportance]")
					.select("span[title]").attr("title");

			String eventName = elem.select("td[id*=eventName]").text();

			/*
			 * More history details does not contain the most recent
			 * announcement hence need to also add the following to the history
			 * (if its not already in there)
			 * 
			 * Information: Actual, Consensus, Previous and Revised From
			 */

			String eventActual = elem.select("td[id*=eventActual]").text();
			String eventForecast = elem.select("td[id*=eventForecast]").text();
			String eventPrevious = elem.select("td[id*=eventPrevious]").text();
			String eventRevisedFrom = elem.select("td[id*=eventRevisedFrom}")
					.text();

			if (!eventId.isEmpty() && !eventName.isEmpty()) {

				edIndicator.setName(eventName);
				edIndicator.setImportance(getImportanceAsEnum(eventImportance));
				String[] time = eventTime.split(":");
				int hour = Integer.parseInt(time[0]);
				int min = Integer.parseInt(time[1]);
				edIndicator.setReleaseTime(new LocalTime(hour, min)
						.toDateTimeToday(DateTimeZone.forID("Etc/UTC"))
						.toDate());
				edIndicator.setReleaseDayOfWeek(day.getDayOfWeek());
				edIndicator.setReleaseDayOfMonth(day.getDayOfMonth());
				edIndicator.setEdCountry(getEdCountry(eventCountry, eventName));

				/*
				 * More Details Information: Release URL, Event Source Report,
				 * Event Description Historical.
				 */

				getMoreDetailsByEventId(edIndicator, eventId);

				EdHistory currentFigures = new EdHistory();
				currentFigures.setActual(eventActual);
				currentFigures.setConsensus(CharMatcher.WHITESPACE.trimFrom(
						eventForecast).isEmpty() ? null : eventActual);
				currentFigures.setPrevious(eventPrevious);
				currentFigures.setRevised(CharMatcher.WHITESPACE.trimFrom(
						eventRevisedFrom).isEmpty() ? null : eventRevisedFrom);
				currentFigures.setReleaseDate(day.toDate());
				currentFigures.setEdIndicator(edIndicator);

				/*
				 * Details: Release Date, Actual, Previous, Consensus. Note it
				 * does not contain current released information
				 */

				edHistories = getHistoricalDetailsByEventId(edIndicator,
						eventId);
				edHistories.add(currentFigures);

				edIndicator.setEdHistories(edHistories);
				// logger.info("Event WebId: " + eventId);
				// logger.info("Event Time: " + eventTime);
				// logger.info("Event Currency: " + eventCurrency);
				// logger.info("Event Country: " + eventCountry);
				// logger.info("Event Importance: " + eventImportance);
				// logger.info("Event Name: " + eventName);
				// logger.info("Event Actual: " + eventActual);
				// logger.info("Event Forecast: " + eventForecast);
				// logger.info("Event Previous: " + eventPrevious);
				// logger.info("Event Revised From: " + eventRevisedFrom);
				// edIndicator.setLastUpdated(DateTime.now(DateTimeZone.forID("Etc/UTC")).toDate());
				edIndicatorsForADay.add(edIndicator);
			}
		}
		// UsedForTest
		// edIndicatorDAOImpl.persistCollection(edIndicatorsForADay);
		// edHistoryDAOImpl.persistCollection(edHistories);
		return edIndicatorsForADay;
	}

	public Set<EdHistory> getHistoricalDetailsByEventId(
			EdIndicator edIndicator, String eventId) throws IOException {
		setConnObj(constructUrlForEventHistoricalData(eventId));
		Document moreEventHistoricalDetailsDoc = getConnObj().get();

		Set<EdHistory> edHistories = new HashSet<EdHistory>();

		Elements historicalDetailElementsRow = moreEventHistoricalDetailsDoc
				.select("tr");

		for (Element historicalDetailElementRow : historicalDetailElementsRow) {
			EdHistory edHistory = new EdHistory();
			Elements historicalDetailElementCells = historicalDetailElementRow
					.select("td");
			int index = 0;
			for (Element historicalDetailCell : historicalDetailElementCells) {
				String element = historicalDetailCell.text().trim()
						.replaceAll("\u00A0", "").replaceAll(" ", "");
				index++;
				switch (index) {
				case 1:
					edHistory
							.setReleaseDate(extractReleaseDateForHistoricalDetails(element));
					break;
				case 2:
					edHistory.setActual(CharMatcher.WHITESPACE
							.trimFrom(element).isEmpty() ? null : element);
					break;
				case 3:
					edHistory.setConsensus(CharMatcher.WHITESPACE.trimFrom(
							element).isEmpty() ? null : element);
					break;
				case 4:
					edHistory.setPrevious(CharMatcher.WHITESPACE.trimFrom(
							element).isEmpty() ? null : element);
				}
			}

			if (edHistory.getReleaseDate() != null) {
				edHistory.setEdIndicator(edIndicator);
				edHistories.add(edHistory);
			}
		}
		return edHistories;
	}

	private Date extractReleaseDateForHistoricalDetails(
			String releaseDateAsString) {
		logger.info("Release Date : " + releaseDateAsString);

		if (releaseDateAsString.equalsIgnoreCase("Noresultsfound")) {
			return null;
		}

		/*
		 * May is inconsistent, this if statement normalises the data, see:
		 * http:
		 * //www.forexpros.com/common/economicCalendar/economicCalendar.data
		 * .php?action=getMoreHistory&eventID=10828&historyNumEventsToShow=150
		 */
		if (releaseDateAsString.contains("May")) {
			releaseDateAsString = releaseDateAsString.replace("May", "May.");
		}

		String[] releaseDateAsArray = releaseDateAsString.split("\\.");
		String month = releaseDateAsArray[0].toUpperCase();
		int monthAsInt = Month.valueOf(month).ordinal() + ARRAY_OFFSET;
		int day = Integer.parseInt(releaseDateAsArray[1].split(",")[0]);
		int year = Integer.parseInt(releaseDateAsArray[1].split(",")[1]);

		DateTime releaseDate = new DateTime(year, monthAsInt, day, HOUR_OF_DAY,
				MINUTE_OF_HOUR, TIMEZONE);
		return releaseDate.toDate();
	}

	public EdIndicator getMoreDetailsByEventId(EdIndicator edIndicator,
			String eventId) throws IOException {
		setConnObj(constructUrlForMoreEventDetails(eventId));
		Document moreEventDetailsDoc = getConnObj().get();
		Elements eventDetails = moreEventDetailsDoc
				.select("td[class*=eventMoreDetailsDescription arial_12]");
		String eventDescription = eventDetails.first().text();

		Element sourceReportElement = moreEventDetailsDoc.select(
				"td[class*=moreDetailsTableRow arial_12]").get(3);
		String sourceReport = sourceReportElement.text();

		Element releaseUrlElement = moreEventDetailsDoc.select(
				"td[class*=moreDetailsTableRow arial_12]").get(5);
		String releaseUrl = releaseUrlElement.text();

		if (!eventDescription.isEmpty()) {
			edIndicator.setDescription(eventDescription);
			edIndicator.setSourceReport(sourceReport);
			edIndicator.setReleaseUrl(releaseUrl);
		}

		return edIndicator;
	}

	private EdCountry getEdCountry(String eventCountry, String eventName) {
		return edCountryDAOImpl.findByNames(eventCountry, eventName);
	}

	private String getEventIdAsString(String eventId) {
		String[] eventRowIdDetails = eventId.split("_");
		return eventRowIdDetails[1];
	}

	private Importance getImportanceAsEnum(String eventImportance) {
		if (eventImportance.equals("High Volatility Expected")) {
			return Importance.High;
		} else if (eventImportance.equals("Moderate Volatility Expected")) {
			return Importance.Medium;
		} else {
			return Importance.Low;
		}
	}

	private String constructUrlStringToGetIndicatorsAParticularDay(DateTime day) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		String urlForDate = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=filter&elemntsValues=dateFrom%3D"
				+ day.toString(fmt)
				+ "%2CdateTo%3D"
				+ day.toString(fmt)
				+ "%2Ccurrency%3D29%2Ccurrency%3D25%2Ccurrency%3D54%2Ccurrency%3D145%2Ccurrency%3D34%2Ccurrency%3D32%2Ccurrency%3D70%2Ccurrency%3D6%2Ccurrency%3D27%2Ccurrency%3D37%2Ccurrency%3D122%2Ccurrency%3D113%2Ccurrency%3D55%2Ccurrency%3D24%2Ccurrency%3D59%2Ccurrency%3D72%2Ccurrency%3D71%2Ccurrency%3D22%2Ccurrency%3D17%2Ccurrency%3D51%2Ccurrency%3D39%2Ccurrency%3D93%2Ccurrency%3D14%2Ccurrency%3D48%2Ccurrency%3D33%2Ccurrency%3D23%2Ccurrency%3D10%2Ccurrency%3D35%2Ccurrency%3D92%2Ccurrency%3D68%2Ccurrency%3D42%2Ccurrency%3D7%2Ccurrency%3D105%2Ccurrency%3D21%2Ccurrency%3D43%2Ccurrency%3D60%2Ccurrency%3D87%2Ccurrency%3D125%2Ccurrency%3D45%2Ccurrency%3D53%2Ccurrency%3D38%2Ccurrency%3D56%2Ccurrency%3D52%2Ccurrency%3D36%2Ccurrency%3D110%2Ccurrency%3D11%2Ccurrency%3D26%2Ccurrency%3D9%2Ccurrency%3D12%2Ccurrency%3D46%2Ccurrency%3D41%2Ccurrency%3D202%2Ccurrency%3D63%2Ccurrency%3D61%2Ccurrency%3D143%2Ccurrency%3D4%2Ccurrency%3D5%2Ccurrency%3D138%2Ccurrency%3D178%2CtimeZone%3D55%2Cdst%3Doff&timeFrame=weekly";
		logger.info("Url String for a single day: " + urlForDate);
		return urlForDate;
	}

	private String constructUrlForMoreEventDetails(String eventId) {
		String urlForEventDetail = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=getMoreDetails&eventID="
				+ eventId;
		logger.info("Url for more details: " + urlForEventDetail);
		return urlForEventDetail;
	}

	private String constructUrlForEventHistoricalData(String eventid) {
		String urlForHistoricalEventDetail = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=getMoreHistory&eventID="
				+ eventid + "&historyNumEventsToShow=150";

		logger.info("Url for historical details: "
				+ urlForHistoricalEventDetail);
		return urlForHistoricalEventDetail;
	}

	private String constructUrlStringToGetHolidays(DateTime day) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		String urlForHoliday = "http://www.forexpros.com/common/ajax_func.php?dates="
				+ day.toString(fmt)
				+ "|"
				+ day.toString(fmt)
				+ "&country=&action=holiday_calendar";
		logger.info("Url for Holidays: " + urlForHoliday);
		return urlForHoliday;
	}

	public Connection getConnObj() {
		return connObj;
	}

	public void setConnObj(String Url) throws IOException {
		this.connObj = Jsoup
				.connect(Url)
				.userAgent(
						"User-A gent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:8.0) Gecko/20100101 Firefox/8.0");

	}

}
