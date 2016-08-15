package com.intuit.developer.sampleapp.crud.entities.vendorcredit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorCreditHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update vendorcredit
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class VendorCreditUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateVendorCredit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateVendorCredit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create vendorcredit
			VendorCredit vendorcredit = VendorCreditHelper.getVendorCreditFields(service);
			VendorCredit addVendorCredit = service.add(vendorcredit);
			LOG.info("VendorCredit added : " + addVendorCredit.getId() + " txn date ::: " + addVendorCredit.getTxnDate());
			
			// sparse update vendorcredit 
			addVendorCredit.setSparse(true);
			addVendorCredit.setTxnDate(DateUtils.getDateWithNextDays(2));
			VendorCredit savedVendorCredit = service.update(addVendorCredit);
			LOG.info("VendorCredit sparse updated: " + savedVendorCredit.getId() + " txn date ::: " + savedVendorCredit.getTxnDate() );
			
			// update vendorcredit with all fields
			addVendorCredit = service.findById(savedVendorCredit);
			VendorCredit updatedVendorCredit = VendorCreditHelper.getVendorCreditFields(service);
			updatedVendorCredit.setId(addVendorCredit.getId());
			updatedVendorCredit.setSyncToken(addVendorCredit.getSyncToken());
		    savedVendorCredit = service.update(updatedVendorCredit);
		    LOG.info("VendorCredit updated with all fields : " + savedVendorCredit.getId() + " txn date ::: " + savedVendorCredit.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
