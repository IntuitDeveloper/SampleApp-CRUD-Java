package com.intuit.developer.sampleapp.crud.entities.term;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TermHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query term data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class TermQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryTerm();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryTerm() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all term
			String sql = "select * from term";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of terms: " + count);

			// add term
			Term term = TermHelper.getTermFields();
			Term savedTerm = service.add(term);
			LOG.info("Term created: " + savedTerm.getId());

			// get term data based on id
			sql = "select * from term where id = '" + savedTerm.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			term = (Term) queryResult.getEntities().get(0);
			LOG.info("Term name : " + term.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
