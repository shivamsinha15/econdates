package com.econdates.domain.persistance;

/**
 * A generic database access object for CRUD (Create, Read,Update & Destroy)
 * type functionality
 * 
 * @author shivamsinha
 * 
 */


public interface GenericDAO<T> {

	
	/**
	 * Returns the instance of the item from the database code
	 * @param id - Primary Key id of item
	 * @return The item read from the database
	 */
	T findById(Long id);

	T saveOrUpdate(T entity);

	void delete(T entity);

}