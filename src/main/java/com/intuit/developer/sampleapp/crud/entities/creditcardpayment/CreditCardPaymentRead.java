package com.intuit.developer.sampleapp.crud.entities.creditcardpayment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.CreditCardPaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CreditCardPaymentTxn;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read creditCardPayment using creditCardPayment id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class CreditCardPaymentRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getCreditCardPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getCreditCardPayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add creditCardPayment
			CreditCardPaymentTxn creditCardPayment = CreditCardPaymentHelper.getCreditCardPaymentFields(service);
			CreditCardPaymentTxn savedCreditCardPayment = service.add(creditCardPayment);
			LOG.info("CreditCardPayment created: " + savedCreditCardPayment.getId() );
			
			CreditCardPaymentTxn creditCardPaymentOut = service.findById(savedCreditCardPayment);
			LOG.info("CreditCardPayment Txn status: " + creditCardPaymentOut.getTxnStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
