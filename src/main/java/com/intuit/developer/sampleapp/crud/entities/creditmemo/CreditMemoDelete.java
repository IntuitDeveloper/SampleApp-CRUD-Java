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
 * Demonstrates methods to delete creditmemo data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class CreditMemoDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteCreditMemo();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteCreditMemo() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create creditmemo
			CreditMemo creditmemo = CreditMemoHelper.getCreditMemoFields(service);
			CreditMemo addCreditMemo = service.add(creditmemo);
			LOG.info("CreditMemo added : " + addCreditMemo.getId());
			
			//delete creditmemo
			CreditMemo deletedCreditMemo = service.delete(addCreditMemo);		
			LOG.info("CreditMemo deleted : " + deletedCreditMemo.getId() + " status ::: " + deletedCreditMemo.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
