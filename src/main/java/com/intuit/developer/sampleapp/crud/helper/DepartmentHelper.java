package com.intuit.developer.sampleapp.crud.helper;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Department;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

/**
 * @author dderose
 *
 */
public final class DepartmentHelper {

	private DepartmentHelper() {
		
	}

	public static Department getDepartmentFields() throws FMSException {

		Department department = new Department();
		department.setName("departmentname_" + RandomStringUtils.randomAlphanumeric(5));
		department.setFullyQualifiedName("departmentname_" + RandomStringUtils.randomAlphanumeric(5));
		department.setSubDepartment(false);
		return department;
	}

	public static Department getDepartment(DataService service) throws FMSException {
		List<Department> departments = (List<Department>) service.findAll(new Department());
		if (!departments.isEmpty()) {
			return departments.get(0);
		}
		return createDepartment(service);
	}

	private static Department createDepartment(DataService service) throws FMSException {
		return service.add(getDepartmentFields());
	}

	public static ReferenceType getDepartmentRef(Department department) {
		ReferenceType departmentRef = new ReferenceType();
		departmentRef.setName(department.getName());
		departmentRef.setValue(department.getId());
		return departmentRef;
	}

}
