package com.intuit.developer.sampleapp.crud.entities.inventoryadjustment;

import com.intuit.developer.sampleapp.crud.helper.InventoryAdjustmentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.InventoryAdjustment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

import java.util.List;

/**
 * Demonstrates methods to read InventoryAdjustment using Inventory Adjustment id
 * Note: We'll create an entity first and then read the same
 * 
 * @author sramadass
 *
 */
public class InventoryAdjustmentRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getInventoryAdjustment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getInventoryAdjustment() {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add Inventory Adjustment
			InventoryAdjustment inventoryAdjustment = InventoryAdjustmentHelper.getInvAdjFields(service);
			InventoryAdjustment savedInventoryAdjustment = service.add(inventoryAdjustment);
			LOG.info("Inventory Adjustment created: " + savedInventoryAdjustment.getId());

			InventoryAdjustment inventoryAdjustmentOut = service.findById(savedInventoryAdjustment);
			LOG.info("Inventory Adjustment ID: " + inventoryAdjustmentOut.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
