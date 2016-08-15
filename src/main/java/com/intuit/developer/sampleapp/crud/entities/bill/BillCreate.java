package com.intuit.developer.sampleapp.crud.entities.bill;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create bill
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author dderose
 *
 */
public class BillCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			createBill();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createBill() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add bill 
			Bill bill = BillHelper.getBillFields(service);
			Bill savedBill = service.add(bill);
			LOG.info("Bill created: " + savedBill.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
