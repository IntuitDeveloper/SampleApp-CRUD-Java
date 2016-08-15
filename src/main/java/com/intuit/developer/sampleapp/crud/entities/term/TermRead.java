package com.intuit.developer.sampleapp.crud.entities.term;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TermHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read term data using term id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class TermRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getTerm();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getTerm() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add term
			Term term = TermHelper.getTermFields();
			Term savedTerm = service.add(term);
			LOG.info("Term created: " + savedTerm.getId());
			
			Term termOut = service.findById(savedTerm);
			LOG.info("Term name: " + termOut.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
