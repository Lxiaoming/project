package com.casd.serviceimpl.construct;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casd.dao.construct.FinalPurchaseDao;
import com.casd.service.construct.FirmLaborCostService;

@Service
public class FirmLaborCostServiceImpl implements FirmLaborCostService {

	@Autowired
	private FinalPurchaseDao finalPurchaseDao;

	@Override
	public List<Map<String, Object>> queryFirmLaborCosts(Map<String, Object> param) {
		
		finalPurchaseDao.finalPurchaseList(param);
		return null;
	}

}
