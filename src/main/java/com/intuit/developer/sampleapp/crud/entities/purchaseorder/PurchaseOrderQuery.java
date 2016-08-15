package com.intuit.developer.sampleapp.crud.entities.purchaseorder;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query purchaseorder data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class PurchaseOrderQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryPurchaseOrder();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryPurchaseOrder() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all purchaseorder
			String sql = "select * from purchaseorder";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of purchaseorders: " + count);

			// add purchaseorder
			PurchaseOrder purchaseorder = PurchaseHelper.getPurchaseOrderFields(service);
			PurchaseOrder savedPurchaseOrder = service.add(purchaseorder);
			LOG.info("PurchaseOrder created: " + savedPurchaseOrder.getId() + " ::purchaseorder doc num: " + savedPurchaseOrder.getDocNumber());

			// get purchaseorder data based on id
			sql = "select * from purchaseorder where id = '" + savedPurchaseOrder.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			purchaseorder = (PurchaseOrder) queryResult.getEntities().get(0);
			LOG.info("PurchaseOrder amount : " + purchaseorder.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
