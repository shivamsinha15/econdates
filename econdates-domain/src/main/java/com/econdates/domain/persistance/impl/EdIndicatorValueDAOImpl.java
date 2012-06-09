package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicatorValue;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.factory.EdIndicatorValueFactory;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.util.DateUtil;
import com.google.common.base.Strings;

@Repository
public class EdIndicatorValueDAOImpl extends GenericEjb3DAO<EdIndicatorValue>
		implements EdIndicatorValueDAO {

	@Autowired
	EdIndicatorValueFactory edIndicatorValueFactoryImpl;

	private static final Logger LOG = LoggerFactory
			.getLogger(EdIndicatorValueDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<EdIndicatorValue> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@SuppressWarnings("unchecked")
	public List<EdIndicatorValue> findByDateValue(LocalDate date,
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue) {

		setEdIndicatorValueEntityToBeQueried(typeOfEdIndicatorValue);

		StringBuilder findByEdIndiValue = new StringBuilder();
		findByEdIndiValue.append("from " + getEntityBeanType().getName());
		findByEdIndiValue.append(" e WHERE e.releaseDate ='"
				+ date.toString(DateUtil.fmt) + "'");

		return (List<EdIndicatorValue>) entityManager.createQuery(
				findByEdIndiValue.toString()).getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<EdIndicatorValue> findByGreaterThanOrEqualToDateValue(LocalDate date,
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue) {

		setEdIndicatorValueEntityToBeQueried(typeOfEdIndicatorValue);

		StringBuilder findByEdIndiValue = new StringBuilder();
		findByEdIndiValue.append("from " + getEntityBeanType().getName());
		findByEdIndiValue.append(" e WHERE e.releaseDate >='"
				+ date.toString(DateUtil.fmt) + "'");

		return (List<EdIndicatorValue>) entityManager.createQuery(
				findByEdIndiValue.toString()).getResultList();

	}

	public EdIndicatorValue findByEdIndicatorValue(EdIndicatorValue edHistory,
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue) {
		try {
			setEdIndicatorValueEntityToBeQueried(typeOfEdIndicatorValue);
			return (EdIndicatorValue) entityManager.createQuery(
					buildFindByEdIndicatorValueQuery(edHistory))
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void moveFromEdScheduledToAnotherEdIndicatorValueEntity(
			EdScheduled fromEdScheduled,
			Class<? extends EdIndicatorValue> toEdIndicatorValue) {

		// If not a managed entity, make it a managed entity
		if (!entityManager.contains(fromEdScheduled)) {
			fromEdScheduled = (EdScheduled) findByEdIndicatorValue(
					fromEdScheduled, EdScheduled.class);
		}

		EdHistory newEdHistory = edIndicatorValueFactoryImpl
				.convertEdScheduledToEdHistory(fromEdScheduled);
		delete(fromEdScheduled);

		EdIndicatorValue edIndicatorValueToBeCopied = findByEdIndicatorValue(
				fromEdScheduled, toEdIndicatorValue);

		setEdIndicatorValueEntityToBeQueried(EdHistory.class);
		if (edIndicatorValueToBeCopied == null) {
			merge(newEdHistory);
		}
	}

	// If entityBean type = EdIndicatorValue.class then all Entities which
	// extend it will be queried separately
	private String buildFindByEdIndicatorValueQuery(
			EdIndicatorValue edIndicatorValue) {
		StringBuilder findByEdIndiValue = new StringBuilder();
		findByEdIndiValue.append("from " + getEntityBeanType().getName());
		findByEdIndiValue.append(getWhereString(edIndicatorValue));

		if (!Strings.isNullOrEmpty(edIndicatorValue.getConsensus())) {
			findByEdIndiValue.append(" AND e.consensus ='"
					+ edIndicatorValue.getConsensus() + "'");
		}

		if (!Strings.isNullOrEmpty(edIndicatorValue.getRevised())) {
			findByEdIndiValue.append(" AND e.revised ='"
					+ edIndicatorValue.getRevised() + "'");
		}

		if (!Strings.isNullOrEmpty(edIndicatorValue.getPrevious())) {
			findByEdIndiValue.append(" AND e.previous ='"
					+ edIndicatorValue.getPrevious() + "'");
		}

		if (!Strings.isNullOrEmpty(edIndicatorValue.getActual())) {
			findByEdIndiValue.append(" AND e.actual ='"
					+ edIndicatorValue.getActual() + "'");
		}

		findByEdIndiValue.append(" AND e.releaseDate ='"
				+ edIndicatorValue.getReleaseDate().toString(DateUtil.fmt) + "'");

		LOG.info(findByEdIndiValue.toString());
		return findByEdIndiValue.toString();
	}

	private String getWhereString(EdIndicatorValue edHistory) {
		String where;
		if (edHistory.getActual() != null) {
			where = " e WHERE e.actual='" + edHistory.getActual() + "'";
		} else {
			where = " e WHERE e.actual IS NULL";
		}
		LOG.info(where);
		return where;
	}

	public void setEdIndicatorValueEntityToBeQueried(
			Class<? extends EdIndicatorValue> typeOfEdIndicatorValue) {
		setEntityBeanType(typeOfEdIndicatorValue);
	}

}