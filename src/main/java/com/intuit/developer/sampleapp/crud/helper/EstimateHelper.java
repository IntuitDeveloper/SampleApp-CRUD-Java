package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.SubTotalLineDetail;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class EstimateHelper {

	private EstimateHelper() {
		
	}

	public static Estimate getEstimateFields(DataService service) throws FMSException, ParseException {
		
		Estimate estimate = new Estimate();
		estimate.setDocNumber(RandomStringUtils.randomNumeric(4));
		try {
			estimate.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}
		try {
			estimate.setExpirationDate(DateUtils.getDateWithNextDays(15));
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date + 15 days.");
		}
		
		Line line1 = new Line();
		line1.setLineNum(new BigInteger("1"));
		line1.setAmount(new BigDecimal("300.00"));
		line1.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
		
		SalesItemLineDetail salesItemLineDetail1 = new SalesItemLineDetail();
		Item item = ItemHelper.getItem(service);
		salesItemLineDetail1.setItemRef(ItemHelper.getItemRef(item));
		
		ReferenceType taxCodeRef1 = new ReferenceType();
		taxCodeRef1.setValue("NON");
		salesItemLineDetail1.setTaxCodeRef(taxCodeRef1);
		line1.setSalesItemLineDetail(salesItemLineDetail1);
		
		Line line2 = new Line();
		line2.setAmount(new BigDecimal("300.00"));
		line2.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);
		line2.setSubTotalLineDetail(new SubTotalLineDetail());
		
		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		lines1.add(line2);
		estimate.setLine(lines1);
		
		Account depositAccount = AccountHelper.getCashBankAccount(service);
		estimate.setDepositToAccountRef(AccountHelper.getAccountRef(depositAccount));

		Customer customer = CustomerHelper.getCustomer(service);
		estimate.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		estimate.setApplyTaxAfterDiscount(false);
		estimate.setTotalAmt(new BigDecimal("300.00"));
		estimate.setPrivateNote("Accurate Estimate");
		
		return estimate;
	}

}
