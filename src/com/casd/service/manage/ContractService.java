package com.casd.service.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.casd.controller.web.Ref;
import com.casd.entity.manage.Contract;
import com.casd.entity.manage.Reqfunds;
import com.casd.entity.uc.User;

public interface ContractService {

	List<Map<String, Object>> contractList(int pageIndex, int pageSize,
			Ref<Integer> record, String fields, String string) throws Exception;

	void saveContract(Contract contract,JSONArray reqfundsJson);
	
     void saveReqfunds(Reqfunds reqfunds);
	 List<Map<String, Object>> contractData(String fields,String where);

	void delete_Contract(String manage_contract_id);

	void delete_Reqfunds(String manage_reqfunds_id);

	String getSum(String manage_contract_company);
	
	Map<String,  Object> findContract(String fields,String where);
	
	void updateReqfunds(Reqfunds reqfunds,User user);
	
	public void updateRfdStatus(DelegateExecution execution, String status);
	
	public void reqfundspass(HttpServletRequest request,Reqfunds reqfunds);
	
	public  String reqfundsNextUser(DelegateExecution execution);
	 
}
