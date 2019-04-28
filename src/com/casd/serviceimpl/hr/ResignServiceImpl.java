package com.casd.serviceimpl.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.dao.hr.HrContractDao;
import com.casd.dao.hr.ResignDao;
import com.casd.entity.hr.Resign;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.hr.ResignService;

@Service
public class ResignServiceImpl implements ResignService {
	@Autowired
	private ResignDao resignDao;
	@Autowired
	private HrContractDao hrContractDao;
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TaskService taskService;
	

	@Override
	@Transactional
	public void addResign(Resign ressign) {
		// TODO Auto-generated method stub
		resignDao.addResign(ressign);
	}

	@Override
	public List<Resign> getData(String fields, String where) {
		// TODO Auto-generated method stub
		Map<String , Object>param=new HashMap<String, Object>();
		param.put("fields",fields );
		param.put("where", where);
		return resignDao.getData(param);
		
	}

	@Override
	public void addAutoPath(String path,String resignId) {
		
		Map<String , Object>param=new HashMap<String, Object>();
		param.put("where", "hr_resign_autoPath = '"+path+"'  WHERE hr_resign_id = "+resignId+"");
		resignDao.addAutoPath(param);
		
	}

	@Override
	public Resign findResign(String fields, String where) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);
		
		return resignDao.findResign(map);
	}
    
	
	
	
	@Override
	@Transactional
	public void passResign(HttpServletRequest request, Resign ressign) throws Exception {
		Map<String, Object> vars=new HashMap<String, Object>();
		
	
		String taskid = request.getParameter("taskid");
		User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
		String userid = user.getUserid() + "";
		String nextUser = request.getParameter("userid");// 下一个审核人id
		String reasons = request.getParameter("reasons");// 获取审核意见
		String sign = request.getParameter("sign");// 是否批准
			 
		activitiService.comment(taskid,userid == null ? "" : userid,reasons == null ? "" : reasons);

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
		if (StringUtils.isNotEmpty(sign)) {
			vars.put("type", sign);
		}
		taskService.complete(taskid,vars);	
		resignDao.updateResign(ressign);
		String theme ="离职申请";
		String content="你有一张离职申请单需要审核";
		//activitiService.sendEmail(theme, content, nextUser);
	}
	//流程定义类方法
	  public  String resignNextUser(DelegateExecution execution) {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String nextUser = request.getParameter("username");// 下一个审核人	
			return nextUser;		
		
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
			map.put("where", " where userid = (select hr_resign_userid from hr_resign where hr_resign_id = "+bizId+") " );
			hrContractDao.updateUserStatus(map);
		}
}
