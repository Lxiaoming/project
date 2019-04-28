package com.casd.dao.construct;

import java.util.List;
import java.util.Map;

import com.casd.entity.construct.Construct;
import com.casd.entity.construct.PurchaseEntry;
import com.casd.entity.construct.PurchaseEntryClass;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.construct.PurchaseHeadClass;

public interface  PurchaseDao {

	int getPurchaseCount(Map<String, Object> param);

	List<Map<String, Object>> purchaseList(Map<String, Object> param);

	List<Map<String, Object>> purchaseMaterialList(Map<String, Object> param);

	int getPurchaseMaterialCount(Map<String, Object> param);

	void saveAssessHead(PurchaseHead purchaseHead);

	void savePurchaseHead(PurchaseHead purchaseHead);

	void savePurchaseEntry(PurchaseEntry purchaseEntry);


	PurchaseHead getHead(Map<String, Object> map);

	List<PurchaseEntry> getEntry(Map<String, Object> map);


	void delePurchase(Map<String, Object> map);

	List<Map<String, Object>> getPHead(Map<String, Object> map);


	List<Map<String, Object>> getPEntry(Map<String, Object> map);

	void savePhc(PurchaseHeadClass purchaseHeadClass);

	void savePec(List<PurchaseEntryClass> entryList);

	void updateBillStatus(Map<String, Object> map);

	void updateBill(Map<String, Object> map);
	
	void updatephoto(Map<String, Object> map);
	
	void delete(Map<String, Object> map);


}
