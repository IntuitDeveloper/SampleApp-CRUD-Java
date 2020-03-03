package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.CreditCardPaymentTxn;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class CreditCardPaymentHelper {
	
	private CreditCardPaymentHelper() {
		
	}

	public static CreditCardPaymentTxn getCreditCardPaymentFields(DataService service) throws FMSException, ParseException {
		CreditCardPaymentTxn creditCardPayment = new CreditCardPaymentTxn();
		
		// Mandatory Fields
				try {
			creditCardPayment.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}
				
		creditCardPayment.setAmount(new BigDecimal("10"));
		
		Account creditCardAccount = AccountHelper.getCreditCardBankAccount(service);
		creditCardPayment.setCreditCardAccountRef(AccountHelper.getAccountRef(creditCardAccount));
		Account bankAccount = AccountHelper.getCashBankAccount(service);
		creditCardPayment.setBankAccountRef(AccountHelper.getAccountRef(bankAccount));

		return creditCardPayment;
	}
	

	  public static CreditCardPaymentTxn getCreditCardPayment(DataService service) throws FMSException, ParseException {
			List<CreditCardPaymentTxn> creditCardPayments = (List<CreditCardPaymentTxn>) service.findAll(new CreditCardPaymentTxn());
			if (!creditCardPayments.isEmpty()) {
				return creditCardPayments.get(0);
			}
			return createCreditCardPayment(service);
	  }
	  
	  
	private static CreditCardPaymentTxn createCreditCardPayment(DataService service) throws FMSException, ParseException {
		return service.add(getCreditCardPaymentFields(service));
	}
	
	  
}
