package com.intuit.developer.sampleapp.crud.entities.billpayment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query billpayment data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class BillPaymentQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryBillPayments();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryBillPayments() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all billpayments
			String sql = "select * from billpayment";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of billpayments: " + count);
			
			// add billpayment with minimum mandatory fields
			BillPayment billpayment = BillHelper.getBillPaymentFields(service);
			BillPayment savedBillPayment = service.add(billpayment);
			LOG.info("BillPayment created: " + savedBillPayment.getId());
			
			// get billpayment data based on id
			sql = "select * from billpayment where id = '" + savedBillPayment.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			billpayment = (BillPayment)queryResult.getEntities().get(0);
			LOG.info("BillPayment amount : " + billpayment.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
