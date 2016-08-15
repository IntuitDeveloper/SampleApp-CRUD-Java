package com.intuit.developer.sampleapp.crud.entities.billpayment;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create billpayment
 * 
 * @author dderose
 *
 */
public class BillPaymentCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			createBillPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createBillPayment() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add billpayment with minimum mandatory fields
			BillPayment account = BillHelper.getBillPaymentFields(service);
			BillPayment savedBillPayment = service.add(account);
			LOG.info("BillPayment created: " + savedBillPayment.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	

	

}
