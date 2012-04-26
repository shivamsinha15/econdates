package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdRegion;
import com.econdates.domain.persistance.EdRegionDAO;

@Repository
public class EdRegionDAOImpl extends GenericEjb3DAO<EdRegion> implements
		EdRegionDAO {

	@SuppressWarnings("unchecked")
	public List<EdRegion> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}


}
