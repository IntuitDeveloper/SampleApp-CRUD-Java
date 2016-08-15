package com.intuit.developer.sampleapp.crud.entities.term;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TermHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete term data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class TermDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteTerm();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteTerm() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create term
			Term term = TermHelper.getTermFields();
			Term addTerm = service.add(term);
			LOG.info("Term added : " + addTerm.getId() + " active flag ::: " + addTerm.isActive());

			// set active flag as false to soft delete
			addTerm.setActive(false);
			Term deletedTerm = service.update(addTerm);		
			LOG.info("Term deleted : " + deletedTerm.getId() + " active flag ::: " + deletedTerm.isActive());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
