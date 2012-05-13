package com.econdates.domain.persistance;

import java.util.Collection;
import java.util.List;

import com.econdates.domain.entities.EdIndicatorValue;
import com.econdates.domain.entities.EdScheduled;

public interface EdIndicatorValueDAO extends GenericDAO<EdIndicatorValue> {

	final String JNDI_NAME = "EdHistoryDAO";

	List<EdIndicatorValue> findAll();

	void mergeCollection(Collection<EdIndicatorValue> edHistory);

	void persistCollection(Collection<EdIndicatorValue> edHistory);

	EdIndicatorValue findByEdIndicatorValue(EdIndicatorValue edHistory,
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue);

	void moveFromEdScheduledToAnotherEdIndicatorValueEntity(
			EdScheduled fromEdScheduled,
			Class<? extends EdIndicatorValue> toEdIndicatorValue);

	void setEdIndicatorValueEntityToBeQueried(
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue);

}
