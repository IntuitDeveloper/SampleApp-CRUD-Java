package com.intuit.developer.sampleapp.crud.entities.estimate;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.EstimateHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create estimate
 * 
 * @author dderose
 *
 */
public class EstimateCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createEstimate();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createEstimate() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add estimate
			Estimate estimate = EstimateHelper.getEstimateFields(service);
			Estimate savedEstimate = service.add(estimate);
			LOG.info("Estimate created: " + savedEstimate.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
}
