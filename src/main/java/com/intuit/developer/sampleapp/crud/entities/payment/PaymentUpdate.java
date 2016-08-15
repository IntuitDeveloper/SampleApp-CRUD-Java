package com.intuit.developer.sampleapp.crud.entities.payment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update payment
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class PaymentUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updatePayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updatePayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create payment
			Payment payment = PaymentHelper.getCheckPaymentFields(service);
			Payment addPayment = service.add(payment);
			LOG.info("Payment added : " + addPayment.getId() + " txn date ::: " + addPayment.getTxnDate());
			
			// sparse update payment 
			addPayment.setSparse(true);
			addPayment.setTxnDate(DateUtils.getDateWithNextDays(2));
			Payment savedPayment = service.update(addPayment);
			LOG.info("Payment sparse updated: " + savedPayment.getId() + " txn date ::: " + savedPayment.getTxnDate() );
			
			// update payment with all fields
			addPayment = service.findById(savedPayment);
			Payment updatedPayment = PaymentHelper.getCheckPaymentFields(service);
			updatedPayment.setId(addPayment.getId());
			updatedPayment.setSyncToken(addPayment.getSyncToken());
		    savedPayment = service.update(updatedPayment);
		    LOG.info("Payment updated with all fields : " + savedPayment.getId() + " txn date ::: " + savedPayment.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
