package com.casd.serviceimpl.uc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.uc.CostappDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.manage.Contractapprove;
import com.casd.entity.uc.Costapp;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.uc.CostappService;


@Service
public class CostappServiceImpl implements CostappService {
	@Autowired
	private CostappDao costappDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private TaskService  taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ActivitiDao activitiDao; 

	@Override
	public List<Map<String, Object>> costappList(int page, int pageSize,
			Ref<Integer> record, String fields, String where) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);

		if (record != null) {
			Integer count = costappDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

			param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
					* pageSize, pageSize));
		return costappDao.getList(param);
	}


	
	
     @Transactional
	public void delete_data(Map<String, Object> map) {
	
    	 costappDao.delete_data(map);
	}


	@Override
	public void save_costapp(Costapp costapp)  {
		
		costappDao.save_costapp(costapp);
	    }


	@Override
	public List<Map<String, Object>> getData(Map<String, Object> map) {
		Map<String, Object> daoMap=new HashMap<String, Object>();
		daoMap.put("fields", "*");
		daoMap.put("where", " uc_costapp c left join uc_user u on c.userid = u.userid where costapp_id="+map.get("costapp_id")+"");
		return costappDao.getData(daoMap);
	}




	@Transactional
	public void costappDelegate(DelegateExecution execution, String status) {
	String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
		Costapp costapp=new Costapp();
		costapp.setCostapp_id(Integer.valueOf(bizId));
		costapp.setCostapp_status(Integer.valueOf(status));
		costappDao.updateCostapp(costapp);
		
	}




	@Override
	public Map<String, Object> findCostapp(String fields, String where) {
		Map<String,Object> param=new  HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);
		return costappDao.findCostapp(param);
	}



     /***
      *  费用申请审核
      *  
      * */
	@Override
	@Transactional
	public void pass_costapp(HttpServletRequest request, Costapp costapp,Map<String, Object> map) {
		String taskid=request.getParameter("taskid");
		String taskName=request.getParameter("taskName");
		
		User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
	    String userid=user.getUserid()+"";  //当前审核人id
		Map<String, Object> vars=new HashMap<String, Object>();
		if (taskName.equals("申请人")){
			vars.put("sign","true");
		
		Map<String, Object> params=new HashMap<String, Object>();	
		String fields="b.role_name";
			StringBuffer sbf=new StringBuffer();
			sbf.append(" uc_user a JOIN uc_role b");
			sbf.append(" ON a.role_id=b.role_id");
			sbf.append(" WHERE a.userid="+userid);
			params.put("fields", fields);
			params.put("where", sbf.toString());
	    Map<String, Object> data =userDao.queryUserById(params);
	    String rolename=data.get("role_name").toString();
	   if (rolename.indexOf("经理") != -1 || rolename.indexOf("助理") != -1) {
		   //总经理级别的职员
		  if ((rolename.indexOf("总经理") != -1) || (rolename.indexOf("助理") != -1)){			   
			   vars.put("place", "1");
		  //经理级别的职员
		   }else {
			   vars.put("place", "2");
	     	}
		  //如果不是经理级别的职员
	    }else {
	    	  vars.put("place","3");
	    	
		}
	     
		}
		//更新 费用申请表
		//costappDao.updateCostapp(costapp);
		if (!taskName.equals("申请人")&&!taskName.equals("管理公司")){
			costappDao.updateAuditor(map);
		}

		//完成任务
		taskService.complete(taskid,vars);	
		
	}

	//流程定义类方法
	  public  String costappNextUser(DelegateExecution execution) {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String nextUser = request.getParameter("username");// 下一个审核人	
			return nextUser;		
		
	    }

	  //保存费用申请并启动流程
		public void startCostapp(HttpServletRequest request,Costapp costapp)  {
			
			costapp.setCostapp_id(0);
			//设置申请时间
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			costapp.setCostapp_time(df.format(new Date()));

			User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
		    int userid=user.getUserid();  //申请人
		    costapp.setUserid(userid);	  
		    costapp.setCostapp_status(0);
		    this.save_costapp(costapp);
		    
		// 保存成功后启动流程
		    Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("applyUserId",user.getUserid()+"");
			String processDefinitioKey = "costappView"; // 定义流程的key,不可修改
			String bizId = processDefinitioKey + "."
					+ costapp.getCostapp_id();
			ProcessInstance pi= runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId,vars);// 流程图id，业务表id
			DataProcinst dataProcinst=new  DataProcinst();
		
			dataProcinst.setProc_inst_id_(pi.getId());
			dataProcinst.setApplicant(user.getUsername());
			dataProcinst.setIllustrate("费用申请单");
			activitiDao.insertDataProcinst(dataProcinst);
			
		    }
		
	// 开始办理费用审批流程
	@Override
	@Transactional
	public Map<String, Object> Costapp_pass(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> vars = new HashMap<String, Object>();
		String taskid = request.getParameter("taskid");
		User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
		String userid = user.getUserid() + "";
		String nextUser = request.getParameter("userid");// 下一个审核人id
		String reasons = request.getParameter("reasons");// 获取审核意见
		String sign = request.getParameter("sign");// 是否批准
		String costapp_amount = request.getParameter("costapp_amount");//申请金额
		if(Double.valueOf(costapp_amount) >= 10000){
			vars.put("costapp_amount", "true");
		}else {
			vars.put("costapp_amount", "false");
		}
		activitiService.comment(taskid, userid, reasons);
		if (StringUtils.isNotEmpty(sign)) {
			vars.put("type", sign);
		}
		taskService.complete(taskid, vars);

		if (StringUtils.isNotEmpty(nextUser)) {
			String content = "您有一张费用申请单需要审批!"; // 请假内容
			content += "<br>申请人：";
			content += "<br>请假时间：";
			content += "<br>结束时间：";
			String theme = "费用申请单!"; // 邮件标题
			jsonMap = activitiService.sendEmail(theme, content, nextUser);
			activitiService.sendEmail(theme, content, userid);
		}

		return jsonMap;
	}


		  /***修改单据状态
	      * 
	      */
		@Override
		@Transactional
		public void updateCostapp(DelegateExecution execution, String status) {
			
	      String bizkey= execution.getProcessBusinessKey();
			
			String[] strs=bizkey.split("\\.");
	        String bizId=null;
			for(int i=0,len=strs.length;i<len;i++){
				bizId=strs[i].toString();
			}
			Costapp costapp=new Costapp();
			costapp.setCostapp_id(Integer.valueOf(bizId));
			costapp.setCostapp_status(Integer.valueOf(status));
			costappDao.updateCostapp(costapp);
			
		}
		
	}


	





     
   


