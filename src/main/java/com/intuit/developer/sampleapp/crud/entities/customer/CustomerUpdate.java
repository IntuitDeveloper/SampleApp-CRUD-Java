package com.intuit.developer.sampleapp.crud.entities.customer;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.CustomerHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update customer
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class CustomerUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateCustomer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateCustomer() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create customer
			Customer customer = CustomerHelper.getCustomerWithMandatoryFields();
			Customer addCustomer = service.add(customer);
			LOG.info("Customer added : " + addCustomer.getId() + " name ::: " + addCustomer.getDisplayName());
			
			// sparse update customer 
			addCustomer.setSparse(true);
			addCustomer.setDisplayName(RandomStringUtils.randomAlphanumeric(6));
			Customer savedCustomer = service.update(addCustomer);
			LOG.info("Customer sparse updated: " + savedCustomer.getId() + " name ::: " + savedCustomer.getDisplayName() );
			
			// update customer with all fields
			addCustomer = service.findById(savedCustomer);
			Customer updatedCustomer = CustomerHelper.getCustomerWithAllFields();
			updatedCustomer.setId(addCustomer.getId());
			updatedCustomer.setSyncToken(addCustomer.getSyncToken());
		    savedCustomer = service.update(updatedCustomer);
		    LOG.info("Customer updated with all fields : " + savedCustomer.getId() + " name ::: " + savedCustomer.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
