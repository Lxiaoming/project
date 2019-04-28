package com.casd.service.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.manage.AchReview;
import com.casd.entity.ownCenter.Leave;

public interface AchReviewService {

	List<Map<String, Object>> achReviewList(int pageIndex, int pageSize,
			Ref<Integer> record, String fields, String string);

	void save_achReview(AchReview achReview,String userid, String username) throws Exception;

	List<Map<String, Object>> getData(Map<String, Object> map);

	void delete_data(Map<String, Object> map);
	
	 public void sava_ReviewAut(HttpServletRequest request) throws Exception;


		
	 
}
