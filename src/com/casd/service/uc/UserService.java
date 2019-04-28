package com.casd.service.uc;

import java.util.List;
import java.util.Map;

import com.casd.controller.web.Ref;
import com.casd.entity.uc.User;

public interface UserService {
	
	public List<Map<String, Object>> userList(int page,int pageSize,Ref<Integer> record,String  where,String fields)  throws Exception;
	
	public User login(Map<String, Object> map);
	
	public	int insertUser(User user) throws Exception;
	
	public	int updateUser(String pars,String where) throws Exception;
	
	public	Map<String, Object> queryUserById(String fields,String where) throws Exception;
	
	public void deleteUser(String fields,String where) throws Exception;
	
	public int	insertfile(User user)throws Exception;
	
	public List<Map<String, Object>> queryId(String fields,String where);
	
	public List<Map<String, Object>> companyList(int page,	int pageSize,Ref<Integer> record,String fields,String  where)  throws Exception;

	public Map<String, Object> attendTotal(Map<String, Object> getDataMap);

   
	
}
