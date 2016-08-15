package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountBasedExpenseLineDetail;
import com.intuit.ipp.data.BillableStatusEnum;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PaymentTypeEnum;
import com.intuit.ipp.data.PrintStatusEnum;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class PurchaseHelper {
	
	private PurchaseHelper() {
		
	}

	public static Purchase getCashPurchaseFields(DataService service) throws FMSException, ParseException {
		Purchase purchase = new Purchase();
		try {
			purchase.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Line line1 = new Line();
		line1.setAmount(new BigDecimal("11.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail accountBasedExpenseLineDetail = new AccountBasedExpenseLineDetail();

		Account expenseAccount = AccountHelper.getExpenseBankAccount(service);
		accountBasedExpenseLineDetail.setAccountRef(AccountHelper.getAccountRef(expenseAccount));

		ReferenceType taxCodeRef = new ReferenceType();
		taxCodeRef.setValue("NON");
		accountBasedExpenseLineDetail.setTaxCodeRef(taxCodeRef);

		accountBasedExpenseLineDetail.setBillableStatus(BillableStatusEnum.NOT_BILLABLE);

		line1.setAccountBasedExpenseLineDetail(accountBasedExpenseLineDetail);

		List<Line> lineList = new ArrayList<Line>();
		lineList.add(line1);
		purchase.setLine(lineList);

		Account account = AccountHelper.getCashBankAccount(service);
		purchase.setAccountRef(AccountHelper.getAccountRef(account));

		Vendor vendor = VendorHelper.getVendor(service);
		purchase.setEntityRef(VendorHelper.getVendorRef(vendor));

		purchase.setPaymentType(PaymentTypeEnum.CASH);
		purchase.setTotalAmt(new BigDecimal("11.00"));
		purchase.setGlobalTaxCalculation(GlobalTaxCalculationEnum.TAX_EXCLUDED);
		return purchase;
	}

	public static Purchase getPurchaseFields(DataService service) throws FMSException, ParseException {
		
		Purchase purchase = new Purchase();
		try {
			purchase.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Line line1 = new Line();
		line1.setAmount(new BigDecimal("11.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		
		AccountBasedExpenseLineDetail accountBasedExpenseLineDetail1 = new AccountBasedExpenseLineDetail();
		Account expenseAccount1 = AccountHelper.getExpenseBankAccount(service);
		accountBasedExpenseLineDetail1.setAccountRef(AccountHelper.getAccountRef(expenseAccount1));
		ReferenceType taxCodeRef = new ReferenceType();
		taxCodeRef.setValue("NON");
		accountBasedExpenseLineDetail1.setTaxCodeRef(taxCodeRef);
		accountBasedExpenseLineDetail1.setBillableStatus(BillableStatusEnum.NOT_BILLABLE);

		line1.setAccountBasedExpenseLineDetail(accountBasedExpenseLineDetail1);

		Line line2 = new Line();
		line2.setAmount(new BigDecimal("22.00"));
		line2.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail accountBasedExpenseLineDetail2 = new AccountBasedExpenseLineDetail();
		Account expenseAccount2 = AccountHelper.getExpenseBankAccount(service);
		accountBasedExpenseLineDetail2.setAccountRef(AccountHelper.getAccountRef(expenseAccount2));
		accountBasedExpenseLineDetail2.setBillableStatus(BillableStatusEnum.NOT_BILLABLE);

		line2.setAccountBasedExpenseLineDetail(accountBasedExpenseLineDetail2);
		
		List<Line> lineList = new ArrayList<Line>();
		lineList.add(line1);
		lineList.add(line2);
		purchase.setLine(lineList);

		Account account = AccountHelper.getCashBankAccount(service);
		purchase.setAccountRef(AccountHelper.getAccountRef(account));

		Vendor vendor = VendorHelper.getVendor(service);
		purchase.setEntityRef(VendorHelper.getVendorRef(vendor));
		
		purchase.setPaymentType(PaymentTypeEnum.CASH);
		purchase.setTotalAmt(new BigDecimal("33.00"));
		purchase.setGlobalTaxCalculation(GlobalTaxCalculationEnum.TAX_EXCLUDED);
		purchase.setMemo("Test Memo " + RandomStringUtils.randomAlphanumeric(5));
		purchase.setPrintStatus(PrintStatusEnum.NOT_SET);
		purchase.setDomain("Domain " + RandomStringUtils.randomAlphanumeric(5));
		purchase.setDocNumber("Doc Num " + RandomStringUtils.randomAlphanumeric(5));
		
		return purchase;
	}

	public static PurchaseOrder getPurchaseOrderFields(DataService service) throws FMSException, ParseException {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		Vendor vendor = VendorHelper.getVendor(service);
		purchaseOrder.setVendorRef(VendorHelper.getVendorRef(vendor));

		Account account = AccountHelper.getLiabilityBankAccount(service);
		purchaseOrder.setAPAccountRef(AccountHelper.getAccountRef(account));
		
		purchaseOrder.setMemo("For Internal usage");
		
		Line line1 = new Line();
		line1.setAmount(new BigDecimal("3.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		
		AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();
		Account account1 = AccountHelper.getExpenseBankAccount(service);
		detail.setAccountRef(AccountHelper.getAccountRef(account1));
		line1.setAccountBasedExpenseLineDetail(detail);

		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		purchaseOrder.setLine(lines1);


		purchaseOrder.setPOEmail(Email.getEmailAddress());
		
		purchaseOrder.setDomain("QBO");
		
		purchaseOrder.setGlobalTaxCalculation(GlobalTaxCalculationEnum.NOT_APPLICABLE);

		purchaseOrder.setReplyEmail(Email.getEmailAddress());

		purchaseOrder.setShipAddr(Address.getPhysicalAddress());

		purchaseOrder.setTotalAmt(new BigDecimal("3.00"));
		purchaseOrder.setTxnDate(DateUtils.getCurrentDateTime());

		return purchaseOrder;
	}

}
