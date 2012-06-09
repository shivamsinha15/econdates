package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdJob;
import com.econdates.domain.persistance.EdJobDAO;
import com.econdates.util.DateUtil;

@Repository
public class EdJobDAOImpl extends GenericEjb3DAO<EdJob> implements EdJobDAO {

	@SuppressWarnings("unchecked")
	public List<EdJob> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@SuppressWarnings("unchecked")
	public List<EdJob> findByGreaterThanOrEqualToDateValue(LocalDate date) {

		StringBuilder findByReleaseDate = new StringBuilder();
		findByReleaseDate.append("from " + getEntityBeanType().getName());
		findByReleaseDate.append(" e WHERE e.executedDate >='"
				+ date.toString(DateUtil.fmt) + "'");

		return (List<EdJob>) entityManager.createQuery(
				findByReleaseDate.toString()).getResultList();

	}

}
