package com.casd.serviceimpl.ownCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.casd.dao.ownCenter.OwnGoalDao;
import com.casd.dao.ownCenter.SealDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.ownCenter.OwnGoal;
import com.casd.entity.ownCenter.Seal;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.ownCenter.OwnGoalService;
import com.casd.service.ownCenter.SealService;
import com.casd.service.uc.UserService;


@Service
public class OwnGoalServiceImpl implements OwnGoalService {
	
	@Autowired
	private  OwnGoalDao ownGoalDao;
	
	@Override
	public List<Map<String, Object>> ownGoalList(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", " * ");
		param.put("where", where);

		if (record != null) {
			Integer count = ownGoalDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));
		return ownGoalDao.getOwnGoalList(param);
	}

	@Override
	public void saveConstruct(OwnGoal ownGoal) {
		// TODO Auto-generated method stub
		
		ownGoalDao.saveConstruct(ownGoal);
	}

	@Override
	public void finishOwnGoal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ownGoalDao.finishOwnGoal(map);
		
	}

}
