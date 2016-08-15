package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.DepositLineDetail;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.LinkedTxn;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class DepositHelper {
	
	private DepositHelper() {
		
	}

	public static Deposit getDepositFields(DataService service) throws FMSException, ParseException {
		
		Deposit deposit = new Deposit();
        try {
            deposit.setTxnDate(DateUtils.getCurrentDateTime());
        } catch (ParseException e) {
            throw new FMSException("ParseException while getting current date.");
        }
        
        Account depositAccount = AccountHelper.getAssetAccount(service);
        deposit.setDepositToAccountRef(AccountHelper.getAccountRef(depositAccount));
        
        deposit.setCashBack(PaymentHelper.getCashBackInfo(service));
        
        deposit.setTotalAmt(new BigDecimal("6.00"));
        
        Line line1 = new Line();
        line1.setAmount(new BigDecimal("11.00"));
        line1.setDetailType(LineDetailTypeEnum.DEPOSIT_LINE_DETAIL);
        
        DepositLineDetail depositLineDetail = new DepositLineDetail();
        Account expenseAccount = AccountHelper.getExpenseBankAccount(service);
        depositLineDetail.setAccountRef(AccountHelper.getAccountRef(expenseAccount));
        
        PaymentMethod paymentMethod = PaymentHelper.getPaymentMethod(service);
        depositLineDetail.setPaymentMethodRef(PaymentHelper.getPaymentMethodRef(paymentMethod));
        
        line1.setDepositLineDetail(depositLineDetail);
        
        List<LinkedTxn> linkedTxn = new ArrayList<LinkedTxn>();
        LinkedTxn lTxn = new LinkedTxn();
        lTxn.setTxnId("55045");
        lTxn.setTxnType("Payment");
        linkedTxn.add(lTxn);
        line1.setLinkedTxn(linkedTxn);
        
        List<Line> lineList = new ArrayList<Line>();
        lineList.add(line1);
        deposit.setLine(lineList);
        
        return deposit;
	}

}
