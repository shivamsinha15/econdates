package com.econdates.schedulingengine;

import org.joda.time.LocalDate;
import org.quartz.Scheduler;

public interface EdIndicatorScheduler {

	Scheduler getEdIndicatorValueScheduler();

	void scheduleEdIndicatorValueJobsForDate(LocalDate date);

}
