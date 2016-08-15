package com.intuit.developer.sampleapp.crud.entities.vendorcredit;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorCreditHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create vendorcredit
 * 
 * @author dderose
 *
 */
public class VendorCreditCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createVendorCredit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createVendorCredit() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add vendorcredit
			VendorCredit vendorcredit = VendorCreditHelper.getVendorCreditFields(service);
			VendorCredit savedVendorCredit = service.add(vendorcredit);
			LOG.info("VendorCredit created: " + savedVendorCredit.getId());
						
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
}
