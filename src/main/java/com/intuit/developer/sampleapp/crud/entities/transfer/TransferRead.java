package com.intuit.developer.sampleapp.crud.entities.transfer;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TransferHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Transfer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read transfer data using transfer id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class TransferRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getTransfer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getTransfer() throws FMSException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add transfer
			Transfer transfer = TransferHelper.getTransferFields(service);
			Transfer savedTransfer = service.add(transfer);
			LOG.info("Transfer created: " + savedTransfer.getId());
			
			Transfer transferOut = service.findById(savedTransfer);
			LOG.info("Transfer amount: " + transferOut.getAmount());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
