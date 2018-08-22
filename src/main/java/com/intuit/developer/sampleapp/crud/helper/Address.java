package com.intuit.developer.sampleapp.crud.helper;

import com.intuit.ipp.data.PhysicalAddress;
import com.intuit.ipp.data.WebSiteAddress;

/**
 * @author dderose
 *
 */
public final class Address {
	
	private Address() {
		
	}

	public static PhysicalAddress getPhysicalAddress() {
		PhysicalAddress billingAdd = new PhysicalAddress();
		billingAdd.setLine1("123 Main St");
		billingAdd.setCity("Mountain View");
		billingAdd.setCountry("United States");
		billingAdd.setCountrySubDivisionCode("CA");
		billingAdd.setPostalCode("94043");
		return billingAdd;
	}
	
	public static WebSiteAddress getWebSiteAddress() {
		WebSiteAddress webSite = new WebSiteAddress();
		webSite.setURI("http://abccorp.com");
		webSite.setDefault(true);
		webSite.setTag("Business");
		return webSite;
	}
	
	public static PhysicalAddress getAddressForAST() {
		PhysicalAddress billingAdd = new PhysicalAddress();
		billingAdd.setLine1("2700 Coast Ave");
		billingAdd.setLine2("MountainView, CA 94043");
		return billingAdd;
	}

}
