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
 * Demonstrates methods to update inventory adjustment
 * Sparse update with limited fields
 * 
 * @author sramadass
 *
 */
public class InventoryAdjustmentUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateInventoryAdjustment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateInventoryAdjustment() {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create Inventory Adjustment
			InventoryAdjustment inventoryAdjustment = InventoryAdjustmentHelper.getInvAdjFields(service);
			InventoryAdjustment savedInventoryAdjustment = service.add(inventoryAdjustment);
			LOG.info("Inventory Adjustment created: " + savedInventoryAdjustment.getId() + " private note ::: " + savedInventoryAdjustment.getPrivateNote());
			
			// sparse update Inventory Adjustment
			savedInventoryAdjustment.setSparse(true);
			savedInventoryAdjustment.setPrivateNote("Update Note");
			InventoryAdjustment updatedInventoryAdjustment = service.update(savedInventoryAdjustment);
			LOG.info("Inventory Adjustment sparse updated: " + updatedInventoryAdjustment.getId() + " private note ::: " + updatedInventoryAdjustment.getPrivateNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
