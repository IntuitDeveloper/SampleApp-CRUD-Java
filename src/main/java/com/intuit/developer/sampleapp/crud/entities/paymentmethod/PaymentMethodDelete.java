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
 * Demonstrates methods to delete paymentmethod data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class PaymentMethodDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deletePaymentMethod();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deletePaymentMethod() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create paymentmethod
			PaymentMethod paymentmethod = PaymentHelper.getPaymentMethodFields();
			PaymentMethod addPaymentMethod = service.add(paymentmethod);
			LOG.info("PaymentMethod added : " + addPaymentMethod.getId() + " active flag ::: " + addPaymentMethod.isActive());

			// set active flag as false to soft delete
			addPaymentMethod.setActive(false);
			PaymentMethod deletedPaymentMethod = service.update(addPaymentMethod);		
			LOG.info("PaymentMethod deleted : " + deletedPaymentMethod.getId() + " active flag ::: " + deletedPaymentMethod.isActive());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
