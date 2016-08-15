package com.intuit.developer.sampleapp.crud.entities.deposit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.DepositHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query deposit data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class DepositQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryDeposit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryDeposit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all deposit
			String sql = "select * from deposit";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of deposits: " + count);

			// add deposit
			Deposit deposit = DepositHelper.getDepositFields(service);
			Deposit savedDeposit = service.add(deposit);
			LOG.info("Deposit created: " + savedDeposit.getId());

			// get deposit data based on id
			sql = "select * from deposit where id = '" + savedDeposit.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			deposit = (Deposit) queryResult.getEntities().get(0);
			LOG.info("Deposit amount : " + deposit.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
