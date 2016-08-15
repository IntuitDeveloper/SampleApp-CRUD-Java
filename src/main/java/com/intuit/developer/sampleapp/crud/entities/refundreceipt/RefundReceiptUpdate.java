package com.intuit.developer.sampleapp.crud.entities.refundreceipt;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update refundreceipt
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class RefundReceiptUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateRefundReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateRefundReceipt() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create refundreceipt
			RefundReceipt refundreceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			RefundReceipt addRefundReceipt = service.add(refundreceipt);
			LOG.info("RefundReceipt added : " + addRefundReceipt.getId() + " doc num ::: " + addRefundReceipt.getDocNumber());
			
			// sparse update refundreceipt 
			addRefundReceipt.setSparse(true);
			addRefundReceipt.setDocNumber(RandomStringUtils.randomNumeric(4));
			RefundReceipt savedRefundReceipt = service.update(addRefundReceipt);
			LOG.info("RefundReceipt sparse updated: " + savedRefundReceipt.getId() + " doc num ::: " + savedRefundReceipt.getDocNumber() );
			
			// update refundreceipt with all fields
			addRefundReceipt = service.findById(savedRefundReceipt);
			RefundReceipt updatedRefundReceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			updatedRefundReceipt.setId(addRefundReceipt.getId());
			updatedRefundReceipt.setSyncToken(addRefundReceipt.getSyncToken());
		    savedRefundReceipt = service.update(updatedRefundReceipt);
		    LOG.info("RefundReceipt updated with all fields : " + savedRefundReceipt.getId() + " doc num ::: " + savedRefundReceipt.getDocNumber());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
