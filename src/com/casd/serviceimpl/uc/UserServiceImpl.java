package com.casd.serviceimpl.uc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



















import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.uc.CenterDao;
import com.casd.dao.uc.CompanyDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.uc.User;
import com.casd.service.uc.UserService;




@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserDao userDao;
	
	
	/**
	 * 员工列表
	 * */
	@Transactional     
	public List<Map<String, Object>> userList(int page,	int pageSize,Ref<Integer> record,String  where,String fields) throws Exception {
	   Map<String, Object> param=new HashMap<String, Object>();
         param.put("fields", fields);
         param.put("where", where);
       
		   if (record != null) {
				Integer count=userDao.geUsertCount(param);
				record.setValue(count);
			}
		   
		 //分页
		   
		   if (pageSize <= 0)
				throw new Exception("pageSize 必须 > 0");

		          param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
					* pageSize, pageSize));
	   
		return userDao.userList(param);
		
	}
	

	public  User login(Map<String, Object> map) {
		
		
	return userDao.login(map);
			
	}


	@Override
	public int insertUser(User user) throws Exception {

		return userDao.insertUser(user);
	}


	@Override
	@Transactional
	public int updateUser(String pars,String where) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("pars", pars);
		params.put("where", where);
		
		return userDao.updateUser(params);
	}


	@Override
	public Map<String, Object> queryUserById(String fields,String where) throws Exception{		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("fields", fields);
		params.put("where", where);
		return (Map<String,Object>) userDao.queryUserById(params);
	}


	@Override
	public void deleteUser(String fields,String where) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("where", where);
		params.put("fields", fields);
		userDao.deleteUser(params);
		
	}


	@Override
	public int insertfile(User user) throws Exception {
	    

		return userDao.informationUser(user); 
	
		
	}


	@Override
	public List<Map<String, Object>> queryId(String fields,String where){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("where", where);
		params.put("fields", fields);
		return userDao.queryId(params);
	}


	@Override
	@Transactional
	public List<Map<String, Object>> companyList(int page,int pageSize,Ref<Integer> record,String fields,String  where) throws Exception {
		 Map<String, Object> param=new HashMap<String, Object>();
       
		 param.put("fields", fields);
         param.put("where", where);

		   if (record != null) {
				Integer count=companyDao.getCompanyCount(param);
				record.setValue(count);
			}
		   //分页
		   if (pageSize <= 0)
				throw new Exception("pageSize 必须 > 0");

		          param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
					* pageSize, pageSize));

		return companyDao.companyList(param);
	}


	@Override
	public Map<String, Object> attendTotal(Map<String, Object> getDataMap) {
		// TODO Auto-generated method stub
		
		return userDao.attendTotal(getDataMap);
		
	}

	



   
}
