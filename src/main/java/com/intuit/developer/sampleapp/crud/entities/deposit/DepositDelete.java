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
 * Demonstrates methods to delete deposit data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class DepositDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteDeposit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteDeposit() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create deposit
			Deposit deposit = DepositHelper.getDepositFields(service);
			Deposit addDeposit = service.add(deposit);
			LOG.info("Deposit added : " + addDeposit.getId());
			
			//delete deposit
			Deposit deletedDeposit = service.delete(addDeposit);		
			LOG.info("Deposit deleted : " + deletedDeposit.getId() + " status ::: " + deletedDeposit.getStatus());
						
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
