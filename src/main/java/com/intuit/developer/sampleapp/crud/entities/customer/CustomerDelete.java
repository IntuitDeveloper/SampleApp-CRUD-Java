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
 * Demonstrates methods to delete customer data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class CustomerDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteCustomer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteCustomer() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create customer
			Customer customer = CustomerHelper.getCustomerWithMandatoryFields();
			Customer addCustomer = service.add(customer);
			LOG.info("Customer added : " + addCustomer.getId() + " active flag ::: " + addCustomer.isActive());
			
			// set active flag as false to soft delete
			addCustomer.setActive(false);
			Customer deletedCustomer = service.update(addCustomer);		
			LOG.info("Customer deleted : " + deletedCustomer.getId() + " active flag ::: " + deletedCustomer.isActive());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
