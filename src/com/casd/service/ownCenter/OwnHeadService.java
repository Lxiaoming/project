package com.casd.service.ownCenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.OwnPurchaseEntry;
import com.casd.entity.ownCenter.OwnPurchaseHead;

public interface OwnHeadService {
	List<Map<String, Object>> ownHeadlist(int page, int pageSize,
			Ref<Integer> record, String fields, String where) throws Exception;
	
	public void saveOwnHead(OwnPurchaseHead ownHead,HttpServletRequest request);
	
	public Map<String, Object> findOwnHead(String fields,String where);
	
	public List<Map<String, Object>> findOwnEntry(String fields,String where);
	
	
	public Map<String, Object> ownHeadPass(OwnPurchaseHead head,HttpServletRequest request);
	
	public void deleteEntry(String where);
	//流程定义类方法
    public  String  ownHeaNextUser(DelegateExecution execution);

    public void updateHeadStatus(DelegateExecution execution,String status); //修改单据状态为审批中


    
    public void updateOwnPurchaseEntry(OwnPurchaseEntry entry);


}
