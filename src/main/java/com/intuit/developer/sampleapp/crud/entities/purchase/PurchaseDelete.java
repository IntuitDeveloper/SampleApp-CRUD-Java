package com.intuit.developer.sampleapp.crud.entities.purchase;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete purchase data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class PurchaseDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deletePurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deletePurchase() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create purchase
			Purchase purchase = PurchaseHelper.getPurchaseFields(service);
			Purchase addPurchase = service.add(purchase);
			LOG.info("Purchase added : " + addPurchase.getId());

			//delete purchase
			Purchase deletedPurchase = service.delete(addPurchase);		
			LOG.info("Purchase deleted : " + deletedPurchase.getId() + " status ::: " + deletedPurchase.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
