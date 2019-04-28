package com.casd.dao.ownCenter;

import java.util.List;
import java.util.Map;

import com.casd.entity.ownCenter.OwnWorkEntry;
import com.casd.entity.ownCenter.OwnWorkHead;
import com.casd.entity.ownCenter.Seal;
import com.casd.entity.sciAndTec.CheckPro;

public interface OwnWorkDao {

	void save_workArrang(OwnWorkHead ownWorkHead);

	void save_workArrangEntry(OwnWorkEntry ownWorkEntry);

	int getOwnWorkCount(Map<String, Object> param);

	List<Map<String, Object>> ownWorkList(Map<String, Object> param);

	OwnWorkHead getOwnWorkHead(Map<String, Object> map);

	List<Map<String, Object>> getOwnWorkEntry(Map<String, Object> map);

	void update_work(OwnWorkEntry ownWorkEntry);


	void delete_worker(Map<String, Object> map);

	int getCompany(Map<String, Object> map);

	int selectArrCount(Map<String, Object> map);


}
