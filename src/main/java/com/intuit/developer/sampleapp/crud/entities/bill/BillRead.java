package com.intuit.developer.sampleapp.crud.entities.bill;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read bill data using bill id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class BillRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getBill();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getBill() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add bill 
			Bill bill = BillHelper.getBillFields(service);
			Bill savedBill = service.add(bill);
			LOG.info("Bill created: " + savedBill.getId());
			
			Bill billOut = service.findById(savedBill);
			LOG.info("Bill amount: " + billOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
