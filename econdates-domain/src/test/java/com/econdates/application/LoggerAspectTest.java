package com.econdates.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.HelloService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-infrastructure.xml")
public class LoggerAspectTest {

	@Autowired
	private HelloService helloServiceImpl;


	@LogPlease
	@Test
	public void testAroundLogGreetMethodExecution() {
		helloServiceImpl.greeting("Shiv");
	}

}
