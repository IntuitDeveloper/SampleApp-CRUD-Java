package com.intuit.developer.sampleapp.crud.entities.classentity;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.ClassHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Class;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query class data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class ClassQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryClass();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryClass() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all class
			String sql = "select * from class";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of class: " + count);
			
			// add class
			Class classObj = ClassHelper.getClassFields();
			Class savedClass = service.add(classObj);
			LOG.info("Class created: " + savedClass.getId());
			
			// get class data based on id
			sql = "select * from class where id = '" + savedClass.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			classObj = (Class) queryResult.getEntities().get(0);
			LOG.info("Class name : " + classObj.getFullyQualifiedName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
