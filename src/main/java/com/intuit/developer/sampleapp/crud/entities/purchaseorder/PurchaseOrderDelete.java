package com.intuit.developer.sampleapp.crud.entities.purchaseorder;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.PurchaseHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete purchaseorder data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class PurchaseOrderDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deletePurchaseOrder();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deletePurchaseOrder() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create purchaseorder
			PurchaseOrder purchaseorder = PurchaseHelper.getPurchaseOrderFields(service);
			PurchaseOrder addPurchaseOrder = service.add(purchaseorder);
			LOG.info("PurchaseOrder added : " + addPurchaseOrder.getId());

			//delete purchaseorder
			PurchaseOrder deletedPurchaseOrder = service.delete(addPurchaseOrder);		
			LOG.info("PurchaseOrder deleted : " + deletedPurchaseOrder.getId() + " status ::: " + deletedPurchaseOrder.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
