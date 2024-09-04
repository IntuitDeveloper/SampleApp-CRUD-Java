package com.intuit.developer.sampleapp.crud.helper;

import com.intuit.ipp.data.*;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sramadass
 *
 */
public final class InventoryAdjustmentHelper {

	private InventoryAdjustmentHelper() {
		
	}

	public static InventoryAdjustment getInvAdjFields(DataService service) throws FMSException {

		InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();

		Account account = AccountHelper.getAssetAccount(service);
		inventoryAdjustment.setAdjustAccountRef(AccountHelper.getAccountRef(account));

		inventoryAdjustment.setPrivateNote("Memo 1");

		List<Line> invLine = new ArrayList<Line>();
		Line line = new Line();
		line.setDetailType(LineDetailTypeEnum.ITEM_ADJUSTMENT_LINE_DETAIL);

		ItemAdjustmentLineDetail itemAdjustmentLineDetail = new ItemAdjustmentLineDetail();
		itemAdjustmentLineDetail.setQtyDiff(new BigDecimal(3));

		Item invItem = ItemHelper.getInventoryItem(service);
		itemAdjustmentLineDetail.setItemRef(ItemHelper.getItemRef(invItem));
		line.setItemAdjustmentLineDetail(itemAdjustmentLineDetail);
		invLine.add(line);
		inventoryAdjustment.setLine(invLine);

		return inventoryAdjustment;
	}

}
