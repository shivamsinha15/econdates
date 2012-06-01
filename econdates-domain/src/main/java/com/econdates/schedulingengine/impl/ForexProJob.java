package com.econdates.schedulingengine.impl;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.econdates.dataharvesterengine.HarvestLocation;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdIndicatorValueDAO;

/**
 * @author shivamsinha
 * 
 */

public class ForexProJob extends QuartzJobBean {

	EdIndicatorValueDAO edIndValDAOImpl;

	HarvestLocation forexPro;

	/**
	 * DI for Job can be achieved via putting the dependency into JobDataMap and
	 * then retrieving them as below. Or alternatively via the application
	 * context
	 * : final BeanFactory applicationContext = (BeanFactory)
	 * context.getMergedJobDataMap().get("applicationContext");
	 *  final MyBean
	 * myBean = (MyBean) applicationContext.getBean("myBean");
	 * 
	 */

	private static final Logger LOG = LoggerFactory
			.getLogger(ForexProJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		LOG.info("Executing Job: " + context.getJobDetail().getName());
		Map dataMap = context.getJobDetail().getJobDataMap();
		EdScheduled toBeReleasedData = (EdScheduled) dataMap.get("edScheduled");
		edIndValDAOImpl = (EdIndicatorValueDAO) dataMap.get("edIndValDAOImpl");
		forexPro = (HarvestLocation) dataMap.get("forexPro");

		try {
			forexPro.populateIndicatorValuesForLatestData(toBeReleasedData);
			edIndValDAOImpl.moveFromEdScheduledToAnotherEdIndicatorValueEntity(
					toBeReleasedData, EdHistory.class);
		} catch (Exception e) {
			LOG.info("Error while executing job: "
					+ context.getJobDetail().getName());
			e.printStackTrace();
			// todo insert into error table

		}
	}

}
