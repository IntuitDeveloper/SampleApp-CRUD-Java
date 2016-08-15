package com.intuit.developer.sampleapp.crud.entities.attachable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.intuit.developer.sampleapp.crud.helper.AttachableHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

/**
 * Demonstrates methods to upload attachment for an invoice
 * Note: Update the location of the file to upload
 * 
 * @author dderose
 *
 */
public class AttachableUpload {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	public static void main(String[] args) {
		try {
			uploadAttachment();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void uploadAttachment() throws Exception {
		
		try {
			DataService service = DataServiceFactory.getDataService();
			
			// get the attachable, in this case we are randomly retrieving a attachable
			Attachable attachable = AttachableHelper.getAttachableFieldsForUpload(service);
			
			// get the base64 content from file 		
			File file = new File("location_of_the_file_to_upload"); //TODO update file location
			//File file = new File("/Users/cderose/Downloads/invoice_sample.pdf");
			InputStream in = new FileInputStream(file);
			
			// call the upload api
			Attachable attachableOutput = service.upload(attachable, in);		
			LOG.info("File name " + attachableOutput.getFileName() + " attached:: Id " + attachableOutput.getId());
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while uploading attachments  :: " + error.getMessage()));
		}
		
	}

}
