package com.casd.serviceimpl.manage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.manage.ContractapproveDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.manage.Contractapprove;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.ContractapproveService;

@Service
public class ContractapproveServiceImpl implements ContractapproveService {
	@Autowired
	private ContractapproveDao contractapproveDao;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TaskService  taskService;
	@Autowired
	private  ActivitiDao activitiDao;
	@Autowired
	private  RuntimeService runtimeService;

	@Override
	@Transactional
	public List<Map<String, Object>> contractapproveList(int page, int pageSize,
			Ref<Integer> record, String fields, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);

		if (record != null) {
			Integer count = contractapproveDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");
		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return contractapproveDao.getList(param);
		
	}

	@Override
	@Transactional
	public void saveContractapprove(Contractapprove contractapprove,HttpServletRequest request) throws Exception {
			Integer cid=contractapprove.getManage_contractapprove_id();
			User user = (User) request.getSession().getAttribute("loginUser");
			String userid = user.getUserid() + "";// 获取申请人
			Map<String, Object> vars=new HashMap<>();
		String category=request.getParameter("category");
			if (cid==null) {
			  
				contractapproveDao.saveContractapprove(contractapprove);
				
				    vars.put("category", category);
				    vars.put("applyUserId", userid);
					String processDefinitioKey = "contractapproveView"; // 定义流程的key,不可修改
					String bizId = processDefinitioKey + "."
							+ contractapprove.getManage_contractapprove_id(); // 获取业务id
			 ProcessInstance pi=runtimeService.startProcessInstanceByKey(
								processDefinitioKey, bizId, vars);
			 
		       DataProcinst dataProcinst=new  DataProcinst();
				dataProcinst.setProc_inst_id_(pi.getId());
				dataProcinst.setApplicant(user.getUsername());
				dataProcinst.setIllustrate(contractapprove.getManage_contractapprove_name());
			    activitiDao.insertDataProcinst(dataProcinst);
	       
			}else {
				contractapproveDao.saveContractapprove(contractapprove);
			}
			


	}


	@Override
	@Transactional
	public Map<String, Object> getData(String manage_contractapprove_id) {

		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> dataMap=new HashMap<String, Object>();

		
		String fieds ="*";
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" manage_contractapprove  where manage_contractapprove_id="+manage_contractapprove_id+" ");
		map.put("fields", fieds);
		map.put("where", sBuffer);
		dataMap=  contractapproveDao.getContractapprove(map);
	
		sBuffer.delete(0, sBuffer.length()-1);
		map.put("where", sBuffer);
		return dataMap;
	}

	@Override
	@Transactional
	public void delete_Contractapprove(String bizId) {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" manage_contractapprove where manage_contractapprove_id = "+bizId);
		map.put("what",sBuffer.toString()); 
		
		String beyId="contractapproveView";
		
	    activitiService.deleteprocessInstance(bizId, beyId);
		contractapproveDao.delete_Contractapprove(map);
		
	}
	@Override
	@Transactional
	public Map<String, Object> uploadFile(MultipartFile file,HttpServletRequest request) {
  
		Map<String, Object> json = new HashMap<String, Object>();
		String bizId=request.getParameter("bizId");
		 try {
	            // 获取原始文件名  
	            String fileName = file.getOriginalFilename();  
		
				if(StringUtils.isEmpty(fileName)){
					json.put("Success", false);
					json.put("Msg", "请选择要导的文件");
					return json;
				}
							       
	        	File file2=new File("e:/uploadFile/file");	
	            if(!file2.exists()) {  
	            	file2.mkdirs();  
	            }  
	            //定义文件路径
	            String fileUploadBasePath = "e:/uploadFile/file/";
	            String timeStr = System.currentTimeMillis() + fileName;
	            String newFilePath = fileUploadBasePath + timeStr;
	            File newFile = new File(newFilePath);
	            file.transferTo(newFile);  // 写入文件到服务器目录
	            System.out.println(timeStr);
	            Map<String, Object> uploadVar=new HashMap<String, Object>();
	            uploadVar.put("filds", timeStr);
	            uploadVar.put("billID", bizId);
	            contractapproveDao.updatefile(uploadVar);

			json.put("Success", true);
			json.put("Msg", "上传成功,可点击查看文件是否正确");
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    json.put("Success", false);
			json.put("Msg", "上传失败"+e);
		}
	    return json;

	
	}


	public Map<String, Object> findContractapprove(String fields, String where) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);
		
		return contractapproveDao.getContractapprove(map);
	}

	@Override
	public void add_Contractapprove(String manage_contractapprove_id) {
		// TODO Auto-generated method stub
		
	}
   
	


     /***修改单据状态
      * 
      */
	@Override
	@Transactional
	public void updateCttp(DelegateExecution execution, String status) {
		
      String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
		Contractapprove cttp=new Contractapprove();
		cttp.setManage_contractapprove_id(Integer.valueOf(bizId));
		cttp.setManage_status(Integer.valueOf(status));
	    contractapproveDao.updateCtp(cttp);
		
	}

	@Transactional
	public void contractapprovePass(HttpServletRequest request,Contractapprove contractapprove) throws Exception {
	
		
		Map<String, Object> vars=new HashMap<String, Object>();
		String taskid=request.getParameter("taskid");
	    String taskName=request.getParameter("taskName");
	    User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
	    String username=user.getUserid()+"";
	 	String reason=request.getParameter("reason");//获取审核意见
	 	String type=request.getParameter("type");//获取审核标识
	 	String category=request.getParameter("category");//获取合同类型
		if(taskName.equals("申请人")){

		contractapproveDao.saveContractapprove(contractapprove);
			 vars.put("category", category);
		 }
        if (StringUtils.isNotEmpty(type)) {
		     vars.put("type", type);
	    }
	    
		 
		     
	     activitiService.comment(taskid,username,reason);
		 taskService.complete(taskid,vars);
		/*String theme="合同审核";
		String content="您有一张合同审核单需要审批";
		activitiService.sendEmail(theme, content, nextUser);*/
		
		
	}

	  //流程定义类方法
	    public  String contractUser(DelegateExecution execution) {
	  	  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	  		String nextUser = request.getParameter("username");// 下一个审核人	
	  		return nextUser;		
	  	
	      }


	



	
	
}
