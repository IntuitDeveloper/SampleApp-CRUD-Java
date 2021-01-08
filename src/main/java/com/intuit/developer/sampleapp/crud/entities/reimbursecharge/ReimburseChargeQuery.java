package com.intuit.developer.sampleapp.crud.entities.reimbursecharge;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

import java.text.ParseException;
import java.util.List;

/**
 * Demonstrates methods to query refundreceipt data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class ReimburseChargeQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryReimburseCharge();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryReimburseCharge() throws FMSException, ParseException {
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// get all reimbursecharge
			String sql = "Select * from ReimburseCharge";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of refundreceipts: " + count);

			// get reimbursecharge based on HasBeenInvoiced
			sql = "Select * from ReimburseCharge Where HasBeenInvoiced = false";
			queryResult = service.executeQuery(sql);
			LOG.info("Size: " + queryResult.getEntities().size());
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
	}
}
