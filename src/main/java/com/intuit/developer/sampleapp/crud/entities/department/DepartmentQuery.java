package com.intuit.developer.sampleapp.crud.entities.department;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.DepartmentHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Department;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query department data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class DepartmentQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryDepartment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryDepartment() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all department
			String sql = "select * from department";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of departments: " + count);

			// add department
			Department department = DepartmentHelper.getDepartmentFields();
			Department savedDepartment = service.add(department);
			LOG.info("Department created: " + savedDepartment.getId());

			// get department data based on id
			sql = "select * from department where id = '" + savedDepartment.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			department = (Department) queryResult.getEntities().get(0);
			LOG.info("Department name : " + department.getFullyQualifiedName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
