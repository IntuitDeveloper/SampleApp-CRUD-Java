package com.intuit.developer.sampleapp.crud.entities.refundreceipt;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query refundreceipt data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class RefundReceiptQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryRefundReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryRefundReceipt() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all refundreceipt
			String sql = "select * from refundreceipt";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of refundreceipts: " + count);

			// add refundreceipt
			RefundReceipt refundreceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			RefundReceipt savedRefundReceipt = service.add(refundreceipt);
			LOG.info("RefundReceipt created: " + savedRefundReceipt.getId() + " ::refundreceipt doc num: " + savedRefundReceipt.getDocNumber());

			// get refundreceipt data based on id
			sql = "select * from refundreceipt where id = '" + savedRefundReceipt.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			refundreceipt = (RefundReceipt) queryResult.getEntities().get(0);
			LOG.info("RefundReceipt amount : " + refundreceipt.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
