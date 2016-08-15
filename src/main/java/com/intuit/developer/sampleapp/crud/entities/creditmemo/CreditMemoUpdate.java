package com.intuit.developer.sampleapp.crud.entities.creditmemo;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.CreditMemoHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CreditMemo;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update creditmemo
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class CreditMemoUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateCreditMemo();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateCreditMemo() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create creditmemo
			CreditMemo creditmemo = CreditMemoHelper.getCreditMemoFields(service);
			CreditMemo addCreditMemo = service.add(creditmemo);
			LOG.info("CreditMemo added : " + addCreditMemo.getId() + " doc num ::: " + addCreditMemo.getDocNumber());
			
			// sparse update creditmemo 
			addCreditMemo.setSparse(true);
			addCreditMemo.setDocNumber(RandomStringUtils.randomNumeric(4));
			CreditMemo savedCreditMemo = service.update(addCreditMemo);
			LOG.info("CreditMemo sparse updated: " + savedCreditMemo.getId() + " doc num ::: " + savedCreditMemo.getDocNumber() );
			
			// update creditmemo with all fields
			addCreditMemo = service.findById(savedCreditMemo);
			CreditMemo updatedCreditMemo = CreditMemoHelper.getCreditMemoFields(service);
			updatedCreditMemo.setId(addCreditMemo.getId());
			updatedCreditMemo.setSyncToken(addCreditMemo.getSyncToken());
		    savedCreditMemo = service.update(updatedCreditMemo);
		    LOG.info("CreditMemo updated with all fields : " + savedCreditMemo.getId() + " doc num ::: " + savedCreditMemo.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
