package com.casd.service.hr;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;




public interface ActivitiService {
	
	   //部署流程
		void deploymentProcessDefinition_zip() throws FileNotFoundException;
		
		//启动流程
	    public ProcessInstance startProcesses(String bizId,String name,String processDefinitioKey,String title) throws Exception;
	    
	     //获取业务id
	    public String getBusinessBizId(String taskId);
	    
	    //获取连线
	    public List<String> findOutComeListByTaskId(String taskId);
	    
	    //使用流程实例id查询审批记录
	    public List<Comment> findCommentBytaskId(String processInstanceId);
	    
	  //使用任务id查询审批记录
	    public List<Map<String, Object>> getProcessComments(String taskId) throws Exception;
	    
	   //使用单据id查询审批记录
	    public List<Map<String, Object>> viewHisComments(String bizId,String beyId) throws Exception;
	    
	    //使用单据id查询审批记录
	    public List<Comment> viewHisComments1(String bizId,String beyId) throws Exception;
	    
    
	    public List<TaskDefinition> nextTaskDefinition(String procInstId);
	    
	    public List<Map<String, Object>> backTaskTab(String taskId) throws Exception;
	    
	    
	    public Comment  comment(String taskId,String userid,String comment);
	    
	    public ActivityImpl usertask(String taskId) throws Exception;

		Map<String, Object> deleteprocessInstance(String bizId, String beyId);
	    
	   public  Map<String, Object> sendEmail(String theme,String content,String nextUser)throws Exception;
	    
	   public Map<String, Object> deleteRecord(String bizId, String beyId) throws Exception;   
	   
	   public Map<String, Object> rejectTaskTo(String taskId,String activityId) throws Exception;
	   
	   
	   public List<?>  getActivityImpl(String taskid);
	   
	   public Object  getRenderedStartForm(String pdfId);

	   public Object getRenderedTaskForm(String taskid);

	
	    
	    
}
