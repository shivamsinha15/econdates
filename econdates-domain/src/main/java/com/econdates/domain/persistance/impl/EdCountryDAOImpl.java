package com.econdates.domain.persistance.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.econdates.domain.entities.EdCountry;
import com.econdates.domain.persistance.EdCountryDAO;

@Repository
public class EdCountryDAOImpl extends GenericEjb3DAO<EdCountry> implements
		EdCountryDAO {
	private static final Logger LOG = LoggerFactory
			.getLogger(EdCountryDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<EdCountry> findAll() {
		return entityManager.createQuery(
				"from " + getEntityBeanType().getName()).getResultList();
	}

	public EdCountry findByName(String countryName) {
		try{
		return  (EdCountry) entityManager.createQuery(
				"from " + getEntityBeanType().getName() + " e WHERE e.countryName='" +countryName + "'").getSingleResult();
		} catch(NoResultException nre){
				LOG.info("Country does not exist in Database " + countryName);
				return null;
		}
	}
	
	public EdCountry findByNames(String countryName,String eventName) {
		try{
		return  (EdCountry) entityManager.createQuery(
				"from " + getEntityBeanType().getName() + " e WHERE e.countryName='" +countryName + "'").getSingleResult();
		} catch(NoResultException nre){
				LOG.info("Country does not exist in Database " + countryName + ", For Event: " + eventName);
				throw nre;
		}
	}

	@Override
	@PersistenceContext(unitName = "EconDatesDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

}
