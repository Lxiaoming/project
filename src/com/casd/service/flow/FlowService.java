package com.casd.service.flow;

import java.util.List;
import java.util.Map;

import com.casd.controller.web.Ref;
import com.casd.entity.flow.Flow;
import com.casd.entity.flow.FlowAudit;
import com.casd.entity.flow.FlowNode;

public interface FlowService {

	List<Map<String, Object>> initFlow(String user, String id);

	List<Map<String, Object>> auditList(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception;

	void saveFlowAudit(Map<String, Object> map);

	List<Map<String, Object>> flowList(int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	List<Map<String, Object>> findFlowNode(Map<String, Object> map);

	Flow findFlowByID(Map<String, Object> map);

	List<FlowAudit> findAuditByBillId(Map<String, Object> map);

	void auditBill(Map<String, Object> map, String auditor);

	void backBill(Map<String, Object> map);

	void submitFlow(FlowNode flowNode);

	void submitFlowHead(Flow flow);

	void delectNode(Map<String, Object> map);

	void deleFlow(Map<String, Object> map);

	List<Map<String, Object>> initFlows( int i);

	List<Map<String, Object>> getAuditors(Map<String, Object> paramMap);

	List<Map<String, Object>> getAuditOption(Map<String, Object> param);
	
	
	
	/*public List<> name;*/
	
	
	


}
