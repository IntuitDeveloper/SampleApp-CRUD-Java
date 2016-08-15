package com.intuit.developer.sampleapp.crud.helper;

import java.text.ParseException;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Employee;
import com.intuit.ipp.data.TimeActivity;
import com.intuit.ipp.data.TimeActivityTypeEnum;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class TimeActivityHelper {
	
	private TimeActivityHelper() {
		
	}

	public static TimeActivity getTimeActivityFields(DataService service) throws FMSException, ParseException {
		TimeActivity timeActivity = new TimeActivity();
		
		timeActivity.setDescription("Time Activity " + RandomStringUtils.randomAlphanumeric(5));

		timeActivity.setNameOf(TimeActivityTypeEnum.EMPLOYEE);
		
		// add Employee Ref		
		Employee employee = EmployeeHelper.getEmployee(service);
		timeActivity.setEmployeeRef(EmployeeHelper.getEmployeeRef(employee));
		
		try {
			timeActivity.setTxnDate(DateUtils.getCurrentDateTime());
			timeActivity.setStartTime(DateUtils.getCurrentDateTime());
			timeActivity.setEndTime(DateUtils.getDateWithNextDays(2));

		} catch (ParseException pe) {
			throw new FMSException("ParseException while setting date for Start and End of Activity");
		}
		timeActivity.setTimeZone("UTC");
		timeActivity.setTaxable(true);
		timeActivity.setDomain("QBO");
		return timeActivity;
	}

}
