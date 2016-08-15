package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountClassificationEnum;
import com.intuit.ipp.data.AccountSubTypeEnum;
import com.intuit.ipp.data.AccountTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

/**
 * @author dderose
 *
 */
public final class AccountHelper {
	
	private AccountHelper() {
		
	}

	public static Account createBankAccount(DataService service) throws FMSException, ParseException {
		return service.add(getBankAccountFields());
	}
	
	public static Account getBankAccountFields() throws FMSException {
		Account account = new Account();
		account.setName("Ba" + RandomStringUtils.randomAlphanumeric(7));
		account.setSubAccount(false);
		account.setFullyQualifiedName(account.getName());
		account.setActive(true);
		account.setClassification(AccountClassificationEnum.ASSET);
		account.setAccountType(AccountTypeEnum.BANK);
		account.setCurrentBalance(new BigDecimal("0"));
		account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
		account.setTxnLocationType("FranceOverseas");
		account.setAcctNum("B" + RandomStringUtils.randomAlphanumeric(6));

		return account;
	}
	
	  public static ReferenceType getAccountRef(Account account) {
			ReferenceType accountRef = new ReferenceType();
			accountRef.setName(account.getName());
			accountRef.setValue(account.getId());
			return accountRef;
	  }
	  
	  public static Account getAssetAccount(DataService service)  throws FMSException{
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.OTHER_CURRENT_ASSET)) {
						return account;
					}
				}
			}
			return createOtherCurrentAssetAccount(service);
	  }
	  
	private static Account createOtherCurrentAssetAccount(DataService service) throws FMSException {
		return service.add(getOtherCurrentAssetAccountFields());
	}
	
	public static Account getOtherCurrentAssetAccountFields() throws FMSException {
		Account account = new Account();
		account.setName("Other CurrentAsse" + RandomStringUtils.randomAlphanumeric(5));
		account.setSubAccount(false);
		account.setFullyQualifiedName(account.getName());
		account.setActive(true);
		account.setClassification(AccountClassificationEnum.ASSET);
		account.setAccountType(AccountTypeEnum.OTHER_CURRENT_ASSET);
		account.setAccountSubType(AccountSubTypeEnum.OTHER_CURRENT_ASSETS.value());
		account.setCurrentBalance(new BigDecimal("0"));
		account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
		ReferenceType currencyRef = new ReferenceType();
		currencyRef.setName("United States Dollar");
		currencyRef.setValue("USD");
		account.setCurrencyRef(currencyRef);

		return account;
	}

	public static Account getCashBankAccount(DataService service) throws FMSException, ParseException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.BANK)) {
						return account;
					}
				}
			}
			return createBankAccount(service);
	  }
	  
		public static Account getCreditCardBankAccount(DataService service) throws FMSException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.CREDIT_CARD)) {
						return account;
					}
				}
			}
			return createCreditCardBankAccount(service);
		}
		
		private static Account createCreditCardBankAccount(DataService service) throws FMSException {
			return service.add(getCreditCardBankAccountFields());
		}

		public static Account getCreditCardBankAccountFields() throws FMSException {
			Account account = new Account();
			account.setName("CreditCa" + RandomStringUtils.randomAlphabetic(5));
			account.setSubAccount(false);
			account.setFullyQualifiedName(account.getName());
			account.setActive(true);
			account.setClassification(AccountClassificationEnum.LIABILITY);
			account.setAccountType(AccountTypeEnum.CREDIT_CARD);
			account.setAccountSubType(AccountSubTypeEnum.CREDIT_CARD.value());
			account.setCurrentBalance(new BigDecimal("0"));
			account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("United States Dollar");
			currencyRef.setValue("USD");
			account.setCurrencyRef(currencyRef);

			return account;
		}
		
		public static Account getIncomeBankAccount(DataService service) throws FMSException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.INCOME)) {
						return account;
					}
				}
			}
			return createIncomeBankAccount(service);
		}

		private static Account createIncomeBankAccount(DataService service) throws FMSException {
			return service.add(getIncomeBankAccountFields());
		}
		
		public static Account getIncomeBankAccountFields() throws FMSException {
			Account account = new Account();
			account.setName("Incom" + RandomStringUtils.randomAlphabetic(5));
			account.setSubAccount(false);
			account.setFullyQualifiedName(account.getName());
			account.setActive(true);
			account.setClassification(AccountClassificationEnum.REVENUE);
			account.setAccountType(AccountTypeEnum.INCOME);
			account.setAccountSubType(AccountSubTypeEnum.SERVICE_FEE_INCOME.value());
			account.setCurrentBalance(new BigDecimal("0"));
			account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("United States Dollar");
			currencyRef.setValue("USD");
			account.setCurrencyRef(currencyRef);

			return account;
		}

		public static Account getExpenseBankAccount(DataService service) throws FMSException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.EXPENSE)) {
						return account;
					}
				}
			}
			return createExpenseBankAccount(service);
		}
		
		private static Account createExpenseBankAccount(DataService service) throws FMSException {
			return service.add(getExpenseBankAccountFields());
		}
		
		public static Account getExpenseBankAccountFields() throws FMSException {
			Account account = new Account();
			account.setName("Expense" + RandomStringUtils.randomAlphabetic(5));
			account.setSubAccount(false);
			account.setFullyQualifiedName(account.getName());
			account.setActive(true);
			account.setClassification(AccountClassificationEnum.EXPENSE);
			account.setAccountType(AccountTypeEnum.EXPENSE);
			account.setAccountSubType(AccountSubTypeEnum.ADVERTISING_PROMOTIONAL.value());
			account.setCurrentBalance(new BigDecimal("0"));
			account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("United States Dollar");
			currencyRef.setValue("USD");
			account.setCurrencyRef(currencyRef);

			return account;
		}

		public static Account getLiabilityBankAccount(DataService service) throws FMSException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.ACCOUNTS_PAYABLE)
							&& account.getClassification().equals(AccountClassificationEnum.LIABILITY)) {
						return account;
					}
				}
			}
			return createLiabilityBankAccount(service);
		}
		
		private static Account createLiabilityBankAccount(DataService service) throws FMSException {
			return service.add(getLiabilityBankAccountFields());
		}
		
		public static Account getLiabilityBankAccountFields() throws FMSException {
			Account account = new Account();
			account.setName("Equity" + RandomStringUtils.randomAlphabetic(5));
			account.setSubAccount(false);
			account.setFullyQualifiedName(account.getName());
			account.setActive(true);
			account.setClassification(AccountClassificationEnum.LIABILITY);
			account.setAccountType(AccountTypeEnum.ACCOUNTS_PAYABLE);
			account.setAccountSubType(AccountSubTypeEnum.ACCOUNTS_PAYABLE.value());
			account.setCurrentBalance(new BigDecimal("3000"));
			account.setCurrentBalanceWithSubAccounts(new BigDecimal("3000"));
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("United States Dollar");
			currencyRef.setValue("USD");
			account.setCurrencyRef(currencyRef);

			return account;
		}

		public static Account getCheckBankAccount(DataService service) throws FMSException, ParseException {
			List<Account> accounts = (List<Account>) service.findAll(new Account());
			if (!accounts.isEmpty()) {
				Iterator<Account> itr = accounts.iterator();
				while (itr.hasNext()) {
					Account account = itr.next();
					if (account.getAccountType().equals(AccountTypeEnum.BANK)) {
						return account;
					}
				}
			}
			return createBankAccount(service);
		}

}
