package com.econdates.dataharvesterengine;

import java.util.List;

import com.econdates.domain.entities.EdCity;
import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdRegion;

public interface ImportStaticData {

	public static final int TOTAL_COUNTRIES = 275;
	public static final int TOTAL_REGIONS = 3951;
	public static final int TOTAL_CITIES = 37253;

	public List<EdRegion> getRegions();

	public List<EdCity> getCities();

	public List<EdCountry> getCountries();
}
