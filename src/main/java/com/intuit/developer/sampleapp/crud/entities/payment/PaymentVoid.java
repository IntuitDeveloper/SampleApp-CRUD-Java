package com.intuit.developer.sampleapp.crud.entities.payment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to void payment
 * Note: We'll create an entity first and then void the same
 * 
 * @author dderose
 *
 */
public class PaymentVoid {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			voidPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void voidPayment() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create payment
			Payment payment = PaymentHelper.getCheckPaymentFields(service);
			Payment addPayment = service.add(payment);
			LOG.info("Payment added : " + addPayment.getId());

			// void payment
			Payment voidedPayment = service.voidRequest(addPayment);		
			LOG.info("Payment voided : " + voidedPayment.getId() + " status ::: " + voidedPayment.getPrivateNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while voiding entity :: " + error.getMessage()));
		}
	}



}
