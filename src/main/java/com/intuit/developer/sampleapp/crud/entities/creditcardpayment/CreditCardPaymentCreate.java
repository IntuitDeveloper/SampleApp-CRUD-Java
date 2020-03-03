package com.intuit.developer.sampleapp.crud.entities.creditcardpayment;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.CreditCardPaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CreditCardPaymentTxn;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create creditCardPayment
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author dderose
 *
 */
public class CreditCardPaymentCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createCreditCardPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createCreditCardPayment() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add creditCardPayment
			CreditCardPaymentTxn creditCardPayment = CreditCardPaymentHelper.getCreditCardPaymentFields(service);
			CreditCardPaymentTxn savedCreditCardPayment = service.add(creditCardPayment);
			LOG.info("CreditCardPayment created: " + savedCreditCardPayment.getId() );
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
