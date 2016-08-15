package com.intuit.developer.sampleapp.crud.entities.purchase;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read purchase data using purchase id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class PurchaseRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getPurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getPurchase() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add purchase
			Purchase purchase = PurchaseHelper.getCashPurchaseFields(service);
			Purchase savedPurchase = service.add(purchase);
			LOG.info("Purchase with cash fields created: " + savedPurchase.getId());
			
			Purchase purchaseOut = service.findById(savedPurchase);
			LOG.info("Purchase amount: " + purchaseOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
