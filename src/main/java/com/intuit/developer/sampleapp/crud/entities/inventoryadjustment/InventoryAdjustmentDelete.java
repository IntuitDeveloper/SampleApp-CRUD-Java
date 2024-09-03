package com.intuit.developer.sampleapp.crud.entities.inventoryadjustment;

import com.intuit.developer.sampleapp.crud.helper.InventoryAdjustmentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.InventoryAdjustment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

import java.text.ParseException;
import java.util.List;

/**
 * Demonstrates methods to delete inventory adjustment
 * Note: We'll create an entity first and then delete the same
 * 
 * @author sramadass
 *
 */
public class InventoryAdjustmentDelete {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteInventoryAdjustment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteInventoryAdjustment() throws ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();

			// add Inventory Adjustment
			InventoryAdjustment inventoryAdjustment = InventoryAdjustmentHelper.getInvAdjFields(service);
			InventoryAdjustment savedInventoryAdjustment = service.add(inventoryAdjustment);
			LOG.info("Inventory Adjustment created: " + savedInventoryAdjustment.getId());

			// delete Inventory Adjustment
			InventoryAdjustment deletedInventoryAdjustment = service.delete(savedInventoryAdjustment);
			LOG.info("Inventory Adjustment deleted : " + deletedInventoryAdjustment.getId() + " status ::: " + deletedInventoryAdjustment.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
	}

}
