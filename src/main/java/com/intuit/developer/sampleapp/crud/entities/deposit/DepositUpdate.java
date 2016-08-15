package com.intuit.developer.sampleapp.crud.entities.deposit;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.DepositHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update deposit
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class DepositUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateDeposit();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateDeposit() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create deposit
			Deposit deposit = DepositHelper.getDepositFields(service);
			Deposit addDeposit = service.add(deposit);
			LOG.info("Deposit added : " + addDeposit.getId() + " txn date ::: " + addDeposit.getTxnDate());
			
			// sparse update deposit 
			addDeposit.setSparse(true);
			addDeposit.setTxnDate(DateUtils.getDateWithNextDays(2));
			Deposit savedDeposit = service.update(addDeposit);
			LOG.info("Deposit sparse updated: " + savedDeposit.getId() + " txn date ::: " + savedDeposit.getTxnDate() );
			
			// update deposit with all fields
			addDeposit = service.findById(savedDeposit);
			Deposit updatedDeposit = DepositHelper.getDepositFields(service);
			updatedDeposit.setId(addDeposit.getId());
			updatedDeposit.setSyncToken(addDeposit.getSyncToken());
		    savedDeposit = service.update(updatedDeposit);
		    LOG.info("Deposit updated with all fields : " + savedDeposit.getId() + " txn date ::: " + savedDeposit.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
