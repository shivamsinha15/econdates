package com.econdates.application;

import javax.interceptor.Interceptors;

import org.jboss.annotation.ejb.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.econdates.dataharvesterengine.EconDateInitDatabase;

@Service(objectName = IntialiseDB.OBJECT_NAME)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class IntialiseDBImpl implements IntialiseDB {

	@Autowired
	EconDateInitDatabase edInitDbImpl;

	public void initalizeCountryDb() {
		edInitDbImpl.initCityData();
	}

}
