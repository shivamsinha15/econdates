package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdCountry;

public interface EdCountryDAO extends GenericDAO<EdCountry> {

	final String JNDI_NAME = "EdCountryDAO";

	List<EdCountry> findAll();

	void mergeCollection(Collection<EdCountry> edCountries);

	void persistCollection(Collection<EdCountry> edCountries);
	
	EdCountry findByName(String countryName);

	EdCountry findByNames(String eventCountry, String eventName);
	
	

}
