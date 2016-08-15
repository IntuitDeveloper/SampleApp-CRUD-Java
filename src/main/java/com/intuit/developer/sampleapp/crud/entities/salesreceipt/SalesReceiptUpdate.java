package com.intuit.developer.sampleapp.crud.entities.salesreceipt;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.SalesReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update salesreceipt
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class SalesReceiptUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateSalesReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateSalesReceipt() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create salesreceipt
			SalesReceipt salesreceipt = SalesReceiptHelper.getSalesReceiptFields(service);
			SalesReceipt addSalesReceipt = service.add(salesreceipt);
			LOG.info("SalesReceipt added : " + addSalesReceipt.getId() + " doc num ::: " + addSalesReceipt.getDocNumber());
			
			// sparse update salesreceipt 
			addSalesReceipt.setSparse(true);
			addSalesReceipt.setDocNumber(RandomStringUtils.randomNumeric(4));
			SalesReceipt savedSalesReceipt = service.update(addSalesReceipt);
			LOG.info("SalesReceipt sparse updated: " + savedSalesReceipt.getId() + " doc num ::: " + savedSalesReceipt.getDocNumber() );
			
			// update salesreceipt with all fields
			addSalesReceipt = service.findById(savedSalesReceipt);
			SalesReceipt updatedSalesReceipt = SalesReceiptHelper.getSalesReceiptFields(service);
			updatedSalesReceipt.setId(addSalesReceipt.getId());
			updatedSalesReceipt.setSyncToken(addSalesReceipt.getSyncToken());
		    savedSalesReceipt = service.update(updatedSalesReceipt);
		    LOG.info("SalesReceipt updated with all fields : " + savedSalesReceipt.getId() + " doc num ::: " + savedSalesReceipt.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
