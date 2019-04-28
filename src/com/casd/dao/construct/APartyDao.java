package com.casd.dao.construct;

import java.util.List;
import java.util.Map;

import com.casd.entity.construct.ApartyMaterial;
import com.casd.entity.construct.ApartyPur;
import com.casd.entity.construct.ApartyPurEntry;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.ConstructDep;
import com.casd.entity.construct.ProSch;

public interface  APartyDao {

	int getCount(Map<String, Object> param);

	List<Map<String, Object>> getList(Map<String, Object> param);

	List<Map<String, Object>> getData(Map<String, Object> param);

	void save_aPartyMaterial(ApartyMaterial apartyMaterial);

	void delete_aPartyMaterial(Map<String, Object> map);

	void save_aPartyPurHead(ApartyPur apartyPur);

	void save_aPartyPurEntry(ApartyPurEntry apartyPurEntry);

	void dele_apartyPur(Map<String, Object> map);

	void delete_aPartyEntry(Map<String, Object> map);
	
	void updateApartyPur(ApartyPur apartyPur);
	
	Map<String, Object> getMap(Map<String, Object> map);

	void update_apartyPur(Map<String, Object> whereMap);
	
	void update_aPartyMaterial(ApartyMaterial apartyMaterial);


}


