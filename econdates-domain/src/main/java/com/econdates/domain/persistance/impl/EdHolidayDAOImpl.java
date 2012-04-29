package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdHoliday;
import com.econdates.domain.persistance.EdHolidayDAO;

@Repository
public class EdHolidayDAOImpl extends GenericEjb3DAO<EdHoliday> implements
		EdHolidayDAO {

	@SuppressWarnings("unchecked")
	public List<EdHoliday> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}


}