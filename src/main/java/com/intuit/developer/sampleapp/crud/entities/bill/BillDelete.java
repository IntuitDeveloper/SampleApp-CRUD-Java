package com.intuit.developer.sampleapp.crud.entities.bill;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete bill data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class BillDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteBill();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteBill() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create bill
			Bill bill = BillHelper.getBillFields(service);
			Bill addBill = service.add(bill);
			LOG.info("Bill added : " + addBill.getId());
			
			//delete bill
			Bill deletedBill = service.delete(addBill);		
			LOG.info("Bill deleted : " + deletedBill.getId() + " status ::: " + deletedBill.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
