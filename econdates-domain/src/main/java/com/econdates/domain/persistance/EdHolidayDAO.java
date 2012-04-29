package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdHoliday;

public interface EdHolidayDAO extends GenericDAO<EdHoliday> {

	final String JNDI_NAME = "EdHolidayDAO";

	List<EdHoliday> findAll();

	void mergeCollection(Collection<EdHoliday> edHoliday);

	void persistCollection(Collection<EdHoliday> edHoliday);


}
