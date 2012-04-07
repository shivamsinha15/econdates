package com.econdates.xignite.webservice;

import java.util.List;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.econdates.xignite.xsd.domain.EventCode;
import com.econdates.xignite.xsd.domain.EventCodes;
import com.econdates.xignite.xsd.domain.EventSummaries;
import com.econdates.xignite.xsd.domain.EventSummary;
import com.econdates.xignite.xsd.domain.GetEventsByCountryCode;
import com.econdates.xignite.xsd.domain.GetEventsByCountryCodeResponse;
import com.econdates.xignite.xsd.domain.Header;
import com.econdates.xignite.xsd.domain.ListEventCodes;
import com.econdates.xignite.xsd.domain.ListEventCodesResponse;
import com.econdates.xignite.xsd.domain.ObjectFactory;

public class XigniteWebServiceProxy extends WebServiceGatewaySupport {



	public void ListEventCodes() throws InterruptedException {

		ListEventCodes request = new ListEventCodes();

		ListEventCodesResponse response = (ListEventCodesResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request);

		EventCodes eventCodes = response.getListEventCodesResult();

		List<EventCode> eventCodesList = eventCodes.getEventCodes()
				.getEventCode();
		for (EventCode eventCode : eventCodesList) {
			System.out.println(eventCode.getEventCode() + " "
					+ eventCode.getEventName());
		}

	}

	public void getEventsByCountryCode() throws InterruptedException {

		EventSummaries eventSummaries = new EventSummaries();

		GetEventsByCountryCode request = new GetEventsByCountryCode();
		GetEventsByCountryCodeResponse response = (GetEventsByCountryCodeResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request);
		
		eventSummaries = response.getGetEventsByCountryCodeResult();
		List<EventSummary> listOfEventSummaries = eventSummaries.getSummaries().getEventSummary();
		
		for(EventSummary eventSummary :  listOfEventSummaries){
			System.out.println(eventSummary.getCountryCode()+" "+eventSummary.getEventCode()+" "+eventSummary.getEventID()+" "+eventSummary.getEventName()+" "+eventSummary.getReleasedOn());
		}
		

	}
	

}
