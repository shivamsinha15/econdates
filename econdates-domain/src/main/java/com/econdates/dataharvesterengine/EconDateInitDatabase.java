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

	void initIndicatorAndHistoryData(LocalDate startDate, LocalDate endDate)
			throws IOException, InterruptedException;

	void initHolidayData(LocalDate startDate, LocalDate endDate)
			throws IOException, InterruptedException;

	void validateAndPersistHistoricalData(Set<EdHistory> edHistories);

	boolean isCountryDataInit();

	boolean isRegionDataInit();

	boolean isEuroZoneAsCountryDataInit();

	boolean isIndicatorAndHistoryDataInit();

	boolean isHolidayDataInit();

}
