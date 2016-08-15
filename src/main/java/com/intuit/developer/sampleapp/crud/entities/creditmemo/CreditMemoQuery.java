package com.intuit.developer.sampleapp.crud.entities.creditmemo;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.CreditMemoHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.CreditMemo;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to query creditmemo data
 * 1. Query all records
 * 2. Query by id, note we'll add the entity first and then query
 * 
 * @author dderose
 *
 */
public class CreditMemoQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryCreditMemo();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryCreditMemo() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// get all creditmemo
			String sql = "select * from creditmemo";
			QueryResult queryResult = service.executeQuery(sql);
			int count = queryResult.getEntities().size();
			
			LOG.info("Total number of creditmemos: " + count);

			// add creditmemo
			CreditMemo creditmemo = CreditMemoHelper.getCreditMemoFields(service);
			CreditMemo savedCreditMemo = service.add(creditmemo);
			LOG.info("CreditMemo created: " + savedCreditMemo.getId());

			// get creditmemo data based on id
			sql = "select * from creditmemo where id = '" + savedCreditMemo.getId() + "'"; 
			queryResult = service.executeQuery(sql);
			creditmemo = (CreditMemo) queryResult.getEntities().get(0);
			LOG.info("CreditMemo remaining credit : " + creditmemo.getRemainingCredit());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
