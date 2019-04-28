package com.casd.service.manage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.manage.AchReview;
import com.casd.entity.manage.SupOpinion;
import com.casd.entity.ownCenter.Leave;

public interface SupOpinionService {

	List<Map<String, Object>> supOpinionList(int pageIndex, int pageSize,
			Ref<Integer> record, String fields, String string) throws Exception;

	void save_supOpinion(SupOpinion supOpinion,String userid,String username) throws Exception;

	List<Map<String, Object>> getData(Map<String, Object> map);

	void delete_data(Map<String, Object> map);
	
	public List<Map<String, Object>> supOpinionUser(String taskName,String description);
	
    public void sava_opinionAut(HttpServletRequest request) throws IOException;

	 
}
