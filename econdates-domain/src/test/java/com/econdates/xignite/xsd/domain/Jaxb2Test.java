package com.econdates.xignite.xsd.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.econdates.xignite.webservice.XigniteWebServiceProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class Jaxb2Test implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	private XigniteWebServiceProxy xigniteWebService;

	@Before
	public void init() {
		xigniteWebService = (XigniteWebServiceProxy) applicationContext
				.getBean("xigniteWebServiceProxy");
	}

	@Test
	public void getEventsForTomorrow() throws InterruptedException {
		xigniteWebService.ListEventCodes();
		assertEquals(1, 1);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
