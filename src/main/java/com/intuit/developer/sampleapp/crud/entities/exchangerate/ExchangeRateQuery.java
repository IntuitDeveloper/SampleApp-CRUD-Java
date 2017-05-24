package com.intuit.developer.sampleapp.crud.entities.exchangerate;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.ExchangeRate;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query ExchangeRate  
 * 1. Query all records
 * 
 * @author dderose
 *
 */
public class ExchangeRateQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryExchangeRate();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryExchangeRate() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all exchangerate
			String sql = "select * from exchangerate";
			QueryResult queryResult = service.executeQuery(sql);
			
			if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
				ExchangeRate exchangerate = (ExchangeRate) queryResult.getEntities().get(0);
				LOG.info("ExchangeRate -> Rate: " + exchangerate.getRate());
				int count = queryResult.getEntities().size();
				LOG.info("Total number of exchangerate: " + count);
			}
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
