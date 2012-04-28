package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.persistance.EdHistoryDAO;
import com.econdates.domain.persistance.EdCountryDAO;

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

	

}
