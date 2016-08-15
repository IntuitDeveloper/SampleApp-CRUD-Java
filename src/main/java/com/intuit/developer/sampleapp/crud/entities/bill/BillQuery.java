package com.intuit.developer.sampleapp.crud.entities.bill;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query bill data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class BillQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryBill();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryBill() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all bills
			String sql = "select * from bill";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of bills: " + count);
			
			// add bill 
			Bill bill = BillHelper.getBillFields(service);
			Bill savedBill = service.add(bill);
			LOG.info("Bill created: " + savedBill.getId());
			
			// get bill data based on id
			sql = "select * from bill where id = '" + savedBill.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			bill = (Bill) queryResult.getEntities().get(0);
			LOG.info("Bill amount : " + bill.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
