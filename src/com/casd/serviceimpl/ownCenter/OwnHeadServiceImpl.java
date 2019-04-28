package com.casd.serviceimpl.ownCenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.ownCenter.OwnHeadDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.ownCenter.OwnPurchaseEntry;
import com.casd.entity.ownCenter.OwnPurchaseHead;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.ownCenter.OwnHeadService;

@Service
public class OwnHeadServiceImpl implements OwnHeadService {

	@Autowired
	private OwnHeadDao ownHeadDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private  ActivitiDao activitiDao;
	@Override
	public List<Map<String, Object>> ownHeadlist(int page, int pageSize,
			Ref<Integer> record, String fields, String where) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("fields", fields);
		param.put("where", where);

		if (record != null) {
			int count = ownHeadDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		return ownHeadDao.ownHeadlist(param);
	}

	@Override
	@Transactional
	public void saveOwnHead(OwnPurchaseHead ownHead, HttpServletRequest request) {

		ownHeadDao.saveOwnHead(ownHead);
		JSONArray jsonArray = JSONArray
				.fromObject(request.getParameter("data"));
		int purchase_id = ownHead.getOwn_purchase_id();
		List<OwnPurchaseEntry> listDetail = (List) JSONArray.toCollection(
				jsonArray, OwnPurchaseEntry.class);
		for (int i = 0; i < listDetail.size(); i++) {
			OwnPurchaseEntry entry = listDetail.get(i);
			entry.setOwn_purchase_parentId(purchase_id);
			ownHeadDao.saveOwnEntry(entry);
		}
		User user = (User) request.getSession().getAttribute("loginUser");

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applyUserId",user.getUserid()+"");
		String processDefinitioKey = "ownHeadView"; // 定义流程的key,不可修改
		String bizId = processDefinitioKey + "." + purchase_id; // 获取业务id
	ProcessInstance pi=runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId,vars);
	
	   DataProcinst dataProcinst=new  DataProcinst();
		dataProcinst.setProc_inst_id_(pi.getId());
		dataProcinst.setApplicant(user.getUsername());
		dataProcinst.setIllustrate("单据编号/"+ownHead.getOwn_purchase_id());
	    activitiDao.insertDataProcinst(dataProcinst);

	}

	@Override
	public Map<String, Object> findOwnHead(String fields, String where) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);
		return ownHeadDao.findOwnHead(map);
	}

	@Override
	public List<Map<String, Object>> findOwnEntry(String fields, String where) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);

		return ownHeadDao.findOwnEntry(map);
	}

	@Override
	@Transactional
	public Map<String, Object> ownHeadPass(OwnPurchaseHead head,
			HttpServletRequest request) {
		
		
	  try {
		String taskid = request.getParameter("taskid");
		String reason = request.getParameter("reason");//审核意见
		User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
		String userid = user.getUserid() + ""; // 当前审核人
		
		String nextUser = request.getParameter("userid");// 下一个审核人
		String type=request.getParameter("type");//是否驳回参数
		System.out.println(head.getOwn_purchase_id());

		Map<String, Object> vars = new HashMap<String, Object>();// 流程参数
		if (!type.isEmpty()) {
			vars.put("type", type);
		}
		   activitiService.comment(taskid,userid,reason); //添加审核记录
           taskService.complete(taskid,vars); //完成任务
   		String content="采购单号："+head.getOwn_purchase_id(); //请假内容
   		       content+="<br>计划员："+head.getOwn_purchase_planMan();
   		       content+="<br>建单时间："+head.getOwn_purchase_time();
		    String theme="普通采购!"; //邮件标题	
		 activitiService.sendEmail(theme, content, userid);
	} catch (Exception e) {
		e.printStackTrace();
	 }
		return null;
	}
    @Transactional
	public void deleteEntry(String where) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("where", where);
		ownHeadDao.deleteEntry(map);

	}
    
	//流程定义类方法
    public  String  ownHeaNextUser(DelegateExecution execution) {
  	  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  		String nextUser = request.getParameter("userid");// 下一个审核人	
  		return nextUser;		
  	
      }

	@Override
	@Transactional
	public void updateHeadStatus(DelegateExecution execution, String status) {
	String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
		OwnPurchaseHead ownHead=new OwnPurchaseHead();
		ownHead.setOwn_purchase_id(Integer.valueOf(bizId));
		ownHead.setOwn_purchase_status(Integer.valueOf(status));
		ownHeadDao.updateOwnHead(ownHead);

	}

	@Override
	public void updateOwnPurchaseEntry(OwnPurchaseEntry entry) {
		ownHeadDao.updateOwnEntry(entry);
    
	}
   
}
