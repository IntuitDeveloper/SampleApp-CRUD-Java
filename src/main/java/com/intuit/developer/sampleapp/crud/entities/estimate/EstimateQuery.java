package com.intuit.developer.sampleapp.crud.entities.estimate;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.EstimateHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query estimate data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class EstimateQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryEstimate();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryEstimate() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all estimate
			String sql = "select * from estimate";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of estimates: " + count);

			// add estimate
			Estimate estimate = EstimateHelper.getEstimateFields(service);
			Estimate savedEstimate = service.add(estimate);
			LOG.info("Estimate created: " + savedEstimate.getId());

			// get estimate data based on id
			sql = "select * from estimate where id = '" + savedEstimate.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			estimate = (Estimate) queryResult.getEntities().get(0);
			LOG.info("Estimate amount : " + estimate.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
