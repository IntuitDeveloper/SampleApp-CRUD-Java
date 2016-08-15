package com.intuit.developer.sampleapp.crud.entities.salesreceipt;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.SalesReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete salesreceipt data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class SalesReceiptDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteSalesReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteSalesReceipt() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create salesreceipt
			SalesReceipt salesreceipt = SalesReceiptHelper.getSalesReceiptFields(service);
			SalesReceipt addSalesReceipt = service.add(salesreceipt);
			LOG.info("SalesReceipt added : " + addSalesReceipt.getId());

			//delete salesreceipt
			SalesReceipt deletedSalesReceipt = service.delete(addSalesReceipt);		
			LOG.info("SalesReceipt deleted : " + deletedSalesReceipt.getId() + " status ::: " + deletedSalesReceipt.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
