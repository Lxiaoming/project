package com.casd.serviceimpl.supplierMod;

import java.util.HashMap;
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

import com.casd.dao.supplierMod.WorkerDao;
import com.casd.entity.supplierMod.Worker;
import com.casd.entity.supplierMod.WorkerApply;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.supplierMod.WorkerService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao workerDao;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ActivitiService activitiService;
	

	@Override
	@Transactional
	public Boolean checkWorkerExist(String supplierMod_worker_userId) {

		return workerDao.checkWorkerExist(supplierMod_worker_userId);

	}

	@Override
	@Transactional
	public void save_Worker(Worker worker) {

		workerDao.save_Worker(worker);

	}

	@Override
	@Transactional
	public void workerApply(WorkerApply workerApply) {

		workerDao.workerApply(workerApply);

	}

	/**
	 * 修改流程状态
	 * 
	 * @param execution
	 *            为流程设定参数
	 * @param status
	 *            流程设定状态
	 * 
	 * */

	@Transactional
	public void updateWorkerFlow(DelegateExecution execution, String status) {
		String bizkey = execution.getProcessBusinessKey();
		String[] strs = bizkey.split("\\.");
		String bizId = null;
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		WorkerApply workerApply = new WorkerApply();
		workerApply
				.setSuppliermod_worker_apply_status(Integer.parseInt(status));
		workerApply.setSuppliermod_worker_apply_id(Integer.parseInt(bizId));
		workerDao.updateworkerApply(workerApply);
	}

	@Override
	public Map<String, Object> findWorker(String fields, String where) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);

		return workerDao.findWorker(param);
	}

	/**
	 * 审核员工调动申请单据
	 * */
	@Override
	@Transactional
	public void workerApplyPass(HttpServletRequest request) throws Exception {
		// 页面数据获取
		String taskid = request.getParameter("taskid");
		String taskName = request.getParameter("taskName");
		String options = request.getParameter("options");
		String obj = request.getParameter("obj");

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("sign", obj + "");
		User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
		String userid = user.getUserid() + "";
		// 获取业务id 顺序不能放在最后 否则获取不到业务id
		String bizkey = activitiService.getBusinessBizId(taskid);
		String[] strs = bizkey.split("\\.");
		String bizId = null;
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		/**
		 * 判断是否同意员工调动
		 * 
		 * */
		if ("true".equals(obj)) {
			
			String fields = " a.supplierMod_worker_id,b.suppliermod_worker_apply_proId,b.suppliermod_worker_apply_teamId";
			String where = " suppliermod_worker_table a join suppliermod_worker_apply b";
			where += " on a.supplierMod_worker_userId=b.suppliermod_worker_apply_userId";
			where += " WHERE b.suppliermod_worker_apply_id=" + bizId;
			// 查询当前所在项目
			Map<String, Object> workdata = findWorker(fields, where);
			Worker worker = new Worker();
			worker.setSupplierMod_worker_id(Integer.parseInt(workdata
					.get("supplierMod_worker_id") + ""));
			worker.setSupplierMod_worker_projectId(Integer.valueOf(workdata
					.get("suppliermod_worker_apply_proId") + ""));
			worker.setSupplierMod_worker_workTeamId(Integer.valueOf(workdata
					.get("suppliermod_worker_apply_teamId") + ""));
			// 人员项目调动完成
			workerDao.updateWorker(worker);
		}
		//修改人员调用状态
		WorkerApply workerApply = new WorkerApply();
		workerApply.setSuppliermod_worker_apply_status(Integer.valueOf("true".equals(obj) ? "2" : "3"));
		workerApply.setSuppliermod_worker_apply_id(Integer.parseInt(bizId));
		workerDao.updateworkerApply(workerApply);
		// 开始审核
		activitiService.comment(taskid, userid, options);
		taskService.complete(taskid, vars);

		String theme = "工人调动申请";
		String content = "";
		// 发送qq邮箱信息通知
		if ("false".equals(obj)) {
			String apply_userId = request.getParameter("apply_userId");
			content = "员工申请调动审批不通过";
			activitiService.sendEmail(theme, content, apply_userId);
		}else {
			String nextUser = request.getParameter("username");
			content = "你有一张员工调动申请单需审核";
			if (nextUser!=null && !StringUtils.isBlank(nextUser)) {
				activitiService.sendEmail(theme, content, nextUser);
			}
			
		}

	}

	@Override
	public int getUserByName(String construct_project_leader) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("construct_project_leader", construct_project_leader);
		return Integer.valueOf(workerDao.getUserByName(map).get("userid").toString().equals("")?"0":workerDao.getUserByName(map).get("userid").toString());
	}

	@Override
	@Transactional
	public void multiplayerApply(Map<String, Object> map) {
		// TODO Auto-generated method stub
		workerDao.multiplayerApply(map);
		
		
	}
	
	//流程定义类方法
	@Override
	public String workerNextUser(DelegateExecution execution) {
	  	  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	  		String nextUser = request.getParameter("username");// 下一个审核人	
	  		return nextUser;		

	}

	@Override
	public void saveWorkerAtt(Map<String, Object> data) {
		// TODO Auto-generated method stub
		workerDao.saveWorkerAtt(data);
		
	}
	
	@Override
	public Map<String, Object> queryHrAttend(Map<String, Object> map) {
		Map<String,Object> map2 = workerDao.queryHrAttend(map);
		return map2;
		
	}

	@Override
	public int updateHrAttend(Map<String, Object> map) {
		return workerDao.updateHrAttend(map);
	}
}
