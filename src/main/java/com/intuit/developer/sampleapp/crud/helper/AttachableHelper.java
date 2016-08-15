package com.intuit.developer.sampleapp.crud.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableCategoryEnum;
import com.intuit.ipp.data.AttachableRef;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

/**
 * @author dderose
 *
 */
public final class AttachableHelper {
	
	private AttachableHelper() {
		
	}

	public static Attachable getAttachableFields(DataService service) throws FMSException, ParseException {
		
		Attachable attachable = new Attachable();
		attachable.setLat("25.293112341223");
		attachable.setLong("-21.3253249834");
		attachable.setPlaceName("Fake Place");
		attachable.setNote("Attachable note " + RandomStringUtils.randomAlphanumeric(5));
		attachable.setTag("Attachable tag " + RandomStringUtils.randomAlphanumeric(5));
		
		// create attachRef for customer
		AttachableRef attachableRef1 = new AttachableRef();
		Customer customer = CustomerHelper.getCustomer(service);
		ReferenceType customerRef = CustomerHelper.getCustomerRef(customer);
		customerRef.setType("Customer");
		attachableRef1.setEntityRef(customerRef);
		
		// create attachRef for vendor
		AttachableRef attachableRef2 = new AttachableRef();
		Vendor vendor = VendorHelper.getVendor(service);
		ReferenceType vendorRef = VendorHelper.getVendorRef(vendor);
		vendorRef.setType("Vendor");
		attachableRef2.setEntityRef(vendorRef);
		
		List<AttachableRef> list = new ArrayList<AttachableRef>();
		list.add(attachableRef1);
		list.add(attachableRef2);
		
		attachable.setAttachableRef(list);
		attachable.setCategory(AttachableCategoryEnum.OTHER.value());
	
		return attachable;
	}
	
	public static Attachable getAttachableFieldsForUpload(DataService service) throws FMSException, ParseException {
		
		Attachable attachable = new Attachable();
		attachable.setLat("25.293112341223");
		attachable.setLong("-21.3253249834");
		attachable.setPlaceName("Fake Place");
		attachable.setNote("Attachable note " + RandomStringUtils.randomAlphanumeric(5));
		attachable.setTag("Attachable tag " + RandomStringUtils.randomAlphanumeric(5));
		
		attachable.setFileName(RandomStringUtils.randomAlphanumeric(5) + ".pdf");
		attachable.setContentType("application/pdf");
		
		// create attachRef for invoice
		AttachableRef ref = new AttachableRef();
		Invoice invoice = InvoiceHelper.getInvoice(service);
		ReferenceType invoiceRef = InvoiceHelper.getInvoiceRef(invoice);
		invoiceRef.setType("Invoice");
		ref.setEntityRef(invoiceRef);
		
		List<AttachableRef> listAttachRef = new ArrayList<>();
		listAttachRef.add(ref);
		attachable.setAttachableRef(listAttachRef);

	
		return attachable;
	}

}
