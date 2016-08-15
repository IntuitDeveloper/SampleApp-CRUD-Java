package com.intuit.developer.sampleapp.crud.entities.vendor;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.VendorHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query vendor data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class VendorQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			queryVendor();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryVendor() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all vendors
			String sql = "select * from vendor";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of vendors: " + count);

			// add vendor with minimum mandatory fields
			Vendor vendor = VendorHelper.getVendorWithMandatoryFields();
			Vendor savedVendor = service.add(vendor);
			LOG.info("Vendor with mandatory fields created: " + savedVendor.getId() + " ::vendor name: " + savedVendor.getDisplayName());

			// get vendor data based on id
			sql = "select * from vendor where id = '" + savedVendor.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			vendor = (Vendor)queryResult.getEntities().get(0);
			LOG.info("Vendor name : " + vendor.getDisplayName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
