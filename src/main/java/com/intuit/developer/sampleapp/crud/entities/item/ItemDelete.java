package com.intuit.developer.sampleapp.crud.entities.item;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.ItemHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete item data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class ItemDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteItem();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteItem() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create item
			Item item = ItemHelper.getItemFields(service);
			Item addItem = service.add(item);
			LOG.info("Item added : " + addItem.getId() + " active flag ::: " + addItem.isActive());

			// set active flag as false to soft delete
			addItem.setActive(false);
			Item deletedItem = service.update(addItem);		
			LOG.info("Item deleted : " + deletedItem.getId() + " active flag ::: " + deletedItem.isActive());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
