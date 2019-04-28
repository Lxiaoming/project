package com.casd.serviceimpl.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

















import com.casd.controller.web.common.JumpActivityCmd;
import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.uc.UserDao;
import com.casd.entity.uc.User;
import com.casd.service.construct.PurchaseService;
import com.casd.service.hr.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngineConfigurationImpl processEngineConfiguration;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ManagementService managementService;
	
	@Autowired
	private FormService formService;

	@Autowired
	private UserDao userDao;

	@Override
	public void deploymentProcessDefinition_zip() throws FileNotFoundException {

		File file = new File(
				"E:/chenganshidai/mydemo/WebRoot/res/activiti/leave_flow.zip");
		InputStream in = new FileInputStream(file);

		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("请假流程")// 添加部署的名称
				.addZipInputStream(zipInputStream)// 指定zip格式的文件完成部署
				.deploy();// 完成部署
	}

	/**
	 * 启动流程
	 * 
	 * @param bizId
	 *            业务为id
	 * @param name
	 *            申请人名字
	 * @param title
	 *            标题
	 * @param processDefinitioKey
	 *            流程定义的 key 例如 ：Leave_flow
	 */
	@Transactional
	public ProcessInstance startProcesses(String bizId, String name,
			String processDefinitioKey, String title) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("username", name);// 设置第一个人审批人为申请人
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(
				processDefinitioKey, bizId, vars);// 流程图id，业务表id
          return pi;
	}

	/***
	 * 获取业务id
	 * 
	 * @param 任务
	 *            id taskId
	 * @return 业务id
	 */
	public String getBusinessBizId(String taskId) {
		// 1 获取任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		// 2 通过任务对象获取流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		// 3 通过流程实例获取“业务键”
		String businessKey = pi.getBusinessKey();

		String bizId = null;
		if (StringUtils.isNotBlank(businessKey)) {
			bizId = businessKey;
		}
		return bizId;
	}

	/**
	 * 查询任务完成后的连线
	 * 
	 * @author liao
	 * @param 任务id
	 *            taskId
	 * 
	 * 
	 */
	public List<String> findOutComeListByTaskId(String taskId) {

		List<IdentityLink> lists = taskService.getIdentityLinksForTask(taskId);// 当前办理人
		// 返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		// 1.使用任务Id,查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId) // 使用任务Id查询
				.singleResult();
		// 2.获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3.查询ProcessDefinitionEntity对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);

		// 使用任务对象task获取流程实例Id
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例Id，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程实例查询
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 获取当前活动的Id
		String activityId = pi.getActivityId();
		// 4.获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity
				.findActivity(activityId);
		// 5.获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				String name = (String) pvm.getProperty("name");// 获取连线的名称
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				} else {
					list.add("提交");
				}
			}

		}

		return list;
	}

	/**
	 * 添加批注
	 * 
	 * @author liao
	 * @param 批注信息
	 *            comment
	 * @param 审批人
	 *            userid 名字
	 * @param 任务id
	 *            taskId 添加字段固定
	 */

	@Transactional
	public Comment comment(String taskId, String userid, String comment) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 利用任务对象，获取流程实例id
		String processInstancesId = task.getProcessInstanceId();

		Authentication.setAuthenticatedUserId(userid);// 批注人的名称
														// 一定要写，不然查看的时候不知道人物信息
		// 添加批注信息
		Comment comment2 = taskService.addComment(taskId, processInstancesId,
				comment);// comment为批注内容

	
		// 完成任务
		// taskService.complete(taskId,vars);//vars是一些变量
		return comment2;
	}

	@Transactional
	public void comment(Map<String, Object> map) {

		/*
		 * Task
		 * task=taskService.createTaskQuery().taskId(taskId).singleResult();
		 * //利用任务对象，获取流程实例id String
		 * processInstancesId=task.getProcessInstanceId();
		 * 
		 * Authentication.setAuthenticatedUserId(userid);//批注人的名称
		 * 一定要写，不然查看的时候不知道人物信息 // 添加批注信息 Comment comment2=
		 * taskService.addComment(taskId, processInstancesId,
		 * comment);//comment为批注内容
		 */

		// 完成任务
		// taskService.complete(taskId,vars);//vars是一些变量
	}

	/***
	 * 使用任务id查询审批记录
	 * 
	 * @author 廖
	 * @param 任务id
	 *            taskId
	 * @return
	 * @throws Exception
	 */

	public List<Map<String, Object>> getProcessComments(String taskId)
			throws Exception {
		List<Map<String, Object>> hislist = new ArrayList<Map<String, Object>>();
		// 1) 获取流程实例的ID
		Task task = this.taskService.createTaskQuery().taskId(taskId)
				.singleResult();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String processInstanceId = pi.getId();
		if (processInstanceId != null) {

			StringBuffer sbf = new StringBuffer();
			sbf.append(" act_hi_taskinst task");
			sbf.append(" LEFT JOIN ACT_HI_COMMENT comm on comm.TASK_ID_=task.ID_");
			sbf.append(" left JOIN  uc_user us on task.ASSIGNEE_ =us.userid");
			sbf.append(" left JOIN  uc_center center on us.center_id=center.center_id");
			sbf.append(" left JOIN  uc_department dep ON us.department= dep.department_id");
			sbf.append(" WHERE task.PROC_INST_ID_=" + processInstanceId);
			Map<String, Object> param = new HashMap<String, Object>();
			String fields = "task.name_,us.username,comm.MESSAGE_,center.center_name,dep.department_name,task.START_TIME_,task.END_TIME_,task.description_,comm.TIME_";
			param.put("fields", fields);
			param.put("where", sbf.toString());

			// 查询部门和中心
			hislist = userDao.queryId(param);
		}

		//

		// 5）返回
		return hislist;
	}

	/***
	 * 使用流程实例id查询审批记录
	 * 
	 * @author 廖
	 * @param 流程实例id
	 *            processInstanceId processInstanceId
	 * @return
	 */

	public List<Comment> findCommentBytaskId(String processInstanceId) {
		List<Comment> historyCommnets = new ArrayList<Comment>();

		historyCommnets = taskService
				.getProcessInstanceComments(processInstanceId);

		return historyCommnets;
	}

	/***
	 * 使用 业务单据id查询审批记录
	 * 
	 * @author 廖
	 * @param 业务单据id
	 *            bizId
	 * @param 流程实例key
	 *            beyId
	 * @return
	 * @throws Exception
	 */

	@Override
	public List<Map<String, Object>> viewHisComments(String bizId, String beyId)
			throws Exception {
		/* List<Comment> historyCommnets = new ArrayList<Comment>(); */

		List<Map<String, Object>> hislist = null;
		/** 2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID */

		String objname = beyId + "." + bizId;
		HistoricProcessInstance hpi = historyService
				.createHistoricProcessInstanceQuery()// 对应历史的流程变量表
				.processInstanceBusinessKey(objname).singleResult();

		if (hpi != null) {
			// 流程实例ID
			String processInstanceId = hpi.getId();

			StringBuffer sbf = new StringBuffer();
			sbf.append(" act_hi_taskinst task");
			sbf.append(" LEFT JOIN ACT_HI_COMMENT comm on comm.TASK_ID_=task.ID_");
			sbf.append(" left JOIN  uc_user us on task.ASSIGNEE_ =us.userid");
			sbf.append(" left JOIN  uc_center center on us.center_id=center.center_id");
			sbf.append(" left JOIN  uc_department dep ON us.department= dep.department_id");
			sbf.append(" WHERE task.PROC_INST_ID_=" + processInstanceId);
			Map<String, Object> param = new HashMap<String, Object>();
			String fields = "task.name_,us.username,comm.MESSAGE_,center.center_name,dep.department_name,task.START_TIME_,task.END_TIME_,task.description_";
			param.put("fields", fields);
			param.put("where", sbf.toString());
			// 查询部门和中心
			hislist = userDao.queryId(param);
		}
		return hislist;
	}

	@Override
	public List<Comment> viewHisComments1(String bizId, String beyId) {
		List<Comment> historyCommnets = new ArrayList<Comment>();
		/** 2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID */

		String objname = beyId + "." + bizId;
		HistoricProcessInstance hpi = historyService
				.createHistoricProcessInstanceQuery()// 对应历史的流程变量表
				.processInstanceBusinessKey(objname).singleResult();
		// 流程实例ID
		String processInstanceId = hpi.getId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 2）通过流程实例查询所有的(用户任务类型)历史活动
		List<HistoricActivityInstance> hais = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(pi.getId()).activityType("userTask").list();
		// 3）查询每个历史任务的批注
		for (HistoricActivityInstance hai : hais) {
			String historytaskId = hai.getTaskId();
			List<Comment> comments = taskService.getTaskComments(historytaskId);
			// 4）如果当前任务有批注信息，添加到集合中
			if (comments != null && comments.size() > 0) {
				historyCommnets.addAll(comments);
			}
		}
		// 5）返回
		return historyCommnets;

	}

	/**
	 * 根据实例编号查找下一个任务节点
	 * 
	 * @param String
	 *            procInstId ：实例编号
	 * @return
	 * @throws Exception
	 */

	public List<Map<String, Object>> backTaskTab(String taskId)
			throws Exception {
		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();

		String procInstId = task.getProcessInstanceId();
		// 流程标示
		String processDefinitionId = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult()
				.getProcessDefinitionId();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		// 执行实例
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		// 当前实例的执行到哪个节点
		String activitiId = execution.getActivityId();
		// 获得当前任务的所有节点
       		List<ActivityImpl> activitiList = def.getActivities();
		ActivityImpl activityImpl = null;

		String fields = " a.userid,a.username";
		String where = null;

		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < activitiList.size(); i++) {
			String flag = activitiList.get(i).getId();
			if (flag.equals(activitiId)) {
				activityImpl = activitiList.get(i);
			}
		}

		int num = activitiList.indexOf(activityImpl);
		ActivityImpl activityImpl_ = activitiList.get(num + 1);
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listuser = new ArrayList<Map<String, Object>>();
		if (activityImpl.getProperty("name").equals("项目经理提出申请")) {
			listuser = purchaseService.fidbusiness();
		} else if (activityImpl.getProperty("name").equals("经营部审核")) {
			listuser = purchaseService.manageraAudit();
		} else if (activityImpl.getProperty("name").equals("总经理审核")) {
			listuser = purchaseService.purchasingcheck();
		} else if (activityImpl.getProperty("name").equals("采购核对单价")) {
			listuser = purchaseService.financialcheck();
		} else if (activityImpl.getProperty("name").equals("财务经理审批")) {
			listuser = purchaseService.chairmancheck();
		} else if (activityImpl.getProperty("name").equals("董事会审批")) {

			String cghd = taskService.getVariable(taskId, "cghd").toString();
			where = " uc_user a  where a.userid=" + cghd;

			param.put("fields", fields);
			param.put("where", where);
			listuser = userDao.queryId(param);

		} else if (activityImpl.getProperty("name").equals("申请采购单下单")) {
			listuser = purchaseService.supplierUser();
		} else if (activityImpl.getProperty("name").equals("配货")) {
			String username = taskService.getVariable(taskId, "username")
					.toString();
			where = " uc_user a  where a.userid=" + username;

			param.put("fields", fields);
			param.put("where", where);
			listuser = userDao.queryId(param);

		} else if (activityImpl.getProperty("name").equals("核对签收")) {
			String cghd = taskService.getVariable(taskId, "cghd").toString();
			where = " uc_user a  where a.userid=" + cghd;

			param.put("fields", fields);
			param.put("where", where);
			listuser = userDao.queryId(param);

		} else if (activityImpl.getProperty("name").equals("复核价格及数量")) {
			String peihuo = taskService.getVariable(taskId, "peihuo")
					.toString();
			where = " uc_user a  where a.userid=" + peihuo;

			param.put("fields", fields);
			param.put("where", where);
			listuser = userDao.queryId(param);

		} else if (activityImpl.getProperty("name").equals("申请请款")) {
			String cghd = taskService.getVariable(taskId, "cghd").toString();
			where = " uc_user a where a.userid=" + cghd;

			param.put("fields", fields);
			param.put("where", where);
			listuser = userDao.queryId(param);
		}

		/*
		 * }else if (activityImpl.getProperty("name").equals("采购核对单价")) {
		 * listuser= purchaseService.fidbusiness();
		 * 
		 * }else if (activityImpl.getProperty("name").equals("财务经理审批")) {
		 * listuser= purchaseService.fidbusiness();
		 */

		return listuser;
	}

	/**
	 * 根据实例编号查找下一个任务节点
	 * 
	 * @param String  procInstId ：实例编号
	 * @return
	 */
	public List<TaskDefinition> nextTaskDefinition(String taskId) {
		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();

		String procInstId = task.getProcessInstanceId();

		// 流程标示
		String processDefinitionId = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult()
				.getProcessDefinitionId();
		List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		// 执行实例
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		// 当前实例的执行到哪个节点
		String activitiId = execution.getActivityId();
		// 获得当前任务的所有节点
		List<ActivityImpl> activitiList = def.getActivities();
	         	
				ActivityImpl activityImpl = null;
				for (int i = 0; i < activitiList.size(); i++) {
					String flag = activitiList.get(i).getId();
				
					if (flag.equals(activitiId)) {
						activityImpl = activitiList.get(i);
						
						System.out.println("当前任务：" + activityImpl.getProperty("name"));
						taskDefinitionList = nextTaskDefinition(activityImpl, activityImpl.getId());
					}
				}
	/*	for (ActivityImpl activityImpl : activitiList) {
			id = activityImpl.getId();
			if (activitiId.equals(id)) {
				System.out.println("当前任务：" + activityImpl.getProperty("name"));
				taskDefinitionList = nextTaskDefinition(activityImpl, activityImpl.getId());
			// s1和审核通过的条件
				// System.out.println(taskDefinition.getCandidateGroupIdExpressions().toArray()[0]);
				// return taskDefinition;
			}
		}*/
				
		return taskDefinitionList;
	}

	/**
	 * 
	 * @author: Longjun
	 * @Description: 获取所有下一节点
	 * @date:2016年3月18日 下午4:33:24
	 */
	private List<TaskDefinition> nextTaskDefinition(ActivityImpl activityImpl, String activityId){
		List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>();//所有的任务实例
		List<TaskDefinition> nextTaskDefinition = new ArrayList<TaskDefinition>();//逐个获取的任务实例
		TaskDefinition taskDefinition = null;
		if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){
			taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();
			taskDefinitionList.add(taskDefinition);
		}else{
			List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
			List<PvmTransition> outTransitionsTemp = null;
			for(PvmTransition tr:outTransitions){  
				PvmActivity ac = tr.getDestination(); //获取线路的终点节点  
				//如果是互斥网关或者是并行网关
				if("exclusiveGateway".equals(ac.getProperty("type"))||"parallelGateway".equals(ac.getProperty("type"))){
					outTransitionsTemp = ac.getOutgoingTransitions();
					if(outTransitionsTemp.size() == 1){							
						nextTaskDefinition = nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId);
						taskDefinitionList.addAll(nextTaskDefinition);
					}else if(outTransitionsTemp.size() > 1){
						for(PvmTransition tr1 : outTransitionsTemp){
							nextTaskDefinition = nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId);
							taskDefinitionList.addAll(nextTaskDefinition);
						}							
					}
				}else if("userTask".equals(ac.getProperty("type"))){
					taskDefinition = ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();
					taskDefinitionList.add(taskDefinition);
				}else{
					System.out.println((String) ac.getProperty("type"));
				}
			} 		
		}
		return taskDefinitionList;
	}


	
	/**
	 * 根据实例编号查找下一个任务节点
	 * 
	 * @param String procInstId ：实例编号
	 * @throws Exception
	 */

	public ActivityImpl usertask(String taskId) throws Exception {
		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();

		String procInstId = task.getProcessInstanceId();
		// 流程标示
		String processDefinitionId = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult()
				.getProcessDefinitionId();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		// 执行实例
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		// 当前实例的执行到哪个节点
		String activitiId = execution.getActivityId();
		// 获得当前任务的所有节点
		List<ActivityImpl> activitiList = def.getActivities();
		ActivityImpl activityImpl = null;
		for (int i = 0; i < activitiList.size(); i++) {
			String flag = activitiList.get(i).getId();
			if (flag.equals(activitiId)) {
				activityImpl = activitiList.get(i);
			}
		}
		return activityImpl;
	}

	/**
	 * 只删除的流程
	 * 不删除历史记录
	 * 
	 * */
	@Override
	@Transactional
	public Map<String, Object> deleteprocessInstance(String bizId, String beyId) {

		//查询实例
		Map<String, Object> map=this.findProc_Inst_id(bizId, beyId);	
		String processInstanceId = null;
		System.out.println(map);
		//判断是否有流程存在
             if(map !=null){ 
            	 processInstanceId=map.get("PROC_INST_ID_")+"";
            	 ProcessInstance pi= runtimeService.createProcessInstanceQuery()//创建流程实例
                         .processInstanceId(processInstanceId)//使用流程实例ID查询
                         .singleResult();
            	 //检测流程是否结束
            	 if (pi==null) {
 					System.out.println("流程已经结束");
 				//流程未结束时强制结束流程
 				}else {
 					System.out.println("流程没有结束");
 					runtimeService.deleteProcessInstance(processInstanceId,"删除原因");//删除流程
 				}
             }
		return null;
	}

	/**
	 * 发送邮件
	 * 
	 * @param theme
	 *            邮件头文件名
	 * @param content
	 *            邮件内容
	 * @param nextUser
	 *            收件人地址 收件人邮箱
	 */
	public Map<String, Object> sendEmail(String theme, String content,
			String nextUser) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		SendmailUtil sendmailUtil = null;
		json.put("message", "");
		if (!StringUtils.isEmpty(nextUser)) {
			String fields = " email";
			String where = " uc_user where userid=" + nextUser + "";
			Map<String, Object> useMap = new HashMap<String, Object>();
			useMap.put("fields", fields);
			useMap.put("where", where);

			User uc = userDao.login(useMap);

			// 判断Emali的邮箱是否为null
			if (uc != null && uc.getEmail() != null) {

				sendmailUtil = (SendmailUtil) Class.forName(
						"com.casd.controller.web.utils.SendmailUtil")
						.newInstance();
				sendmailUtil.doSendHtmlEmail(theme, content,uc.getEmail());
				json.put("message", "邮件已发送!");

			} else {

				json.put("message", "邮件发送失败!");
			}
		}
		return json;
	}
   
	/**
	 *  @author Mr.Liao
	 * 删除流程
	 * 同时删全部记录
	 * @throws Exception 
	 * 
	 * */
	@Override
	@Transactional
	public Map<String, Object> deleteRecord(String bizId, String beyId) throws Exception {
		
		//查询实例
		Map<String, Object> map=this.findProc_Inst_id(bizId, beyId);
		if (map!=null) {
			String bizid2=map.get("PROC_INST_ID_")+"";
			//定义删除流程数据表
			String [] strArray = {"act_hi_actinst where PROC_INST_ID_ ="+bizid2,
					              "act_hi_attachment where PROC_INST_ID_="+bizid2,
					              "act_hi_comment where PROC_INST_ID_="+bizid2,
					              "act_hi_detail where PROC_INST_ID_="+bizid2,
					              "act_hi_identitylink where TASK_ID_ IN (select ID_ from act_hi_taskinst where PROC_INST_ID_ ="+bizid2+")",
					              "act_hi_identitylink where PROC_INST_ID_="+bizid2,
					              "act_hi_procinst where PROC_INST_ID_= "+bizid2,
					              "act_hi_taskinst where PROC_INST_ID_="+bizid2,
					              "act_hi_varinst where PROC_INST_ID_="+bizid2,
					              "act_ru_event_subscr where PROC_INST_ID_="+bizid2,
					              "act_ru_identitylink where TASK_ID_ IN (select ID_ from act_ru_task where PROC_INST_ID_="+bizid2+")",
					              "act_ru_identitylink where PROC_INST_ID_="+bizid2,
					              "act_ru_task where PROC_INST_ID_="+bizid2,
					              "act_ru_variable where PROC_INST_ID_="+bizid2,
					              "act_ru_execution where PROC_INST_ID_="+bizid2,
					              "act_data_procinst where PROC_INST_ID_="+bizid2
                                 };
			
			  for (String str : strArray) {
				  Map<String, Object> useMap = new HashMap<String, Object>();
					String where="";
					useMap.put("fields","from "+str);
					useMap.put("where", where);
					userDao.deleteUser(useMap);
	            }
		}
		return null;
	}
	
	
        //选择回退
	   public Map<String, Object> rejectTaskTo(String taskId,String activityId) throws Exception{
				Map<String, Object> json=new HashMap<String, Object>();
		  	 HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
		  	.taskId(taskId).finished().singleResult();
		  	 if(historicTaskInstance !=null){
		  	  json.put("Msg", "任务已结束，不能进行回退操作！");
		  	 throw new Exception("任务已结束，不能进行回退操作！");
		  	  }
		  	 if(activityId == null || "".equals(activityId)){
		  		json.put("Msg", "回退目标节点不能为空！");
		   throw new Exception("回退目标节点不能为空！");
		  }
		  long count = taskService.createTaskQuery().taskId(taskId).count();
		  	 if(count == 0){
		  		json.put("Msg", "要驳回的任务已不存在！");
		  	throw new Exception("要驳回的任务已不存在！");
		  	 }
		   Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
		  	 ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getProcessDefinition(currentTask.getProcessDefinitionId());
		  	 String instanceId = currentTask.getProcessInstanceId();
		  	ActivityImpl activityImpl = definitionEntity.findActivity(activityId);
		  	if(activityImpl == null){
		  		json.put("Msg", "要驳回的节点不存在！");
		  	 throw new Exception("要驳回的节点不存在！");
		  	}
		  	 managementService.executeCommand(new JumpActivityCmd(activityId, instanceId));
			return json;
		  	 
	  	 }
	
	/**
	 * 回调数据
	 * */
     public Map<String, Object>  findProc_Inst_id(String bizId, String beyId) {
 		Map<String, Object> param=new HashMap<String, Object>();
 		String objname = beyId + "." + bizId;
 		String fields="RES.PROC_INST_ID_";
 		String where="ACT_HI_PROCINST RES WHERE RES.BUSINESS_KEY_ ='"+objname+"'";
 		param.put("fields", fields);
 		param.put("where", where);
		return userDao.queryUserById(param);	
	}

     
       /***
        * @param act_re_procdef  processDefinitionId
        *  获取全部节点
        * 
        * 
        * **/
	@Override
	public  List<?>  getActivityImpl(String processDefinitionId) {
		 List<Map<String, Object>> list=new ArrayList<>();
		
			ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
			//获得当前任务的所有节点
			List<ActivityImpl> activitiList = pde.getActivities();
			for(ActivityImpl activityImpl:activitiList){  
				
				if (activityImpl.getProperty("type").equals("userTask")) {
					Map<String, Object> map=new HashMap<>();
					map.put("name", activityImpl.getProperty("name"));
					map.put("id", activityImpl.getId());
					list.add(map);
				}
				
			}
			return list;
		
	}

	/**
	 *  部署编号
	 *  例如：resignView:1:177504
	 *  
	 * */
	@Override
	public Object getRenderedStartForm(String pdfId) {
		Object startForm = formService.getRenderedStartForm(pdfId);
		return startForm;
	}
	
	/**
	 *  任务 taskid
	 *  
	 *      
	 * */
	@Override
	public Object getRenderedTaskForm(String taskid) {
		Object startForm = formService.getRenderedTaskForm(taskid);
		return startForm;
	}


}
