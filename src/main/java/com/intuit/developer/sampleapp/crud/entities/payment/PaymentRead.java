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
 * Demonstrates methods to read payment data using payment id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class PaymentRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getPayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add payment with cash fields
			Payment payment = PaymentHelper.getCheckPaymentFields(service);
			Payment savedPayment = service.add(payment);
			LOG.info("Payment with cash fields created: " + savedPayment.getId());
			
			Payment paymentOut = service.findById(savedPayment);
			LOG.info("Payment amount: " + paymentOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
