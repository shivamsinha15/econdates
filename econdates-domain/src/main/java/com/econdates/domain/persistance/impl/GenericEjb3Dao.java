package com.econdates.domain.persistance.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.domain.persistance.GenericDAO;

public abstract class GenericEjb3DAO<T> implements GenericDAO<T> {

	private Class<T> entityBeanType;
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericEjb3DAO() {
		this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getEntityBeanType() {
		return entityBeanType;
	}

	@SuppressWarnings("unchecked")
	public Class<T> setEntityBeanType(Class<?> returnType) {
		return this.entityBeanType = (Class<T>) returnType;
	}

	public T findById(Long id) {
		return entityManager.find(getEntityBeanType(), id);
	}

	public T saveOrUpdate(T entity) {
		persist(entity);
		return entity;
	}

	public void delete(T entity) {
		entityManager.remove(entity);
		// entityManager.flush();
	}

	/**
	 * The semantics of the persist operation, applied to an entity X are as
	 * follows:
	 * 
	 * If X is a new entity, it becomes managed. The entity X will be entered
	 * into the database at or before transaction commit or as a result of the
	 * flush operation.
	 * 
	 * If X is a preexisting managed entity, it is ignored by the persist
	 * operation. However, the persist operation is cascaded to entities
	 * referenced by X, if the relationships from X to these other entities are
	 * annotated with the cascade=PERSIST or cascade=ALL annotation element
	 * value or specified with the equivalent XML descriptor element.
	 * 
	 * If X is a removed entity, it becomes managed.
	 * 
	 * If X is a detached object, the EntityExistsException may be thrown when
	 * the persist operation is invoked, or the EntityExistsException or another
	 * PersistenceException may be thrown at flush or commit time.
	 * 
	 * For all entities Y referenced by a relationship from X, if the
	 * relationship to Y has been annotated with the cascade element value
	 * cascade=PERSIST or cascade=ALL, the persist operation is applied to Y.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void persistCollection(Collection<T> entities) {
		for (T t : entities) {
			System.out.println("Persisting: " + t.toString());
			persist(t);
		}

	}

	// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	// entityManager.flush();
	public void persist(T t) {
		entityManager.persist(t);
		entityManager.flush();
	}

	/**
	 * MyEntity e = new MyEntity();
	 * 
	 * // scenario 1 // tran starts em.persist(e); e.setSomeField(someValue); //
	 * tran ends, and the row for someField is updated in the database
	 * 
	 * 
	 * // scenario 2 / / tran starts e = new MyEntity(); em.merge(e);
	 * e.setSomeField(anotherValue); // tran ends but the row for someField is
	 * not updated in the database (you made the changes *after* merging
	 * 
	 * 
	 * // scenario 3 // tran starts e = new MyEntity(); MyEntity e2
	 * =em.merge(e); e2.setSomeField(anotherValue); // tran ends and the row for
	 * someField is updated (the changes were made to e2, not e)
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void mergeCollection(Collection<T> entities) {
		for (T t : entities) {
			System.out.println("Merging: " + t.toString());
			merge(t);
		}
	}

	public void merge(T entity) {
		entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	public T getMaxEntity() {
		try {
			return (T) entityManager
					.createQuery("from " + getEntityBeanType().getName())
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public abstract void setEntityManager(EntityManager entityManager);

}
