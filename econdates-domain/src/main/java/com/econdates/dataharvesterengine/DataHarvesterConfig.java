package com.econdates.dataharvesterengine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataHarvesterConfig {


	@Bean
	public ForexPro forexPro() {
		return new ForexPro();
	}

}
