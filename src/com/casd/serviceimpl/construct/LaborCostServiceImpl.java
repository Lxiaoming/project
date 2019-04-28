package com.casd.serviceimpl.construct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casd.controller.web.Ref;
import com.casd.dao.construct.LaborCostDao;
import com.casd.service.construct.LaborCostService;
import com.casd.service.uc.UserService;

@Service
public class LaborCostServiceImpl implements LaborCostService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private LaborCostDao laborCostDao;
	
	@Override
	public List<Map<String, Object>> getList(int page, int pageSize,
			Ref<Integer> record,String fields, String where) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);
		if (record != null) {
			Map<String, Object> param2=new HashMap<String, Object>();
			param2.put("fields", "us.userid");
			List<Map<String, Object>> list=	userService.queryId(fields, where);
			record.setValue(list.size());
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return laborCostDao.getList(param);
	}


	
	
	

}
