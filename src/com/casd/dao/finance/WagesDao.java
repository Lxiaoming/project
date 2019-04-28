package com.casd.dao.finance;

import java.util.Map;

import com.casd.entity.finance.Base_wages;
import com.casd.entity.finance.Wages;

public interface WagesDao {

	void save_userWages(Wages wages);

	Map<String, Object> base_Wages(int userid);

	void submitWages(Base_wages base_wages);

	void dele_userWages(int finance_wages_id);
	
	void updateUc_wage(Base_wages base_wages);
	
	void updateUcwages(Map<String, Object> param);


}
