package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.econdates.dataharvesterengine.EconDateInitDatabase;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdIndicatorDAO;
import com.econdates.domain.persistance.EdIndicatorValueDAO;

@ContextConfiguration(locations = { "classpath:application-testcontext.xml",
		"classpath:test-infrastructure.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
public class EdIndicatorValueDAOImplTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private static final long COUNTRY_ID = 14;
	private static final String NAME = "AIG Construction Index";

	DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
	private EdIndicator aigIndicator;
	private EdHistory expectedEdHistory;
	private EdScheduled expectedEdScheduled;

	@Autowired
	EdIndicatorDAO edIndicatorDAOImpl;

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	@Autowired
	EconDateInitDatabase econDateInitDbImpl;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	@Before
	public void setUp() {
		initDbForTest();
	}

	private void initDbForTest() {

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
		expectedEdScheduled.setReleaseDate(new LocalDate(2029, 2, 6));
		expectedEdScheduled.setRevised(null);

		expectedEdHistory.setEdIndicator(aigIndicator);
		expectedEdScheduled.setEdIndicator(aigIndicator);

		EdHistory edHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		if (edHistory == null) {
			edIndicatorValueDAOImpl.persist(expectedEdHistory);
		}

		EdScheduled edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		if (edScheduled == null) {
			edIndicatorValueDAOImpl.persist(expectedEdScheduled);
		}
	}

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void testFindByEdHistory() {
		EdHistory actualEdHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		assertEquals(expectedEdHistory, actualEdHistory);
	}

	@Test
	public void testFindEdScheduled() {
		EdScheduled actualEdScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		assertEquals(expectedEdScheduled, actualEdScheduled);
	}

	@Test
	public void testRemoveEdScheduled() {
		TransactionStatus status = transactionManager.getTransaction(null);

		EdScheduled edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		edIndicatorValueDAOImpl.delete(edScheduled);
		EdHistory actualEdHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		transactionManager.commit(status);

		edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		actualEdHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);
		assertNull(edScheduled);
		assertNotNull(actualEdHistory);
	}

	@Test
	public void testMoveFromEdScheduledToAnotherEdIndicatorValueEntity() {

		EdScheduled toBeMovedEdScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);

		toBeMovedEdScheduled.setActual("0.99");

		edIndicatorValueDAOImpl
				.moveFromEdScheduledToAnotherEdIndicatorValueEntity(
						toBeMovedEdScheduled, EdHistory.class);

		toBeMovedEdScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);

		EdHistory edHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdHistory.class);

		assertNull(toBeMovedEdScheduled);
		assertNotNull(edHistory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveFromEdScheduledToAnotherEdIndicatorValueEntityWithIllegalArgumentException() {

		EdScheduled toBeMovedEdScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);

		edIndicatorValueDAOImpl
				.moveFromEdScheduledToAnotherEdIndicatorValueEntity(
						toBeMovedEdScheduled, EdHistory.class);
	}

	@Test
	public void edEdHistoryEquals() {

		EdHistory other = new EdHistory();
		other.setActual("40.2");
		other.setAnalysis(null);
		other.setConsensus("");
		other.setPrevious("43.8");
		other.setReleaseDate(new LocalDate(2011, 2, 6));
		other.setRevised(null);
		other.setEdIndicator(aigIndicator);

		assertEquals(expectedEdHistory, other);

	}

}