package com.intuit.developer.sampleapp.crud.entities.item;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.ItemHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query item data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class ItemQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryItem();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryItem() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all item
			String sql = "select * from item";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of items: " + count);

			// add item
			Item item = ItemHelper.getItemFields(service);
			Item savedItem = service.add(item);
			LOG.info("Item created: " + savedItem.getId());

			// get item data based on id
			sql = "select * from item where id = '" + savedItem.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			item = (Item) queryResult.getEntities().get(0);
			LOG.info("Item name : " + item.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
