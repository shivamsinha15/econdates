package com.econdates.domain.factory.impl;

import java.util.Date;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.econdates.domain.entities.EdError;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdProcessed;
import com.econdates.domain.entities.EdScheduled;
import com.econdates.domain.factory.EdIndicatorValueFactory;
import com.econdates.domain.persistance.EdIndicatorValueDAO;
import com.google.common.base.Preconditions;

@Component
public class EdIndicatorValueFactoryImpl implements EdIndicatorValueFactory {

	@Autowired
	EdIndicatorValueDAO edIndicatorValueDAOImpl;

	public EdHistory convertEdScheduledToEdHistory(EdScheduled edScheduled) {
		Preconditions
				.checkArgument(!StringUtil.isEmpty(edScheduled.getActual()));
		EdHistory edHistory = new EdHistory();
		edHistory.setActual(edScheduled.getActual());
		edHistory.setAnalysis(edScheduled.getAnalysis());
		edHistory.setConsensus(edScheduled.getConsensus());
		edHistory.setEdIndicator(edScheduled.getEdIndicator());
		edHistory.setPrevious(edScheduled.getPrevious());
		edHistory.setReleaseDate(edScheduled.getReleaseDate());
		edHistory.setRevised(edScheduled.getRevised());
		edHistory.setLastUpdatedDate(new Date());
		edIndicatorValueDAOImpl
				.setEdIndicatorValueEntityToBeQueried(EdHistory.class);

		boolean valid = edHistory.validate((EdHistory) edIndicatorValueDAOImpl
				.getMaxEntity());
		edHistory.setValidated(valid);
		return edHistory;
	}

	public EdError convertEdScheduledToError(EdScheduled edScheduled) {
		EdError edError = new EdError();
		edError.setActual(edScheduled.getActual());
		edError.setAnalysis(edScheduled.getAnalysis());
		edError.setConsensus(edScheduled.getConsensus());
		edError.setEdIndicator(edScheduled.getEdIndicator());
		edError.setPrevious(edScheduled.getPrevious());
		edError.setReleaseDate(edScheduled.getReleaseDate());
		edError.setRevised(edScheduled.getRevised());
		edError.setLastUpdatedDate(new Date());
		return edError;
	}

	public EdProcessed convertEdScheduledToEdProcessed(EdScheduled edScheduled) {
		EdProcessed edProcessed = new EdProcessed();
		edProcessed.setActual(edScheduled.getActual());
		edProcessed.setAnalysis(edScheduled.getAnalysis());
		edProcessed.setConsensus(edScheduled.getConsensus());
		edProcessed.setEdIndicator(edScheduled.getEdIndicator());
		edProcessed.setPrevious(edScheduled.getPrevious());
		edProcessed.setReleaseDate(edScheduled.getReleaseDate());
		edProcessed.setRevised(edScheduled.getRevised());
		edProcessed.setLastUpdatedDate(new Date());
		return edProcessed;
	}

}
