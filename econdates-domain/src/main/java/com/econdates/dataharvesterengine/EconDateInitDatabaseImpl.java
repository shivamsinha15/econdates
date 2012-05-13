package com.econdates.dataharvesterengine;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.econdates.domain.entities.EdCity;
import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdHoliday;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdRegion;
import com.econdates.domain.persistance.EdCityDAO;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdHolidayDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.domain.persistance.EdRegionDAO;

@Service
public class EconDateInitDatabaseImpl implements EconDateInitDatabase {

	private static final String EURO_ZONE = "Euro Zone";
	private boolean isCountryDataInit = false;
	private boolean isRegionDataInit = false;
	private boolean isCityDataInit = false;
	private boolean isHolidayDataInit = false;
	private boolean isEuroZoneAsCountryDataInit = false;
	private boolean isIndicatorAndHistoryDAOInit = false;

	private static final Logger LOG = LoggerFactory
			.getLogger(EconDateInitDatabaseImpl.class);

	private static final int ONE_DAY = 1;
	private static final long COUNTRY_ID = 14;
	private static final String COUNTRY_NAME = "Australia";
	private static final String NAME = "AIG Construction Index";

	@Autowired
	EdCountryDAO edCountryDAOImpl;

	@Autowired
	EdRegionDAO edRegionDAOImpl;

	@Autowired
	EdCityDAO edCityDAOImpl;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	@Autowired
	EdHolidayDAO edHolidayDAOImpl;

	@Autowired
	ImportStaticData importStaticDataImpl;

	@Autowired
	HarvestLocation forexPro;

	public void initHolidayData(LocalDate startDate, LocalDate endDate)
			throws IOException {
		if (!isHolidayDataInit()) {
			while (startDate.isAfter(endDate)) {

				List<EdHoliday> edHolidays = forexPro
						.getEdHolidaysForASingleDay(startDate);
				for (EdHoliday edHoliday : edHolidays) {
					edHolidayDAOImpl.persist(edHoliday);
				}
				startDate = startDate.minusDays(ONE_DAY);
			}
		}
	}

	public void initIndicatorAndHistoryData(LocalDate startDate,
			LocalDate endDate) throws IOException {
		forexPro.setAttachHistoricalDataToIndicators(true);
		forexPro.setAttachMoreDetailsToIndicators(true);
		if (!isIndicatorAndHistoryDataInit()) {
			while (startDate.isAfter(endDate)) {
				// for a date get all the Indicators and the associated
				// EdHistories
				List<EdIndicator> edIndicators = forexPro
						.getEconomicIndicatorsForSingleDay(startDate);

				// for each indicator determine if it is in the database if not
				// persist
				for (EdIndicator edIndicator : edIndicators) {
					EdIndicator dbEdIndicator = edIndicatorDAOImpl
							.findByNameCountryAndImportance(edIndicator
									.getName(), edIndicator.getEdCountry()
									.getId(), edIndicator.getImportance());

					if (dbEdIndicator == null) {
						edIndicatorDAOImpl.persist(edIndicator);
					}
					validateAndPersistHistoricalData(edIndicator
							.getEdHistories());
				}
				startDate = startDate.minusDays(ONE_DAY);
			}
		}
	}

	public void validateAndPersistHistoricalData(Set<EdHistory> edHistories) {

		EdHistory nextEdHistory;

		TreeSet<EdHistory> sortedSetHistories = new TreeSet<EdHistory>();
		sortedSetHistories.addAll(edHistories);

		for (EdHistory currentEdHistory : sortedSetHistories) {
			nextEdHistory = sortedSetHistories.higher(currentEdHistory);
			boolean valid = false;

			if (!(nextEdHistory == null)) {
				valid = ((currentEdHistory.getActual().equals(
						nextEdHistory.getPrevious()) || currentEdHistory
						.getActual().equals(nextEdHistory.getRevised())));
			}

			EdHistory dbEdHistory = (EdHistory) edIndicatorValueDAOImpl
					.findByEdIndicatorValue(currentEdHistory, EdHistory.class);
			if (dbEdHistory == null) {
				currentEdHistory.setValidated(valid);
				edIndicatorValueDAOImpl.persist(currentEdHistory);
			}
		}
	}

	public void initCountryData() {
		List<EdCountry> edCountries = edCountryDAOImpl.findAll();

		if (edCountries.isEmpty()) {
			LOG.info("Initialising Country Table");
			List<EdCountry> importedStaticDataEdCountries = importStaticDataImpl
					.getCountries();
			LOG.info("Number of EdCountry to be added: "
					+ importedStaticDataEdCountries.size());
			edCountryDAOImpl.persistCollection(importedStaticDataEdCountries);
		}

		isCountryDataInit();
	}

	public void initRegionData() {
		List<EdRegion> edRegions = edRegionDAOImpl.findAll();

		if (edRegions.isEmpty()) {
			LOG.info("Initialising Region Table");
			List<EdRegion> importedStaticDataEdRegions = importStaticDataImpl
					.getRegions();
			LOG.info("Number of EdRegions to be added: "
					+ importedStaticDataEdRegions.size());
			edRegionDAOImpl.mergeCollection(importedStaticDataEdRegions);
		}

	}

