package com.intuit.developer.sampleapp.crud.entities.estimate;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.EstimateHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete estimate data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class EstimateDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteEstimate();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteEstimate() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create estimate
			Estimate estimate = EstimateHelper.getEstimateFields(service);
			Estimate addEstimate = service.add(estimate);
			LOG.info("Estimate added : " + addEstimate.getId());

			//delete estimate
			Estimate deletedEstimate = service.delete(addEstimate);		
			LOG.info("Estimate deleted : " + deletedEstimate.getId() + " status ::: " + deletedEstimate.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
