package com.casd.serviceimpl.finance;

import java.util.ArrayList;
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
import com.casd.entity.finance.Material;
import com.casd.entity.finance.MaterialModel;
import com.casd.entity.finance.MaterialPrice;
import com.casd.entity.finance.MaterialSeries;
import com.casd.service.finance.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService{

	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private MaterialPriceDao materialPriceDao;
	
	@Override
	public List<Map<String, Object>> materialList(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			Integer count = materialDao.getMaterialCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return materialDao.materialList(param);
	}

	@Override
	@Transactional
	public void addMaterial(Material material) {
		
		int construct_material_id = material.getConstruct_material_id();
		if(construct_material_id!=0){
			materialDao.addMaterial(material);
		}else{
			HashMap<String, Object>  numMap=new HashMap<String, Object>();
			numMap.put("fields", "construct_material_num");
			numMap.put("where", " construct_material_table where  construct_material_seriesId="+material.getConstruct_material_seriesId()+" ORDER BY construct_material_num DESC LIMIT 1");
			List<Map<String, Object>> numData = materialDao.selectData(numMap);
			if (numData.size()==0) {
				material.setConstruct_material_num(100);
			}else {
				material.setConstruct_material_num(Integer.valueOf(numData.get(0).get("construct_material_num").toString())+1);
			}
			materialDao.addMaterial(material);
		}
	        
	}

	@Override
	public List<Map<String, Object>> getMaterialById(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception {
	    
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			Integer count = materialDao.getCountModel(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return materialDao.getMaterialById(param);
	}
    
	@Override
	@Transactional
	public Map<String, Object> editmaterial(MaterialModel materialModel) {
		int modelId=materialModel.getConstruct_material_model_id();
		int construct_material_model_parentid = materialModel.getConstruct_material_model_parentid();
		if(modelId!=0){
			materialDao.addMaterialModel(materialModel);
		}else {
			
			HashMap<String, Object>  numMap=new HashMap<String, Object>();
			numMap.put("fields", " construct_materail_model_num"); 
			numMap.put("where", "  construct_material_model where construct_material_model_parentid="+construct_material_model_parentid+" ORDER BY construct_materail_model_num DESC LIMIT 1");
			List<Map<String, Object>> numData = materialDao.selectData(numMap);
			if (numData.size()==0) {
				materialModel.setConstruct_materail_model_num(100);
			}else {
				materialModel.setConstruct_materail_model_num(Integer.valueOf(numData.get(0).get("construct_materail_model_num").toString())+1);
			}
			
			materialDao.addMaterialModel(materialModel);
			//MaterialPrice price=new MaterialPrice();
			Map<String, Object> map=new HashMap<String, Object>();
			String fields="*";
			StringBuffer sBuffer=new StringBuffer();
			sBuffer.append(" construct_material_table material ");
			sBuffer.append(" LEFT JOIN construct_material_series series on material.construct_material_seriesId=series.construct_material_seriesID ");
			sBuffer.append(" where material.construct_material_id="+construct_material_model_parentid+" ");
			map.put("fields", fields);
			map.put("where", sBuffer);
			List<Map<String, Object>> construct=materialDao.selectData(map);
			String suppliers = construct.get(0).get("construct_material_seriesSuppliers").toString();
			
			fields="construct_supplier_id";
			sBuffer.delete(0, sBuffer.length() - 1);		
			sBuffer.append("construct_supplier_table  where  construct_supplier_id in ("+suppliers+")");
			map.clear();
			map.put("fields", fields);
			map.put("where", sBuffer);
			List<Map<String, Object>> supplier=materialDao.selectData(map);
			for (int i = 0; i < supplier.size(); i++) {
				map.clear();
				map.put("table", "construct_material_price");
				map.put("fieds", "("+supplier.get(i).get("construct_supplier_id")+","+materialModel.getConstruct_material_model_id()+","
						+ "'"+materialModel.getConstruct_material_model_unit()+"','"+materialModel.getConstruct_material_model_name()+"',"
						+"'"+construct.get(0).get("construct_material_name")+"')");
				materialDao.insertData(map);
			}
			
		}
		return null;
		
		
	}

	@Override
	@Transactional
	public String delete(Map<String, Object> deleMap) throws Exception{
		String msg="";
		if(materialDao.isExistQua(deleMap.get("menuids").toString())==0){
			
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("where", " where construct_material_modelId =  "+deleMap.get("menuids")+" ");
			materialPriceDao.deletePrice(jsonMap);
			
			materialDao.deleteMateria(deleMap);
			
		}else {
			msg="存在合同工程量，不可删除";
		}
		return msg;
	}

	@Override
	public void deletemt(Map<String, Object> map) {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("where", " where construct_material_modelId in (select construct_material_model_id from construct_material_model model "
				+ " LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid "
				+ " where material.construct_material_id="+map.get("material_id")+") ");
		materialPriceDao.deletePrice(jsonMap);
		materialDao.deletemultiple(map);
	}

	@Override
	@Transactional
	public void saveSeries(MaterialSeries materialSeries) {
		// TODO Auto-generated method stub
		int seriesId=materialSeries.getConstruct_material_seriesID();
		if (seriesId==0) {
			HashMap<String, Object>  numMap=new HashMap<String, Object>();
			numMap.put("fields", "construct_material_num");
			numMap.put("where", " construct_material_series  ORDER BY construct_material_num DESC LIMIT 1");
			List<Map<String, Object>> numData = materialDao.selectData(numMap);
			materialSeries.setConstruct_material_num(Integer.valueOf(numData.get(0).get("construct_material_num").toString())+10);
			
			materialDao.saveSeries(materialSeries);
			seriesId=materialSeries.getConstruct_material_seriesID();
		}else{
			materialDao.saveSeries(materialSeries);
		}
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" construct_material_price price LEFT JOIN construct_material_model model on price.construct_material_modelId=model.construct_material_model_id ");
		sBuffer.append(" LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid ");
		sBuffer.append(" LEFT JOIN construct_material_series series on series.construct_material_seriesID=material.construct_material_seriesId ");
		sBuffer.append(" LEFT JOIN construct_supplier_table supp on supp.construct_supplier_id=price.construct_material_supplierId");
		sBuffer.append(" where series.construct_material_seriesID="+seriesId+"  GROUP BY  price.construct_material_supplierId");
		
		map.put("fields", "construct_supplier_id");
		map.put("where", sBuffer);
		List<Map<String, Object>> selectData = materialDao.selectData(map);
		
		List<String> priceSupplier=new ArrayList<String>();
		for (int i = 0; i < selectData.size(); i++) {
			priceSupplier.add(selectData.get(i).get("construct_supplier_id").toString());
		}
		
		String[] supplierId = materialSeries.getConstruct_material_seriesSuppliers().split(",");
		List<String> materialSupplier=new ArrayList<String>();
		for (int i = 0; i < supplierId.length; i++) {
			materialSupplier.add(supplierId[i]);
		}
		
		for (int i = 0; i < priceSupplier.size(); i++) {
			if(!materialSupplier.contains(priceSupplier.get(i))){
				//单价表   删除单价存在材料不存在的供应商
				HashMap<String, Object> whereMap=new HashMap<String, Object>();
				whereMap.put("where", " where construct_material_modelId in (select construct_material_model_id from construct_material_model model "
						+ " LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid "
						+ " LEFT JOIN construct_material_series series on series.construct_material_seriesID=material.construct_material_seriesId "
						+ " where series.construct_material_seriesID="+seriesId+")  and construct_material_supplierId="+priceSupplier.get(i)+"  ");

				materialPriceDao.deletePrice(whereMap);
			}
		}
		if (!supplierId[0].toString().equals("0")) {
			HashMap<String, Object> map2=new HashMap<String, Object>();
			StringBuffer sBuffer2=new StringBuffer();
			sBuffer2.append(" construct_material_model model ");
			sBuffer2.append(" LEFT JOIN construct_material_table material ON model.construct_material_model_parentid=material.construct_material_id  ");
			sBuffer2.append(" LEFT JOIN construct_material_series series on  series.construct_material_seriesID=material.construct_material_seriesId ");
			sBuffer2.append(" where series.construct_material_seriesID="+seriesId+"");
	
			map2.put("fields", " material.construct_material_name,model.construct_material_model_name,model.construct_material_model_unit,model.construct_material_model_id ");
			map2.put("where", sBuffer2);
			List<Map<String, Object>> materialData = materialDao.selectData(map2);
			if (materialData.size()!=0) {
				for (int i = 0; i < supplierId.length; i++) {
					if(!priceSupplier.contains(supplierId[i])){
						//单价表 添加材料存在 单价不存在的供应商
						for (int j = 0; j < materialData.size(); j++) {
							MaterialPrice materialPrice=new MaterialPrice();
							materialPrice.setConstruct_material_model(materialData.get(j).get("construct_material_model_name").toString());
							materialPrice.setConstruct_material_modelId(Integer.valueOf(materialData.get(j).get("construct_material_model_id").toString()));
							materialPrice.setConstruct_material_priceId(0);
							materialPrice.setConstruct_material_supplierId(Integer.valueOf(supplierId[i]));
							materialPrice.setConstruct_material_name(materialData.get(j).get("construct_material_name").toString());
							materialPrice.setConstruct_material_unit(materialData.get(j).get("construct_material_model_unit").toString());
							materialPriceDao.addPrice(materialPrice);
						}
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectData(String fields, String sbf) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		dataList=materialDao.selectData(map);
		return dataList;
	}

	@Override
	@Transactional
	public void delete_Series(Map<String, Object> map) {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("where", " where construct_material_modelId in (select construct_material_model_id from construct_material_model model "
				+ " LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid "
				+ " LEFT JOIN construct_material_series series on series.construct_material_seriesID=material.construct_material_seriesId "
				+ " where series.construct_material_seriesID="+map.get("seriesID")+") ");
		materialPriceDao.deletePrice(jsonMap);
		jsonMap.clear();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_material_table LEFT JOIN construct_material_model");
		sbf.append(" ON construct_material_table.construct_material_id = construct_material_model.construct_material_model_parentid");
		sbf.append(" WHERE construct_material_table.construct_material_seriesId = " + map.get("seriesID"));
		jsonMap.put("where", sbf);
		materialDao.deletemultiple(jsonMap);
		materialDao.delete_Series(map);
		
	}

	@Override
	@Transactional
	public void cp_material_exl(List<List<Object>> list) {
		
		String materialName="";
		String series="";
		int material_id=0;
		int series_id=0;
		for (int i = 1; i < list.size(); i++) {
			  List<Object> eobj = list.get(i);
			  if(!eobj.get(6).toString().trim().equals(series)){
				  MaterialSeries materialSeries=new MaterialSeries();
				  materialSeries.setConstruct_material_seriesID(0);
				  materialSeries.setConstruct_material_seriesName(eobj.get(6).toString().trim());
				  materialSeries.setConstruct_material_seriesSuppliers(eobj.get(7).toString().trim());
				  String num =eobj.get(0).toString();
				  materialSeries.setConstruct_material_num(Integer.valueOf(num.substring(0, 3)));
				  
				  materialDao.saveSeries(materialSeries);
				  series=eobj.get(6).toString().trim();
				  series_id=materialSeries.getConstruct_material_seriesID();
				  
				  Material material=new Material();
				  material.setConstruct_material_id(0);
				  material.setConstruct_material_name(eobj.get(1).toString().trim());
				  material.setConstruct_material_seriesId(Integer.valueOf(materialSeries.getConstruct_material_seriesID()));
				  
				  material.setConstruct_material_num(Integer.valueOf(num.substring(3, 6)));
				  material_id=materialDao.addMaterial(material);
				  material_id=material.getConstruct_material_id();
				  materialName=eobj.get(1).toString().trim();
				  addMaterialModel(material_id,eobj);
				  
			  }else if (!eobj.get(1).toString().trim().equals(materialName)) {
				  Material material=new Material();
				  material.setConstruct_material_id(0);
				  material.setConstruct_material_name(eobj.get(1).toString().trim());
				  material.setConstruct_material_seriesId(Integer.valueOf(series_id));
				  String num =eobj.get(0).toString();
				  material.setConstruct_material_num(Integer.valueOf(num.substring(3, 6)));
				  material_id=materialDao.addMaterial(material);
				  material_id=material.getConstruct_material_id();
				  materialName=eobj.get(1).toString().trim();
				  addMaterialModel(material_id,eobj);
				  
			}else {
				addMaterialModel(material_id,eobj);
			}
	}
	}
		
	private  void addMaterialModel(int material_id, List<Object> eobj){
			
		  MaterialModel materialModel =new MaterialModel();
		  materialModel.setConstruct_material_model_id(0);
		  materialModel.setConstruct_material_model_parentid(material_id);
		  
		  materialModel.setConstruct_material_model_name(eobj.get(2).toString().trim());
		  String num =eobj.get(0).toString();
		  materialModel.setConstruct_materail_model_num(Integer.valueOf(num.substring(6, 9)));
		  materialModel.setConstruct_material_model_unit(eobj.get(3).toString().trim());
		  materialDao.addMaterialModel(materialModel);
		  
		  
		  MaterialPrice price =new MaterialPrice();
		  price.setConstruct_material_priceId(0);
		  price.setConstruct_material_model(eobj.get(2).toString().trim());
		  price.setConstruct_material_modelId(materialModel.getConstruct_material_model_id());
		  price.setConstruct_material_name(eobj.get(1).toString());
		  price.setConstruct_material_price(Double.parseDouble(eobj.get(4).toString().trim()));
		  double dID = Double.parseDouble(eobj.get(7).toString().trim());
		  Double id=new Double(dID); 
		  price.setConstruct_material_supplierId(id.intValue());
		  price.setConstruct_material_unit(eobj.get(3).toString().trim());
		  materialPriceDao.addPrice(price);
		  
	}
	

}
