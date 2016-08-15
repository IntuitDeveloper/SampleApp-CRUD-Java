package com.intuit.developer.sampleapp.crud.entities.classentity;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.ClassHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Class;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete class data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class ClassDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteClass();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteClass() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create class
			Class classObj = ClassHelper.getClassFields();
			Class addClass = service.add(classObj);
			LOG.info("Class added : " + addClass.getId() + " active flag ::: " + addClass.isActive());
			
			// set active flag as false to soft delete
			addClass.setActive(false);
			Class deletedClass = service.update(addClass);		
			LOG.info("Class deleted : " + deletedClass.getId() + " active flag ::: " + deletedClass.isActive());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
