package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AttachableHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query attachable
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class AttachableQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryAttachable();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryAttachable() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all attachables
			String sql = "select * from attachable";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of attachables: " + count);
			
			// add attachable with minimum mandatory fields
			Attachable attachable = AttachableHelper.getAttachableFields(service);
			Attachable savedAttachable = service.add(attachable);
			LOG.info("Attachable  created: " + savedAttachable.getId());

			
			// get attachable data based on id
			sql = "select * from attachable where id = '" + savedAttachable.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			attachable = (Attachable) queryResult.getEntities().get(0);
			LOG.info("Attachable name : " + attachable.getNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
