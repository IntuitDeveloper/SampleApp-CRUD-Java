package com.intuit.developer.sampleapp.crud.entities.taxpayment;

import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TaxPayment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read TaxPayment data using TaxPayment id
 * 
 * @author dderose
 *
 */
public class TaxPaymentRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getTaxPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getTaxPayment() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();

			TaxPayment taxPayment = new TaxPayment();
			taxPayment.setId(""); //Set the id
			
			TaxPayment taxPaymentOut = service.findById(taxPayment);
			LOG.info("TaxPayment Amount: " + taxPaymentOut.getPaymentAmount());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
