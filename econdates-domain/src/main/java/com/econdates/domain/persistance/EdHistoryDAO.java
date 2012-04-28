package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdHistory;

public interface EdHistoryDAO extends GenericDAO<EdHistory> {

	final String JNDI_NAME = "EdHistoryDAO";

	List<EdHistory> findAll();

	void mergeCollection(Collection<EdHistory> edHistory);

	void persistCollection(Collection<EdHistory> edHistory);

}
