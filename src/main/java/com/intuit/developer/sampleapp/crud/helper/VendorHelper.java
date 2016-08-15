package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.Address;
import com.intuit.developer.sampleapp.crud.helper.Email;
import com.intuit.developer.sampleapp.crud.helper.Telephone;
import com.intuit.developer.sampleapp.crud.helper.TermHelper;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class VendorHelper {
	
	private VendorHelper() {
		
	}

	public static Vendor getVendor(DataService service) throws FMSException, ParseException {
		List<Vendor> vendors = (List<Vendor>) service.findAll(new Vendor());
		
		if (!vendors.isEmpty()) {
			return vendors.get(0);
		}
		return createVendor(service);
	}
	
	private static Vendor createVendor(DataService service) throws FMSException, ParseException {
		return service.add(getVendorWithAllFields(service));
	}

	public static ReferenceType getVendorRef(Vendor vendor) {
			ReferenceType vendorRef = new ReferenceType();
			vendorRef.setName(vendor.getDisplayName());
			vendorRef.setValue(vendor.getId());
			return vendorRef;
	  }
	
	public static Vendor getVendorWithMandatoryFields() throws FMSException {
		Vendor vendor = new Vendor();
		// Mandatory Fields
		vendor.setDisplayName(RandomStringUtils.randomAlphanumeric(8));		
		return vendor;

	}
	
	public static Vendor getVendorWithAllFields(DataService service) throws FMSException, ParseException {
		Vendor vendor = new Vendor();
		// Mandatory Fields
		vendor.setDisplayName(RandomStringUtils.randomAlphanumeric(8));

		// Optional Fields
		vendor.setCompanyName("ABC Corp");
		vendor.setTitle(RandomStringUtils.randomAlphanumeric(7));
		vendor.setGivenName(RandomStringUtils.randomAlphanumeric(8));
		vendor.setMiddleName(RandomStringUtils.randomAlphanumeric(1));
		vendor.setFamilyName(RandomStringUtils.randomAlphanumeric(8));
		vendor.setSuffix("Sr.");
		vendor.setPrintOnCheckName("MS");
			
		vendor.setBillAddr(Address.getPhysicalAddress());
		
		vendor.setTaxIdentifier("1111111");
		
		vendor.setPrimaryEmailAddr(Email.getEmailAddress());
		
		vendor.setPrimaryPhone(Telephone.getPrimaryPhone());
		vendor.setAlternatePhone(Telephone.getAlternatePhone());
		vendor.setMobile(Telephone.getMobilePhone());
		vendor.setFax(Telephone.getFax());
	
		vendor.setWebAddr(Address.getWebSiteAddress());
		
		vendor.setDomain("QBO");

		Term term = TermHelper.getTerm(service);

		vendor.setTermRef(TermHelper.getTermRef(term));
		
		vendor.setAcctNum("11223344");
		vendor.setBalance(new BigDecimal("0"));
		try {
			vendor.setOpenBalanceDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		return vendor;

	}

}
