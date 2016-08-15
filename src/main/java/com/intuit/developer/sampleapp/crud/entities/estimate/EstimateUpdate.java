package com.intuit.developer.sampleapp.crud.entities.estimate;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.EstimateHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update estimate
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class EstimateUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateEstimate();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateEstimate() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create estimate
			Estimate estimate = EstimateHelper.getEstimateFields(service);
			Estimate addEstimate = service.add(estimate);
			LOG.info("Estimate added : " + addEstimate.getId() + " doc num ::: " + addEstimate.getDocNumber());
			
			// sparse update estimate 
			addEstimate.setSparse(true);
			addEstimate.setDocNumber(RandomStringUtils.randomNumeric(4));
			Estimate savedEstimate = service.update(addEstimate);
			LOG.info("Estimate sparse updated: " + savedEstimate.getId() + " doc num ::: " + savedEstimate.getDocNumber() );
			
			// update estimate with all fields
			addEstimate = service.findById(savedEstimate);
			Estimate updatedEstimate = EstimateHelper.getEstimateFields(service);
			updatedEstimate.setId(addEstimate.getId());
			updatedEstimate.setSyncToken(addEstimate.getSyncToken());
		    savedEstimate = service.update(updatedEstimate);
		    LOG.info("Estimate updated with all fields : " + savedEstimate.getId() + " doc num ::: " + savedEstimate.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
