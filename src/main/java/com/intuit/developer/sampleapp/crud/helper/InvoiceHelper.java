package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PrintStatusEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.data.TxnTaxDetail;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class InvoiceHelper {
	
	private InvoiceHelper() {
		
	}

	public static Invoice getInvoiceFields(DataService service) throws FMSException, ParseException {
		Invoice invoice = new Invoice();
		
		// Mandatory Fields
		invoice.setDocNumber(RandomStringUtils.randomAlphanumeric(5));

		try {
			invoice.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Customer customer = CustomerHelper.getCustomer(service);
		invoice.setCustomerRef(CustomerHelper.getCustomerRef(customer));
		
		invoice.setPrivateNote("Testing");
		invoice.setTxnStatus("Payable");
		invoice.setBalance(new BigDecimal("10000"));

		invoice.setBillAddr(Address.getPhysicalAddress());

		List<Line> invLine = new ArrayList<Line>();
		Line line = new Line();
		line.setDescription("New test (14.48mm)\nGiftBox: Red Heart Gift Box [ £ 3.00]");
		line.setAmount(new BigDecimal("10000"));
		line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
		
		
		SalesItemLineDetail silDetails = new SalesItemLineDetail();
		
		Item item = ItemHelper.getItem(service);
		silDetails.setItemRef(ItemHelper.getItemRef(item));

		line.setSalesItemLineDetail(silDetails);
		invLine.add(line);
		invoice.setLine(invLine);

		invoice.setRemitToRef(CustomerHelper.getCustomerRef(customer));

		invoice.setPrintStatus(PrintStatusEnum.NEED_TO_PRINT);
		invoice.setTotalAmt(new BigDecimal("10000"));
		invoice.setFinanceCharge(false);
		
		return invoice;
	}
	
	public static Invoice getASTInvoiceFields(DataService service) throws FMSException, ParseException {
		Invoice invoice = new Invoice();
		
		//add customer
		Customer customer = CustomerHelper.getCustomer(service);
		invoice.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		// add line
		List<Line> invLine = new ArrayList<Line>();
		Line line = new Line();
		line.setAmount(new BigDecimal("100"));
		line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
			
		SalesItemLineDetail silDetails = new SalesItemLineDetail();
		
		Item item = ItemHelper.getItem(service);
		silDetails.setItemRef(ItemHelper.getItemRef(item));
		
		//set line item as taxable
		silDetails.setTaxCodeRef(TaxCodeInfo.getTaxCodeRef("TAX"));

		line.setSalesItemLineDetail(silDetails);
		invLine.add(line);
		invoice.setLine(invLine);
		
		TxnTaxDetail txnTaxDetail = new TxnTaxDetail();
		//pass dummy tax code
		TaxCode taxcode = TaxCodeInfo.getTaxCode(service);
		txnTaxDetail.setTxnTaxCodeRef(TaxCodeInfo.getTaxCodeRef(taxcode));
		invoice.setTxnTaxDetail(txnTaxDetail);
		
		//set shipping address
		invoice.setShipAddr(Address.getAddressForAST());

		return invoice;
	}
	
	public static Invoice getASTOverrideFields(DataService service) throws FMSException, ParseException {
		Invoice invoice = new Invoice();
		
		//add customer
		Customer customer = CustomerHelper.getCustomer(service);
		invoice.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		// add line
		List<Line> invLine = new ArrayList<Line>();
		Line line = new Line();
		line.setAmount(new BigDecimal("100"));
		line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
			
		SalesItemLineDetail silDetails = new SalesItemLineDetail();
		
		Item item = ItemHelper.getItem(service);
		silDetails.setItemRef(ItemHelper.getItemRef(item));
		
		//set line item as taxable
		silDetails.setTaxCodeRef(TaxCodeInfo.getTaxCodeRef("TAX"));

		line.setSalesItemLineDetail(silDetails);
		invLine.add(line);
		invoice.setLine(invLine);
		
		TxnTaxDetail txnTaxDetail = new TxnTaxDetail();
		//override tax value
		txnTaxDetail.setTotalTax(new BigDecimal("12"));
		invoice.setTxnTaxDetail(txnTaxDetail);
		
		//set shipping address
		invoice.setShipAddr(Address.getAddressForAST());

		return invoice;
	}

	  public static Invoice getInvoice(DataService service) throws FMSException, ParseException {
			List<Invoice> invoices = (List<Invoice>) service.findAll(new Invoice());
			if (!invoices.isEmpty()) {
				return invoices.get(0);
			}
			return createItem(service);
	  }
	  
	  
	private static Invoice createItem(DataService service) throws FMSException, ParseException {
		return service.add(getInvoiceFields(service));
	}

	public static ReferenceType getInvoiceRef(Invoice invoice) {
			ReferenceType invoiceRef = new ReferenceType();
			invoiceRef.setValue(invoice.getId());
			return invoiceRef;
	}
	
	
	  
}
