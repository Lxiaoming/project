package com.casd.serviceimpl.manage;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.manage.NoticeDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.manage.Notice;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.NoticeService;
import com.casd.service.uc.UserService;
@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
    private NoticeDao noticeDao;
	@Autowired
    private ActivitiService activitiService;
	@Autowired
	private TaskService  taskService;
	@Autowired
	private UserService  userService;
	@Autowired
	private  ActivitiDao activitiDao;
	
	@Autowired
	private RuntimeService  runtimeService;
	
	
	@Transactional
	public int savaNotice(HttpServletRequest request) throws Exception{
		
		String cid=request.getParameter("bizId");
		
		
		if(cid==null){
					
	
		String delIds=request.getParameter("delIds");// 公司集合
		String content=request.getParameter("content");// 发文内容		
		User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
		String	userid=user.getUserid()+"";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	    Notice notice=new Notice();
	    notice.setNotice_content(content);
	    notice.setCompany_id(delIds);
	    notice.setStatus(1);
	    notice.setStart_time(df.format(new java.util.Date()));
	    notice.setUser_id(user.getUserid()+"");
	  
		noticeDao.savaNotice(notice);


		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applyUserId",user.getUserid()+"");
	   String processDefinitioKey = "notice_view"; // 定义流程的key,不可修改
			String bizId = processDefinitioKey + "."
					+ notice.getNotice_id(); // 获取业务id
		
   ProcessInstance pi=runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId,vars);

	DataProcinst dataProcinst=new  DataProcinst();
	dataProcinst.setProc_inst_id_(pi.getId());
	dataProcinst.setApplicant(user.getUsername());
	dataProcinst.setIllustrate("通知公告");
    activitiDao.insertDataProcinst(dataProcinst);
		 
		  }
		 
			
			return 0;
	}

	@Override
	public List<Map<String, Object>> noticeList(int page, int pageSize,
			Ref<Integer> record, String fields,String where) throws Exception {
		
		
		
		Map<String, Object> param=new HashMap<String, Object>();
        param.put("fields", fields);
        param.put("where", where);
      
		   if (record != null) {
				Integer count=noticeDao.getNoticeCount(param);
				record.setValue(count);
			}
		   
		 //分页
		   
		   if (pageSize <= 0)
				throw new Exception("pageSize 必须 > 0");

		          param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
					* pageSize, pageSize));
		return noticeDao.noticeList(param);
	}

	@Transactional
	public void notice_pass(HttpServletRequest request) throws Exception{
		  String taskid=request.getParameter("taskid");
		   String nextUser=request.getParameter("userid");//下一个审核人
		    taskService.complete(taskid);
		    String content="您有一张公告需要审批!"; //请假内容
			String theme="公告/通知!"; //邮件标题
		    activitiService.sendEmail(theme, content, nextUser);
		    
		
	}

	@Transactional
	public void updete(Notice notice) {
		noticeDao.updateNotice(notice);		
		
	}
	
	@Override
	@Transactional
	public void updateNotStatus(DelegateExecution execution, String status) {
	String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
		Notice notice =new Notice();
		notice.setNotice_id(Integer.valueOf(bizId));
		notice.setStatus(Integer.valueOf(status));
		noticeDao.updateNotice(notice);		
	}

	@Override
	public void delete(String where) {
	  Map<String, Object> map=new HashMap<>();
	 
	  map.put("where", where);
	  noticeDao.deleteNotice(map);
		
	}
	
	
}
