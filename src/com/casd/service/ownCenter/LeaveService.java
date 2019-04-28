package com.casd.service.ownCenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.Leave;

public interface LeaveService {

	void saveLeave(Leave leave,Map<String, Object> map);

	List<Map<String, Object>> leaveList(int pageIndex, int pageSize,
			Ref<Integer> record,String fields, String string) throws Exception;

	Leave findLeaveByID(Map<String, Object> map);

	void updateLeave(Leave leave);

	void deleLeave(Map<String, Object> map);


	String cancelLeave(Map<String, Object> map);

	void updateStatus(Map<String, Object> map);
	

	
	public void leave_reject(Map<String, Object> map);
	
	
	  //修改状态为0
	 public void updateBizStatus(DelegateExecution execution,String status);
	 
	 public  List<Map<String, Object>> usertask(String taskId,String bizId,String day) throws Exception;
	 
	 //查询
	 
	    List<Map<String, Object>> fidbusiness() throws Exception;        
	    
	    List<Map<String, Object>> manageraAudit() throws Exception;      
	    
	    List<Map<String, Object>> purchasingcheck() throws Exception;    
	    
	    List<Map<String, Object>> financialcheck() throws Exception;    
	    
	    List<Map<String, Object>> chairmancheck() throws Exception;     
	    
	    Map<String, Object> leave_pass(HttpServletRequest request) throws Exception;     //开始办理
	    
	     Map<String, Object> rejects_leave(HttpServletRequest request);

		
	 
}
