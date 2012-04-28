package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;

public interface EdIndicatorDAO extends GenericDAO<EdIndicator> {

	final String JNDI_NAME = "EdIndicatorDAO";

	List<EdIndicator> findAll();

	void mergeCollection(Collection<EdIndicator> edIndicator);

	void persistCollection(Collection<EdIndicator> edIndicator);

	EdIndicator findByNameCountryAndImportance(String name, long edCountry,
			Importance importance);

}
