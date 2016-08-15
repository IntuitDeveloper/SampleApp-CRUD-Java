package com.intuit.developer.sampleapp.crud.entities.journalentry;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.JournalEntryHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read journalentry data using journalentry id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class JournalEntryRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getJournalEntry();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getJournalEntry() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add journalentry
			JournalEntry journalentry = JournalEntryHelper.getJournalEntryFields(service);
			JournalEntry savedJournalEntry = service.add(journalentry);
			LOG.info("JournalEntry created: " + savedJournalEntry.getId());
			
			JournalEntry journalentryOut = service.findById(savedJournalEntry);
			LOG.info("JournalEntry amount: " + journalentryOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
