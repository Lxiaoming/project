package com.casd.dao.construct;

import java.util.List;
import java.util.Map;

import com.casd.entity.construct.Construct;
import com.casd.entity.construct.ConstructDep;
import com.casd.entity.construct.ConstructWorkTeam;

public interface  ConstructDepDao {

	int getDepCount(Map<String, Object> param);

	List<Map<String, Object>> constructDepList(Map<String, Object> param);

	int getConstructCount(Map<String, Object> param);

	List<Map<String, Object>> constructList(Map<String, Object> param);

	Construct getConstructById(Map<String, Object> map);
	
	ConstructDep cptDepById(Map<String, Object> map);
	
	void updatectDep(ConstructDep dep) throws Exception;;
	

	ConstructDep isExist(Map<String, Object> param);
	
	 void  delete_Dep(Map<String, Object> map);

	void saveConstruct(Construct construct);

	void delePro(Map<String, Object> map);

	void saveWorkTeam(ConstructWorkTeam constructWorkTeam);

	List<Map<String, Object>> getConstructWorkTeam(Map<String, Object> map);

	void delete_WorkTeam(Map<String, Object> map);

	boolean existCon(int construct_project_contractId);

	Map<String, Object> getSupplierMod(Map<String, Object> map);


}
