package com.econdates.fixtures;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.econdates.domain.entities.EdIndicator;
import com.econdates.domain.entities.EdIndicator.Importance;
import com.econdates.domain.entities.EdScheduled;

public class EdIndicatorFixture {

	private static final String NAME = "AIG Construction Index";

	public static EdIndicator setsUpExampleAIGEdIndicator() {
		EdIndicator edIndicator = new EdIndicator();
		edIndicator.setName(NAME);
		edIndicator.setImportance(Importance.Low);
		edIndicator.setReleaseTime(new LocalTime().plusSeconds(20));
		return edIndicator;
	}
	
	

}
