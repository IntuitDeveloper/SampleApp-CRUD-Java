package com.intuit.developer.sampleapp.crud.entities.journalentry;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.JournalEntryHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query journalentry data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class JournalEntryQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryJournalEntry();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryJournalEntry() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all journalentry
			String sql = "select * from journalentry";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of journalentry: " + count);

			// add journalentry
			JournalEntry journalentry = JournalEntryHelper.getJournalEntryFields(service);
			JournalEntry savedJournalEntry = service.add(journalentry);
			LOG.info("JournalEntry created: " + savedJournalEntry.getId());

			// get journalentry data based on id
			sql = "select * from journalentry where id = '" + savedJournalEntry.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			journalentry = (JournalEntry) queryResult.getEntities().get(0);
			LOG.info("JournalEntry amount : " + journalentry.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
