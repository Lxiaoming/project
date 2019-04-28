package com.casd.service.finance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

import com.casd.controller.web.Ref;
import com.casd.entity.finance.Paylist;
import com.casd.entity.finance.Payroll;
import com.casd.entity.finance.Settle;
import com.casd.entity.finance.SettlePay;


public interface SettleService {

	List<Map<String, Object>> supplierList(int pageIndex, int pageSize,
			Ref<Integer> record, String string, String fields) throws Exception;

	List<Map<String, Object>> purSelect(int pageIndex, int pageSize,
			Ref<Integer> record, String string, String fields) throws Exception;

	String getProject(int projectId);

	int saveSettlePay(Settle settle, String purRows);

	Map<String, Object> initSettle(String id);
	

	List<Map<String, Object>> settlePayNumList(int pageIndex, int pageSize,
			Ref<Integer> record, String string, String fields) throws Exception;

	int add_payNumList(SettlePay settlePay);

	void delePur(Map<String, Object> map);

	void delePayList(Map<String, Object> map);

	void delect_payNum(Map<String, Object> map);

	void audit_payNum(SettlePay settlePay, String auditor, String username);

	void auditBill(Map<String, Object> map, String auditor, int status);

	Map<String, Object> initAuditSettle(String id);

	List<Map<String, Object>> projectSuppliers(int pageIndex, int pageSize,Ref<Integer> record, String  fields, String where) throws Exception;

	List<Map<String, Object>> projectList(int pageIndex, int pageSize,Ref<Integer> record, String  fields, String where) throws Exception;
	
	List<Map<String, Object>> findList(Map<String, Object> map);
	
	
	
	void payNumStatus(DelegateExecution execution, String status);
    
	List<Map<String, Object>> findPumcheck(String key,String node_name) throws Exception;
	
	
	 public  List<Map<String, Object>> Pumtask(String taskId) throws Exception;
	
	 public Map<String, Object> Pum_pass(HttpServletRequest request) throws Exception;
	 
	 
	
	/*List<Map<String, Object>> Pumcialcheck()throws Exception;
	
	
	List<Map<String, Object>> Pummancheck()throws Exception;
*/
}
