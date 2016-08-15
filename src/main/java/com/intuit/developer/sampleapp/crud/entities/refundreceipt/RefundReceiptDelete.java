package com.intuit.developer.sampleapp.crud.entities.refundreceipt;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.RefundReceiptHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete refundreceipt data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class RefundReceiptDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteRefundReceipt();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteRefundReceipt() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create refundreceipt
			RefundReceipt refundreceipt = RefundReceiptHelper.getRefundReceiptFields(service);
			RefundReceipt addRefundReceipt = service.add(refundreceipt);
			LOG.info("RefundReceipt added : " + addRefundReceipt.getId());

			//delete refundreceipt
			RefundReceipt deletedRefundReceipt = service.delete(addRefundReceipt);		
			LOG.info("RefundReceipt deleted : " + deletedRefundReceipt.getId() + " status ::: " + deletedRefundReceipt.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
