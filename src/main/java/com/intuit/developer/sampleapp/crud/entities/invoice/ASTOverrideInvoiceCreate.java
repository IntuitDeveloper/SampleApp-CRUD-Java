package com.intuit.developer.sampleapp.crud.entities.invoice;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.InvoiceHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Preferences;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create invoice for AST company
 * 1. Check prefrences to determine if company is AST enabled
 * 2. Create invoice by overriding tax amount
 * 
 * @author dderose
 *
 */
public class ASTOverrideInvoiceCreate {
	
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
			
			//check preferences to determine if company is AST enabled
			if(isASTEnabledCompany()) {
			
				// add invoice
				Invoice invoice = InvoiceHelper.getASTOverrideFields(service);
				Invoice savedInvoice = service.add(invoice);
				LOG.info("Invoice created: " + savedInvoice.getId() + " ::invoice total tax: " + savedInvoice.getTxnTaxDetail().getTotalTax());
			} 
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
	private static boolean isASTEnabledCompany() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all preferences
			String sql = "select * from preferences";
			QueryResult queryResult = service.executeQuery(sql);
			if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
				Preferences preferences = (Preferences) queryResult.getEntities().get(0);
				if(preferences.getTaxPrefs().isPartnerTaxEnabled()) {
					return true;
				}
				LOG.info("Preferences -> SalesFormsPrefs - > DefaultCustomerMessage: " + preferences.getSalesFormsPrefs().getDefaultCustomerMessage());
			}
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		return false;
		
	}

}
