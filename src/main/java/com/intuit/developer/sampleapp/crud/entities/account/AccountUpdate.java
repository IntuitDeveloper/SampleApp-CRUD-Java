package com.intuit.developer.sampleapp.crud.entities.account;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.AccountHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update account
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class AccountUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateAccount();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateAccount() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create account
			Account account = AccountHelper.getBankAccountFields();
			Account addAccount = service.add(account);
			LOG.info("Account added : " + addAccount.getId() + " name ::: " + addAccount.getName());
			
			// sparse update account 
			addAccount.setSparse(true);
			addAccount.setName(RandomStringUtils.randomAlphanumeric(6));
			Account savedAccount = service.update(addAccount);
			LOG.info("Account sparse updated: " + savedAccount.getId() + " name ::: " + savedAccount.getName() );
			
			// update account with all fields
			addAccount = service.findById(savedAccount);
			Account updatedAccount = AccountHelper.getBankAccountFields();
			updatedAccount.setId(addAccount.getId());
			updatedAccount.setSyncToken(addAccount.getSyncToken());
		    savedAccount = service.update(updatedAccount);
		    LOG.info("Account updated with all fields : " + savedAccount.getId() + " name ::: " + savedAccount.getName());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
