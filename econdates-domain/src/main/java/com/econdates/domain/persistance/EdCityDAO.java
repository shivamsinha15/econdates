package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdCity;

public interface EdCityDAO extends GenericDAO<EdCity> {

	final String JNDI_NAME = "EdCityDAO";

	List<EdCity> findAll();

	void mergeCollection(Collection<EdCity> edCities);

	void persistCollection(Collection<EdCity> edCities);

}
