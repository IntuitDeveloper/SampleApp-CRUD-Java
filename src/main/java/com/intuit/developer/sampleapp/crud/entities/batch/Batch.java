package com.intuit.developer.sampleapp.crud.entities.batch;

import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.BatchOperation;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create bill
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author dderose
 *
 */
public class Batch {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			addBatch();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void addBatch() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			BatchOperation batchOperation = new BatchOperation();
			
			String query = "select * from Customer startposition 1 maxresults 1";
			batchOperation.addQuery(query, "bID1");
			
			String reportQuery = "select * from VendorExpenses";
			batchOperation.addReportQuery(reportQuery, "bID2");
			
			service.executeBatch(batchOperation);
			
			QueryResult queryResult = batchOperation.getQueryResponse("bID1");
			LOG.info("report created: " + queryResult.getEntities().size());
			
			Report report = batchOperation.getReport("bID2");
			
			LOG.info("report created: " + report.getRows().getRow().size());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
