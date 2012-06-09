package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import com.econdates.domain.entities.EdJob;

public interface EdJobDAO extends GenericDAO<EdJob> {

	final String JNDI_NAME = "EdJobDAO";

	List<EdJob> findAll();

	void mergeCollection(Collection<EdJob> edJobs);

	void persistCollection(Collection<EdJob> edJobs);

	List<EdJob> findByGreaterThanOrEqualToDateValue(LocalDate date);

}
