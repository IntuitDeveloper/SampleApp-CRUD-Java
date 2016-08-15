package com.intuit.developer.sampleapp.crud.entities.bill;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update bill
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class BillUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateBill();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateBill() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create bill
			Bill bill = BillHelper.getBillFields(service);
			Bill addBill = service.add(bill);
			LOG.info("Bill added : " + addBill.getId() + " due date ::: " + addBill.getDueDate());
			
			// sparse update bill 
			addBill.setSparse(true);
			addBill.setDueDate(DateUtils.getDateWithNextDays(40));
			Bill savedBill = service.update(addBill);
			LOG.info("Bill sparse updated: " + savedBill.getId() + " due date ::: " + savedBill.getDueDate() );
			
			// update bill with all fields
			addBill = service.findById(savedBill);
			Bill updatedBill = BillHelper.getBillFields(service);
			updatedBill.setId(addBill.getId());
			updatedBill.setSyncToken(addBill.getSyncToken());
		    savedBill = service.update(updatedBill);
		    LOG.info("Bill updated with all fields : " + savedBill.getId() + " due date ::: " + savedBill.getDueDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
