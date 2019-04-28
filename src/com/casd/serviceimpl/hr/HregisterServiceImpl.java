package com.casd.serviceimpl.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.flow.FlowDao;
import com.casd.dao.hr.HrContractDao;
import com.casd.dao.hr.HrSalaryDao;
import com.casd.dao.hr.HregisterDao;
import com.casd.dao.hr.HtrainingrecordDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.activiti.Taskinst;
import com.casd.entity.hr.Become;
import com.casd.entity.hr.HrContract;
import com.casd.entity.hr.HrSalary;
import com.casd.entity.hr.Hregister;
import com.casd.entity.uc.Costapp;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.hr.HregisterService;

@Service
public class HregisterServiceImpl implements HregisterService,TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private HrContractDao hrContractDao;
	@Autowired
	private HrSalaryDao hrSalaryDao;
	@Autowired
	private HregisterDao hregisterDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private HtrainingrecordDao htrainingrecordDao;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FlowDao flowDao;
	@Autowired
	private  ActivitiDao activitiDao;
	
	
	
	public Map<String, Object> getAllContrac(String userid) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" hr_contract userid where user_id=" + userid);
		param.put("fields", "out_stime,start_time,remark,user_id");
		param.put("where", sBuilder.toString());

		List<Map<String, Object>> paramss = hrContractDao.selectgetAll(param);

		param.put("rows", paramss);
		return param;

	}

	@Override
	public void deleteHregister(Map<String, Object> map) {
		hregisterDao.deleteHregister(map);

	}

	@Override
	public void deleteContract(Map<String, Object> map) {
		hrContractDao.deleteContract(map);

	}

	@Override
	public void deleteHrSalary(Map<String, Object> map) {

		hrSalaryDao.deleteHrSalary(map);

	}

	@Override
	public void deleteHrecord(Map<String, Object> map) {

		htrainingrecordDao.deleteHrecord(map);

	}

	@Override
	@Transactional
	public JSONObject savexistence(User user, JSONArray myJsonArray,
			 JSONArray myJsonArray3,
			JSONArray myJsonArray4) throws Exception {
		JSONObject erro=new JSONObject();
		erro.put("erro", "");
		Boolean existUser=true;
		if (user.getUserid()==0) {
			existUser=userDao.existUser(user.getUsername());
			existUser=!existUser;
		}
		if (existUser) {
			if (user.getPassword()==null||user.getPassword()=="") {
				user.setPassword("123");
			}
			userDao.existence(user);
			// 是否添加和更新员工变动记录
			if (!myJsonArray.isEmpty()&&!myJsonArray.getJSONObject(0).isNullObject()) {
				for (int i = 0; i < myJsonArray.size(); i++) {
					Hregister hregister = new Hregister();
					JSONObject myjObject = myJsonArray.getJSONObject(i);
					String job_id = ((myjObject.getString("job_id").isEmpty() ? String
							.valueOf("0") : myjObject.getString("job_id")));
		
					hregister.setJob_id(Integer.parseInt(job_id));
					hregister.setBegin_date(myjObject.getString("begin_date"));
					hregister.setEnd_date(myjObject.getString("end_date"));
					hregister.setCorporate_name(myjObject.getString("corporate_name"));
		
					hregister.setJob_name(myjObject.getString("job_name"));
					hregister.setReason(myjObject.getString("reason"));
					hregister.setUser_id(user.getUserid());
					hregisterDao.addHregisters(hregister);
				}
			}
	
			// 是否添加和更新薪资调整
			if (!myJsonArray3.isEmpty()&&!myJsonArray3.getJSONObject(0).isNullObject()) {
				for (int i = 0; i < myJsonArray3.size(); i++) {
					HrSalary hsy = new HrSalary();
		
					JSONObject myjObject3 = myJsonArray3.getJSONObject(i);
					String salary_id = ((myjObject3.getString("salary_id").isEmpty() ? String
							.valueOf("0") : myjObject3.getString("salary_id")));
		
					hsy.setSalary_id(Integer.parseInt(salary_id));
					hsy.setAdjustment_time(myjObject3.getString("adjustment_time"));
					hsy.setOriginal_salary(myjObject3.getString("original_salary"));
					hsy.setAdjusted_salary(myjObject3.getString("adjusted_salary"));
					hsy.setReason(myjObject3.getString("reason"));
					hsy.setNote_taker(myjObject3.getString("note_taker"));
					hsy.setUser_id(user.getUserid());
					hrSalaryDao.addSalary(hsy);
				}
			}
			// 是否添加和更新合同
			if (!myJsonArray4.isEmpty()&&!myJsonArray4.getJSONObject(0).isNullObject()) {
				for (int i = 0; i < myJsonArray4.size(); i++) {
					HrContract hct = new HrContract();
		
					JSONObject myjObject4 = myJsonArray4.getJSONObject(i);
					String pact_id = ((myjObject4.getString("pact_id").isEmpty() ? String
							.valueOf("0") : myjObject4.getString("pact_id")));
		
					hct.setPact_id(Integer.parseInt(pact_id));
					hct.setStart_time(myjObject4.getString("start_time"));
					hct.setOut_stime(myjObject4.getString("out_stime"));
					hct.setRemark(myjObject4.getString("remark"));
					hct.setUser_id(user.getUserid());
					hrContractDao.insertContract(hct);
		
				}
			}
		}else {
			erro.put("erro", "存在相同名字，请确认是否重复");
		}
		return erro;
	}

	/**
	 * 添加记录
	 * 
	 * @throws Exception
	 * */
	@Override
	public void insertBecome(Become become, String name) throws Exception {


		    int id = hrContractDao.insertBecome(become);
	  
			String processDefinitioKey = "Become_for"; // 定义流程的key,不可修改
		
			String bizId = processDefinitioKey + "."
					+ become.getBc_id(); // 获取业务id
			Map<String, Object> vars=new HashMap<String, Object>();
	 		vars.put("applyUserId", become.getUser_id()+"");
	 	   ProcessInstance pi=runtimeService.startProcessInstanceByKey(processDefinitioKey,bizId,vars);
	 	   
	 	   DataProcinst dataProcinst=new  DataProcinst();
			dataProcinst.setProc_inst_id_(pi.getId());
			dataProcinst.setApplicant(become.getUsername());
			dataProcinst.setIllustrate(become.getRole_name());
		    activitiDao.insertDataProcinst(dataProcinst);

	      }
   @Transactional
	public void updateFlow(DelegateExecution execution, String status)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String bizkey = execution.getProcessBusinessKey();
		String[] strs = bizkey.split("\\.");
		String bizId = null;
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		String pars = " bc_status=" + status;
		StringBuffer sbf = new StringBuffer();
		sbf.append(" where bc_id=" + bizId);
		map.put("pars", pars);
		map.put("where", sbf.toString());
		hrContractDao.updateBecome(map);
		
		/*if(status.equals("2")){
			Map<String, Object>  params=new HashMap<String, Object> ();
			String field = " u.user_id";
			 sbf.delete(0,sbf.length());
			
			sbf.append(" uc_become u where 1=1");
			sbf.append(" and bc_id=" + bizId);
			params.put("fields", field);
			params.put("where", sbf.toString());
			Map<String, Object> dataMap = userDao.queryUserById(params);
			params.clear();
			String file="`status`="+1;
			String userid= dataMap.get("user_id").toString();
		
			params.put("pars", file);
			params.put("where", "where userid="+userid);
			userDao.updateUser(params);
			
		}*/
	}

	// 查询审核人
	@Override
	public List<Map<String, Object>> becomecheck(String taskName)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields = "c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_description='Become_for"
				+ "' AND c.flow_node_name='" + taskName + "'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name = flowDao.newlis(map);
		map.clear();
		sbf.delete(0, sbf.length());
		String nameString = "*";
		sbf.append("uc_user a WHERE a.userid in(" + name + ")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		return userDao.queryId(map);
	}

	public List<Map<String, Object>> becomeUser(String taskId) throws Exception {

		ActivityImpl activityImpl = activitiService.usertask(taskId);
		List<Map<String, Object>> listuser = new ArrayList<Map<String, Object>>();
		String taskName = activityImpl.getProperty("name").toString();
		if (taskName.equals("提交申请")) {

			Task task = taskService.createTaskQuery().taskId(taskId)
					.singleResult();

			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId())
					.singleResult();
			String businessKey = pi.getBusinessKey();
			String objId = null;
			if (StringUtils.isNotBlank(businessKey)) {
				objId = businessKey.split("\\.")[1];
			}
			Map<String, Object> params = new HashMap<String, Object>();
			String field = " u.username";
			StringBuffer sbf = new StringBuffer();
			sbf.append(" uc_become u where 1=1");
			sbf.append(" and bc_id=" + objId);
			params.put("fields", field);
			params.put("where", sbf.toString());
			Map<String, Object> dataMap = userDao.queryUserById(params);
			String username = dataMap.get("username").toString();
			params.put("username", username);
			listuser.add(params);
		} else if (taskName.equals("当事人")) {
			listuser = this.becomecheck(taskName);
		} else if (taskName.equals("主管公司")) {
			listuser = this.becomecheck(taskName);
		} else if (taskName.equals("总裁")) {
			listuser = this.becomecheck(taskName);
		} else if (taskName.equals("董事会")) {

		}

		return listuser;

	}

	@Transactional
	public void becomepass(HttpServletRequest request) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		String taskid = request.getParameter("taskid");
		User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
		String userid = user.getUserid() + "";
		String nextUser = request.getParameter("userid");// 下一个审核人id
		String reasons = request.getParameter("reasons");// 获取审核意见
		String sign = request.getParameter("sign");// 是否批准
		String personal = request.getParameter("personal");//自我评价
		String bc_id = request.getParameter("bc_id");
		
		activitiService.comment(taskid,userid,reasons == null ? "" : reasons);	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", bc_id);
		map.put("personal", personal);
		hrContractDao.updateBecomePersonal(map);
		
		if (StringUtils.isNotEmpty(sign)) {
			vars.put("type", sign);
		}
		String role_name = request.getParameter("role_name");// 获取角色
		if (role_name.indexOf("总经理") != -1 || role_name.indexOf("董事长助理") != -1) {
			vars.put("Level", "3");
		} else if ((role_name.indexOf("经理") != -1)
				|| (role_name.indexOf("项目助理") != -1)
				|| (role_name.indexOf("秘书") != -1)) {
			vars.put("Level", "2");
		} else {
			vars.put("Level", "1");
		}
		taskService.complete(taskid, vars);

	}
	/***修改单据状态
     * 
     */
	@Override
	@Transactional
	public void updateBecome(DelegateExecution execution, String status) {

		String bizkey = execution.getProcessBusinessKey();

		String[] strs = bizkey.split("\\.");
		String bizId = null;
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("pars", " bc_status = " + status);
		map.put("where", " where bc_id = " + bizId);
		hrContractDao.updateBecome(map);
		
	}
	/***修改人员转正状态
	 * 
	 */
	@Override
	@Transactional
	public void updateUserStatus(DelegateExecution execution, String status) {
		String bizkey = execution.getProcessBusinessKey();
		
		String[] strs = bizkey.split("\\.");
		String bizId = null;
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		//修改人员状态为成功
		Map<String, Object> map = new HashMap<>();
		map.put("pars", " status = " + status);
		map.put("where", " where userid = (select user_id from uc_become where bc_id = "+bizId+") " );
		hrContractDao.updateUserStatus(map);
	}
	//流程定义类方法
  public  String becomenextUser(DelegateExecution execution) {
	  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String nextUser = request.getParameter("username");// 下一个审核人	
		return nextUser;		
	
    }
	

	
	//监听器 重写 设置下一个审核人
	@Transactional
	public void notify(DelegateTask delegateTask) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String nextUser = request.getParameter("username");// 下一个审核人	
		delegateTask.addCandidateUser(nextUser);		
	}


}
