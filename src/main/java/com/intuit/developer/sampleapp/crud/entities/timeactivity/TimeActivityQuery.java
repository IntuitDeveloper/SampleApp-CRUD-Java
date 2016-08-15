package com.intuit.developer.sampleapp.crud.entities.timeactivity;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TimeActivityHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TimeActivity;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query timeactivity data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class TimeActivityQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryTimeActivity();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryTimeActivity() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all timeactivity
			String sql = "select * from timeactivity";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of timeactivity: " + count);

			// add time activity
			TimeActivity timeactivity = TimeActivityHelper.getTimeActivityFields(service);
			TimeActivity savedTimeActivity = service.add(timeactivity);
			LOG.info("TimeActivity created: " + savedTimeActivity.getId() + " ::TimeActivity starttime: " + savedTimeActivity.getStartTime() + "::: " + savedTimeActivity.getEndTime());

			// get timeactivity data based on id
			sql = "select * from timeactivity where id = '" + savedTimeActivity.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			timeactivity = (TimeActivity) queryResult.getEntities().get(0);
			LOG.info("TimeActivity status : " + timeactivity.getBillableStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
