package com.intuit.developer.sampleapp.crud.entities.paymentmethod;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create payment method
 * 
 * @author dderose
 *
 */
public class PaymentMethodCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			createPaymentMethod();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createPaymentMethod() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add paymentmethod
			PaymentMethod paymentmethod = PaymentHelper.getPaymentMethodFields();
			PaymentMethod savedPaymentMethod = service.add(paymentmethod);
			LOG.info("PaymentMethod created: " + savedPaymentMethod.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
