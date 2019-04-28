package com.casd.dao.ownCenter;

import java.util.List;
import java.util.Map;

import com.casd.entity.ownCenter.Leave;

public interface  LeaveDao {

	int saveLeave(Leave leave);


	Integer getLeaveCount(Map<String, Object> param);

	List<Map<String, Object>> leaveList(Map<String, Object> param);

	Leave findLeaveByID(Map<String, Object> map);

	void updateLeave(Leave leave);

	void deleLeave(Map<String, Object> map);

	int selectStatus(Map<String, Object> map);

	void updateStatus(Map<String, Object> map);
	
}
