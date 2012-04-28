package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdIndicator;

public interface EdIndicatorDAO extends GenericDAO<EdIndicator> {

	final String JNDI_NAME = "EdIndicatorDAO";

	List<EdIndicator> findAll();

	void mergeCollection(Collection<EdIndicator> edCities);

	void persistCollection(Collection<EdIndicator> edCities);

}
