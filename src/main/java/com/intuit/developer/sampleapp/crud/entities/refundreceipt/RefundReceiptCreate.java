package com.intuit.developer.sampleapp.crud.entities.refundreceipt;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to create refundreceipt
 * 
 * @author dderose
 *
 */
public class RefundReceiptCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createRefundReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createRefundReceipt() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add refundreceipt
			RefundReceipt refundreceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			RefundReceipt savedRefundReceipt = service.add(refundreceipt);
			LOG.info("RefundReceipt created: " + savedRefundReceipt.getId() + " ::refundreceipt doc num: " + savedRefundReceipt.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
	
}
