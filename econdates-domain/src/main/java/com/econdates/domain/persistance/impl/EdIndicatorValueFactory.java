package com.econdates.domain.persistance.impl;

import com.econdates.domain.entities.EdHistory;
import com.econdates.domain.entities.EdScheduled;

public interface EdIndicatorValueFactory {
	EdHistory convertEdScheduledToEdHistory(EdScheduled edScheduled);

}
