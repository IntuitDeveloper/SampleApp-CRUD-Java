package com.intuit.developer.sampleapp.crud.entities.payment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PaymentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query payment data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class PaymentQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryPayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all payment
			String sql = "select * from payment";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of payments: " + count);

			// add payment with cash fields
			Payment payment = PaymentHelper.getCheckPaymentFields(service);
			Payment savedPayment = service.add(payment);
			LOG.info("Payment with cash fields created: " + savedPayment.getId());

			// get payment data based on id
			sql = "select * from payment where id = '" + savedPayment.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			payment = (Payment) queryResult.getEntities().get(0);
			LOG.info("Payment amount : " + payment.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
