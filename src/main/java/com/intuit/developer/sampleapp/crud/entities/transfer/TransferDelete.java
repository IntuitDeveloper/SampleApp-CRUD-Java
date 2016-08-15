package com.intuit.developer.sampleapp.crud.entities.transfer;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TransferHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Transfer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to delete transfer data
 * Note: We'll create an entity first and then delete the same
 * 
 * @author dderose
 *
 */
public class TransferDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteTransfer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteTransfer() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// create transfer
			Transfer transfer = TransferHelper.getTransferFields(service);
			Transfer addTransfer = service.add(transfer);
			LOG.info("Transfer added : " + addTransfer.getId());

			//delete transfer
			Transfer deletedTransfer = service.delete(addTransfer);		
			LOG.info("Transfer deleted : " + deletedTransfer.getId() + " status ::: " + deletedTransfer.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
