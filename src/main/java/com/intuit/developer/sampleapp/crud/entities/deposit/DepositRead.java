package com.intuit.developer.sampleapp.crud.entities.deposit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.DepositHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read deposit data using deposit id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class DepositRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getDeposit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getDeposit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add deposit
			Deposit deposit = DepositHelper.getDepositFields(service);
			Deposit savedDeposit = service.add(deposit);
			LOG.info("Deposit with all fields created: " + savedDeposit.getId());
			
			Deposit depositOut = service.findById(savedDeposit);
			LOG.info("Deposit amount: " + depositOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
