package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdJob;
import com.econdates.domain.persistance.EdJobDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EdJobDAOImplTest {

	@Autowired
	EdJobDAO edJobDAOImpl;

	@Autowired
	EconDateInitDatabase edInitDbImpl;

	@Test
	public void testUpdateDoesNotProduceDuplicates() {

		LocalDate startDate = new LocalDate(2012, 01, 30);
		LocalDate endDate = new LocalDate(2012, 01, 20);

		EdJob edJob = new EdJob();
		edJob.setName("Initialize ED db");

		while (startDate.isAfter(endDate)) {
			edJob.setLastUpdated(new DateTime());
			edJob.setReleaseDate(startDate);
			edJobDAOImpl.saveOrUpdate(edJob);
			startDate = startDate.minusDays(1);
		}

		assertEquals(1,
				edJobDAOImpl.findByGreaterThanOrEqualToDateValue(endDate)
						.size());

	}
}
