package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.persistance.EdHistoryDAO;
import com.google.common.base.Strings;

@Repository
public class EdHistoryDAOImpl extends GenericEjb3DAO<EdHistory> implements
		EdHistoryDAO {

	@SuppressWarnings("unchecked")
	public List<EdHistory> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public EdHistory findByEdHistory(EdHistory edHistory) {
		try {
			return (EdHistory) entityManager.createQuery(
					buildFindByEdHistoryQuery(edHistory)).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	private String buildFindByEdHistoryQuery(EdHistory edHistory) {
		StringBuilder findByEdHistory = new StringBuilder();
		findByEdHistory.append("from " + getEntityBeanType().getName()
				+ " e WHERE e.actual='" + edHistory.getActual());

		if (!Strings.isNullOrEmpty(edHistory.getConsensus())) {
			findByEdHistory.append("' AND e.consensus ='"
					+ edHistory.getConsensus());
		}

		if (!Strings.isNullOrEmpty(edHistory.getRevised())) {
			findByEdHistory.append("' AND e.revised ='"
					+ edHistory.getRevised());
		}

		if (!Strings.isNullOrEmpty(edHistory.getPrevious())) {
			findByEdHistory.append("' AND e.previous ='"
					+ edHistory.getPrevious());
		}

		findByEdHistory.append("' AND e.edIndicator ='"
				+ edHistory.getEdIndicator().getId() + "'");

		return findByEdHistory.toString();
	}
}