package com.casd.serviceimpl.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casd.controller.web.Ref;
import com.casd.dao.finance.MaterialDao;
import com.casd.dao.finance.MaterialPriceDao;
import com.casd.entity.finance.MaterialPrice;
import com.casd.service.finance.MaterialPriceService;
import com.casd.service.finance.MaterialService;

@Service
public class MaterialPriceServiceImpl implements MaterialPriceService{

	@Autowired
	private MaterialPriceDao materialPriceDao;
	

	@Override
	public List<Map<String, Object>> materialPrice(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			Integer count = materialPriceDao.getMaterialPriceCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return materialPriceDao.materialPrice(param);
	}


	@Override
	public String insert(MaterialPrice mp) {
		String messageString="";
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" construct_material_price  where construct_material_modelId="+mp.getConstruct_material_modelId()+" and construct_material_supplierId="+mp.getConstruct_material_supplierId()+"");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("where", sBuffer);
		Integer materialPriceCount = materialPriceDao.getMaterialPriceCount(map);
		if(materialPriceCount==0 || mp.getConstruct_material_priceId()!=0){
			materialPriceDao.addPrice(mp);
		}else {
			messageString="此材料已存在，不可选择";
		}
		return messageString;
	}


	@Override
	public void delete(String where) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("where", where);
		materialPriceDao.deletePrice(map);
		
		
		
	}


	@Override
	public List<Map<String, Object>> exis_repetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return materialPriceDao.exis_repetition(map);
	}

}
