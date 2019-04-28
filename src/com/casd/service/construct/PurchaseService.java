package com.casd.service.construct;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.web.multipart.MultipartFile;

import com.casd.controller.web.Ref;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.construct.PurchaseHeadClass;

public interface PurchaseService {

	List<Map<String, Object>> purchaseList(String filds, int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	List<Map<String, Object>> purchaseMaterialList(int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	int submitPurchase(PurchaseHead purchaseHead, String rows);


	void auditPurchase(PurchaseHead purchaseHead, String rows, String username,
			String selectRole);

	Map<String, Object> initPurchase(String string, String string2, int construct_purchase_status);

	Map<String, Object> delePurchase(String headId, int construct_purchase_status);

	Map<String, Object> getHeadData(int flow_bill_id);
	
	
	/*String auditBill(Map<String, Object> map, String auditor,
			String rows, PurchaseHeadClass purchaseHeadClass);*/

	List<Map<String, Object>> getAuditOption(Map<String, Object> param);

	void updateBillStatus(Map<String, Object> map);

	String chooseSupplier(String rows, int supplier, String supplierName, int purchase_id);

	String auditBill(Map<String, Object> map, String auditor);

	void updateBill(String bizkey,String status);
	


	
	
	public Map<String, Object> uploadPhoto(MultipartFile photo,HttpServletRequest request);
	
	
    public void updateBizStatus(DelegateExecution execution,String status); //修改单据状态为审批中
    
    List<Map<String, Object>> fidbusiness() throws Exception;        //查询经营部审核人
    
    List<Map<String, Object>> manageraAudit() throws Exception;      //查询总经理审核人
    
    List<Map<String, Object>> purchasingcheck() throws Exception;    //查询采购核对单价
    
    List<Map<String, Object>> financialcheck() throws Exception;     //查询财务经理审批
    
    List<Map<String, Object>> chairmancheck() throws Exception;     //查询董事会审批
    
    List<Map<String, Object>> supplierUser() throws Exception;     //查询供应商审批

     public Map<String, Object> start_examination(HttpServletRequest request) throws Exception;
     
     public void  deleteEntry(String where) throws Exception;

	Map<String, Object> headData_Audit(Integer valueOf);


	 public  void modBill(DelegateExecution execution, String status);
    
   /* public List<String> purchasingcheck
    
    public List<String> purchasingcheck*/
    
    

	
	

}
