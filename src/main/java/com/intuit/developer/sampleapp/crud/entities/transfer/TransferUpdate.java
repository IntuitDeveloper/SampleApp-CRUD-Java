package com.intuit.developer.sampleapp.crud.entities.transfer;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.TransferHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Transfer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to update transfer
 * 1. Sparse update with limited fields
 * 2. Full update with all fields
 * Note: We'll create an entity first and then update the same
 * 
 * @author dderose
 *
 */
public class TransferUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateTransfer();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateTransfer() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// create transfer
			Transfer transfer = TransferHelper.getTransferFields(service);
			Transfer addTransfer = service.add(transfer);
			LOG.info("Transfer added : " + addTransfer.getId() + " txn date ::: " + addTransfer.getTxnDate());
			
			// sparse update transfer 
			addTransfer.setSparse(true);
			addTransfer.setTxnDate(DateUtils.getDateWithNextDays(2));
			Transfer savedTransfer = service.update(addTransfer);
			LOG.info("Transfer sparse updated: " + savedTransfer.getId() + " txn date ::: " + savedTransfer.getTxnDate() );
			
			// update transfer with all fields
			addTransfer = service.findById(savedTransfer);
			Transfer updatedTransfer = TransferHelper.getTransferFields(service);
			updatedTransfer.setId(addTransfer.getId());
			updatedTransfer.setSyncToken(addTransfer.getSyncToken());
		    savedTransfer = service.update(updatedTransfer);
		    LOG.info("Transfer updated with all fields : " + savedTransfer.getId() + " txn date ::: " + savedTransfer.getTxnDate());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}

}
