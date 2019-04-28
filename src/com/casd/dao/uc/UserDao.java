package com.casd.dao.uc;

import java.util.List;
import java.util.Map;

import com.casd.entity.uc.User;

public interface UserDao {

	public List<Map<String, Object>> userList(Map<String, Object> param)  throws Exception;
	
	public	User login(Map<String, Object> map);
	
	public	int geUsertCount(Map<String, Object> param);
	
	public	int insertUser(User user) throws Exception;
	
	public	int updateUser(Map<String, Object> params) throws Exception;
	
	public	Map<String, Object> queryUserById(Map<String, Object> params);
	
	public void deleteUser(Map<String, Object> params) throws Exception;

	
	List<User> queryUser(Map<String, Object> param);
	
	public List<Map<String, Object>> queryId(Map<String, Object> param);
	public	int informationUser(User user) throws Exception;
	public void existence(User user)throws Exception;

	public Boolean existUser(String username);

	public Map<String, Object> attendTotal(Map<String, Object> getDataMap);

}
