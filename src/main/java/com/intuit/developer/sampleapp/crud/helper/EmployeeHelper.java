package com.intuit.developer.sampleapp.crud.helper;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Employee;
import com.intuit.ipp.data.Gender;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class EmployeeHelper {
	
	private EmployeeHelper() {
		
	}

	public static Employee getEmployeeWithMandatoryFields() throws FMSException {
		Employee employee = new Employee();
		// Mandatory Fields
		employee.setGivenName(RandomStringUtils.randomAlphanumeric(4));
		employee.setFamilyName(RandomStringUtils.randomAlphanumeric(4));
		employee.setDisplayName(RandomStringUtils.randomAlphanumeric(4));
		
		return employee;

	}
	
	public static Employee getEmployeeWithAllFields() throws FMSException, ParseException {
		Employee employee = new Employee();
		// Mandatory Fields
		employee.setGivenName(RandomStringUtils.randomAlphanumeric(4));
		employee.setMiddleName(RandomStringUtils.randomAlphanumeric(1));
		employee.setFamilyName(RandomStringUtils.randomAlphanumeric(4));
		employee.setFullyQualifiedName(RandomStringUtils.randomAlphanumeric(4));
		employee.setCompanyName(RandomStringUtils.randomAlphanumeric(2));
		employee.setDisplayName(RandomStringUtils.randomAlphanumeric(4));
		employee.setPrintOnCheckName(RandomStringUtils.randomAlphanumeric(3));

		// Optional Fields
		employee.setPrimaryPhone(Telephone.getPrimaryPhone());
		employee.setMobile(Telephone.getMobilePhone());
		
		employee.setPrimaryAddr(Address.getPhysicalAddress());
		
		employee.setSSN("empSSN_"+ RandomStringUtils.randomAlphanumeric(6));

		employee.setHiredDate(DateUtils.getDateWithPrevDays(300));
		employee.setReleasedDate(DateUtils.getDateWithNextDays(300));
		employee.setBirthDate(DateUtils.getDateWithPrevDays(6200));
		
		employee.setGender(Gender.MALE);
		employee.setEmployeeNumber("emp_no"+ RandomStringUtils.randomAlphanumeric(7));

		return employee;

	}
	
	public static Employee getEmployee(DataService service) throws FMSException, ParseException {
		List<Employee> employees = (List<Employee>) service.findAll(new Employee());
		if (!employees.isEmpty()) {
			return employees.get(0);
		}
		return createEmployee(service);
	}

	private static Employee createEmployee(DataService service) throws FMSException, ParseException {
		return service.add(getEmployeeWithAllFields());
	}

	public static ReferenceType getEmployeeRef(Employee employee) {
		ReferenceType employeeRef = new ReferenceType();
		employeeRef.setName(employee.getDisplayName());
		employeeRef.setValue(employee.getId());
		return employeeRef;
	}


}
