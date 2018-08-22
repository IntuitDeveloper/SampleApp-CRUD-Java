package com.intuit.developer.sampleapp.crud.helper;

import java.util.List;

import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

/**
 * @author dderose
 *
 */
public final class TaxCodeInfo {
	
	private TaxCodeInfo() {
		
	}

	public static TaxCode getTaxCode(DataService service) throws FMSException {
		List<TaxCode> taxcodes = (List<TaxCode>) service.findAll(new TaxCode());
		return taxcodes.get(0); 
	}
	
	  public static ReferenceType getTaxCodeRef(TaxCode taxcode) {
			ReferenceType taxcodeRef = new ReferenceType();
			taxcodeRef.setName(taxcode.getName());
			taxcodeRef.setValue(taxcode.getId());
			return taxcodeRef;
	  }
	  
	  public static ReferenceType getTaxCodeRef(String taxcode) {
			ReferenceType taxcodeRef = new ReferenceType();
			taxcodeRef.setValue(taxcode);
			return taxcodeRef;
	  }

}
