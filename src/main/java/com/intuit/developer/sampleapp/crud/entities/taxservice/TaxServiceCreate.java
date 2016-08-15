package com.intuit.developer.sampleapp.crud.entities.taxservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.intuit.developer.sampleapp.crud.qbo.ContextFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.TaxRateApplicableOnEnum;
import com.intuit.ipp.data.TaxRateDetails;
import com.intuit.ipp.data.TaxService;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.GlobalTaxService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create tax service
 * 
 * @author dderose
 *
 */
public class TaxServiceCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createTaxService();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createTaxService() throws Exception {
		
		try {
			
			// create tax service
			TaxService taxservice = new TaxService();
	        taxservice.setTaxCode("MyTaxCode" + UUID.randomUUID());

	        TaxRateDetails trd = new TaxRateDetails();
	        trd.setRateValue(BigDecimal.ONE);
	        trd.setTaxAgencyId("1"); // create tax agency first and use that id
	        trd.setTaxRateName("MyTaxRate" + UUID.randomUUID());
	        trd.setTaxApplicableOn(TaxRateApplicableOnEnum.SALES);
	        
	        List<TaxRateDetails> taxRateDetails = new ArrayList<TaxRateDetails>();
	        taxRateDetails.add(trd);
	        taxservice.setTaxRateDetails(taxRateDetails);       
	      
	        GlobalTaxService taxdataservice = new GlobalTaxService(ContextFactory.getContext());
	        TaxService ts = taxdataservice.addTaxCode(taxservice);
	        LOG.info("tx code id " + ts.getTaxCodeId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity taxCode:: " + error.getMessage()));			
		}	
	}
	
}
