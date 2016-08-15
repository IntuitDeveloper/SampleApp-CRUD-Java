package com.intuit.developer.sampleapp.crud.entities.journalentry;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.JournalEntryHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update journalentry
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class JournalEntryUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateJournalEntry();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateJournalEntry() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create journalentry
			JournalEntry journalentry = JournalEntryHelper.getJournalEntryFields(service);
			JournalEntry addJournalEntry = service.add(journalentry);
			LOG.info("JournalEntry added : " + addJournalEntry.getId() + " txn date ::: " + addJournalEntry.getTxnDate());
			
			// sparse update journalentry 
			addJournalEntry.setSparse(true);
			addJournalEntry.setTxnDate(DateUtils.getDateWithNextDays(2));
			JournalEntry savedJournalEntry = service.update(addJournalEntry);
			LOG.info("JournalEntry sparse updated: " + savedJournalEntry.getId() + " txn date ::: " + savedJournalEntry.getTxnDate() );
			
			// update journalentry with all fields
			addJournalEntry = service.findById(savedJournalEntry);
			JournalEntry updatedJournalEntry = JournalEntryHelper.getJournalEntryFields(service);
			updatedJournalEntry.setId(addJournalEntry.getId());
			updatedJournalEntry.setSyncToken(addJournalEntry.getSyncToken());
		    savedJournalEntry = service.update(updatedJournalEntry);
		    LOG.info("JournalEntry updated with all fields : " + savedJournalEntry.getId() + " txn date ::: " + savedJournalEntry.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
