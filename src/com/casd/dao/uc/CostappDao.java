package com.casd.dao.uc;

import java.util.List;
import java.util.Map;

import com.casd.entity.uc.Costapp;

public interface CostappDao {

	Integer getCount(Map<String, Object> param);

	List<Map<String, Object>> getList(Map<String, Object> param);


	List<Map<String, Object>> getData(Map<String, Object> daoMap);

	void delete_data(Map<String, Object> map);

	void save_costapp(Costapp costapp);
	
	void updateCostapp(Costapp costapp);
	
    Map<String, Object> findCostapp(Map<String, Object> map);

	void updateAuditor(Map<String, Object> map);

}