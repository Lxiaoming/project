package com.casd.serviceimpl.ownCenter;

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
import com.casd.dao.ownCenter.SealDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.ownCenter.Seal;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.ownCenter.SealService;
import com.casd.service.uc.UserService;


@Service
public class SealServiceImpl implements SealService {
	@Autowired
	private SealDao sealDao;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private  RuntimeService runtimeService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private  ActivitiDao activitiDao;
	
	@Override
	@Transactional
	public List<Map<String, Object>> getData(String fields,String where) {
		
		Map<String, Object> daoMap=new HashMap<String, Object>();
		daoMap.put("fields",fields);
		daoMap.put("where",where);
		return sealDao.getData(daoMap);
	}
	
	
	@Transactional
	public void save_seal(Seal seal) {
		sealDao.save_seal(seal);
	}



	@Override
	@Transactional
	public void delete_data(Map<String, Object> map) {
		 sealDao.delete_data(map);
		
	}



	@Override
	@Transactional
	public List<Map<String, Object>> sealList(int page, int pageSize,
			Ref<Integer> record, String fields, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);

	
		if (record != null) {
			Integer count = sealDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		return sealDao.getList(param);
		
		
	
	}

  /**
   * 修改流程状态
   *  @param  execution  为流程设定参数
   *  @param status      流程设定状态
   * 
   * */
	@Override
	@Transactional
	public void updateSealProcess(DelegateExecution execution, String status) {
	    String bizkey= execution.getProcessBusinessKey();
		
			String[] strs=bizkey.split("\\.");
	        String bizId=null;
			for(int i=0,len=strs.length;i<len;i++){
				bizId=strs[i].toString();
			}
		
			Seal seal=new Seal();
			seal.setOwn_seal_id(Integer.valueOf(bizId));
			seal.setOwn_seal_status((Integer.valueOf(status)));
		
			
			sealDao.updateSeal(seal);
			
			
	}
	
	

    /**
     *  启动盖章流程
     * @throws Exception 
     *  
     * */
	@Override
	@Transactional
	public void start_seal(HttpServletRequest request, Seal seal) throws Exception {
		
		User user = (User) request.getSession().getAttribute("loginUser");
		String userid = user.getUserid()+"";// 获取申请人
		seal.setOwn_user_id(userid);
		sealDao.save_seal(seal);
		int own_seal_id=seal.getOwn_seal_id();	
	
		String processDefinitioKey ="sealView"; // 定义流程的key,不可修改
	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applyUserId",userid);// 设置第一个人审批人为申请人
	
		
	   String category=seal.getOwn_seal_chapCategory();
			if(category.equals("1")){
				vars.put("category", "1");
			}else if(category.indexOf('2') !=-1 || category.indexOf('3') !=-1 || category.indexOf('4') !=-1){
				if(category.indexOf('1') !=-1 || category.indexOf('5') !=-1){
					 throw new Exception("章类型不对，无法启动流程");
				}else{
					vars.put("category", "2");// 节点
				}
				
			}else if(category.equals("5")){
				vars.put("category", "3");// 节点
			}else {
				 throw new Exception("章类型不对，无法启动流程");
			}

	
	
				
			 String bizId = processDefinitioKey + "." + own_seal_id; //获取业务id
			 ProcessInstance pi=runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId, vars);
			   
		 	   DataProcinst dataProcinst=new  DataProcinst();
				dataProcinst.setProc_inst_id_(pi.getId());
				dataProcinst.setApplicant(user.getUsername());
				dataProcinst.setIllustrate(seal.getOwn_seal_fileName());
			    activitiDao.insertDataProcinst(dataProcinst);
		   
		   }


	@Override
	public Seal findSealById(String fields, String where) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);
		return sealDao.findSealById(param);
		
		
	}


	@Override
	@Transactional
	public void pass_seal(HttpServletRequest request,Seal seal) throws Exception {
		
		String taskid=request.getParameter("taskid");
		String type=request.getParameter("type");
		String reason=request.getParameter("reason");//获取审核意见
		
		User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
	    String userid=user.getUserid()+"";  //当前审核人id
    
	    Map<String, Object> vars=new HashMap<String, Object>();
		   String category=seal.getOwn_seal_chapCategory();
		   if(category.equals("1")){
				vars.put("category", "1");// 节点
			}else if(category.indexOf('2') !=-1 || category.indexOf('3') !=-1 || category.indexOf('4') !=-1){
				if(category.indexOf('1') !=-1 || category.indexOf('5') !=-1){
					 throw new Exception("章类型不对，无法启动流程");
				}else{
					vars.put("category", "2");// 节点
				}
			}else if(category.equals("5")){
				vars.put("category", "3");// 节点
			}else {
				 throw new Exception("章类型不对，无法启动流程");
			}
				if(type.equals("apply")){
					  this.save_seal(seal);
				}
				if(StringUtils.isNoneEmpty(type)){
					vars.put("type",type);// 节点
				}
		activitiService.comment(taskid,userid,reason);
		taskService.complete(taskid,vars);			
	  }


	@Override
	@Transactional
	public void updateSeal(Seal seal) {

       sealDao.updateSeal(seal);
		
	} 

	//流程定义类方法
	  public  String sealnextUser(DelegateExecution execution) {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String nextUser = request.getParameter("username");// 下一个审核人	
			return nextUser;		
		
	    }
}
