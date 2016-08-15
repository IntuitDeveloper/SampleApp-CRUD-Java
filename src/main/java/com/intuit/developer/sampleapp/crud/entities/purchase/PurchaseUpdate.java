package com.intuit.developer.sampleapp.crud.entities.purchase;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update purchase
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class PurchaseUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updatePurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updatePurchase() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create purchase
			Purchase purchase = PurchaseHelper.getPurchaseFields(service);
			Purchase addPurchase = service.add(purchase);
			LOG.info("Purchase added : " + addPurchase.getId() + " txn date ::: " + addPurchase.getTxnDate());
			
			// sparse update purchase 
			addPurchase.setSparse(true);
			addPurchase.setTxnDate(DateUtils.getDateWithNextDays(2));
			Purchase savedPurchase = service.update(addPurchase);
			LOG.info("Purchase sparse updated: " + savedPurchase.getId() + " txn date ::: " + savedPurchase.getTxnDate() );
			
			// update purchase with all fields
			addPurchase = service.findById(savedPurchase);
			Purchase updatedPurchase = PurchaseHelper.getPurchaseFields(service);
			updatedPurchase.setId(addPurchase.getId());
			updatedPurchase.setSyncToken(addPurchase.getSyncToken());
		    savedPurchase = service.update(updatedPurchase);
		    LOG.info("Purchase updated with all fields : " + savedPurchase.getId() + " txn date ::: " + savedPurchase.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
