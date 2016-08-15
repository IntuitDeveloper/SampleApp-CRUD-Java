package com.intuit.developer.sampleapp.crud.entities.classentity;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.ClassHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Class;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update class
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class ClassUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateClass();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateClass() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create class
			Class classObj = ClassHelper.getClassFields();
			Class addClass = service.add(classObj);
			LOG.info("Class added : " + addClass.getId() + " name ::: " + addClass.getName());
			
			// sparse update class 
			addClass.setSparse(true);
			addClass.setName(RandomStringUtils.randomAlphanumeric(6));
			Class savedClass = service.update(addClass);
			LOG.info("Class sparse updated: " + savedClass.getId() + " name ::: " + savedClass.getName() );
			
			// update class with all fields
			addClass = service.findById(savedClass);
			Class updatedClass = ClassHelper.getClassFields();
			updatedClass.setId(addClass.getId());
			updatedClass.setSyncToken(addClass.getSyncToken());
		    savedClass = service.update(updatedClass);
		    LOG.info("Class updated with all fields : " + savedClass.getId() + " name ::: " + savedClass.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
