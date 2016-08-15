package com.intuit.developer.sampleapp.crud.entities.vendorcredit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorCreditHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete vendorcredit data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class VendorCreditDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteVendorCredit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteVendorCredit() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create vendorcredit
			VendorCredit vendorcredit = VendorCreditHelper.getVendorCreditFields(service);
			VendorCredit addVendorCredit = service.add(vendorcredit);
			LOG.info("VendorCredit added : " + addVendorCredit.getId());

			//delete vendorcredit
			VendorCredit deletedVendorCredit = service.delete(addVendorCredit);		
			LOG.info("VendorCredit deleted : " + deletedVendorCredit.getId() + " status ::: " + deletedVendorCredit.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
