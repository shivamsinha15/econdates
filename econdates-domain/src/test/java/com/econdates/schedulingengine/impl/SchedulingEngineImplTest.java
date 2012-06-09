package com.econdates.schedulingengine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;

import com.econdates.domain.persistance.impl.AbstractPersistanceUnitTestCase;
import com.econdates.schedulingengine.EdIndicatorScheduler;

public class SchedulingEngineImplTest extends AbstractPersistanceUnitTestCase {

	@Autowired
	EdIndicatorScheduler edIndiSchedulerImpl;

	@Before
	public void setup() {
		setupCountryData();
		setupIndicator();

		setupEdHistory();
		setupEdScheduled();

		// EdHistory edHistory = (EdHistory) edIndicatorValueDAOImpl
		// .findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		// if (edHistory == null) {
		// edIndicatorValueDAOImpl.persist(expectedEdHistory);
		// }

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
