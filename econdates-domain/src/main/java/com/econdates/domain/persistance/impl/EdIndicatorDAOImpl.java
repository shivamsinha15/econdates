package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.persistance.EdIndicatorDAO;

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

	public EdIndicator findByNameCountryAndImportance(String name,
			long edCountry, Importance importance) {
		
		try{
		return (EdIndicator) entityManager.createQuery(
				"from " + getEntityBeanType().getName() + " e WHERE e.name='"
						+ name + "' AND e.edCountry ='" + edCountry
						+ "'AND e.importance ='" + importance + "'")
				.getSingleResult();
		} catch (NoResultException nre){
			return null;
		}

	}
}