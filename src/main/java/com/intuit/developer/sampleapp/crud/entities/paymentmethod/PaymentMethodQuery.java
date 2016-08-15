package com.intuit.developer.sampleapp.crud.entities.paymentmethod;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query paymentmethod data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class PaymentMethodQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryPaymentMethod();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryPaymentMethod() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all paymentmethod
			String sql = "select * from paymentmethod";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of paymentmethods: " + count);

			// add paymentmethod
			PaymentMethod paymentmethod = PaymentHelper.getPaymentMethodFields();
			PaymentMethod savedPaymentMethod = service.add(paymentmethod);
			LOG.info("PaymentMethod created: " + savedPaymentMethod.getId());

			// get paymentmethod data based on id
			sql = "select * from paymentmethod where id = '" + savedPaymentMethod.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			paymentmethod = (PaymentMethod) queryResult.getEntities().get(0);
			LOG.info("PaymentMethod name : " + paymentmethod.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
