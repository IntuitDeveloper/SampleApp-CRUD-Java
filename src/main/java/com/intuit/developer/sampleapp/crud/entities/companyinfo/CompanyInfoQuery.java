package com.intuit.developer.sampleapp.crud.entities.companyinfo;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CompanyInfo;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query companyinfo data
 * 1. Query all records
 * 
 * @author dderose
 *
 */
public class CompanyInfoQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryCompanyInfo();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryCompanyInfo() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all companyinfo
			String sql = "select * from companyinfo";
			QueryResult queryResult = service.executeQuery(sql);
			if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
				CompanyInfo companyInfo = (CompanyInfo) queryResult.getEntities().get(0);
				LOG.info("Companyinfo -> CompanyName: " + companyInfo.getCompanyName());
			}
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
