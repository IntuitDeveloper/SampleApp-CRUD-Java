package com.intuit.developer.sampleapp.crud.entities.department;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.DepartmentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Department;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read department data using department id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class DepartmentRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getDepartment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getDepartment() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add department
			Department department = DepartmentHelper.getDepartmentFields();
			Department savedDepartment = service.add(department);
			LOG.info("Department created: " + savedDepartment.getId());
			
			Department departmentOut = service.findById(savedDepartment);
			LOG.info("Department name: " + departmentOut.getFullyQualifiedName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
