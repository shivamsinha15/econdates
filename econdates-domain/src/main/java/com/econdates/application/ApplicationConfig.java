package com.econdates.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


//<context:component-scan base-package="org.jboss.spring.samples.orders.domain"/>

@Configuration
@ComponentScan(basePackages = "org.jboss.spring.samples.orders.domain", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
public class ApplicationConfig {

//	 <bean id="localContainerEntityManagerFactorBean"
//		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
//		<property name="persistenceXmlLocation" value="META-INF/jpa-persistence.xml" />
//	</bean>
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory
				.setPersistenceXmlLocation("META-INF/jpa-persistence.xml");
		return entityManagerFactory;

	}

}
