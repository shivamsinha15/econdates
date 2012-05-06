package com.econdates.dataharvesterengine;

import java.io.IOException;
import java.util.Set;

import org.joda.time.LocalDate;

import com.econdates.domain.entities.EdHistory;

public interface EconDateInitDatabase {

	void initCountryData();
	
	void initEuroZoneAsCountry();
	
	void initRegionData();
	
	void initCityData();

	void initIndicatorAndHistoryData(LocalDate startDate, LocalDate endDate) throws IOException;

	void initHolidayData(LocalDate startDate, LocalDate endDate)
			throws IOException;
	
	void validateAndPersistHistoricalData(Set<EdHistory> edHistories);

	boolean isCountryDataInit();

	boolean isRegionDataInit();

	void setUpExampleAIGEdIndicator();

	boolean isEuroZoneAsCountryDataInit();

	boolean isIndicatorAndHistoryDataInit();

	boolean isHolidayDataInit();

}
