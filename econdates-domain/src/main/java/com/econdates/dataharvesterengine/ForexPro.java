package com.econdates.dataharvesterengine;

import hirondelle.date4j.DateTime;

//Crappy Code, but still worth more than paying >$5000 for SOAP WS for financial data

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;

public class ForexPro implements HarvestLocation {

	public static final String BASE_URL = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=filter&elemntsValues=dateFrom%3D2012-2-5%2CdateTo%3D2012-2-11%2Ccurrency%3D29%2Ccurrency%3D25%2Ccurrency%3D54%2Ccurrency%3D145%2Ccurrency%3D34%2Ccurrency%3D32%2Ccurrency%3D70%2Ccurrency%3D6%2Ccurrency%3D27%2Ccurrency%3D37%2Ccurrency%3D122%2Ccurrency%3D113%2Ccurrency%3D55%2Ccurrency%3D24%2Ccurrency%3D59%2Ccurrency%3D72%2Ccurrency%3D71%2Ccurrency%3D22%2Ccurrency%3D17%2Ccurrency%3D51%2Ccurrency%3D39%2Ccurrency%3D93%2Ccurrency%3D14%2Ccurrency%3D48%2Ccurrency%3D33%2Ccurrency%3D23%2Ccurrency%3D10%2Ccurrency%3D35%2Ccurrency%3D92%2Ccurrency%3D68%2Ccurrency%3D42%2Ccurrency%3D7%2Ccurrency%3D105%2Ccurrency%3D21%2Ccurrency%3D43%2Ccurrency%3D60%2Ccurrency%3D87%2Ccurrency%3D125%2Ccurrency%3D45%2Ccurrency%3D53%2Ccurrency%3D38%2Ccurrency%3D56%2Ccurrency%3D52%2Ccurrency%3D36%2Ccurrency%3D110%2Ccurrency%3D11%2Ccurrency%3D26%2Ccurrency%3D9%2Ccurrency%3D12%2Ccurrency%3D46%2Ccurrency%3D41%2Ccurrency%3D202%2Ccurrency%3D63%2Ccurrency%3D61%2Ccurrency%3D143%2Ccurrency%3D4%2Ccurrency%3D5%2Ccurrency%3D138%2Ccurrency%3D178%2CtimeZone%3D55%2Cdst%3Doff&timeFrame=weekly";

	private final Logger logger = LoggerFactory.getLogger(ForexPro.class);
	private Connection connObj;

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
		logger.info("Retriving economic indicators for the date: "
				+ day.toString());
		setConnObj(constructUrlStringForAParticularDay(day));
		return parseDocumentToRetrieveIndicatorsForAParticularDay(
				getDocument(), day);
	}

	private List<EdIndicator> parseDocumentToRetrieveIndicatorsForAParticularDay(
			Document document, DateTime day) throws IOException {
		Elements indicatorDetails = document.select("tr[id*=eventRowId]");

		Iterator indicatorIterator = indicatorDetails.iterator();

		List<EdIndicator> edIndicatorsForADay = new ArrayList<EdIndicator>();

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
			String eventActual = elem.select("td[id*=eventActual]").text();
			String eventForecast = elem.select("td[id*=eventForecast]").text();
			String eventPrevious = elem.select("td[id*=eventPrevious]").text();
			String eventRevisedFrom = elem.select("td[id*=eventRevisedFrom}")
					.text();

			if (!eventId.isEmpty() && !eventName.isEmpty()) {
				getMoreDetailsByEventId(edIndicator, eventId);

			}

			if (!eventName.isEmpty() && !eventId.isEmpty()) {

				edIndicator.setName(eventName);
				edIndicator.setReleaseTime(new DateTime(eventTime));
				edIndicator.setImportance(getImportanceAsEnum(eventImportance));
				edIndicator.setReleaseDayOfMonth(day.getDay());
				edIndicator.setReleaseDayOfWeek(day.getWeekIndex());
				edIndicator.setEdCountry(getEdCountry(eventCountry));

				logger.info("Event WebId: " + eventId);
				logger.info("Event Time: " + eventTime);
				logger.info("Event Currency: " + eventCurrency);
				logger.info("Event Country: " + eventCountry);
				logger.info("Event Importance: " + eventImportance);
				logger.info("Event Name: " + eventName);
				logger.info("Event Actual: " + eventActual);
				logger.info("Event Forecast: " + eventForecast);
				logger.info("Event Previous: " + eventPrevious);
				logger.info("Event Revised From: " + eventRevisedFrom);

			}

		}

		return null;
	}

	private EdIndicator getMoreDetailsByEventId(EdIndicator edIndicator,
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

		logger.info("Release URL :" + releaseUrl);
		logger.info("Event Source Report :" + sourceReport);
		logger.info("Event Description: " + eventDescription);

		return edIndicator;
	}

	private EdCountry getEdCountry(String eventCountry) {
		// TODO Auto-generated method stub
		return null;
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

	private String constructUrlStringForAParticularDay(DateTime day) {
		String urlForDate = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=filter&elemntsValues=dateFrom%3D"
				+ day.toString()
				+ "%2CdateTo%3D"
				+ day.toString()
				+ "%2Ccurrency%3D29%2Ccurrency%3D25%2Ccurrency%3D54%2Ccurrency%3D145%2Ccurrency%3D34%2Ccurrency%3D32%2Ccurrency%3D70%2Ccurrency%3D6%2Ccurrency%3D27%2Ccurrency%3D37%2Ccurrency%3D122%2Ccurrency%3D113%2Ccurrency%3D55%2Ccurrency%3D24%2Ccurrency%3D59%2Ccurrency%3D72%2Ccurrency%3D71%2Ccurrency%3D22%2Ccurrency%3D17%2Ccurrency%3D51%2Ccurrency%3D39%2Ccurrency%3D93%2Ccurrency%3D14%2Ccurrency%3D48%2Ccurrency%3D33%2Ccurrency%3D23%2Ccurrency%3D10%2Ccurrency%3D35%2Ccurrency%3D92%2Ccurrency%3D68%2Ccurrency%3D42%2Ccurrency%3D7%2Ccurrency%3D105%2Ccurrency%3D21%2Ccurrency%3D43%2Ccurrency%3D60%2Ccurrency%3D87%2Ccurrency%3D125%2Ccurrency%3D45%2Ccurrency%3D53%2Ccurrency%3D38%2Ccurrency%3D56%2Ccurrency%3D52%2Ccurrency%3D36%2Ccurrency%3D110%2Ccurrency%3D11%2Ccurrency%3D26%2Ccurrency%3D9%2Ccurrency%3D12%2Ccurrency%3D46%2Ccurrency%3D41%2Ccurrency%3D202%2Ccurrency%3D63%2Ccurrency%3D61%2Ccurrency%3D143%2Ccurrency%3D4%2Ccurrency%3D5%2Ccurrency%3D138%2Ccurrency%3D178%2CtimeZone%3D55%2Cdst%3Doff&timeFrame=weekly";
		logger.info("Url String for a single day: " + urlForDate);
		return urlForDate;
	}

	private String constructUrlForMoreEventDetails(String id) {
		String urlForEventDetail = "http://www.forexpros.com/common/economicCalendar/economicCalendar.data.php?action=getMoreDetails&eventID="
				+ id;
		return urlForEventDetail;
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
