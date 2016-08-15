package com.intuit.developer.sampleapp.crud.entities.customer;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.CustomerHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read customer data using customer id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class CustomerRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getCustomer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getCustomer() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add customer with minimum mandatory fields
			Customer customer = CustomerHelper.getCustomerWithMandatoryFields();
			Customer savedCustomer = service.add(customer);
			LOG.info("Customer with mandatory fields created: " + savedCustomer.getId() + " ::customer name: " + savedCustomer.getDisplayName());
			
			Customer customerOut = service.findById(savedCustomer);
			LOG.info("Customer Display name: " + customerOut.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
