package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.EntityTypeEnum;
import com.intuit.ipp.data.EntityTypeRef;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.data.JournalEntryLineDetail;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PostingTypeEnum;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class JournalEntryHelper {
	
	private JournalEntryHelper() {
		
	}

	public static JournalEntry getJournalEntryFields(DataService service) throws FMSException, ParseException {
		JournalEntry journalEntry = new JournalEntry();
		try {
			journalEntry.setTxnDate(DateUtils.getCurrentDateTime());
		} catch (ParseException e) {
			throw new FMSException("ParseException while getting current date.");
		}

		Line line1 = new Line();
		line1.setDetailType(LineDetailTypeEnum.JOURNAL_ENTRY_LINE_DETAIL);
		JournalEntryLineDetail journalEntryLineDetail1 = new JournalEntryLineDetail();
		journalEntryLineDetail1.setPostingType(PostingTypeEnum.DEBIT);
		Account debitAccount = AccountHelper.getCashBankAccount(service);
        journalEntryLineDetail1.setAccountRef(AccountHelper.getAccountRef(debitAccount));
		
		line1.setJournalEntryLineDetail(journalEntryLineDetail1);
		line1.setDescription("Description " + RandomStringUtils.randomAlphanumeric(15));
		line1.setAmount(new BigDecimal("100.00"));
		
		Line line2 = new Line();
		line2.setDetailType(LineDetailTypeEnum.JOURNAL_ENTRY_LINE_DETAIL);
		JournalEntryLineDetail journalEntryLineDetail2 = new JournalEntryLineDetail();
		journalEntryLineDetail2.setPostingType(PostingTypeEnum.CREDIT);
		Account creditAccount = AccountHelper.getCreditCardBankAccount(service);
        journalEntryLineDetail2.setAccountRef(AccountHelper.getAccountRef(creditAccount));
        EntityTypeRef eRef = new EntityTypeRef();
        eRef.setType(EntityTypeEnum.VENDOR);
        eRef.setEntityRef(VendorHelper.getVendorRef(VendorHelper.getVendor(service)));
        journalEntryLineDetail2.setEntity(eRef);
		
		line2.setJournalEntryLineDetail(journalEntryLineDetail2);
		line2.setDescription("Description " + RandomStringUtils.randomAlphanumeric(15));
		line2.setAmount(new BigDecimal("100.00"));		
		
		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		lines1.add(line2);
		journalEntry.setLine(lines1);
		journalEntry.setPrivateNote("Journal Entry");
		journalEntry.setDomain("QBO");
		
		return journalEntry;
	}

}
