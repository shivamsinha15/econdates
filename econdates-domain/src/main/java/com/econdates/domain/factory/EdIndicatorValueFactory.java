package com.econdates.domain.factory;

import com.econdates.domain.entities.EdError;
import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;

public interface EdIndicatorValueFactory {
	EdHistory convertEdScheduledToEdHistory(EdScheduled edScheduled);

	EdScheduled convertEdHistoryToEdScheduled(EdHistory edHistory);

	EdError convertEdScheduledToError(EdScheduled edScheduled);

}
