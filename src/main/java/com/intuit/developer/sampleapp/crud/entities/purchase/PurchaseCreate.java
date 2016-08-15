package com.intuit.developer.sampleapp.crud.entities.purchase;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create purchase
 * 
 * @author dderose
 *
 */
public class PurchaseCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createPurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createPurchase() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add purchase
			Purchase purchase = PurchaseHelper.getCashPurchaseFields(service);
			Purchase savedPurchase = service.add(purchase);
			LOG.info("Purchase with cash fields created: " + savedPurchase.getId());
		
			purchase = PurchaseHelper.getPurchaseFields(service);
			savedPurchase = service.add(purchase);
			LOG.info("Purchase with all fields created: " + savedPurchase.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
	
}
