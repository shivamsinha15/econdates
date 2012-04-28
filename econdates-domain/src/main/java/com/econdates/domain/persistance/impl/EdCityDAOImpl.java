package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdCity;
import com.econdates.domain.persistance.EdCityDAO;

@Repository
public class EdCityDAOImpl extends GenericEjb3DAO<EdCity> implements
		EdCityDAO {

	@SuppressWarnings("unchecked")
	public List<EdCity> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	

}
