package com.casd.service.uc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.uc.Costapp;

public interface CostappService {

	List<Map<String, Object>> costappList(int pageIndex, int pageSize,
			Ref<Integer> record, String fields, String string) throws Exception;


	void delete_data(Map<String, Object> map);

	List<Map<String, Object>> getData(Map<String, Object> map);


	void save_costapp(Costapp costapp);
	
	void costappDelegate(DelegateExecution execution,String status);
	
	Map<String, Object> findCostapp(String fields, String where);
	
	
	void pass_costapp(HttpServletRequest request,Costapp costapp, Map<String, Object> map);
	
	 void startCostapp(HttpServletRequest request,Costapp costapp);  
	 
	 Map<String, Object> Costapp_pass(HttpServletRequest request) throws Exception;  
	 
	 public void updateCostapp(DelegateExecution execution, String status);
	
	//流程定义类方法
	  public  String costappNextUser(DelegateExecution execution);
	  
   

	 
}
