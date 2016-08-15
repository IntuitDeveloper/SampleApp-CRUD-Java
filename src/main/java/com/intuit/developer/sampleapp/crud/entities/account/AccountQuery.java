package com.intuit.developer.sampleapp.crud.entities.account;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AccountHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query account data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class AccountQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryAccount();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryAccount() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all accounts
			String sql = "select * from account";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of accounts: " + count);
			
			// add bank account
			Account account = AccountHelper.getBankAccountFields();
			Account savedAccount = service.add(account);
			LOG.info("Account created: " + savedAccount.getId());		
				
			// get account data based on id
			sql = "select * from account where id = '" + savedAccount.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			account = (Account) queryResult.getEntities().get(0);
			LOG.info("Account name : " + account.getFullyQualifiedName());
				
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
