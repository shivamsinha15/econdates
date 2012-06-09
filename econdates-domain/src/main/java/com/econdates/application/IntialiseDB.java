package com.econdates.application;

import javax.ejb.Local;

import org.jboss.annotation.ejb.Management;

@Local
@Management
public interface IntialiseDB {

	void initalizeCountryDb();

	static final String OBJECT_NAME = "com.econdates.application:service=InitialiseDBMBean";

}
