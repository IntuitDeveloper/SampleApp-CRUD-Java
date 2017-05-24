package com.intuit.developer.sampleapp.crud.entities.companycurrency;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CompanyCurrency;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query company currency 
 * 1. Query all records
 * 
 * @author dderose
 *
 */
public class CompanyCurrencyQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryCompanyCurrency();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryCompanyCurrency() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all companycurrency
			String sql = "select * from companycurrency";
			QueryResult queryResult = service.executeQuery(sql);
			
			if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
				CompanyCurrency companycurrency = (CompanyCurrency) queryResult.getEntities().get(0);
				LOG.info("CompanyCurrency -> name: " + companycurrency.getName());
				int count = queryResult.getEntities().size();
				LOG.info("Total number of companycurrency: " + count);
			}
			
			
			
			
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
