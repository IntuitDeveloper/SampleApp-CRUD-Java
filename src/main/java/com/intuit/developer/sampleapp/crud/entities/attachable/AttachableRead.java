package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AttachableHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to read attachable data using attachable id
 * Note: We'll create an entity first and then read the same
 * 
 * @author dderose
 *
 */
public class AttachableRead {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			getAttachable();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void getAttachable() throws FMSException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add attachable with minimum mandatory fields
			Attachable attachable = AttachableHelper.getAttachableFields(service);
			Attachable savedAttachable = service.add(attachable);
			LOG.info("Attachable  created: " + savedAttachable.getId());
			
			Attachable attachableOut = service.findById(savedAttachable);
			LOG.info("Attachable note: " + attachableOut.getNote());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity findById:: " + error.getMessage()));
		}
		
	}
	
}
