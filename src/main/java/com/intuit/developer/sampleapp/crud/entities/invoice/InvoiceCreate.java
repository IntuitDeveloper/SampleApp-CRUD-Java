package com.intuit.developer.sampleapp.crud.entities.invoice;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.InvoiceHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create invoice
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author dderose
 *
 */
public class InvoiceCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createInvoice();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createInvoice() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add invoice
			Invoice invoice = InvoiceHelper.getInvoiceFields(service);
			Invoice savedInvoice = service.add(invoice);
			LOG.info("Invoice created: " + savedInvoice.getId() + " ::invoice doc num: " + savedInvoice.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
