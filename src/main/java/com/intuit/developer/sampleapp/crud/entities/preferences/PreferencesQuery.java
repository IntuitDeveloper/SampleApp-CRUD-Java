package com.intuit.developer.sampleapp.crud.entities.preferences;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Preferences;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query preferences data
 * 1. Query all records
 * 
 * @author dderose
 *
 */
public class PreferencesQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryPreferences();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryPreferences() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all preferences
			String sql = "select * from preferences";
			QueryResult queryResult = service.executeQuery(sql);
			if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
				Preferences preferences = (Preferences) queryResult.getEntities().get(0);
				LOG.info("Preferences -> SalesFormsPrefs - > DefaultCustomerMessage: " + preferences.getSalesFormsPrefs().getDefaultCustomerMessage());
			}
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
