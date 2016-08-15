package com.intuit.developer.sampleapp.crud.entities.account;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AccountHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete account data
 * Name-list resources can only be soft deleted meaning, marked as inactive
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class AccountDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteAccount();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteAccount() throws FMSException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create account
			Account account = AccountHelper.getBankAccountFields();
			Account addAccount = service.add(account);
			LOG.info("Account added : " + addAccount.getId() + " active flag ::: " + addAccount.isActive());
			
			// set active flag as false to soft delete
			addAccount.setActive(false);
			Account deletedAccount = service.update(addAccount);		
			LOG.info("Account deleted : " + deletedAccount.getId() + " active flag ::: " + deletedAccount.isActive());
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
