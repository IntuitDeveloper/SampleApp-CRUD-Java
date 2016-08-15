package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AttachableHelper;
/**
 * Demonstrates methods to delete attachable 
 * 
 * @author dderose
 *
 */
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

public class AttachableDelete {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			deleteAttachable();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void deleteAttachable() throws FMSException, ParseException {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// add attachable with minimum mandatory fields
			Attachable attachable = AttachableHelper.getAttachableFields(service);
			Attachable addAttachable = service.add(attachable);
			LOG.info("Attachable  created: " + addAttachable.getId());
			
			// delete attachable
			Attachable deletedAttachable = service.delete(addAttachable);		
			LOG.info("Attachable deleted : " + deletedAttachable.getId() + " status ::: " + deletedAttachable.getStatus());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while deleting entity :: " + error.getMessage()));
		}
		
	}
}
