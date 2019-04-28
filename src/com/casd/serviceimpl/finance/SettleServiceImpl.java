package com.casd.serviceimpl.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.construct.PurchaseDao;
import com.casd.dao.finance.SettleDao;
import com.casd.dao.flow.FlowDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.construct.PurchaseEntry;
import com.casd.entity.construct.PurchaseEntryClass;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.finance.Settle;
import com.casd.entity.finance.SettlePay;
import com.casd.entity.finance.SettlePur;
import com.casd.entity.uc.User;
import com.casd.service.finance.SettleService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;

@Service
public class SettleServiceImpl implements SettleService {

	@Autowired
	private SettleDao settleDao;
	@Autowired
	private FlowService flowService;
	@Autowired
	private  PurchaseDao  purchaseDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FlowDao flowDao;
	@Autowired
	private ActivitiService activitiService;
	@Autowired    
    private TaskService taskService;
	
	@Override
	public List<Map<String, Object>> supplierList(int page, int pageSize,
		Ref<Integer> record, String where, String fields) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("where", where);
		param.put("fields", fields);

		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		
		List<Map<String, Object>> supplierList = settleDao.supplierList(param);
		
		if (record != null) {
			record.setValue(supplierList.size());
		}
		
		List<Map<String, Object>> listMaps=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < supplierList.size(); i++) {
			
			Map<String, Object> map = supplierList.get(i);
			Boolean isExiseProname=map.containsKey("finance_settlepay_id");
			if(isExiseProname){
				listMaps.add(map);
				continue;
			}
			
			Boolean isExiseProid=map.containsKey("construct_project_id");
			
			String construct_supplier_name=map.get("construct_supplier_name").toString();

			StringBuffer sbf = new StringBuffer();
			String field = " SUM(finance_settlepay_payed) ";
			Map<String, Object> params = new HashMap<String, Object>();
			String payed="";
			int payedInt=0;
			if(isExiseProid){
				String construct_project_id=map.get("construct_project_id").toString();
				sbf.append("finance_settlepay_table  where finance_settlepay_supplier='"+construct_supplier_name+"' and finance_settlepay_projectid='"+construct_project_id+"'");
				params.put("where", sbf);
				params.put("fields", field);
				payed=settleDao.getPayedTotal(params);
				payedInt=Integer.valueOf(payed==null?"0":payed);
			}else{
				sbf.append("finance_settlepay_table  where finance_settlepay_supplier='"+construct_supplier_name+"'");
				params.put("where", sbf);
				params.put("fields", field);
				payed=settleDao.getPayedTotal(params);
				payedInt=Integer.valueOf(payed==null?"0":payed);
			}
			map.put("payedTotal", payedInt);
			listMaps.add(map);	
		}
		
		
		
		
		return listMaps;
	}

	@Override
	public List<Map<String, Object>> purSelect(int page, int pageSize,
			Ref<Integer> record, String where, String fields) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("where", where);
		param.put("fields", fields);

		if (record != null) {
			Integer count = userDao.geUsertCount(param);
			record.setValue(count);
		}
		
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		List<Map<String, Object>> purList = settleDao.purSelect(param);
	
		//sql判断是否存在
		 return purList;
	}

	@Override
	public String getProject(int projectId) {
		Map<String, Object> map=new HashMap<String, Object>();
		String filds="*";
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("  construct_project_table where  construct_project_id= "+projectId);
		map.put("where", sBuffer);
		map.put("filds", filds);
		Map<String, Object> project=new HashMap<String, Object>();
		project=settleDao.getProject(map);
		return project.get("construct_project_name").toString();
	}

	@Override
	@Transactional
	public int saveSettlePay(Settle settle, String purRows) {
		JSONArray  purRowsArray= JSONArray.fromObject(purRows);
		//List<SettlePur> settlePurs=new ArrayList<SettlePur>();
		
		int settleId = settle.getFinance_settlepay_id();
		if(settleId==0){
			settleDao.saveSettle(settle);
			settleId = settle.getFinance_settlepay_id();
		}else {
			settleDao.saveSettle(settle);
		}
		
		for (int i = 0; i < purRowsArray.size(); i++) {
			JSONObject purEntry = purRowsArray.getJSONObject(i);
			SettlePur settlePur = (SettlePur) JSONObject.toBean(purEntry,  
					SettlePur.class);
			settlePur.setFinance_settlepay_pur_parentid(settleId);
			//settlePurs.add(settlePur);
			settleDao.saveSettlePur(settlePur);
		 
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("billID",settlePur.getFinance_settlepay_pur_purchaseid());
			map.put("status",14);
            purchaseDao.updateBillStatus(map);
		  
		}
		
		/*for(int i = 0; i < settlePurs.size(); i++){
			settleDao.saveSettlePur(settlePurs.get(i));
		}*/
		return settleId;
		
	}

	@Override
	public Map<String, Object> initSettle(String id) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> data=new HashMap<String, Object>();
		
		String filds="*";
		StringBuffer purSbuffer=new StringBuffer();
		StringBuffer payPbuffer=new StringBuffer();
		StringBuffer head=new StringBuffer();

		map.put("filds", filds);
		List<Map<String, Object>> settlePays=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> settlePurs=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> heads=new ArrayList<Map<String, Object>>();
		
		map.put("where", payPbuffer);
		payPbuffer.append("  finance_settlepay_payNum  where  finance_settlepay_purNum_parentid= "+id);
		settlePays=settleDao.initSettle(map);
		
		map.put("where", head);
		head.append("  finance_settlepay_table sett left join construct_project_table pro on sett.finance_settlepay_projectid=pro.construct_project_id  where  finance_settlepay_id= "+id);
		heads=settleDao.initSettle(map);
		
		filds="finance_settlepay_pur_purchaseid,finance_settlepay_pur_supplier,construct_project_name,finance_settlepay_pur_shouldpay,finance_settlepay_pur_id,finance_settlepay_pur_parentid,finance_settlepay_pur_arrivedid";
		purSbuffer.append("  finance_settlepay_pur pur left join construct_project_table pro on pur.finance_settlepay_pur_projectid=pro.construct_project_id  where  pur.finance_settlepay_pur_parentid= "+id);
		map.put("where", purSbuffer);
		map.put("filds", filds);
		settlePurs=settleDao.initSettle(map);
		
		data.put("settlePays", settlePays);
		data.put("settlePurs", settlePurs);
		data.put("heads", heads);
		return data;
	}

	/*@Override
	public void auditSettle(Settle settle, String payRows, String purRows,
			String username, String selectRole) {
		int billId= saveSettlePay(settle, purRows);
		// 工作流
		Map<String, Object> linkMap = new HashMap<String, Object>();
		linkMap.put("selectRole", selectRole); // 审批人
		linkMap.put("flow_bill_id", billId); // 单据id
		linkMap.put("flow_submitter", username); // 申请人
		linkMap.put("flow_bill_url", "settlePayAudit.do");// 审核界面url
		linkMap.put("flow_bill_type", "材料付款"); // 审核单据类型
		flowService.saveFlowAudit(linkMap);
		
		
	}
*/
	@Override
	public List<Map<String, Object>> settlePayNumList(int page,
			int pageSize, Ref<Integer> record, String where, String fields) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("where", where);
		param.put("fields", fields);

		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		
		List<Map<String, Object>> supplierList = settleDao.supplierList(param);
		
		if (record != null) {
			record.setValue(supplierList.size());
		}
		return supplierList;
	}

	@Override
	public int add_payNumList(SettlePay settlePay) {
		int finance_settlepay_payNum_id = settlePay.getFinance_settlepay_payNum_id();
		if(finance_settlepay_payNum_id==0){
			settleDao.add_payNumList(settlePay);
			finance_settlepay_payNum_id = settlePay.getFinance_settlepay_payNum_id();
		}else {
			settleDao.add_payNumList(settlePay);
		}
		return finance_settlepay_payNum_id;
	}

	@Override
	@Transactional
	public void delePur(Map<String, Object> map) {
		// TODO Auto-generated method stub
		

		settleDao.delePur(map);
	
		map.put("billID",map.get("billid"));
		map.put("status",13);
        purchaseDao.updateBillStatus(map);
	
	}

	@Override
	@Transactional
	public void delePayList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		settleDao.delePayList(map);
		StringBuffer sbf = new StringBuffer();
		String table="finance_settlepay_pur";
		sbf.append(" where finance_settlepay_pur_parentid in(" + Integer.valueOf(map.get("finance_settlepay_id").toString()) + ")");
		map.put("table", table);
		map.put("where", sbf.toString());
		settleDao.delePayList(map);
	}

	@Override
	public void delect_payNum(Map<String, Object> map){
		settleDao.delePayList(map);
	}

	@Override
	@Transactional
	public void audit_payNum(SettlePay settlePay, String auditor,
			String username) { 
		int billId= add_payNumList(settlePay);
		//工作流
		Map<String, Object> linkMap = new HashMap<String, Object>();
		linkMap.put("auditor", auditor); // 审批人
		linkMap.put("flow_bill_id", billId); // 单据id
		linkMap.put("flow_submitter", username); // 申请人
		linkMap.put("flow_bill_url", "settlePayAudit.do");// 审核界面url
		linkMap.put("flow_bill_type", "采购付款"); // 审核单据类型
		flowService.saveFlowAudit(linkMap);
		
	}

	@Override
	@Transactional
	public void auditBill(Map<String, Object> map, String auditor, int status) {
		flowService.auditBill(map, auditor);
		Map<String, Object> statusMap=new HashMap<String, Object>();
		statusMap.put("status", status);
		statusMap.put("billID", map.get("billID"));
		settleDao.update_settlePayNum(statusMap);
	}

	@Override
	public Map<String, Object> initAuditSettle(String id) {
		String filds="finance_settlepay_purNum_parentid";
		StringBuffer sBuffer=new StringBuffer();
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String, Object>> parentIdList=new ArrayList<Map<String, Object>>();
		sBuffer.append("  finance_settlepay_paynum   where  finance_settlepay_payNum_id= "+id);
		map.put("where", sBuffer);
		map.put("filds", filds);
		parentIdList=settleDao.initSettle(map);
		return initSettle(parentIdList.get(0).get("finance_settlepay_purNum_parentid").toString());
	}
  
	 /***
	  * 显示材料结算项目 
	  * 
	  * 
	  * */
	@Override
	public List<Map<String, Object>> projectSuppliers(int page,
			int pageSize, Ref<Integer> record,String fields, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("where", where);
		param.put("fields", fields);

		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		
		List<Map<String, Object>> supplierList = settleDao.supplierList(param);
		
		if (record != null) {
			record.setValue(supplierList.size());
		}
		return supplierList;
	}

	//
	@Override
	public List<Map<String, Object>> projectList(int page,
			int pageSize, Ref<Integer> record,String fields, String where) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("where", where);
		param.put("fields", fields);

		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		
		List<Map<String, Object>> supplierList = settleDao.supplierList(param);
		
		if (record != null) {
			record.setValue(supplierList.size());
		}
		return supplierList;
		

	}
	

	@Override
	public List<Map<String, Object>> findList(Map<String, Object> map) {
	 
		return  settleDao.initSettle(map);
	}
	 
	
	
	/**
	 * @author Administrator
	 * 修改审核单据状态
	 * 
	 * */
	@Override
	@Transactional
	public void payNumStatus(DelegateExecution execution, String status) {
		
        String bizkey= execution.getProcessBusinessKey();
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}

        Map<String, Object> map=new HashMap<String, Object>();
         map.put("bizId", bizId);
         map.put("filds","finance_settlepay_purNum_status="+status);
         settleDao.update(map);
	}
	
	//查询付款流程各个节点的审核人
	@Override
	public List<Map<String, Object>> findPumcheck(String key,String node_name) throws Exception {
		 Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sbf=new StringBuffer();
			String fields="c.flow_node_auditors";
			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");	
			sbf.append(" where b.flow_description='"+key+"' AND c.flow_node_name='"+node_name+"'");
			map.put("fields", fields);
			map.put("where", sbf);
			String name=flowDao.newlis(map);	
			 map.clear();
			 sbf.delete(0,sbf.length());
			String nameString="*";
			sbf.append("uc_user a WHERE a.userid in("+name+")");
			map.put("fields", nameString);
			map.put("where", sbf.toString());
			return userDao.queryId(map);
		
	}

	//查询付款流程审核人
	@Override
	public List<Map<String, Object>> Pumtask(String taskId) throws Exception {
	
		
		 ActivityImpl activityImpl= activitiService.usertask(taskId);

		  List<Map<String, Object>> listuser=new ArrayList<Map<String,Object>>();
		  String key="Purchase_order";
		  String node_name=activityImpl.getProperty("name").toString();
		Map<String, Object> params=new HashMap<String, Object>();
		 if (node_name.equals("采购部提交")){
		    
			 listuser=this.findPumcheck(key,"财务经理审核");
		  }else if (node_name.equals("财务经理审核")){
			  listuser=this.findPumcheck(key,"建设公司审核");
		  }else if (node_name.equals("建设公司审核")){
			  listuser=this.findPumcheck(key,"董事会");
              
		  }else if(node_name.equals("董事会")){
			   String cwjl=  taskService.getVariable(taskId,"cwjl").toString();
				params.put("username",cwjl);
				params.put("userid",cwjl);
				listuser.add(params);
			  //listuser=this.findPumcheck(key,node_name);
		 }
		 
	  return listuser;
	
	}

	@Override
	@Transactional
	public Map<String, Object> Pum_pass(HttpServletRequest request) throws Exception {
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		Map<String, Object> vars=new HashMap<String, Object>();
		  String taskid=request.getParameter("taskid");
		  String bizkey=request.getParameter("bizId");
		User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
		String username=user.getUserid()+"";
	
		String taskName=request.getParameter("taskName");
		String nextUser=request.getParameter("username");//下一个审核人
		String options=request.getParameter("options");//获取审核意见
		
	
		SendmailUtil sendmailUtil = null;
		
		if (taskName.equals("采购部提交")){
			
		        options="已提交";
				vars.put("cwjl", nextUser);
				activitiService.comment(taskid,username,options);
				taskService.complete(taskid,vars);
			
	   }else if(taskName.equals("财务经理审核")){
		
			vars.put("jsgs", nextUser);
			vars.put("sign", "true");
			activitiService.comment(taskid,username,options);
			taskService.complete(taskid,vars);
		   
	   }else if (taskName.equals("建设公司审核")){
		  			
		    vars.put("dshl", nextUser);
			activitiService.comment(taskid,username,options);   
			taskService.complete(taskid,vars);
		   
	   }else if (taskName.equals("董事会")){
 	
		activitiService.comment(taskid,username,options);
		taskService.complete(taskid,vars);
		
       }else if (taskName.equals("财务中心")){
			activitiService.comment(taskid,username,options);
			taskService.complete(taskid,vars);
	 }
			
	  
		   
		jsonMap.put("leva", "");
		
	 if(!StringUtils.isEmpty(nextUser)){
			String fields=" email ";
		
		String where=" uc_user where userid='"+nextUser+"'";
		Map<String, Object> useMap=new HashMap<String, Object>();
		useMap.put("fields", fields);
		useMap.put("where", where);
		String content="您有一张付款单需要审批!"; //付款提示
		String theme="采购付款!"; //邮件标题
		User uc=userDao.login(useMap);
		
		//判断Emali的邮箱是否为null
		if (uc.getEmail()!=null) {
			sendmailUtil = (SendmailUtil) Class.forName(
					"com.casd.controller.web.utils.SendmailUtil").newInstance();
			sendmailUtil.doSendHtmlEmail(theme, content, uc.getEmail());
		
		}else {
			jsonMap.put("leva", "邮件发送失败!");
		}
		}
		return jsonMap;
	}


	
	
	
	
}