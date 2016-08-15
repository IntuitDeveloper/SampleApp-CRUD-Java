package com.intuit.developer.sampleapp.crud.entities.billpayment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete billpayment data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class BillPaymentDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteBillPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteBillPayment() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create billpayment
			BillPayment billpayment = BillHelper.getBillPaymentFields(service);
			BillPayment addBillPayment = service.add(billpayment);
			LOG.info("BillPayment added : " + addBillPayment.getId());
			
			//delete billpayment
			BillPayment deletedBillPayment = service.delete(addBillPayment);		
			LOG.info("BillPayment deleted : " + deletedBillPayment.getId() + " status ::: " + deletedBillPayment.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
