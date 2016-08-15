package com.intuit.developer.sampleapp.crud.entities.refundreceipt;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read refundreceipt data using refundreceipt id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class RefundReceiptRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getRefundReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getRefundReceipt() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add refundreceipt
			RefundReceipt refundreceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			RefundReceipt savedRefundReceipt = service.add(refundreceipt);
			LOG.info("RefundReceipt created: " + savedRefundReceipt.getId() + " ::refundreceipt doc num: " + savedRefundReceipt.getDocNumber());
			
			RefundReceipt refundreceiptOut = service.findById(savedRefundReceipt);
			LOG.info("RefundReceipt amount: " + refundreceiptOut.getTotalAmt());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
