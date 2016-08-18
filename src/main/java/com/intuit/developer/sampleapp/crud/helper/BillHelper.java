package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountBasedExpenseLineDetail;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.data.BillPaymentCheck;
import com.intuit.ipp.data.BillPaymentTypeEnum;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.LinkedTxn;
import com.intuit.ipp.data.PrintStatusEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TxnTypeEnum;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class BillHelper {

	private BillHelper() {
		
	}

	public static Bill getBillFields(DataService service) throws FMSException, ParseException {

		Bill bill = new Bill();

		Vendor vendor = VendorHelper.getVendor(service);
		bill.setVendorRef(VendorHelper.getVendorRef(vendor));

		Account liabilityAccount = AccountHelper.getLiabilityBankAccount(service);
		bill.setAPAccountRef(AccountHelper.getAccountRef(liabilityAccount));

		Line line1 = new Line();
		line1.setAmount(new BigDecimal("30.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();
		Account account = AccountHelper.getExpenseBankAccount(service);
		ReferenceType expenseAccountRef = AccountHelper.getAccountRef(account);
		detail.setAccountRef(expenseAccountRef);
		line1.setAccountBasedExpenseLineDetail(detail);

		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		bill.setLine(lines1);

		bill.setBillEmail(Email.getEmailAddress());
		bill.setDomain("QBO");

		bill.setGlobalTaxCalculation(GlobalTaxCalculationEnum.NOT_APPLICABLE);

		bill.setRemitToAddr(Address.getPhysicalAddress());

		bill.setReplyEmail(Email.getEmailAddress());

		bill.setShipAddr(Address.getPhysicalAddress());

		bill.setTotalAmt(new BigDecimal("30.00"));
		bill.setTxnDate(DateUtils.getCurrentDateTime());
		bill.setDueDate(DateUtils.getDateWithNextDays(45));

		return bill;
	}

	public static Bill getBill(DataService service) throws FMSException, ParseException {
		List<Bill> bills = (List<Bill>) service.findAll(new Bill());
		if (!bills.isEmpty()) {
			return bills.get(0);
		}
		return createBill(service);
	}

	private static Bill createBill(DataService service) throws FMSException, ParseException {
		return service.add(getBillFields(service));
	}
	
	public static BillPayment getBillPaymentFields(DataService service) throws FMSException, ParseException {
		BillPayment billPayment = new BillPayment();

		billPayment.setTxnDate(DateUtils.getCurrentDateTime());
		
		billPayment.setPrivateNote("Check billPayment");

		Vendor vendor = VendorHelper.getVendor(service);
		billPayment.setVendorRef(VendorHelper.getVendorRef(vendor));
		
		Line line1 = new Line();
		line1.setAmount(new BigDecimal("30"));
		List<LinkedTxn> linkedTxnList1 = new ArrayList<LinkedTxn>();
		LinkedTxn linkedTxn1 = new LinkedTxn();
		Bill bill = getBill(service);
		linkedTxn1.setTxnId(bill.getId());
		linkedTxn1.setTxnType(TxnTypeEnum.BILL.value());
		linkedTxnList1.add(linkedTxn1);
		line1.setLinkedTxn(linkedTxnList1);
		
		List<Line> lineList = new ArrayList<Line>();
		lineList.add(line1);
		billPayment.setLine(lineList);

		BillPaymentCheck billPaymentCheck = new BillPaymentCheck();
		Account bankAccount = AccountHelper.getCheckBankAccount(service);
		billPaymentCheck.setBankAccountRef(AccountHelper.getAccountRef(bankAccount));
		
		billPaymentCheck.setCheckDetail(PaymentHelper.getCheckPayment());		

		billPaymentCheck.setPayeeAddr(Address.getPhysicalAddress());
		billPaymentCheck.setPrintStatus(PrintStatusEnum.NEED_TO_PRINT);
		
		billPayment.setCheckPayment(billPaymentCheck);
		billPayment.setPayType(BillPaymentTypeEnum.CHECK);
		billPayment.setTotalAmt(new BigDecimal("30"));
		return billPayment;
	}

}
