package com.casd.serviceimpl.finance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.dao.finance.MaterialDao;
import com.casd.dao.finance.MaterialPriceDao;
import com.casd.dao.finance.WagesDao;
import com.casd.entity.construct.PurChangeEmtry;
import com.casd.entity.finance.Base_wages;
import com.casd.entity.finance.Material;
import com.casd.entity.finance.MaterialModel;
import com.casd.entity.finance.MaterialPrice;
import com.casd.entity.finance.MaterialSeries;
import com.casd.entity.finance.Wages;
import com.casd.service.finance.MaterialService;
import com.casd.service.finance.WagesService;

@Service
public class WagesServiceImpl implements WagesService{

	@Autowired
	private WagesDao wagesDao;


	@Override
	@Transactional
	public void save_userWages(Wages wages) {

		Calendar c1 = Calendar.getInstance();
		// 获得年份
		int year = c1.get(Calendar.YEAR);
		// 获得月份
		int month = c1.get(Calendar.MONTH);
		String monString=String.valueOf(month);
		  if (month<10) {
			monString="0"+month;
		   }
	   wages.setUc_wage_yearMon(""+year+"-"+monString+"");
		   wagesDao.save_userWages(wages);
		   String where="uc_wage SET uc_wage_status=1 where uc_wage_userId="+wages.getUc_wage_userid();
			this.updateUcwages(where);
	
	
	}

	@Override
	public Map<String, Object> base_Wages(int userid) {

		return wagesDao.base_Wages(userid);
	}

	@Override
	public void submitWages(Base_wages base_wages) {

        if(base_wages.getUc_wage_id()==0){
        	wagesDao.submitWages(base_wages);
        }else {
        	wagesDao.updateUc_wage(base_wages);
		}
		
	}

	@Override
	public void dele_userWages(int finance_wages_id) {
		
		wagesDao.dele_userWages(finance_wages_id);
	}

	@Override
	public void updateUcwages(String where) {
		Map<String, Object> param=new HashMap<String, Object>();
		  param.put("where", where);
		wagesDao.updateUcwages(param);
		
	}
	

}
