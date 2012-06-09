package com.econdates.dataharvesterengine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.stereotype.Service;

import com.econdates.domain.entities.EdCity;
import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdRegion;

/**
 * @author shivamsinha
 * 
 *         Purpose of this class is to Import data from StaticCountryData.xls
 *         and populate the tables in the database
 * 
 */

@Service
public class ImportStaticDataImpl implements ImportStaticData {

	private static final String WORKSHEET_COUNTRY = "Ed_Country";
	private static final String WORKSHEET_REGION = "Ed_Region";
	private static final String WORKSHEET_CITY = "Ed_City";

	private static final int COUNTRY_COL_ID = 0;
	private static final int COUNTRY_COL_NAME = 1;
	private static final int COUNTRY_COL_CAPITAL = 2;
	private static final int COUNTRY_COL_CURRENCY_NAME = 3;
	private static final int COUNTRY_COL_CURRENCY_CODE = 4;
	private static final int COUNTRY_COL_MOBILE_EXT = 5;
	private static final int COUNTRY_COL_MAP_REFERENCE = 6;
	private static final int COUNTRY_COL_NATIONALITY_SINGULAR = 7;
	private static final int COUNTRY_COL_NATIONALITY_PLURAL = 8;
	private static final int COUNTRY_COL_FIPS_104 = 9;
	private static final int COUNTRY_COL_ISO_2 = 10;
	private static final int COUNTRY_COL_ISO_3 = 11;

	private static final int REGION_COL_ID = 0;
	private static final int REGION_COUNTRY_ID = 1;
	private static final int REGION_NAME = 2;
	private static final int REGION_CODE = 3;

	private static final int CITY_ID = 0;
	private static final int CITY_COUNTRY_ID = 1;
	private static final int CITY_REGION_ID = 2;
	private static final int CITY_NAME = 3;
	private static final int CITY_LATITUDE = 4;
	private static final int CITY_LONGITUDE = 5;
	private static final int CITY_TIMEZONE = 6;
	private static final int CITY_CODE = 7;

	private Workbook staticCountryDataWorkbook;

	public ImportStaticDataImpl() throws BiffException, IOException {

		staticCountryDataWorkbook = Workbook.getWorkbook(new File(
				"src/main/resources/StaticCountryData.xls"));
		Sheet edCountryDataSheet = staticCountryDataWorkbook
				.getSheet(WORKSHEET_COUNTRY);
		Cell a1 = edCountryDataSheet.getCell(0, 0);
		System.out.println("Cell A1 :" + a1.getContents());
	}

	public List<EdCountry> getCountries() {

		Sheet edCountryDataSheet = staticCountryDataWorkbook
				.getSheet(WORKSHEET_COUNTRY);

		List<EdCountry> edCountries = new ArrayList<EdCountry>();

		for (int row = 1; row <= TOTAL_COUNTRIES; row++) {
			EdCountry edCountry = new EdCountry();

			edCountry.setId(Integer.parseInt(edCountryDataSheet
					.getCell(COUNTRY_COL_ID, row).getContents().trim()));
			edCountry.setCountryName(edCountryDataSheet
					.getCell(COUNTRY_COL_NAME, row).getContents().trim());
			edCountry.setCapital(edCountryDataSheet
					.getCell(COUNTRY_COL_CAPITAL, row).getContents().trim());
			edCountry.setCurrencyName(edCountryDataSheet
					.getCell(COUNTRY_COL_CURRENCY_NAME, row).getContents()
					.trim());
			edCountry.setCurrencyCode(edCountryDataSheet
					.getCell(COUNTRY_COL_CURRENCY_CODE, row).getContents()
					.trim());
			edCountry.setMobileExtension(edCountryDataSheet
					.getCell(COUNTRY_COL_MOBILE_EXT, row).getContents().trim());
			edCountry.setMapReference(edCountryDataSheet
					.getCell(COUNTRY_COL_MAP_REFERENCE, row).getContents()
					.trim());
			edCountry.setNationalitySingular(edCountryDataSheet
					.getCell(COUNTRY_COL_NATIONALITY_SINGULAR, row)
					.getContents().trim());
			edCountry.setNationalityPlural(edCountryDataSheet
					.getCell(COUNTRY_COL_NATIONALITY_PLURAL, row).getContents()
					.trim());
			edCountry.setFips(edCountryDataSheet.getCell(COUNTRY_COL_FIPS_104,
					row).getContents());
			edCountry.setIso2(edCountryDataSheet
					.getCell(COUNTRY_COL_ISO_2, row).getContents().trim());
			edCountry.setIso3(edCountryDataSheet
					.getCell(COUNTRY_COL_ISO_3, row).getContents().trim());
			edCountries.add(edCountry);
		}
		return edCountries;
	}

	public List<EdRegion> getRegions() {
		Sheet edRegionDataSheet = staticCountryDataWorkbook
				.getSheet(WORKSHEET_REGION);

		List<EdRegion> edRegions = new ArrayList<EdRegion>();

		for (int row = 1; row <= TOTAL_REGIONS; row++) {
			EdRegion edRegion = new EdRegion();
			edRegion.setId(Long.parseLong(edRegionDataSheet
					.getCell(REGION_COL_ID, row).getContents().trim()));
			edRegion.setCountryId(Integer.parseInt(edRegionDataSheet
					.getCell(REGION_COUNTRY_ID, row).getContents().trim()));
			edRegion.setName(edRegionDataSheet.getCell(REGION_NAME, row)
					.getContents().trim());
			edRegion.setCode(edRegionDataSheet.getCell(REGION_CODE, row)
					.getContents().trim());
			edRegions.add(edRegion);
		}
		return edRegions; // .subList(5, 7);
	}

	public List<EdCity> getCities() {
		List<EdCity> edCities = new ArrayList<EdCity>();

		Sheet edCityDataSheet = staticCountryDataWorkbook
				.getSheet(WORKSHEET_CITY);
		for (int row = 1; row <= TOTAL_CITIES; row++) {
			EdCity edCity = new EdCity();

			edCity.setId(Long.parseLong(edCityDataSheet.getCell(CITY_ID, row)
					.getContents().trim()));
			edCity.setCountryId(Long.parseLong(edCityDataSheet
					.getCell(CITY_COUNTRY_ID, row).getContents().trim()));
			edCity.setRegionID(Long.parseLong(edCityDataSheet
					.getCell(CITY_REGION_ID, row).getContents().trim()));
			edCity.setName(edCityDataSheet.getCell(CITY_NAME, row)
					.getContents().trim());
			edCity.setLatitude(edCityDataSheet.getCell(CITY_LATITUDE, row)
					.getContents().trim());
			edCity.setLongitude(edCityDataSheet.getCell(CITY_LONGITUDE, row)
					.getContents().trim());
			edCity.setTimeZone(edCityDataSheet.getCell(CITY_TIMEZONE, row)
					.getContents().trim());
			edCity.setCode(edCityDataSheet.getCell(CITY_CODE, row)
					.getContents().trim());
			edCities.add(edCity);
		}

		return edCities;
	}
}
