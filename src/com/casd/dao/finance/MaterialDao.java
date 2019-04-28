package com.casd.dao.finance;

import java.util.List;
import java.util.Map;

import com.casd.entity.finance.Material;
import com.casd.entity.finance.MaterialModel;
import com.casd.entity.finance.MaterialPrice;
import com.casd.entity.finance.MaterialSeries;



public interface MaterialDao {

	Integer getMaterialCount(Map<String, Object> param);

	List<Map<String, Object>> materialList(Map<String, Object> param);
	
	int addMaterial(Material material);
	
	public List<Map<String, Object>> getMaterialById(Map<String, Object>params);
	
	Integer getCountModel(Map<String, Object> param);
	
	void addMaterialModel(MaterialModel materialModel);
	
	void deleteMateria(Map<String, Object> param);
	
	void deletemultiple(Map<String, Object> map);

	void saveSeries(MaterialSeries materialSeries);

	List<Map<String, Object>> selectData(Map<String, Object> map);

	void delete_Series(Map<String, Object> map);

	void insertData(Map<String, Object> map);

	int isExistQua(String string);

}
