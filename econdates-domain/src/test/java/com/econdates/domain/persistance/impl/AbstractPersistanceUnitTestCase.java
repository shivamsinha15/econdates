package com.econdates.domain.persistance.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdCountryDAO;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.econdates.fixtures.EdIndicatorFixture;
import com.econdates.fixtures.EdIndicatorValueFixture;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
public abstract class AbstractPersistanceUnitTestCase {

	protected EdIndicator aigIndicator;
	protected EdHistory expectedEdHistory;
	protected EdScheduled expectedEdScheduled;

	protected static final long COUNTRY_ID = 14;
	protected static final String NAME = "AIG Construction Index";
	protected static final String COUNTRY_NAME = "Australia";

	@Autowired
	protected EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	protected EdIndicatorValueDAO edIndicatorValueDAOImpl;

	@Autowired
	protected EdCountryDAO edCountryDAOImpl;

	@Autowired
	protected EconDateInitDatabase econDateInitDbImpl;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	// Control Transactional Boundaries
	// //TransactionStatus status = transactionManager.getTransaction(null);
	// transactionManager.commit(status);

	protected void setupCountryData() {
		if (!econDateInitDbImpl.isCountryDataInit()) {
			econDateInitDbImpl.initCountryData();
		}

	}

	protected void setupIndicator() {
		aigIndicator = edIndicatorDAOImpl.findByNameCountryAndImportance(NAME,
				COUNTRY_ID, Importance.Low);

		if (aigIndicator == null) {

			aigIndicator = EdIndicatorFixture.setsUpExampleAIGEdIndicator();
			aigIndicator
					.setEdCountry(edCountryDAOImpl.findByName(COUNTRY_NAME));

			edIndicatorDAOImpl.persist(aigIndicator);
			aigIndicator = edIndicatorDAOImpl.findByNameCountryAndImportance(
					NAME, COUNTRY_ID, Importance.Low);
		}
	}

	protected void setupEdHistory() {
		expectedEdHistory = EdIndicatorValueFixture.getEdHistory();
		expectedEdHistory.setEdIndicator(aigIndicator);
		EdHistory edHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		if (edHistory == null) {
			edIndicatorValueDAOImpl.persist(expectedEdHistory);
		}
	}

	protected void setupEdScheduled() {
		expectedEdScheduled = EdIndicatorValueFixture.getEdScheduled();
		expectedEdScheduled.setEdIndicator(aigIndicator);
		EdScheduled edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		if (edScheduled == null) {
			edIndicatorValueDAOImpl.persist(expectedEdScheduled);
		}
	}

}
