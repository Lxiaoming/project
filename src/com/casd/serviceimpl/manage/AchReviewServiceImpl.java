package com.casd.serviceimpl.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.manage.AchReviewDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.manage.AchReview;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.AchReviewService;
import com.casd.service.uc.UserService;

@Service
public class AchReviewServiceImpl implements AchReviewService {
	@Autowired
	private AchReviewDao achReviewDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService  taskService;
	
	@Autowired
	private  ActivitiDao activitiDao;


	@Override
	public List<Map<String, Object>> achReviewList(int page, int pageSize,
			Ref<Integer> record, String fields, String where) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);

		if (record != null) {
			Integer count = achReviewDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		//if (pageSize <= 0)

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return achReviewDao.getList(param);
	}

	 @Transactional
	public void save_achReview(AchReview achReview,String userid,String username) throws Exception {
		
		achReviewDao.save_achReview(achReview);
		String processDefinitioKey = "achReviewView"; // 定义流程的key,不可修改
		String bizId = processDefinitioKey + "."
				+ achReview.getAchReview_id(); // 获取业务id
		 ProcessInstance pi=activitiService.startProcesses(bizId, userid, processDefinitioKey,null);
		 
		   DataProcinst dataProcinst=new  DataProcinst();
					dataProcinst.setProc_inst_id_(pi.getId());
					dataProcinst.setApplicant(username);
					dataProcinst.setIllustrate(achReview.getAchReview_company());
				    activitiDao.insertDataProcinst(dataProcinst);
		 
	}

	@Override
	public List<Map<String, Object>> getData(Map<String, Object> map) {
		Map<String, Object> daoMap=new HashMap<String, Object>();
		daoMap.put("fields", "*");
		daoMap.put("where", " manage_achreview where achReview_id="+map.get("achReview_id")+"");
		return achReviewDao.getData(daoMap);
	}
	
     @Transactional
	public void delete_data(Map<String, Object> map) {
	
		achReviewDao.delete_data(map);
	}
     
     public void sava_ReviewAut(HttpServletRequest request) throws Exception{
    	 
    		Map<String, Object> vars=new HashMap<String, Object>();
    		AchReview achReview=new AchReview();
    		 String taskid=request.getParameter("taskid");
    		 String taskName=request.getParameter("taskName");
    		 
    		int achReview_id=Integer.parseInt(request.getParameter("achReview_id"));
    		achReview.setAchReview_id(achReview_id);
    		achReview.setAchReview_comOpinion(request.getParameter("achReview_comOpinion"));
    		achReview.setAchReview_company(request.getParameter("achReview_company"));
    		achReview.setAchReview_dirOpinion(request.getParameter("achReview_dirOpinion"));
    		achReview.setAchReview_month(request.getParameter("achReview_month"));
    		achReview.setAchReview_supCenter(request.getParameter("achReview_supCenter"));
    	
    	    String date2= request.getParameter("achReview_creatDate");
            String str = date2;  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
            java.util.Date d = null;  
          
                try {
   				d = format.parse(str);
   			} catch (ParseException e) {
   			
   				e.printStackTrace();
   			}  
            java.sql.Date date = new java.sql.Date(d.getTime()); 
            achReview.setAchReview_creatDate(date);
            
        	User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
    		String username=user.getUserid()+"";
    		  String nextUser=null;
            if (taskName.equals("绩效考核申请")) {
              	 Map<String, Object> map=new HashMap<String, Object>();
               	String  fields="a.userid"; 
               	String  where="uc_user a JOIN uc_role b on a.role_id=b.role_id WHERE b.role_name='董事长'"; 
               	map.put("fields", fields);
               	map.put("where", where);
                   Map<String, Object> userlist=userDao.queryUserById(map);
                    nextUser=userlist.get("userid").toString();
           			vars.put("department", nextUser);		
           			taskService.complete(taskid,vars);
           	}else{         
           		   taskService.complete(taskid,vars);
           	}
        	       achReviewDao.save_achReview(achReview);
        	  
        	  String theme="绩效考评";
        	  String content="你有一张绩效考评单据需要审核";
        	  activitiService.sendEmail(theme, content, nextUser);
    	 
     }

}
