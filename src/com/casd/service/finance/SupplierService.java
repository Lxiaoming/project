package com.casd.service.finance;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.entity.finance.Construct_change_head;
import com.casd.entity.finance.Supplier;

public interface SupplierService {


	void saveSupplier(Supplier supplier);

	void deleSupplier(Map<String, Object> map);
	
	int  add_change_head(Construct_change_head cch);
	
	public void start_change_price(HttpServletRequest request);
	
	
	public void  change_examine(HttpServletRequest request);
	
	public void  new_update_price(String id,String price);
	
	Object add_price(HttpServletRequest request) throws Exception; 
	
	void deleteNewPrice(Map<String, Object> map); 
	
	void back_Newprice(HttpServletRequest request);
	
	
	Map<String, Object> toMaterialPrice();
	
	 public void updateBizStatus(DelegateExecution execution,String status); //修改单据状态为审批中

	  
}
