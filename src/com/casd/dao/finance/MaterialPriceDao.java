package com.casd.dao.finance;

import java.util.List;
import java.util.Map;

import com.casd.entity.finance.Construct_change_price;
import com.casd.entity.finance.MaterialPrice;

public interface MaterialPriceDao {


	Integer getMaterialPriceCount(Map<String, Object> param);

	List<Map<String, Object>> materialPrice(Map<String, Object> param);
	
	void addPrice(MaterialPrice mp);
	
	void deletePrice (Map<String, Object> map);
	
	void add_NewPrice(Construct_change_price cch);
	
	 void update_price(Map<String, Object> map);
	 
	 void  deleteNewPrice(Map<String, Object> map);

	List<Map<String, Object>> exis_repetition(Map<String, Object> map);

}
