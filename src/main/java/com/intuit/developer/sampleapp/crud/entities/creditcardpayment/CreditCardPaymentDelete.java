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
 * Demonstrates methods to delete creditCardPayment
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class CreditCardPaymentDelete {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteCreditCardPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteCreditCardPayment() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create creditCardPayment
			CreditCardPaymentTxn creditCardPayment = CreditCardPaymentHelper.getCreditCardPaymentFields(service);
			CreditCardPaymentTxn addCreditCardPayment = service.add(creditCardPayment);
			LOG.info("CreditCardPayment added : " + addCreditCardPayment.getId());

			// delete creditCardPayment
			CreditCardPaymentTxn deletedCreditCardPayment = service.delete(addCreditCardPayment);		
			LOG.info("CreditCardPayment deleted : " + deletedCreditCardPayment.getId() + " status ::: " + deletedCreditCardPayment.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
	}

}
