package com.intuit.developer.sampleapp.crud.entities.invoice;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.InvoiceHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update invoice
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * 
 * @author dderose
 *
 */
public class InvoiceUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateInvoice();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateInvoice() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create invoice
			Invoice invoice = InvoiceHelper.getInvoiceFields(service);
			Invoice addInvoice = service.add(invoice);
			LOG.info("Invoice added : " + addInvoice.getId() + " doc num ::: " + addInvoice.getDocNumber());
			
			// sparse update invoice 
			addInvoice.setSparse(true);
			addInvoice.setDocNumber(RandomStringUtils.randomAlphanumeric(5));
			Invoice savedInvoice = service.update(addInvoice);
			LOG.info("Invoice sparse updated: " + savedInvoice.getId() + " doc num ::: " + savedInvoice.getDocNumber() );
			
			// update invoice with all fields
			addInvoice = service.findById(savedInvoice);
			Invoice updatedInvoice = InvoiceHelper.getInvoiceFields(service);
			updatedInvoice.setId(addInvoice.getId());
			updatedInvoice.setSyncToken(addInvoice.getSyncToken());
		    savedInvoice = service.update(updatedInvoice);
		    LOG.info("Invoice updated with all fields : " + savedInvoice.getId() + " doc num ::: " + savedInvoice.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
