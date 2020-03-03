package com.intuit.developer.sampleapp.crud.entities.creditcardpayment;

import java.math.BigDecimal;
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
 * Demonstrates methods to update creditCardPayment
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * 
 * @author dderose
 *
 */
public class CreditCardPaymentUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateCreditCardPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateCreditCardPayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create creditCardPayment
			CreditCardPaymentTxn creditCardPayment = CreditCardPaymentHelper.getCreditCardPaymentFields(service);
			CreditCardPaymentTxn addCreditCardPayment = service.add(creditCardPayment);
			LOG.info("CreditCardPayment added : " + addCreditCardPayment.getId() + " amt ::: " + addCreditCardPayment.getAmount());
			
			// sparse update creditCardPayment 
			addCreditCardPayment.setSparse(true);
			addCreditCardPayment.setAmount(new BigDecimal("20"));
			CreditCardPaymentTxn savedCreditCardPayment = service.update(addCreditCardPayment);
			LOG.info("CreditCardPayment sparse updated: " + savedCreditCardPayment.getId() + " amt ::: " + addCreditCardPayment.getAmount() );
			
			// update creditCardPayment with all fields
			addCreditCardPayment = service.findById(savedCreditCardPayment);
			CreditCardPaymentTxn updatedCreditCardPayment = CreditCardPaymentHelper.getCreditCardPaymentFields(service);
			updatedCreditCardPayment.setId(addCreditCardPayment.getId());
			updatedCreditCardPayment.setSyncToken(addCreditCardPayment.getSyncToken());
		    savedCreditCardPayment = service.update(updatedCreditCardPayment);
		    LOG.info("CreditCardPayment updated with all fields : " + savedCreditCardPayment.getId() + " amt ::: " + addCreditCardPayment.getAmount() );
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
