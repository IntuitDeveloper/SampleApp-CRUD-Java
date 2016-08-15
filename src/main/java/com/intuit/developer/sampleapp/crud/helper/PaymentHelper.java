package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountBasedExpenseLineDetail;
import com.intuit.ipp.data.BillableStatusEnum;
import com.intuit.ipp.data.CashBackInfo;
import com.intuit.ipp.data.CheckPayment;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.LinkedTxn;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.data.PaymentTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TxnTypeEnum;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class PaymentHelper {
	
	private PaymentHelper() {
		
	}

	public static Payment getCheckPaymentFields(DataService service) throws FMSException, ParseException {
		Payment payment = new Payment();
		try {
			payment.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Account depositAccount = AccountHelper.getAssetAccount(service);
		payment.setDepositToAccountRef(AccountHelper.getAccountRef(depositAccount));

		Customer customer = CustomerHelper.getCustomer(service);
		payment.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		PaymentMethod paymentMethod = PaymentHelper.getPaymentMethod(service);
		payment.setPaymentMethodRef(PaymentHelper.getPaymentMethodRef(paymentMethod));
		
		payment.setPaymentType(PaymentTypeEnum.CHECK);
		
		payment.setCheckPayment(PaymentHelper.getCheckPayment());
		
		payment.setTotalAmt(new BigDecimal("11.00"));
		payment.setUnappliedAmt(new BigDecimal("11.00"));
		return payment;
	}

	public static Payment getPaymentFields(DataService service) throws FMSException, ParseException {
		
		Payment payment = new Payment();
		try {
			payment.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}
		
		List<LinkedTxn> linkedTxnList = new ArrayList<LinkedTxn>();
		LinkedTxn linkedTxn = new LinkedTxn();
		Invoice invoice = InvoiceHelper.getInvoice(service);
		linkedTxn.setTxnId(invoice.getId());
		linkedTxn.setTxnType(TxnTypeEnum.INVOICE.value());
		
		linkedTxnList.add(linkedTxn);
        
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
		line1.setLinkedTxn(linkedTxnList);
		
		Line line2 = new Line();
		line2.setAmount(new BigDecimal("22.00"));
		line2.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail accountBasedExpenseLineDetail2 = new AccountBasedExpenseLineDetail();
		Account expenseAccount2 = AccountHelper.getExpenseBankAccount(service);
		accountBasedExpenseLineDetail2.setAccountRef(AccountHelper.getAccountRef(expenseAccount2));
		accountBasedExpenseLineDetail2.setBillableStatus(BillableStatusEnum.NOT_BILLABLE);

		line2.setAccountBasedExpenseLineDetail(accountBasedExpenseLineDetail2);
		line2.setLinkedTxn(linkedTxnList);
		
		List<Line> lineList = new ArrayList<Line>();
		lineList.add(line1);
		lineList.add(line2);
		payment.setLine(lineList);

		Account depositAccount = AccountHelper.getAssetAccount(service);
		payment.setDepositToAccountRef(AccountHelper.getAccountRef(depositAccount));

		Customer customer = CustomerHelper.getCustomer(service);
		payment.setCustomerRef(CustomerHelper.getCustomerRef(customer));

		PaymentMethod paymentMethod = PaymentHelper.getPaymentMethod(service);
		payment.setPaymentMethodRef(PaymentHelper.getPaymentMethodRef(paymentMethod));
		
		payment.setPaymentType(PaymentTypeEnum.CREDIT_CARD);
		payment.setTotalAmt(new BigDecimal("33.00"));
		payment.setUnappliedAmt(new BigDecimal("33.00"));
		return payment;
	}
	
	public static PaymentMethod getPaymentMethodFields() throws FMSException {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setName("PaymentMethod" + RandomStringUtils.randomAlphanumeric(5));
		paymentMethod.setType(PaymentTypeEnum.CREDIT_CARD.name());
		return paymentMethod;
	}
	
	public static PaymentMethod getPaymentMethod(DataService service) throws FMSException {
		List<PaymentMethod> paymentMethods = (List<PaymentMethod>) service.findAll(new PaymentMethod());
		 if (!paymentMethods.isEmpty()) { 
			 return paymentMethods.get(0); 
		 }
		return createPaymentMethod(service);
	}

	private static PaymentMethod createPaymentMethod(DataService service) throws FMSException {
		return service.add(getPaymentMethodFields());
	}

	public static ReferenceType getPaymentMethodRef(PaymentMethod paymentMethod) {
		ReferenceType paymentMethodRef = new ReferenceType();
		paymentMethodRef.setValue(paymentMethod.getId());
		return paymentMethodRef;
	}

	public static CheckPayment getCheckPayment() throws FMSException {
		String uuid = RandomStringUtils.randomAlphanumeric(8);

		CheckPayment checkPayment = new CheckPayment();
		checkPayment.setAcctNum("AccNum" + uuid);
		checkPayment.setBankName("BankName" + uuid);
		checkPayment.setCheckNum("CheckNum" + uuid);
		checkPayment.setNameOnAcct("Name" + uuid);
		checkPayment.setStatus("Status" + uuid);
		return checkPayment;
	}

	public static CashBackInfo getCashBackInfo(DataService service) throws FMSException, ParseException {
		CashBackInfo cashBackInfo = new CashBackInfo();
		Account cashbackAccount = AccountHelper.getCashBankAccount(service);
		cashBackInfo.setAccountRef(AccountHelper.getAccountRef(cashbackAccount));
		cashBackInfo.setAmount(new BigDecimal("5.00"));
		cashBackInfo.setMemo("testLinkedTxn");
		return cashBackInfo;
	}

}
