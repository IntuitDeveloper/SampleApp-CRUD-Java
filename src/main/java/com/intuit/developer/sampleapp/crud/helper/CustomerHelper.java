package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.Address;
import com.intuit.developer.sampleapp.crud.helper.Email;
import com.intuit.developer.sampleapp.crud.helper.Job;
import com.intuit.developer.sampleapp.crud.helper.Telephone;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class CustomerHelper {
	
	private CustomerHelper() {
		
	}

	public static Customer getCustomer(DataService service) throws FMSException, ParseException {
		List<Customer> customers = (List<Customer>) service.findAll(new Customer());
		if (!customers.isEmpty()) {
			return customers.get(0);
		}
		return createCustomer(service);
	}

	private static Customer createCustomer(DataService service) throws FMSException, ParseException {
		return service.add(getCustomerWithAllFields());
	}

	public static ReferenceType getCustomerRef(Customer customer) {
		ReferenceType customerRef = new ReferenceType();
		customerRef.setName(customer.getDisplayName());
		customerRef.setValue(customer.getId());
		return customerRef;
	}

	public static Customer getCustomerWithMandatoryFields() throws FMSException {
		Customer customer = new Customer();
		// Mandatory Fields
		customer.setDisplayName(RandomStringUtils.randomAlphanumeric(6));
		return customer;

	}

	public static Customer getCustomerWithAllFields() throws FMSException, ParseException {
		Customer customer = new Customer();
		// Mandatory Fields
		customer.setDisplayName(RandomStringUtils.randomAlphanumeric(6));
		customer.setTitle(RandomStringUtils.randomAlphanumeric(3));
		customer.setGivenName(RandomStringUtils.randomAlphanumeric(6));
		customer.setMiddleName(RandomStringUtils.randomAlphanumeric(6));
		customer.setFamilyName(RandomStringUtils.randomAlphanumeric(6));

		// Optional Fields
		customer.setOrganization(false);
		customer.setSuffix("Sr.");
		customer.setCompanyName("ABC Corporations");
		customer.setPrintOnCheckName("Print name");
		customer.setActive(true);

		customer.setPrimaryPhone(Telephone.getPrimaryPhone());
		customer.setAlternatePhone(Telephone.getAlternatePhone());
		customer.setMobile(Telephone.getMobilePhone());
		customer.setFax(Telephone.getFax());

		customer.setPrimaryEmailAddr(Email.getEmailAddress());

		customer.setContactName("Contact Name");
		customer.setAltContactName("Alternate Name");
		customer.setNotes("Testing Notes");
		customer.setBalance(new BigDecimal("0"));
		customer.setOpenBalanceDate(DateUtils.getCurrentDateTime());
		customer.setBalanceWithJobs(new BigDecimal("5055.5"));
		customer.setCreditLimit(new BigDecimal("200000"));
		customer.setAcctNum("Test020102");
		customer.setResaleNum("40");
		customer.setJob(false);

		customer.setJobInfo(Job.getJobInfo());

		customer.setBillAddr(Address.getPhysicalAddress());
		customer.setShipAddr(Address.getPhysicalAddress());

		return customer;

	}

}
