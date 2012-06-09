package com.econdates.domain.persistance.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;

@Transactional
public class EdIndicatorValueDAOImplTest extends
		AbstractPersistanceUnitTestCase {

	@Before
	public void setUp() {
		setupCountryData();
		setupIndicator();
		setupEdHistory();
		setupEdScheduled();
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
		EdScheduled edScheduled = (EdScheduled) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdScheduled, EdScheduled.class);
		edIndicatorValueDAOImpl.delete(edScheduled);
		EdHistory actualEdHistory = (EdHistory) edIndicatorValueDAOImpl
				.findByEdIndicatorValue(expectedEdHistory, EdHistory.class);

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