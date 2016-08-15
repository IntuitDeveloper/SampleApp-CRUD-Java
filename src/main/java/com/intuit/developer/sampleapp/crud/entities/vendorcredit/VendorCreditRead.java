package com.intuit.developer.sampleapp.crud.entities.vendorcredit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorCreditHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read vendorcredit data using vendorcredit id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class VendorCreditRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getVendorCredit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getVendorCredit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add vendorcredit
			VendorCredit vendorcredit = VendorCreditHelper.getVendorCreditFields(service);
			VendorCredit savedVendorCredit = service.add(vendorcredit);
			LOG.info("VendorCredit created: " + savedVendorCredit.getId());
			
			VendorCredit vendorcreditOut = service.findById(savedVendorCredit);
			LOG.info("VendorCredit amount: " + vendorcreditOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
