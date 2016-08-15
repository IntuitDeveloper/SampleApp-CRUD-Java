package com.intuit.developer.sampleapp.crud.entities.employee;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.EmployeeHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Employee;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create employee
 * 1. Using mandatory fields
 * 2. Using all fields
 * 
 * @author dderose
 *
 */
public class EmployeeCreate {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createEmployee();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createEmployee() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add employee with minimum mandatory fields
			Employee employee = EmployeeHelper.getEmployeeWithMandatoryFields();
			Employee savedEmployee = service.add(employee);
			LOG.info("Employee with mandatory fields created: " + savedEmployee.getId() + " ::employee name: " + savedEmployee.getDisplayName());
			
			// add employee with all fields
			employee = EmployeeHelper.getEmployeeWithAllFields();
			savedEmployee = service.add(employee);
			LOG.info("Employee with all fields created: " + savedEmployee.getId() + " ::employee name: " + savedEmployee.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}

}
