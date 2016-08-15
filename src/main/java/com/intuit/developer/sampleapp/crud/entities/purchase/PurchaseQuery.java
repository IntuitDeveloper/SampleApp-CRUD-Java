package com.intuit.developer.sampleapp.crud.entities.purchase;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query purchase data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class PurchaseQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryPurchase();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryPurchase() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all purchase
			String sql = "select * from purchase";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of purchase: " + count);

			// add purchase
			Purchase purchase = PurchaseHelper.getCashPurchaseFields(service);
			Purchase savedPurchase = service.add(purchase);
			LOG.info("Purchase with cash fields created: " + savedPurchase.getId());

			// get purchase data based on id
			sql = "select * from purchase where id = '" + savedPurchase.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			purchase = (Purchase) queryResult.getEntities().get(0);
			LOG.info("Purchase amount : " + purchase.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
