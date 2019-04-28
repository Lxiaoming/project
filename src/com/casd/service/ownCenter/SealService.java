package com.casd.service.ownCenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.Seal;


public interface SealService {

	List<Map<String, Object>> sealList(int page, int pageSize,
			Ref<Integer> record, String fields, String string) throws Exception;

	void save_seal(Seal seal);

	List<Map<String, Object>> getData(String fields,String where);

	void delete_data(Map<String, Object> map);
	
	public void updateSealProcess(DelegateExecution execution, String status);//修改盖章流程状态
	
	public void updateSeal(Seal seal);//修改盖章流程状态

	public void start_seal(HttpServletRequest request,Seal seal) throws Exception;
	
	public Seal findSealById(String fields,String where);
	
	
	public void pass_seal(HttpServletRequest request,Seal seal) throws Exception;
	
	//流程定义类方法
	  public  String sealnextUser(DelegateExecution execution);

		
	 
}