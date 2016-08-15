package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class SalesReceiptHelper {

	private SalesReceiptHelper() {
		
	}

	public static SalesReceipt getSalesReceiptFields(DataService service) throws FMSException, ParseException {
		SalesReceipt salesReceipt = new SalesReceipt();
		salesReceipt.setDocNumber(RandomStringUtils.randomNumeric(4));
		try {
			salesReceipt.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Line line1 = new Line();
		line1.setLineNum(new BigInteger("1"));
		line1.setAmount(new BigDecimal("40.00"));
		line1.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);

		SalesItemLineDetail salesItemLineDetail1 = new SalesItemLineDetail();
		Item item = ItemHelper.getItem(service);
		salesItemLineDetail1.setItemRef(ItemHelper.getItemRef(item));

		TaxCode taxcode = TaxCodeInfo.getTaxCode(service);
		salesItemLineDetail1.setTaxCodeRef(TaxCodeInfo.getTaxCodeRef(taxcode));

		salesItemLineDetail1.setUnitPrice(new BigDecimal("40.00"));
		salesItemLineDetail1.setQty(new BigDecimal("1"));
		line1.setSalesItemLineDetail(salesItemLineDetail1);

		Line line2 = new Line();
		line2.setAmount(new BigDecimal("60.00"));
		line2.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);

		SalesItemLineDetail salesItemLineDetail2 = new SalesItemLineDetail();
		salesItemLineDetail2.setItemRef(ItemHelper.getItemRef(item));
		salesItemLineDetail2.setTaxCodeRef(TaxCodeInfo.getTaxCodeRef(taxcode));
		salesItemLineDetail2.setUnitPrice(new BigDecimal("60.00"));
		salesItemLineDetail2.setQty(new BigDecimal("1"));
		line2.setSalesItemLineDetail(salesItemLineDetail2);

		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		lines1.add(line2);
		salesReceipt.setLine(lines1);

		Account depositAccount = AccountHelper.getAssetAccount(service);
		salesReceipt.setDepositToAccountRef(AccountHelper.getAccountRef(depositAccount));

		Customer customer = CustomerHelper.getCustomer(service);
		salesReceipt.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		salesReceipt.setApplyTaxAfterDiscount(false);
		salesReceipt.setTotalAmt(new BigDecimal("100.00"));
		return salesReceipt;
	}

}
