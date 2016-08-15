package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AttachableHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates method to create attachable
 * 
 * @author dderose
 *
 */
public class AttachableCreate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			createAttachable();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void createAttachable() throws Exception {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();
			
			// add attachable with minimum mandatory fields
			Attachable attachable = AttachableHelper.getAttachableFields(service);
			Attachable savedAttachable = service.add(attachable);
			LOG.info("Attachable  created: " + savedAttachable.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));			
		}
		
	}
	
}
