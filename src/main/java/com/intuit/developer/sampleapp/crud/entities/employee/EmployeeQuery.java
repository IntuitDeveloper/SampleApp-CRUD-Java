package com.intuit.developer.sampleapp.crud.entities.employee;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.EmployeeHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Employee;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query employee data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class EmployeeQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			queryEmployees();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryEmployees() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all employees
			String sql = "select * from employee";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of employees: " + count);

			// add employee with minimum mandatory fields
			Employee employee = EmployeeHelper.getEmployeeWithMandatoryFields();
			Employee savedEmployee = service.add(employee);
			LOG.info("Employee with mandatory fields created: " + savedEmployee.getId() + " ::employee name: " + savedEmployee.getDisplayName());

			// get employee data based on id
			sql = "select * from employee where id = '" + savedEmployee.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			employee = (Employee)queryResult.getEntities().get(0);
			LOG.info("Employee name : " + employee.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
