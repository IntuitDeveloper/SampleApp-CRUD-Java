package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

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

	public static Item getItem(DataService service) throws FMSException {
		List<Item> items = (List<Item>) service.findAll(new Item());
		if (!items.isEmpty()) { 
			return items.get(0); 
		}
		return createItem(service);
	}

	private static Item createItem(DataService service) throws FMSException {
		return service.add(getItemFields(service));
	}

	public static ReferenceType getItemRef(Item item) {
		ReferenceType itemRef = new ReferenceType();
		itemRef.setName(item.getName());
		itemRef.setValue(item.getId());
		return itemRef;
	}

}
