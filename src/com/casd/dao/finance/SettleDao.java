package com.casd.dao.finance;

import java.util.List;
import java.util.Map;

import com.casd.entity.finance.Paylist;
import com.casd.entity.finance.Payroll;
import com.casd.entity.finance.Settle;
import com.casd.entity.finance.SettlePay;
import com.casd.entity.finance.SettlePur;


public interface  SettleDao {


	List<Map<String, Object>> supplierList(Map<String, Object> param);


	List<Map<String, Object>> purSelect(Map<String, Object> param);


	Map<String, Object> getProject(Map<String, Object> map);


	void saveSettle(Settle settle);


	void saveSettlePay(SettlePay settlePay);


	void saveSettlePur(SettlePur settlePur);


	List<Map<String, Object>> initSettle(Map<String, Object> map);


	String getPayedTotal(Map<String, Object> param);


	void add_payNumList(SettlePay settlePay);


	void delePur(Map<String, Object> map);


	void delePayList(Map<String, Object> map);


	void update_settlePayNum(Map<String, Object> statusMap);
	
	void update(Map<String, Object> map);
	
	



}
