package com.casd.service.supplierMod;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.entity.supplierMod.Worker;
import com.casd.entity.supplierMod.WorkerApply;

public interface WorkerService {

	Boolean checkWorkerExist(String supplierMod_worker_userId);

	void save_Worker(Worker worker);

	void workerApply(WorkerApply workerApply);
	
	public void updateWorkerFlow(DelegateExecution execution, String status);
	
	Map<String, Object>  findWorker(String fields, String string);
	
	void workerApplyPass(HttpServletRequest request) throws Exception;

	int getUserByName(String construct_project_leader);

	void multiplayerApply(Map<String, Object> map);
	
	//流程定义类方法
    public  String  workerNextUser(DelegateExecution execution);

	void saveWorkerAtt(Map<String, Object> data);
	
	Map<String, Object> queryHrAttend(Map<String, Object> map);

	int updateHrAttend(Map<String, Object> map);	
	 
}
