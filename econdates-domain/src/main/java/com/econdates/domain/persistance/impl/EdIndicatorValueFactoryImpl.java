package com.econdates.domain.persistance.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.persistance.EdIndicatorValueDAO;

@Component
public class EdIndicatorValueFactoryImpl implements EdIndicatorValueFactory {

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	public EdHistory convertEdScheduledToEdHistory(EdScheduled edScheduled) {
		EdHistory edHistory = new EdHistory();
		edHistory.setActual(edScheduled.getActual());
		edHistory.setAnalysis(edScheduled.getAnalysis());
		edHistory.setConsensus(edScheduled.getConsensus());
		edHistory.setEdIndicator(edScheduled.getEdIndicator());
		edHistory.setPrevious(edScheduled.getPrevious());
		edHistory.setReleaseDate(edScheduled.getReleaseDate());
		edHistory.setRevised(edScheduled.getRevised());

		edIndicatorValueDAOImpl
				.setEdIndicatorValueEntityToBeQueried(EdHistory.class);

		boolean valid = edHistory.validate((EdHistory) edIndicatorValueDAOImpl
				.getMaxEntity());
		edHistory.setValidated(valid);
		return edHistory;
	}

}
