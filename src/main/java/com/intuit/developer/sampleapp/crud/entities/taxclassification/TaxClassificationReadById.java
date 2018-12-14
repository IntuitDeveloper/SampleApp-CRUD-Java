package com.intuit.developer.sampleapp.crud.entities.taxclassification;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TaxClassification;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read TaxClassification data using id
 * 
 * @author dderose
 *
 */
public class TaxClassificationReadById {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			findTaxClassificationById();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void findTaxClassificationById() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			//read by id
			TaxClassification taxClassification = new TaxClassification();
			taxClassification.setId("EUC-04030101-V1-00060000");
			
			TaxClassification newTaxClassification = service.findById(taxClassification);
			LOG.info("TaxClassification name :: " + newTaxClassification.getName() + " id:: " + newTaxClassification.getId());
			
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
