package com.intuit.developer.sampleapp.crud.entities.payment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete payment data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class PaymentDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deletePayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deletePayment() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create payment
			Payment payment = PaymentHelper.getCheckPaymentFields(service);
			Payment addPayment = service.add(payment);
			LOG.info("Payment added : " + addPayment.getId());

			//delete payment
			Payment deletedPayment = service.delete(addPayment);		
			LOG.info("Payment deleted : " + deletedPayment.getId() + " status ::: " + deletedPayment.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
