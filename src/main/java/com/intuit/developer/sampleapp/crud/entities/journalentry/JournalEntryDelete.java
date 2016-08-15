package com.intuit.developer.sampleapp.crud.entities.journalentry;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.JournalEntryHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete journalentry data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class JournalEntryDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteJournalEntry();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteJournalEntry() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create journalentry
			JournalEntry journalentry = JournalEntryHelper.getJournalEntryFields(service);
			JournalEntry addJournalEntry = service.add(journalentry);
			LOG.info("JournalEntry added : " + addJournalEntry.getId());

			//delete journalentry
			JournalEntry deletedJournalEntry = service.delete(addJournalEntry);		
			LOG.info("JournalEntry deleted : " + deletedJournalEntry.getId() + " status ::: " + deletedJournalEntry.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
