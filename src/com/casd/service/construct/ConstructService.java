package com.casd.service.construct;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.casd.controller.web.Ref;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.ConstructDep;

public interface ConstructService {

	List<Map<String, Object>> constructDepList(int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	List<Map<String, Object>> constructList(int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	Map<String, Object> getConstructById(Map<String, Object> map);
	

	ConstructDep findDepById(String where);
	
    Map<String,Object> updatectDep(ConstructDep dep) throws Exception;
    
    
	Boolean isExist(Map<String, Object>params);

	void delete_Dep(String where);

	JSONObject saveConstruct(Construct construct, JSONArray myJsonArray);

	void delePro(Map<String, Object> map);

	void delete_WorkTeam(String string);

	Map<String, Object> getSupplierMod(String string);


}
