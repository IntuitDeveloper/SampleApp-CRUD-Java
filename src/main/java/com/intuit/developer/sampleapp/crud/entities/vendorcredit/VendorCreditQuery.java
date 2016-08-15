package com.intuit.developer.sampleapp.crud.entities.vendorcredit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorCreditHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query vendorcredit data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class VendorCreditQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryVendorCredit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryVendorCredit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all vendorcredit
			String sql = "select * from vendorcredit";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of vendorcredits: " + count);

			// add vendorcredit
			VendorCredit vendorcredit = VendorCreditHelper.getVendorCreditFields(service);
			VendorCredit savedVendorCredit = service.add(vendorcredit);
			LOG.info("VendorCredit created: " + savedVendorCredit.getId());

			// get vendorcredit data based on id
			sql = "select * from vendorcredit where id = '" + savedVendorCredit.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			vendorcredit = (VendorCredit) queryResult.getEntities().get(0);
			LOG.info("VendorCredit name : " + vendorcredit.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
