package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdRegion;

public interface EdRegionDAO extends GenericDAO<EdRegion> {

	final String JNDI_NAME = "EdRegionDAO";

	List<EdRegion> findAll();

	void mergeCollection(Collection<EdRegion> edCountries);

	void persistCollection(Collection<EdRegion> importedStaticDataEdRegions);

}
