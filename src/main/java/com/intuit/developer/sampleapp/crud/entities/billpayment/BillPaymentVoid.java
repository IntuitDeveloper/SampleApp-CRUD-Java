package com.intuit.developer.sampleapp.crud.entities.billpayment;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.BillHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to void billPayment
 * Note: We'll create an entity first and then void the same
 * 
 * @author dderose
 *
 */
public class BillPaymentVoid {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			voidBillPayment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void voidBillPayment() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create billPayment
			BillPayment billPayment = BillHelper.getBillPaymentFields(service);
			BillPayment addBillPayment = service.add(billPayment);
			LOG.info("BillPayment added : " + addBillPayment.getId());

			// void billPayment
			BillPayment voidedBillPayment = service.voidRequest(addBillPayment);		
			LOG.info("BillPayment voided : " + voidedBillPayment.getId() + " status ::: " + voidedBillPayment.getPrivateNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while voiding entity :: " + error.getMessage()));
		}
	}



}
