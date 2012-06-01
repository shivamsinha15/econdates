package com.econdates.schedulingengine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.schedulingengine.EdIndicatorScheduler;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SchedulingEngineImplTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	EconDateInitDatabase econDateInitDbImpl;

	@Autowired
	EdIndicatorScheduler edIndiSchedulerImpl;

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	private EdIndicator aigIndicator;
	private EdHistory expectedEdHistory;
	private EdScheduled expectedEdScheduled;
	private static final long COUNTRY_ID = 14;
	private static final String NAME = "AIG Construction Index";

	@Before
	public void setup() {
		if (!econDateInitDbImpl.isCountryDataInit()) {
			econDateInitDbImpl.initCountryData();
		}
		aigIndicator = edIndicatorDAOImpl.findByNameCountryAndImportance(NAME,
				COUNTRY_ID, Importance.Low);

		if (aigIndicator == null) {
			econDateInitDbImpl.setUpExampleAIGEdIndicator();
			aigIndicator = edIndicatorDAOImpl.findByNameCountryAndImportance(
					NAME, COUNTRY_ID, Importance.Low);
		}

		expectedEdHistory = new EdHistory();
		expectedEdHistory.setActual("40.2");
		expectedEdHistory.setAnalysis(null);
		expectedEdHistory.setConsensus(null);
		expectedEdHistory.setPrevious("43.8");
		expectedEdHistory.setReleaseDate(new LocalDate(2011, 2, 6));
		expectedEdHistory.setRevised(null);

		expectedEdScheduled = new EdScheduled();
		expectedEdScheduled.setActual(null);
		expectedEdScheduled.setAnalysis(null);
		expectedEdScheduled.setConsensus(null);
		expectedEdScheduled.setPrevious("40.2");
		expectedEdScheduled.setReleaseDate(new LocalDate());
		expectedEdScheduled.setRevised(null);
		expectedEdHistory.setEdIndicator(aigIndicator);
		expectedEdScheduled.setEdIndicator(aigIndicator);

		// EdHistory edHistory = (EdHistory) edIndicatorValueDAOImpl
		// .findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		// if (edHistory == null) {
		// edIndicatorValueDAOImpl.persist(expectedEdHistory);
		// }

		EdScheduled edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		if (edScheduled == null) {
			edIndicatorValueDAOImpl.persist(expectedEdScheduled);
		}

	}

	@Test
	public void testScheduleEdIndicatorValueJobsForDate()
			throws SchedulerException, InterruptedException {

		edIndiSchedulerImpl
				.scheduleEdIndicatorValueJobsForDate(new LocalDate());
		Scheduler scheduler = edIndiSchedulerImpl
				.getEdIndicatorValueScheduler();

		JobDetail job = scheduler.getJobDetail(aigIndicator.getName() + ";"
				+ expectedEdScheduled.getReleaseDate(), null);
		Trigger trigger = scheduler.getTrigger(aigIndicator.getName() + ";"
				+ expectedEdScheduled.getReleaseDate(), null);
		scheduler.start();
		Thread.sleep(300000);

		assertNotNull(job);
		assertNotNull(trigger);

		assertNull(expectedEdScheduled);
		assertEquals(
				Trigger.STATE_COMPLETE,
				scheduler.getTriggerState(aigIndicator.getName() + ";"
						+ expectedEdScheduled.getReleaseDate(), null));

	}
}
