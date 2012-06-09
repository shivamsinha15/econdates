package com.econdates.fixtures;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;

public class EdIndicatorValueFixture {

	public static EdHistory getEdHistory() {

		EdHistory expectedEdHistory = new EdHistory();
		expectedEdHistory.setActual("40.2");
		expectedEdHistory.setAnalysis(null);
		expectedEdHistory.setConsensus(null);
		expectedEdHistory.setPrevious("43.8");
		expectedEdHistory.setReleaseDate(new LocalDate(2011, 2, 6));
		expectedEdHistory.setRevised(null);
		return expectedEdHistory;

	}

	public static EdScheduled getEdScheduled() {
		EdScheduled expectedEdScheduled = new EdScheduled();
		expectedEdScheduled.setActual(null);
		expectedEdScheduled.setAnalysis(null);
		expectedEdScheduled.setConsensus(null);
		expectedEdScheduled.setPrevious("40.2");
		expectedEdScheduled.setReleaseDate(new LocalDate(2029, 2, 6));
		expectedEdScheduled.setRevised(null);
		return expectedEdScheduled;
	}

	public static Set<EdHistory> getSetOfEdHistory(LocalDate startDate,
			LocalDate endDate) {
		Set<EdHistory> edHistories = new HashSet<EdHistory>();

		int actual = 1;
		int previous = 0;

		while (startDate.isAfter(endDate)) {
			String actualAsString = String.valueOf(actual);
			String previousASString = String.valueOf(previous);
			EdHistory expectedEdHistory = new EdHistory();
			expectedEdHistory.setActual(actualAsString);
			expectedEdHistory.setAnalysis(null);
			expectedEdHistory.setConsensus(null);
			expectedEdHistory.setPrevious(previousASString);
			expectedEdHistory.setReleaseDate(startDate);
			expectedEdHistory.setRevised(null);
			edHistories.add(expectedEdHistory);
			startDate = startDate.minusDays(1);
			actual++;
			previous++;
		}

		// Note EdScheduled contains no actual value, this is the way we
		// distinguish between, between Historical and Scheduled Indicator
		// value when parsing, hence the following scheduled should be saved in
		// EdScheduled table.
		EdHistory scheduled = new EdHistory();
		scheduled.setActual(null);
		scheduled.setAnalysis(null);
		scheduled.setConsensus(null);
		scheduled.setPrevious(String.valueOf(previous));
		scheduled.setReleaseDate(endDate);
		scheduled.setRevised(null);
		edHistories.add(scheduled);
		return edHistories;
	}
}
