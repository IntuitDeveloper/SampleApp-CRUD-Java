package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountBasedExpenseLineDetail;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class VendorCreditHelper {

	private VendorCreditHelper() {
		
	}

	public static VendorCredit getVendorCreditFields(DataService service) throws FMSException, ParseException {
		
		VendorCredit vendorCredit = new VendorCredit();
		Vendor vendor = VendorHelper.getVendor(service);
		vendorCredit.setVendorRef(VendorHelper.getVendorRef(vendor));
		
		Account account = AccountHelper.getLiabilityBankAccount(service);
		vendorCredit.setAPAccountRef(AccountHelper.getAccountRef(account));

		Line line1 = new Line();
		line1.setAmount(new BigDecimal("30.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();
		Account expenseAccount = AccountHelper.getExpenseBankAccount(service);
		detail.setAccountRef(AccountHelper.getAccountRef(expenseAccount));
		line1.setAccountBasedExpenseLineDetail(detail);
		
		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		vendorCredit.setLine(lines1);

		vendorCredit.setDomain("QBO");		
		vendorCredit.setPrivateNote("Credit should be specified");
		vendorCredit.setTxnDate(DateUtils.getCurrentDateTime());
		vendorCredit.setTotalAmt(new BigDecimal("30.00"));
		return vendorCredit;
	}

}
