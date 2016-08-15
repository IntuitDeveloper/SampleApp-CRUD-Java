package com.intuit.developer.sampleapp.crud.entities.salesreceipt;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.SalesReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read salesreceipt data using salesreceipt id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class SalesReceiptRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getSalesReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getSalesReceipt() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add salesreceipt
			SalesReceipt salesreceipt = SalesReceiptHelper.getSalesReceiptFields(service);
			SalesReceipt savedSalesReceipt = service.add(salesreceipt);
			LOG.info("SalesReceipt created: " + savedSalesReceipt.getId() + " ::salesreceipt doc num: " + savedSalesReceipt.getDocNumber());
			
			SalesReceipt salesreceiptOut = service.findById(savedSalesReceipt);
			LOG.info("SalesReceipt amount: " + salesreceiptOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
