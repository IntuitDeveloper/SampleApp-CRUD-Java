package com.intuit.developer.sampleapp.crud.entities.paymentmethod;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update paymentmethod
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class PaymentMethodUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updatePaymentMethod();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updatePaymentMethod() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create paymentmethod
			PaymentMethod paymentmethod = PaymentHelper.getPaymentMethodFields();
			PaymentMethod addPaymentMethod = service.add(paymentmethod);
			LOG.info("PaymentMethod added : " + addPaymentMethod.getId() + " name ::: " + addPaymentMethod.getName());
			
			// sparse update paymentmethod 
			addPaymentMethod.setSparse(true);
			addPaymentMethod.setName("PaymentMethod" + RandomStringUtils.randomAlphanumeric(5));
			PaymentMethod savedPaymentMethod = service.update(addPaymentMethod);
			LOG.info("PaymentMethod sparse updated: " + savedPaymentMethod.getId() + " name ::: " + savedPaymentMethod.getName() );
			
			// update paymentmethod with all fields
			addPaymentMethod = service.findById(savedPaymentMethod);
			PaymentMethod updatedPaymentMethod = PaymentHelper.getPaymentMethodFields();
			updatedPaymentMethod.setId(addPaymentMethod.getId());
			updatedPaymentMethod.setSyncToken(addPaymentMethod.getSyncToken());
		    savedPaymentMethod = service.update(updatedPaymentMethod);
		    LOG.info("PaymentMethod updated with all fields : " + savedPaymentMethod.getId() + " name ::: " + savedPaymentMethod.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
