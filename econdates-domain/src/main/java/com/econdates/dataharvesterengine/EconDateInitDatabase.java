package com.econdates.dataharvesterengine;

public interface EconDateInitDatabase {

	void initCountryData(); 
	boolean isCountryDataInit();
	boolean isRegionDataInit();
	void initRegionData();
	boolean isCityDataInit();
	void initCityData();
	void setUpExampleAIGEdIndicator();
}
