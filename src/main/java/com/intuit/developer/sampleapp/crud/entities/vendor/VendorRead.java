package com.intuit.developer.sampleapp.crud.entities.vendor;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read vendor data using vendor id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class VendorRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getVendor();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getVendor() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add vendor
			Vendor vendor = VendorHelper.getVendorWithMandatoryFields();
			Vendor savedVendor = service.add(vendor);
			LOG.info("Vendor created: " + savedVendor.getId() + " ::vendor name: " + savedVendor.getDisplayName());
			
			Vendor vendorOut = service.findById(savedVendor);
			LOG.info("Vendor Display name: " + vendorOut.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
