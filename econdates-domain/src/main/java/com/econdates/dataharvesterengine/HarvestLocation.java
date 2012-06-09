package com.econdates.dataharvesterengine;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdHoliday;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdScheduled;

public interface HarvestLocation {
	void setConnObj(String Url) throws IOException, InterruptedException;

	Response getResponse() throws IOException;

	Document getDocument() throws IOException;

	boolean isValidConnection(Response response) throws IOException;

	boolean isValidEconomicDocument(Document doc);

	List<EdIndicator> getEconomicIndicatorsForSingleDay(LocalDate dateOnly)
			throws IOException, InterruptedException;

	EdIndicator getMoreDetailsByEventId(EdIndicator edIndicator, String eventId)
			throws IOException, InterruptedException;

	Set<EdHistory> getHistoricalDetailsByEventId(EdIndicator edIndicator,
			String eventId) throws IOException, InterruptedException;

	List<EdHoliday> getEdHolidaysForASingleDay(LocalDate dateOnly)
			throws IOException, InterruptedException;

	void populateIndicatorValuesForLatestData(EdScheduled toBeReleasedData)
			throws IOException, InterruptedException;

	void setAttachHistoricalDataToIndicators(
			boolean attachHistoricalDataToIndicators);

	void setAttachMoreDetailsToIndicators(boolean attachMoreDetailsToIndicators);
	
	EdHistory getEdIndicatorValuesByDate(EdIndicator edIndicator,
			LocalDate releaseDate) throws IOException, InterruptedException;
	
	String test();

}