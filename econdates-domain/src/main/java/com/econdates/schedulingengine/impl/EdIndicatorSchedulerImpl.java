package com.econdates.schedulingengine.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.dataharvesterengine.HarvestLocation;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicatorValue;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.factory.EdIndicatorValueFactory;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.schedulingengine.EdIndicatorScheduler;

/**
 * @author shivamsinha
 * 
 */

@Service
public class EdIndicatorSchedulerImpl implements EdIndicatorScheduler,
		ApplicationContextAware {

	@Autowired
	EdIndicatorValueDAO edIndValDAOImpl;

	@Autowired
	HarvestLocation forexPro;

	@Autowired
	EdIndicatorValueFactory edIndValFactoryImpl;

	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	private Scheduler edIndicatorValueScheduler;
	private ApplicationContext applicationContext;

	private static final Logger LOG = LoggerFactory
			.getLogger(EdIndicatorSchedulerImpl.class);

	public EdIndicatorSchedulerImpl() throws SchedulerException {

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		schedulerFactoryBean
				.setApplicationContextSchedulerContextKey("applicationContextJobDataKey");
		schedulerFactoryBean.setApplicationContext(applicationContext);
		this.edIndicatorValueScheduler = schedulerFactoryBean.getScheduler();

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void scheduleEdIndicatorValueJobsForDate(LocalDate Date) {
		List<EdIndicatorValue> scheduledIndicatorValues = edIndValDAOImpl
				.findByDateValue(new LocalDate(), EdScheduled.class);

		for (EdIndicatorValue toBeScheduled : scheduledIndicatorValues) {
			EdScheduled edScheduled = (EdScheduled) toBeScheduled;
			JobDetail job = createJobDetail(edScheduled);
			SimpleTrigger trigger = createJobTrigger(edScheduled);
			try {
				LOG.info("Scheduling Job: " + job.getName());
				edIndicatorValueScheduler.scheduleJob(job, trigger);
			} catch (SchedulerException e) {
				// todo
			}

		}

	}

	private JobDetail createJobDetail(EdScheduled toBeScheduled) {
		JobDetail job = new JobDetail();
		job.setJobClass(ForexProJob.class);

		job.setName(toBeScheduled.getEdIndicator().getName() + ";"
				+ toBeScheduled.getReleaseDate());
		Map dataMap = job.getJobDataMap();
		dataMap.put("edScheduled", toBeScheduled);
		dataMap.put("forexPro", forexPro);
		dataMap.put("edIndValDAOImpl", edIndValDAOImpl);
		return job;

	}

	private SimpleTrigger createJobTrigger(EdScheduled toBeScheduled) {
		SimpleTrigger trigger = new SimpleTrigger();
		Date triggerTime = getTriggerDate(toBeScheduled);
		trigger.setStartTime(triggerTime);
		trigger.setName(toBeScheduled.getEdIndicator().getName() + ";"
				+ toBeScheduled.getReleaseDate());
		return trigger;
	}

	private Date getTriggerDate(EdScheduled edScheduled) {
		EdIndicator edIndicator = edScheduled.getEdIndicator();
		LocalTime releaseTime = edIndicator.getReleaseTime();
		LocalDate releaseDate = edScheduled.getReleaseDate();

		int hourOfDay = releaseTime.getHourOfDay();
		int minuteOfHour = releaseTime.getMinuteOfHour();
		int secondOfMinute = releaseTime.getSecondOfMinute();
		int millisOfSecond = releaseTime.getMillisOfSecond();

		int dayOfMonth = releaseDate.getDayOfMonth();
		int monthOfyear = releaseDate.getMonthOfYear();
		int year = releaseDate.getYear();

		DateTime releaseDateTime = new DateTime(year, monthOfyear, dayOfMonth,
				hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);

		return releaseDateTime.toDate();
	}

	public Scheduler getEdIndicatorValueScheduler() {
		return edIndicatorValueScheduler;
	}

}
