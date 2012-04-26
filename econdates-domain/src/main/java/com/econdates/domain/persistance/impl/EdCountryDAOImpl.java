package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.persistance.EdCountryDAO;

@Repository
public class EdCountryDAOImpl extends GenericEjb3DAO<EdCountry> implements
		EdCountryDAO {

	@SuppressWarnings("unchecked")
	public List<EdCountry> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	

}