	public void initCityData() {
		List<EdCity> edCities = edCityDAOImpl.findAll();

		if (edCities.isEmpty()) {
			LOG.info("Initialising City Table");
			List<EdCity> importStaticDataEdCities = importStaticDataImpl
					.getCities();
			LOG.info("Number of EdCity to be added: "
					+ importStaticDataEdCities.size());
			edCityDAOImpl.mergeCollection(importStaticDataEdCities);
		}

		LOG.info(edCityDAOImpl.findAll().size()
				+ " Entries have been added to the EdCity Table");

	}

	public void initEuroZoneAsCountry() {
		EdCountry edCountry = edCountryDAOImpl.findByName(EURO_ZONE);
		if (edCountry == null) {
			EdCountry euroZone = new EdCountry();
			euroZone.setCountryName(EURO_ZONE);
			euroZone.setCurrencyCode("EUR");
			euroZone.setCurrencyName("Euros");
			edCountryDAOImpl.merge(euroZone);
		}
		isEuroZoneAsCountryDataInit();
	}

	public boolean isEuroZoneAsCountryDataInit() {
		EdCountry edCountry = edCountryDAOImpl.findByName(EURO_ZONE);
		if (edCountry != null) {
			setEuroZoneAsCountryDataInit(true);
			LOG.info("Euro Zone has been initalised");
		}
		return isEuroZoneAsCountryDataInit;
	}

	public boolean isCountryDataInit() {
		int countriesInDatabase = edCountryDAOImpl.findAll().size();

		if (countriesInDatabase == ImportStaticData.TOTAL_COUNTRIES) {
			LOG.info("EdCountry table has been initialised with : "
					+ countriesInDatabase + " countries");
			setCountryDataInit(true);
		} else {
			LOG.info("EdCountry table has NOT been initialised with countries");
		}

		return isCountryDataInit;
	}

	public boolean isRegionDataInit() {
		int regionsInDatabase = edRegionDAOImpl.findAll().size();
		if (regionsInDatabase == ImportStaticData.TOTAL_COUNTRIES) {
			LOG.info("EdRegion table has been initialised with : "
					+ regionsInDatabase + " regions");
			setRegionDataInit(true);
		} else {
			LOG.info("EdTables table has NOT been initialised with regions");
		}

		return isRegionDataInit;
	}

	public boolean isCityDataInit() {
		int citiesInDatabase = edCityDAOImpl.findAll().size();
		if (citiesInDatabase == ImportStaticData.TOTAL_CITIES) {
			LOG.info("EdCity table has been initialised with : "
					+ citiesInDatabase + " Cities");
			setCityDataInit(true);
		} else {
			// todo
		}
		return isCityDataInit;
	}

	public void setUpExampleAIGEdIndicator() {
		EdIndicator aigIndicator = edIndicatorDAOImpl
				.findByNameCountryAndImportance(NAME, COUNTRY_ID,
						Importance.Low);

		if (aigIndicator == null) {
			EdIndicator edIndicator = new EdIndicator();
			edIndicator.setName(NAME);
			edIndicator.setEdCountry(edCountryDAOImpl.findByName(COUNTRY_NAME));
			edIndicator.setImportance(Importance.Low);
			edIndicatorDAOImpl.saveOrUpdate(edIndicator);
		}
	}

	private void setCityDataInit(boolean isCityDataInit) {
		this.isCityDataInit = isCityDataInit;
	}

	private void setRegionDataInit(boolean isRegionDataInit) {
		this.isRegionDataInit = isRegionDataInit;

	}

	private void setCountryDataInit(boolean isCountryDataInit) {
		this.isCountryDataInit = isCountryDataInit;
	}

	public void setEuroZoneAsCountryDataInit(boolean isEuroZoneAsCountryDataInit) {
		this.isEuroZoneAsCountryDataInit = isEuroZoneAsCountryDataInit;
	}

	public boolean isIndicatorAndHistoryDataInit() {
		if ((edIndicatorValueDAOImpl.getMaxEntity() != null)
				&& (edIndicatorDAOImpl.getMaxEntity() != null)) {
			setIndicatorAndHistoryDAOInit(true);
		}
		return isIndicatorAndHistoryDAOInit;
	}

	public void setIndicatorAndHistoryDAOInit(
			boolean isIndicatorAndHistoryDAOInit) {
		this.isIndicatorAndHistoryDAOInit = isIndicatorAndHistoryDAOInit;
	}

	public boolean isHolidayDataInit() {
		if (edHolidayDAOImpl.getMaxEntity() != null) {
			setHolidayDataInit(true);
		}
		return isHolidayDataInit;
	}

	public void setHolidayDataInit(boolean isHolidayDataInit) {
		this.isHolidayDataInit = isHolidayDataInit;
	}

}
