package com.intuit.developer.sampleapp.crud.entities.taxclassification;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TaxClassification;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read TaxClassification data using level
 * 
 * @author dderose
 *
 */
public class TaxClassificationReadByLevel {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			findTaxClassificationByLevel();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void findTaxClassificationByLevel() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			//read by level
			TaxClassification taxClassification = new TaxClassification();
			taxClassification.setLevel("1");
			QueryResult queryResult = service.findTaxClassificationByLevel(taxClassification);
			int count = queryResult.getEntities().size();
			LOG.info("Total number of taxclassification :: " + count);
			
			taxClassification.setLevel("2");
			queryResult = service.findTaxClassificationByLevel(taxClassification);
			count = queryResult.getEntities().size();
			LOG.info("Total number of taxclassification :: " + count);
			
			
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
