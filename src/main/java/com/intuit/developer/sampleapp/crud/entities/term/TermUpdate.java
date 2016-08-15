package com.intuit.developer.sampleapp.crud.entities.term;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TermHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update term
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class TermUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateTerm();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateTerm() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create term
			Term term = TermHelper.getTermFields();
			Term addTerm = service.add(term);
			LOG.info("Term added : " + addTerm.getId() + " due days ::: " + addTerm.getDueDays());
			
			// sparse update term 
			addTerm.setSparse(true);
			addTerm.setDueDays(40);
			Term savedTerm = service.update(addTerm);
			LOG.info("Term sparse updated: " + savedTerm.getId() + " due days ::: " + savedTerm.getDueDays() );
			
			// update term with all fields
			addTerm = service.findById(savedTerm);
			Term updatedTerm = TermHelper.getTermFields();
			updatedTerm.setId(addTerm.getId());
			updatedTerm.setSyncToken(addTerm.getSyncToken());
		    savedTerm = service.update(updatedTerm);
		    LOG.info("Term updated with all fields : " + savedTerm.getId() + " due days ::: " + savedTerm.getDueDays());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
