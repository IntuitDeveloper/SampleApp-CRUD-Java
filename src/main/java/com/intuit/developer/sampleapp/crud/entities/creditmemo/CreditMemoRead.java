package com.intuit.developer.sampleapp.crud.entities.creditmemo;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.CreditMemoHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CreditMemo;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read creditmemo data using creditmemo id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class CreditMemoRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getCreditMemo();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getCreditMemo() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add creditmemo
			CreditMemo creditmemo = CreditMemoHelper.getCreditMemoFields(service);
			CreditMemo savedCreditMemo = service.add(creditmemo);
			LOG.info("CreditMemo created: " + savedCreditMemo.getId());
			
			CreditMemo creditmemoOut = service.findById(savedCreditMemo);
			LOG.info("CreditMemo remaining credit: " + creditmemoOut.getRemainingCredit());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
