package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.econdates.domain.persistance.EdCountryDAO;

@Repository
public class EdIndicatorDAOImpl extends GenericEjb3DAO<EdIndicator> implements
		EdIndicatorDAO {

	@SuppressWarnings("unchecked")
	public List<EdIndicator> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

}
