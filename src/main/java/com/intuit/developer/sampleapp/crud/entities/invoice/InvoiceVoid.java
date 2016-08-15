package com.intuit.developer.sampleapp.crud.entities.invoice;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.InvoiceHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to void invoice
 * Note: We'll create an entity first and then void the same
 * 
 * @author dderose
 *
 */
public class InvoiceVoid {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			voidInvoice();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void voidInvoice() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create invoice
			Invoice invoice = InvoiceHelper.getInvoiceFields(service);
			Invoice addInvoice = service.add(invoice);
			LOG.info("Invoice added : " + addInvoice.getId());

			// void invoice
			Invoice voidedInvoice = service.voidRequest(addInvoice);		
			LOG.info("Invoice voided : " + voidedInvoice.getId() + " status ::: " + voidedInvoice.getPrivateNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while voiding entity :: " + error.getMessage()));
		}
	}



}
