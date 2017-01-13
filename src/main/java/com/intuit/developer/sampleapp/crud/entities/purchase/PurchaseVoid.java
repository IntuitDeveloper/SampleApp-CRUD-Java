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
 * Demonstrates methods to void purchase
 * Note: We'll create an entity first and then void the same
 * 
 * @author dderose
 *
 */
public class PurchaseVoid {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			voidPurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void voidPurchase() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create purchase
			Purchase purchase = PurchaseHelper.getPurchaseFields(service);
			Purchase addPurchase = service.add(purchase);
			LOG.info("Purchase added : " + addPurchase.getId());

			// void purchase
			Purchase voidedPurchase = service.voidRequest(addPurchase);		
			LOG.info("Purchase voided : " + voidedPurchase.getId() + " status ::: " + voidedPurchase.getPrivateNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while voiding entity :: " + error.getMessage()));
		}
	}



}
