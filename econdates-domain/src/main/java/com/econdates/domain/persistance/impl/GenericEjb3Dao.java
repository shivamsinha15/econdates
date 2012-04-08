package com.econdates.domain.persistance.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import com.econdates.domain.persistance.GenericDAO;

public abstract class GenericEjb3Dao<T> implements GenericDAO<T> {

	private final Class<T> entityBeanType;
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericEjb3Dao() {
		this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getEntityBeanType() {
		return entityBeanType;
	}

	public T findById(Long id) {
		return entityManager.find(getEntityBeanType(), id);
	}

	public T saveOrUpdate(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	

}
