package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.CreditMemo;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.SubTotalLineDetail;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class CreditMemoHelper {
	
	private CreditMemoHelper() {
		
	}

	public static CreditMemo getCreditMemoFields(DataService service) throws FMSException, ParseException {
		
		CreditMemo creditMemo = new CreditMemo();
		
		Customer customer = CustomerHelper.getCustomer(service);
		creditMemo.setCustomerRef(CustomerHelper.getCustomerRef(customer));
		
		Line line1 = new Line();
		line1.setLineNum(new BigInteger("1"));
		line1.setAmount(new BigDecimal("300.00"));
		line1.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
		
		SalesItemLineDetail salesItemLineDetail1 = new SalesItemLineDetail();
		Item item = ItemHelper.getItem(service);
		salesItemLineDetail1.setItemRef(ItemHelper.getItemRef(item));		
		line1.setSalesItemLineDetail(salesItemLineDetail1);
		
		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		creditMemo.setLine(lines1);
		
		creditMemo.setDocNumber(RandomStringUtils.randomNumeric(4));
		try {
			creditMemo.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}
		
		return creditMemo;
	}

}
