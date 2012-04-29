package com.econdates.dataharvesterengine;

import java.io.IOException;

public interface EconDateInitDatabase {

	void initCountryData();

	boolean isCountryDataInit();

	boolean isRegionDataInit();

	void initRegionData();

	boolean isCityDataInit();

	void initCityData();

	void setUpExampleAIGEdIndicator();

	void initIndicatorAndHistoryData() throws IOException;
	
	void initEuroZoneAsCountry();
	
	boolean isEuroZoneAsCountryDataInit();

	boolean isIndicatorAndHistoryDataInit();

	boolean isHolidayDataInit();

	void initHolidayData();
	
}
