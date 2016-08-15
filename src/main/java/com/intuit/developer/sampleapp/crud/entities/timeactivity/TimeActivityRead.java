package com.intuit.developer.sampleapp.crud.entities.timeactivity;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TimeActivityHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TimeActivity;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read timeactivity data using timeactivity id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class TimeActivityRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getTimeActivity();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getTimeActivity() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add time activity
			TimeActivity timeActivityIn = TimeActivityHelper.getTimeActivityFields(service);
			TimeActivity timeActivityOut = service.add(timeActivityIn);
			LOG.info("TimeActivity created: " + timeActivityOut.getId() + " ::TimeActivity starttime: " + timeActivityOut.getStartTime() + "::: " + timeActivityOut.getEndTime());
			
			TimeActivity timeactivityOut = service.findById(timeActivityOut);
			LOG.info("TimeActivity status: " + timeactivityOut.getBillableStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
