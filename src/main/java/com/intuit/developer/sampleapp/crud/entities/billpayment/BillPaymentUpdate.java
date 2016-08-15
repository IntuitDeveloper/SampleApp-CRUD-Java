package com.intuit.developer.sampleapp.crud.entities.billpayment;

import java.math.BigDecimal;
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
 * Demonstrates methods to update billpayment
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class BillPaymentUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateBillPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateBillPayment() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create billpayment
			BillPayment billpayment = BillHelper.getBillPaymentFields(service);
			BillPayment addBillPayment = service.add(billpayment);
			LOG.info("BillPayment added : " + addBillPayment.getId() + " amount ::: " + addBillPayment.getTotalAmt());
			
			// sparse update billpayment 
			addBillPayment.setSparse(true);
			addBillPayment.setTotalAmt(new BigDecimal("33"));
			BillPayment savedBillPayment = service.update(addBillPayment);
			LOG.info("BillPayment sparse updated: " + savedBillPayment.getId() + " amount ::: " + savedBillPayment.getTotalAmt() );
			
			// update billpayment with all fields
			addBillPayment = service.findById(savedBillPayment);
			BillPayment updatedBillPayment = BillHelper.getBillPaymentFields(service);
			updatedBillPayment.setId(addBillPayment.getId());
			updatedBillPayment.setSyncToken(addBillPayment.getSyncToken());
		    savedBillPayment = service.update(updatedBillPayment);
		    LOG.info("BillPayment updated with all fields : " + savedBillPayment.getId() + " amount ::: " + savedBillPayment.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
