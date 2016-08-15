package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
 * Demonstrates methods to update attachable
 *
 * 
 * @author dderose
 *
 */
public class AttachableUpdate {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			updateAttachable();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void updateAttachable() throws FMSException, FileNotFoundException, ParseException {
		
		try {
			
			DataService service = DataServiceFactory.getDataService();

			//upload attachment
			Attachable attachable = AttachableHelper.getAttachableFieldsForUpload(service);
			File file = new File("/Users/cderose/Downloads/invoice_sample.pdf");
			InputStream in = new FileInputStream(file);
			
			// call the upload api
			Attachable attachableOutput = service.upload(attachable, in);		
			LOG.info("File name " + attachableOutput.getFileName() + " attached:: Id " + attachableOutput.getId());
			
			//update attachment
			File filenew = new File("/Users/cderose/Downloads/PlanSummary.pdf");
			InputStream inStream = new FileInputStream(filenew);
			attachableOutput.setSparse(true);
			
			// call the upload api
			Attachable updatedAttachment = service.upload(attachableOutput, inStream);	
			LOG.info("Attachable updated  : " + updatedAttachment.getId());
		    
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling entity update:: " + error.getMessage()));
		}
		
	}
}
