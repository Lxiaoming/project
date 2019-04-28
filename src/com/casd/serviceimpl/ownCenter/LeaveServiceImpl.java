package com.casd.serviceimpl.ownCenter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.flow.FlowDao;
import com.casd.dao.ownCenter.LeaveDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.activiti.Taskinst;
import com.casd.entity.flow.FlowAudit;
import com.casd.entity.ownCenter.Leave;
import com.casd.entity.uc.User;
import com.casd.service.flow.CommentServiec;
import com.casd.service.hr.ActivitiService;
import com.casd.service.ownCenter.LeaveService;
import com.casd.service.uc.UserService;

@Service
public class LeaveServiceImpl implements LeaveService{

	@Autowired
	private LeaveDao leaveDao;
	@Autowired
	private FlowDao flowDao;
	
	@Autowired
	private ProcessEngine processEngine;//注入bean,获取流程引擎

	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private TaskService  taskService;
		
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommentServiec  commentServiec;
	
	@Autowired
	private UserService  userService;
	@Autowired
	private ActivitiDao  activitiDao; 
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * 保存请假条同时启动流程
	 * */
	@Override
	@Transactional
	public void saveLeave(Leave leave,Map<String, Object> vars) {

		  leave.setStatus(2);
		   leaveDao.saveLeave(leave);
			String processDefinitioKey = "Leave_flow"; // 定义流程的key,不可修改
			String bizId = processDefinitioKey + "."
					+ leave.getId(); // 获取业务id
			ProcessInstance pi= runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId, vars);// 流程图id，业务表id
			DataProcinst dataProcinst=new  DataProcinst();
		
