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
 * Demonstrates methods to create inventory adjustment
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author sramadass
 *
 */
public class InventoryAdjustmentCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createInventoryAdjustment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createInventoryAdjustment() throws Exception {
		
		try {

			DataService service = DataServiceFactory.getDataService();

			// add Inventory Adjustment
			InventoryAdjustment inventoryAdjustment = InventoryAdjustmentHelper.getInvAdjFields(service);
			InventoryAdjustment savedInventoryAdjustment = service.add(inventoryAdjustment);
			LOG.info("Inventory Adjustment created: " + savedInventoryAdjustment.getId());

		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
