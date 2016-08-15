package com.intuit.developer.sampleapp.crud.entities.purchaseorder;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update purchaseorder
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class PurchaseOrderUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updatePurchaseOrder();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updatePurchaseOrder() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create purchaseorder
			PurchaseOrder purchaseorder = PurchaseHelper.getPurchaseOrderFields(service);
			PurchaseOrder addPurchaseOrder = service.add(purchaseorder);
			LOG.info("PurchaseOrder added : " + addPurchaseOrder.getId() + " txn date ::: " + addPurchaseOrder.getTxnDate());
			
			// sparse update purchaseorder 
			addPurchaseOrder.setSparse(true);
			addPurchaseOrder.setTxnDate(DateUtils.getDateWithNextDays(2));
			PurchaseOrder savedPurchaseOrder = service.update(addPurchaseOrder);
			LOG.info("PurchaseOrder sparse updated: " + savedPurchaseOrder.getId() + " txn date ::: " + savedPurchaseOrder.getTxnDate());
			
			// update purchaseorder with all fields
			addPurchaseOrder = service.findById(savedPurchaseOrder);
			PurchaseOrder updatedPurchaseOrder = PurchaseHelper.getPurchaseOrderFields(service);
			updatedPurchaseOrder.setId(addPurchaseOrder.getId());
			updatedPurchaseOrder.setSyncToken(addPurchaseOrder.getSyncToken());
		    savedPurchaseOrder = service.update(updatedPurchaseOrder);
		    LOG.info("PurchaseOrder updated with all fields : " + savedPurchaseOrder.getId() + " txn date ::: " + savedPurchaseOrder.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