			dataProcinst.setProc_inst_id_(pi.getId());
			dataProcinst.setApplicant(leave.getApplicant());
			dataProcinst.setIllustrate(leave.getReason());
			activitiDao.insertDataProcinst(dataProcinst);
			
	
		      
		
	}

	

	@Override
	@Transactional
	public List<Map<String, Object>> leaveList(int page, int pageSize,
			Ref<Integer> record,String fields, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("fields",fields);
		param.put("where", where);

		if (record != null) {
			Integer count = leaveDao.getLeaveCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return leaveDao.leaveList(param);
		
	}

	@Override
	public Leave findLeaveByID(Map<String, Object> map) {
		
		return leaveDao.findLeaveByID(map);
		
	}

	@Override
	public void updateLeave(Leave leave) {
		leaveDao.updateLeave(leave);
	}
	
	
	
	
	
	@Override
	public void deleLeave(Map<String, Object> map) {
			leaveDao.deleLeave(map);
	}


	@Override
	public String cancelLeave(Map<String, Object> map) {
		int status=leaveDao.selectStatus(map);
		if(status==1){
			leaveDao.deleLeave(map);
			
		}else{
			return "请假条已批准不能取消,请联系人事处理!";
		}
		return null;
	}

	@Override
	public void updateStatus(Map<String, Object> map) {
		Object nextNode = map.get("nextNode");
		if(nextNode!=null){
			map.put("status", 3);
			leaveDao.updateStatus(map);
		}else{
			map.put("status", 0);
			leaveDao.updateStatus(map);
		}
		
	}
	
	
	
	
	 public void leave_reject(Map<String, Object> map) {
			int flow_node_order=Integer.parseInt(map.get("node_order").toString());
			Map<String, Object> idMap=new HashMap<String, Object>();
		    int flowAuditId=Integer.valueOf(map.get("flow_audit_last_auditid").toString());
		    idMap.put("auditID", flowAuditId);
		    FlowAudit flowAuditMap= flowDao.getFlowAudit(idMap);
		    
		    
			Date currentDate = new Date(System.currentTimeMillis());   
			FlowAudit audit=new FlowAudit();
			audit.setFlow_audit_time(null);
			audit.setFlow_bill_id(flowAuditMap.getFlow_bill_id());
			audit.setFlow_submitter(flowAuditMap.getFlow_submitter());
			audit.setFlow_bill_url(flowAuditMap.getFlow_bill_url());
			audit.setFlow_bill_type(flowAuditMap.getFlow_bill_type());
			audit.setFlow_submit_time(flowAuditMap.getFlow_submit_time());
			audit.setFlow_audit_option(null);
			audit.setFlow_audit_next_auditid(0);
			audit.setFlow_auditer(flowAuditMap.getFlow_auditer());
			audit.setFlow_audit_id(0);
			audit.setFlow_audit_last_auditid(flowAuditMap.getFlow_audit_last_auditid());
			audit.setFlow_status(1);
			audit.setFlow_node_order(flowAuditMap.getFlow_node_order());
			  if(flow_node_order-1==1) {
			 
			  }  else {
               flowDao.addFlowAudit(audit);
			}
			Map<String, Object> nextIDMap=new HashMap<String, Object>();
			nextIDMap.put("id", 0);
			nextIDMap.put("flow_audit_time", currentDate);
			nextIDMap.put("flow_status", 3);
			nextIDMap.put("flow_audit_option",map.get("option").toString());
			nextIDMap.put("where", Integer.valueOf(map.get("auditID").toString()));
			flowDao.addNextAuditID(nextIDMap);
			  if(flow_node_order-1==1) {
				   map.put("status",1);
				  leaveDao.updateStatus(map);
				  
				  
				  
			  }
	}
	       
	     // activiti 修改单据状态为
	      @Transactional
		 public void updateBizStatus(DelegateExecution execution,String status) {
				String bizkey= execution.getProcessBusinessKey();
				
				String[] strs=bizkey.split("\\.");
		        String bizId=null;
				for(int i=0,len=strs.length;i<len;i++){
					bizId=strs[i].toString();
				}
		        Map<String, Object> map=new HashMap<String, Object>();
		         map.put("billID", bizId);
		         map.put("status", status);
		      
		         leaveDao.updateStatus(map);
			
		} 
		 
		 //
		 public  List<Map<String, Object>> usertask(String taskId,String position,String day) throws Exception{
			 
			 ActivityImpl activityImpl= activitiService.usertask(taskId);
			 double days=Double.parseDouble(day);
			  List<Map<String, Object>> listuser=new ArrayList<Map<String,Object>>();
			     if (activityImpl.getProperty("name").equals("提交申请")){
			    	 
			    	 if(position.indexOf("总经理")!=-1 || position.indexOf("董事长助理")!=-1){

			    			listuser =this.financialcheck(); //查询管理公司总经理
			   
			    	 }else if(position.indexOf("经理")!=-1) {
			    		 
			    			listuser =this.manageraAudit();//查询总经理
						
					 }else {
							listuser =this.fidbusiness();//查询经理
					}
			    	
			 }else if (activityImpl.getProperty("name").equals("经理审批")){
				 listuser =this.manageraAudit();	 
			 }else if (activityImpl.getProperty("name").equals("总经理审批")){
				 
				/* if (position.indexOf("总经理")!=-1) {
					 listuser =this.chairmancheck();//查询懂事会
				}else {*/
					 listuser =this.purchasingcheck();//查询人事
				/*}*/
				
			 }else if (activityImpl.getProperty("name").equals("人事存档")){
				 
				 if(position.indexOf("总经理")!=-1){
					 
				 }else if(position.indexOf("总经理")==-1 && days>3){
					 listuser =this.financialcheck();
				}
				 
			 }else if (activityImpl.getProperty("name").equals("管理公司总经理")){
                   if(position.indexOf("总经理")!=-1 || position.indexOf("董事长助理")!=-1){
                		 listuser =this.chairmancheck();
				   }
				 
			 }else if(activityImpl.getProperty("name").equals("董事会")){
				 listuser =this.purchasingcheck();//查询人事
			}
			 
		  return listuser;
			
		 }

		 
		//查询经理
		@Override
		public List<Map<String, Object>> fidbusiness() throws Exception {
			 Map<String, Object> map=new HashMap<String, Object>();
 			StringBuffer sbf=new StringBuffer();
 			String fields="c.flow_node_auditors";
 			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
 			sbf.append(" WHERE  b.flow_description='Leave_flow"+"' AND c.flow_node_name='经理审批'");
 			map.put("fields", fields);
 			map.put("where", sbf);
 			String name=flowDao.newlis(map);	
 			 map.clear();
 			 sbf.delete(0,sbf.length());
 			String nameString="*";
 			sbf.append("uc_user a WHERE a.userid in("+name+")");
 			map.put("fields", nameString);
 			map.put("where", sbf.toString());
 		    List<Map<String, Object>>listuser =userDao.queryId(map);
			
			
			
			return listuser;
		}

		 //查询总经理
		@Override
		public List<Map<String, Object>> manageraAudit() throws Exception {
			 Map<String, Object> map=new HashMap<String, Object>();
	 			StringBuffer sbf=new StringBuffer();
	 			String fields="c.flow_node_auditors";
	 			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
	 			sbf.append(" WHERE   b.flow_description='Leave_flow"+"' AND c.flow_node_name='总经理审批'");
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

		 //查询人事存档
		@Override
		public List<Map<String, Object>> purchasingcheck() throws Exception {
			 Map<String, Object> map=new HashMap<String, Object>();
	 			StringBuffer sbf=new StringBuffer();
	 			String fields="c.flow_node_auditors";
	 			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
	 			sbf.append(" WHERE  b.flow_description='Leave_flow"+"' AND c.flow_node_name='人事存档'");
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
         
		 //查询管理公司审核人
		@Override
		public List<Map<String, Object>> financialcheck() throws Exception {
			 Map<String, Object> map=new HashMap<String, Object>();
	 			StringBuffer sbf=new StringBuffer();
	 			String fields="c.flow_node_auditors";
	 			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
	 			
	 			sbf.append(" where b.flow_description='Leave_flow"+"' AND c.flow_node_name='管理公司总经理'");
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
         
		//查询董事会
		@Override
		public List<Map<String, Object>> chairmancheck() throws Exception {
			 Map<String, Object> map=new HashMap<String, Object>();
	 			StringBuffer sbf=new StringBuffer();
	 			String fields="c.flow_node_auditors";
	 			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
	 			sbf.append(" WHERE  b.flow_description='Leave_flow"+"' AND c.flow_node_name='董事会'");
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
        
		
		//开始办理
		@Override
		@Transactional
		public Map<String, Object> leave_pass(HttpServletRequest request) throws Exception {
			Map<String, Object> jsonMap=new HashMap<String, Object>();
			Map<String, Object> vars=new HashMap<String, Object>();
			  String taskid=request.getParameter("taskid");
			  User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
			  String userid=user.getUserid()+"";
			String nextUser=request.getParameter("userid");//下一个审核人id
			String reasons=request.getParameter("reasons");//获取审核意见
			String sign=request.getParameter("sign");//是否批准

		    activitiService.comment(taskid,userid,reasons);	
		    if (StringUtils.isNotEmpty(sign)) {
		    	vars.put("sign", sign);
			}
			 taskService.complete(taskid, vars);	
			
			if (StringUtils.isNotEmpty(nextUser)) {
				String content="您有一张请假单需要审批!"; //请假内容
			        /*   content+="<br>申请人：";
			           content+="<br>请假时间：";
			           content+="<br>结束时间：";*/
				String theme="请假条!"; //邮件标题
				jsonMap= activitiService.sendEmail(theme, content, nextUser);
			    activitiService.sendEmail(theme, content, userid);
			}
	     	
			return jsonMap;
		}

		
		//不批准
		@Override
		@Transactional
		public Map<String, Object> rejects_leave(HttpServletRequest request) {
			User user = (User) request.getSession().getAttribute("loginUser");
			String username = user.getUserid()+"";// 当前办理人
			Map<String, Object> vars = new HashMap<String, Object>();
			String taskid = request.getParameter("taskid");// 任务id
			String options = request.getParameter("options");// 审核意见
			vars.put("sign", "false");
			activitiService.comment(taskid, username, options);// 添加审核记录
			taskService.complete(taskid, vars);// 完成任务
			return null;
		}
	 
	 
}
