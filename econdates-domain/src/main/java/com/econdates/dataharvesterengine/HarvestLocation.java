package com.econdates.dataharvesterengine;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;

public interface HarvestLocation {
	void setConnObj(String Url) throws IOException;

	Response getResponse() throws IOException;

	Document getDocument() throws IOException;

	boolean isValidConnection(Response response) throws IOException;

	boolean isValidEconomicDocument(Document doc);

	List<EdIndicator> getEconomicIndicatorsForSingleDay(DateTime dateOnly)
			throws IOException;

	EdIndicator getMoreDetailsByEventId(EdIndicator edIndicator, String eventId)
			throws IOException;

	Set<EdHistory> getHistoricalDetailsByEventId(EdIndicator edIndicator,
			String eventId) throws IOException;

}