package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.intuit.ipp.services.QueryResult;
import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import org.apache.commons.lang.StringUtils;

/**
 * @author dderose
 *
 */
public final class ItemHelper {

	private ItemHelper() {

	}

	public static Item getItemFields(DataService service) throws FMSException {

		Item item = new Item();
		item.setName("Item" + RandomStringUtils.randomAlphanumeric(5));
		item.setActive(true);
		item.setTaxable(false);
		item.setUnitPrice(new BigDecimal("200"));
		item.setType(ItemTypeEnum.SERVICE);

		Account incomeAccount = AccountHelper.getIncomeBankAccount(service);
		item.setIncomeAccountRef(AccountHelper.getAccountRef(incomeAccount));
		item.setPurchaseCost(new BigDecimal("300"));

		Account expenseAccount = AccountHelper.getExpenseBankAccount(service);
		item.setExpenseAccountRef(AccountHelper.getAccountRef(expenseAccount));

		item.setTrackQtyOnHand(false);

		return item;
	}

	public static Item getInvItemFields(DataService service) throws FMSException {

		Item invItem = new Item();
		invItem.setName("Item" + RandomStringUtils.randomAlphanumeric(5));
		invItem.setActive(true);
		invItem.setType(ItemTypeEnum.INVENTORY);
		invItem.setQtyOnHand(new BigDecimal(100));
		invItem.setTrackQtyOnHand(true);
		invItem.setInvStartDate(new Date());

		String sql = "select * from account where Name = 'Cost of sales'";
		QueryResult queryResult = service.executeQuery(sql);
		Account account = (Account) queryResult.getEntities().get(0);
		invItem.setExpenseAccountRef(AccountHelper.getAccountRef(account));

		sql = "select * from account where Name = 'Sales of product income'";
		queryResult = service.executeQuery(sql);
		account = (Account) queryResult.getEntities().get(0);
		invItem.setIncomeAccountRef(AccountHelper.getAccountRef(account));

		invItem.setPurchaseCost(new BigDecimal("300"));

		List<Account> accounts = (List<Account>) service.findAll(new Account());
		for(int i=0; i<=accounts.size();i++) {
			if(StringUtils.equals(accounts.get(i).getName(), "Inventory Asset")){
				invItem.setAssetAccountRef(AccountHelper.getAccountRef(accounts.get(i)));
				break;
			}
		}
		return invItem;
	}

	public static Item getItem(DataService service) throws FMSException {
		List<Item> items = (List<Item>) service.findAll(new Item());
		if (!items.isEmpty()) {
			return items.get(0);
		}
		return createItem(service);
	}

	public static Item getInventoryItem(DataService service) throws FMSException {
		return createInventoryItem(service);
	}

	private static Item createItem(DataService service) throws FMSException {
		return service.add(getItemFields(service));
	}

	private static Item createInventoryItem(DataService service) throws FMSException {
		return service.add(getInvItemFields(service));
	}

	public static ReferenceType getItemRef(Item item) {
		ReferenceType itemRef = new ReferenceType();
		itemRef.setName(item.getName());
		itemRef.setValue(item.getId());
		return itemRef;
	}

}
