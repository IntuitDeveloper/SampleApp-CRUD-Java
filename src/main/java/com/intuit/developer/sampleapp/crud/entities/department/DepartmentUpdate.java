package com.intuit.developer.sampleapp.crud.entities.department;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.DepartmentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Department;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update department
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class DepartmentUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateDepartment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateDepartment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create department
			Department department = DepartmentHelper.getDepartmentFields();
			Department addDepartment = service.add(department);
			LOG.info("Department added : " + addDepartment.getId() + " name ::: " + addDepartment.getName());
			
			// sparse update department 
			addDepartment.setSparse(true);
			addDepartment.setName(RandomStringUtils.randomAlphanumeric(6));
			Department savedDepartment = service.update(addDepartment);
			LOG.info("Department sparse updated: " + savedDepartment.getId() + " name ::: " + savedDepartment.getName() );
			
			// update department with all fields
			addDepartment = service.findById(savedDepartment);
			Department updatedDepartment = DepartmentHelper.getDepartmentFields();
			updatedDepartment.setId(addDepartment.getId());
			updatedDepartment.setSyncToken(addDepartment.getSyncToken());
		    savedDepartment = service.update(updatedDepartment);
		    LOG.info("Department updated with all fields : " + savedDepartment.getId() + " name ::: " + savedDepartment.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
