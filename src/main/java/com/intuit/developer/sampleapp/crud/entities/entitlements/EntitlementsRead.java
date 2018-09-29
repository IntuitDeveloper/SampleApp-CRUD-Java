package com.intuit.developer.sampleapp.crud.entities.entitlements;

import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.InvalidTokenException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query entitlements data
 * 
 * @author dderose
 *
 */
public class EntitlementsRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryEntitlements();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryEntitlements() throws FMSException {
		
		try {
			
			//Set the following url for companies connected via OAuth1
			//Config.setProperty(Config.BASE_URL_ENTITLEMENTSERVICE, "https://qbo.sbfinance.intuit.com/manage");
			
			DataService service = DataServiceFactory.getDataService();
			EntitlementsResponse response = service.getEntitlements();
			if (response != null) {
				LOG.info("Entitlements Response -> isQboCompany: " + response.isQboCompany());
			}
			
		} 
		catch (InvalidTokenException e) {			
			System.out.println("Error while calling Entitlements :: " + e.getMessage());
		}
		catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling Entitlements :: " + error.getMessage()));
		}
		
	}
}
