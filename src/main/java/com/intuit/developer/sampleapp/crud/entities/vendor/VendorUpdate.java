package com.intuit.developer.sampleapp.crud.entities.vendor;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.VendorHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update vendor
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class VendorUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateVendor();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateVendor() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create vendor
			Vendor vendor = VendorHelper.getVendorWithMandatoryFields();
			Vendor addVendor = service.add(vendor);
			LOG.info("Vendor added : " + addVendor.getId() + " name ::: " + addVendor.getDisplayName());
			
			// sparse update vendor 
			addVendor.setSparse(true);
			addVendor.setDisplayName(RandomStringUtils.randomAlphanumeric(6));
			Vendor savedVendor = service.update(addVendor);
			LOG.info("Vendor sparse updated: " + savedVendor.getId() + " name ::: " + savedVendor.getDisplayName() );
			
			// update vendor with all fields
			addVendor = service.findById(savedVendor);
			Vendor updatedVendor = VendorHelper.getVendorWithAllFields(service);
			updatedVendor.setId(addVendor.getId());
			updatedVendor.setSyncToken(addVendor.getSyncToken());
		    savedVendor = service.update(updatedVendor);
		    LOG.info("Vendor updated with all fields : " + savedVendor.getId() + " name ::: " + savedVendor.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
