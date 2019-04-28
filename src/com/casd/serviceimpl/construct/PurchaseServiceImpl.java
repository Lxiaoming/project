package com.casd.serviceimpl.construct;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.construct.ConstructDepDao;
import com.casd.dao.construct.PurchaseDao;
import com.casd.dao.flow.FlowDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.PurchaseEntry;
import com.casd.entity.construct.PurchaseEntryClass;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.construct.PurchaseHeadClass;
import com.casd.entity.hr.AssessEntry;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.PurchaseService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private FlowService flowService;
	@Autowired
	private FlowDao flowDao;
	
	
	@Autowired
	 private ActivitiService activitiService;
	 @Autowired    
	 private TaskService taskService; 
	
	private String flow_bill_type="采购申请";
		
	
	@Override
	@Transactional
	public List<Map<String, Object>> purchaseList(String filds,int page,
			int pageSize, Ref<Integer> record, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);
		param.put("filds", filds);
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		List<Map<String, Object>> list= purchaseDao.purchaseList(param);
		if (record != null) {
			int count =purchaseDao.getPurchaseCount(param);
			record.setValue(count);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> purchaseMaterialList(int page,
			int pageSize, Ref<Integer> record, String where) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			int count = purchaseDao.getPurchaseMaterialCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return purchaseDao.purchaseMaterialList(param);
	}

	@Override
	@Transactional
	public int submitPurchase(PurchaseHead purchaseHead, String rows) {
		// TODO Auto-generated method stub
		int construct_purchase_id = purchaseHead.getConstruct_purchase_id();
		if(construct_purchase_id==0){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			purchaseHead.setConstruct_purchase_creatTime(df.format(new Date()).toString());
			purchaseDao.savePurchaseHead(purchaseHead);
			construct_purchase_id = purchaseHead.getConstruct_purchase_id();
		}else {
			purchaseDao.savePurchaseHead(purchaseHead);
		}
		JSONArray myJsonArray = JSONArray.fromObject(rows);
		List<PurchaseEntry> entries=new ArrayList<PurchaseEntry>();
		for (int i = 0; i < myJsonArray.size(); i++) {
			PurchaseEntry purchaseEntry=new PurchaseEntry();
			JSONObject entry = myJsonArray.getJSONObject(i);
			purchaseEntry.setConstruct_purchase_applyNum(Integer.valueOf(entry.getString("construct_purchase_applyNum")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_applyNum").toString()));
			purchaseEntry.setConstruct_purchase_approvalNum(Integer.valueOf(entry.getString("construct_purchase_approvalNum")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_approvalNum").toString()));
			purchaseEntry.setConstruct_purchase_contractPrice(Double.valueOf(entry.getString("construct_purchase_contractPrice")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_contractPrice").toString()));
			purchaseEntry.setConstruct_purchase_entryId(Integer.valueOf(entry.getString("construct_purchase_entryId")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_entryId").toString()));
			purchaseEntry.setConstruct_purchase_material(entry.getString("construct_purchase_material"));
			purchaseEntry.setConstruct_purchase_model(entry.getString("construct_purchase_model"));
			purchaseEntry.setConstruct_purchase_parentId(construct_purchase_id);//父id
			
			if(entry.containsKey("construct_purchase_purchasePrice")){
			
			purchaseEntry.setConstruct_purchase_purchasePrice(Double.valueOf(entry.getString("construct_purchase_purchasePrice")
					
					.toString().isEmpty()?"0":entry.getString("construct_purchase_purchasePrice").toString()));
			}
		
			if(entry.containsKey("construct_purchase_purchaseTotal")){				
				purchaseEntry.setConstruct_purchase_purchaseTotal(Double.valueOf(entry.getString("construct_purchase_purchaseTotal")
						.toString().isEmpty()?"0":entry.getString("construct_purchase_purchaseTotal").toString()));
				}		
			purchaseEntry.setConstruct_purchase_quantities(Integer.valueOf(entry.getString("construct_purchase_quantities")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_quantities").toString()));
			purchaseEntry.setConstruct_purchase_remarks(entry.getString("construct_purchase_remarks"));
			purchaseEntry.setConstruct_purchase_unit(entry.getString("construct_purchase_unit"));
			purchaseEntry.setConstruct_purchase_brand(entry.getString("construct_purchase_brand"));
			purchaseEntry.setConstruct_purchase_quantitiesId(Integer.valueOf(entry.getString("construct_purchase_quantitiesId")
					.toString().isEmpty()?"0":entry.getString("construct_purchase_quantitiesId").toString()));
			entries.add(purchaseEntry);
		}
		for(int i = 0; i < entries.size(); i++){
			purchaseDao.savePurchaseEntry(entries.get(i));
		}
		return construct_purchase_id;
	}

	
	@Override
	@Transactional
	public void auditPurchase(PurchaseHead purchaseHead, String rows,String username,String auditor) {
		int billId= submitPurchase(purchaseHead, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 2); 
		purchaseDao.updateBillStatus(map);
		// 工作流
		Map<String, Object> linkMap = new HashMap<String, Object>();
		linkMap.put("auditor", auditor); // 审批人
		linkMap.put("flow_bill_id", billId); // 单据id
		linkMap.put("flow_submitter", username); // 申请人
		linkMap.put("flow_bill_url", "purAuditView.do");// 审核界面url
		linkMap.put("flow_bill_type", "采购申请"); // 审核单据类型
		flowService.saveFlowAudit(linkMap);
	}

	@Override
	public Map<String, Object> initPurchase(String construct_purchase_id,String type,int status) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> data=new HashMap<String, Object>();
		map.put("id", construct_purchase_id);
		
		PurchaseHead head=new PurchaseHead();
		//List<PurchaseEntry> entries=new ArrayList<PurchaseEntry>();
		head=purchaseDao.getHead(map);
		
		
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", " construct_purchase_entry entry LEFT JOIN construct_project_quantities quan ON entry.construct_purchase_quantitiesId = quan.construct_project_quantities_id "
				+ " LEFT JOIN construct_material_model model on quan.construct_project_quantities_modelId=model.construct_material_model_id "
				+ " LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid  "
				+ " LEFT JOIN construct_material_series series on series.construct_material_seriesID=material.construct_material_seriesId "
				+ " where entry.construct_purchase_parentId="+construct_purchase_id+" ");

		param.put("filds", " CONCAT(series.construct_material_num , material.construct_material_num ,model.construct_materail_model_num)  num,entry.* ");
		
		param.put("limit", "");
		
		List<Map<String, Object>> list= purchaseDao.purchaseList(param);
		
		//entries=purchaseDao.getEntry(map);
		data.put("purchaseHead", head);
		data.put("rows", list);
		return data;
	}

	@Override
	@Transactional
	public Map<String, Object> delePurchase(String construct_purchase_id,int status) {
		Map<String, Object> data=new HashMap<String, Object>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			
		
	     	map.put("id", construct_purchase_id);
		if(status==1||status==0){
			StringBuffer  sbf=new StringBuffer();
			sbf.append(" construct_purchase_head LEFT JOIN construct_purchase_entry");
			sbf.append(" ON construct_purchase_head.construct_purchase_id = construct_purchase_entry.construct_purchase_parentId");
			sbf.append(" WHERE construct_purchase_id = "+construct_purchase_id);
			map.put("what",sbf.toString()); 
			purchaseDao.delePurchase(map);
			activitiService.deleteRecord(construct_purchase_id, "Purchase_payment");
			    data.put("Msg","已删除");
			}else {
				data.put("Msg","该单已经审核，未退单不能删除！");
			 }	
		    data.put("Success", true);   
		} catch (Exception e) {
			data.put("Success", false);
			data.put("Msg", "操作失败");
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	
	
	
	@Override
	@Transactional
	public Map<String, Object> getHeadData(int flow_bill_id) {
		//单头信息
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> data=new HashMap<String, Object>();
		map.put("id", flow_bill_id);
		String filds="*";
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" construct_purchase_head head left join construct_project_table pro on head.construct_purchase_projectId=pro.construct_project_id ");
		sBuffer.append(" where head.construct_purchase_id = "+flow_bill_id+"");
		map.put("where", sBuffer);
		map.put("filds", filds);
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		dataList=purchaseDao.getPHead(map);//查询项目信息和材料信息
		
		data.put("pHead", dataList.get(0));
		int materialSerId = Integer.valueOf(dataList.get(0).get("construct_purchase_materialSerId").toString());
		
		//取供应商id,名字，供应商个数
		dataList.clear();
		sBuffer.setLength(0);
		filds="construct_material_seriesSuppliers";
		sBuffer.append(" construct_material_series where  construct_material_seriesID="+materialSerId);
		map.put("where", sBuffer);
		map.put("filds", filds);
		dataList=purchaseDao.getPHead(map);//查看该材料是有那些供应商持有的
		
		sBuffer.setLength(0);
		List<Map<String, Object>> idlList=new ArrayList<Map<String,Object>>();
		String seriesSuppliersId = dataList.get(0).get("construct_material_seriesSuppliers").toString();
		filds="construct_supplier_name,construct_supplier_id";
		sBuffer.append(" construct_supplier_table where  construct_supplier_id in ("+seriesSuppliersId+")");
		map.put("where", sBuffer);
		map.put("filds", filds);
		idlList=purchaseDao.getPHead(map);//查看该材料有那些供应商
		
		List<Map<String, Object>> columnsList=new ArrayList<Map<String,Object>>();
		Map<String, Object> columnsMap =new LinkedHashMap<String, Object>();
		columnsMap.put("ID", "construct_purchase_entryId");
		columnsMap.put("材料名称", "construct_purchase_material");
		columnsMap.put("型号规格", "construct_purchase_model");
		columnsMap.put("单位", "construct_purchase_unit");
		columnsMap.put("合同工程量", "construct_purchase_quantities");
		columnsMap.put("累计审批量", "construct_purchase_approvalNum");
		columnsMap.put("计划采购量", "construct_purchase_applyNum");
		columnsMap.put("合同单价", "construct_purchase_contractPrice");
		columnsMap.put("备注", "construct_purchase_remarks");
		for (int i = 0; i < idlList.size(); i++) {
			columnsMap.put(idlList.get(i).get("construct_supplier_name").toString(), idlList.get(i).get("construct_supplier_name").toString());
		}
		columnsList.add(columnsMap);
		data.put("columns", columnsList);
		data.put("supplier", idlList);
		
		//分录数据
		sBuffer.setLength(0);
		List<Map<String, Object>> entryList=new ArrayList<Map<String,Object>>();
		StringBuffer fildsStringBuffer=new StringBuffer();
		fildsStringBuffer.append("pur.*,CONCAT(series.construct_material_num , material.construct_material_num ,model.construct_materail_model_num) as num ");
		for (int i = 0; i < idlList.size(); i++) {
				fildsStringBuffer.append(" , MAX(case priceNum.construct_material_supplierId when "+idlList.get(i).get("construct_supplier_id")+" then priceNum.construct_material_price else 0 end  ) as  "+idlList.get(i).get("construct_supplier_name")+"");
		}
		
		sBuffer.append(" (select price.construct_material_modelId, price.construct_material_price,price.construct_material_supplierId "
				+ " from construct_material_price price where price.construct_material_supplierId in ("+seriesSuppliersId+") )as priceNum ");
		sBuffer.append(" LEFT JOIN construct_material_model model on model.construct_material_model_id=priceNum.construct_material_modelId ");
		sBuffer.append(" LEFT JOIN construct_project_quantities quantities  on model.construct_material_model_id=quantities.construct_project_quantities_modelId ");
		sBuffer.append("  LEFT JOIN construct_purchase_entry pur on pur.construct_purchase_quantitiesId = quantities.construct_project_quantities_id ");
		sBuffer.append("  LEFT JOIN construct_material_table material on material.construct_material_id=model.construct_material_model_parentid ");
		sBuffer.append("  LEFT JOIN construct_material_series series on series.construct_material_seriesID=material.construct_material_seriesId ");
		sBuffer.append(" WHERE pur.construct_purchase_parentId = "+flow_bill_id+" GROUP BY pur.construct_purchase_entryId");
		filds = fildsStringBuffer.toString();
		map.put("where", sBuffer);
		map.put("filds", filds);
		entryList=purchaseDao.getPHead(map);
		data.put("Entries", entryList);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		footMap.put("num", "合计");
		Double total = 0.00;
		for (int i = 0; i < entryList.size(); i++) {
			if (entryList.get(i).get("construct_purchase_purchaseTotal") != null) {

				String purchaseTotal = entryList.get(i).get("construct_purchase_purchaseTotal").toString();
				total = total+ (Double.valueOf(purchaseTotal));
			}
		}
		footMap.put("construct_purchase_purchaseTotal", total);
		list.add(footMap);
		data.put("footer", list);
		
		
		return data;
	}

/*
	@Override
	@Transactional
	public String auditBill(Map<String, Object> map, String auditor,
			String rows,PurchaseHeadClass purchaseHeadClass) {
		flowService.auditBill(map,auditor);
		
		String message="";
		if(auditor==""){
			//更新状态为审核通过
			map.put("status", 3); 
			purchaseDao.updateBillStatus(map);
			
			//根据供应商分类
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			Map<Object,List<JSONObject>> entriesmaps=new HashMap<Object, List<JSONObject>>();
			
			for (int i=0;i<myJsonArray.size();i++) {
				List<JSONObject> list=new ArrayList<JSONObject>();
				String construct_purchase_suppliers = myJsonArray.getJSONObject(i).get("construct_purchase_suppliers").toString();
				if(!entriesmaps.containsKey(construct_purchase_suppliers)){
					list.add(myJsonArray.getJSONObject(i));
					for(int m=i+1;m<myJsonArray.size();m++){
						JSONObject entryNext=myJsonArray.getJSONObject(m);
						if(entryNext.get("construct_purchase_suppliers").equals(construct_purchase_suppliers)){
							list.add(entryNext);
						}
					}
				}
				if(list.size()!=0){
					entriesmaps.put(construct_purchase_suppliers, list);
				}
			}  
			//分完类别后取出来保存
			for (Object supplier : entriesmaps.keySet()) {
				List<JSONObject> entriesList = entriesmaps.get(supplier);
				List<PurchaseEntryClass> entryList=new ArrayList<PurchaseEntryClass>();
				JSONObject Object = entriesList.get(0);
				purchaseHeadClass.setConstruct_purchase_supplier(supplier.toString());
				purchaseHeadClass.setConstruct_purchase_id(0);
				purchaseHeadClass.setConstruct_purchase_supplierTel(Object.getString("construct_material_supplierTel"));
				purchaseDao.savePhc(purchaseHeadClass);
				int construct_purchase_id = purchaseHeadClass.getConstruct_purchase_id();
				for(int o=0; o<entriesList.size();o++){
					JSONObject jsonObject = entriesList.get(o);
					PurchaseEntryClass purchaseEntryClass = (PurchaseEntryClass) JSONObject.toBean(jsonObject,  
							PurchaseEntryClass.class); 
					purchaseEntryClass.setConstruct_purchase_entryId(0);
					purchaseEntryClass.setConstruct_purchase_parentId(construct_purchase_id);
					purchaseEntryClass.setConstruct_purchase_noArrive(purchaseEntryClass.getConstruct_purchase_applyNum());
					purchaseEntryClass.setConstruct_purchase_arriveNum(0);
					entryList.add(purchaseEntryClass);
				}
				purchaseDao.savePec(entryList);
			}
			message="价钱保存成功！";
		}else{
			//更新状态为审核中
			map.put("status", 1); 
			purchaseDao.updateBillStatus(map);
		}
		return message;
	}*/

	@Override
	public List<Map<String, Object>> getAuditOption(Map<String, Object> param) {
		StringBuffer sbf=new StringBuffer();
		String fields = "flow_audit_option,flow_auditer";
		sbf.append(" flow_audit_table where flow_bill_id='"+param.get("flow_bill_id")+"' and flow_bill_type='"+flow_bill_type+"'");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		List<Map<String, Object>> numList=new ArrayList<Map<String,Object>>();
		numList=flowDao.getAuditorId(map);
		return numList;
	}

	@Override
	public void updateBillStatus(Map<String, Object> map) {
		
		purchaseDao.updateBillStatus(map);
	}

	@Override
	@Transactional
	public String  chooseSupplier(String rows, int supplier, String supplierName,int purchase_id) {
		StringBuffer  erro=new StringBuffer();
		//erro.append("");
		JSONArray myJsonArray = JSONArray.fromObject(rows);
		
		for (int j = 0; j < myJsonArray.size(); j++) {
			JSONObject entry = myJsonArray.getJSONObject(j);
			PurchaseEntry purchaseEntry = (PurchaseEntry) JSONObject.toBean(entry,  
					PurchaseEntry.class);
			double price=0.00;
			
			try {
				price = entry.getDouble(supplierName);
				if (price==0) {
					erro.append("存在单价为0！");
				}
			} catch (Exception e) {
				erro.append("存在单价为空！");
			}
			if(erro.toString().equals("")){
				purchaseEntry.setConstruct_purchase_purchasePrice(price);
				purchaseEntry.setConstruct_purchase_purchaseTotal(price*purchaseEntry.getConstruct_purchase_applyNum());
				purchaseDao.savePurchaseEntry(purchaseEntry);
			}
		}
		if(erro.toString().equals("")){
			Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sBuffer=new StringBuffer();
			sBuffer.append(" construct_purchase_supplier='"+supplierName+"' ");
			map.put("billID", purchase_id);
			map.put("filds", sBuffer);
			purchaseDao.updateBill(map);
		}
		return erro.toString();
	}

	@Override
	@Transactional
	public String auditBill(Map<String, Object> map, String auditor) {
		flowService.auditBill(map,auditor);
		if(auditor==""){
			//更新状态为审核通过
			map.put("status", 3); 
			purchaseDao.updateBillStatus(map);
		
		}else{
			//更新状态为审核中
			map.put("status", 1); 
			purchaseDao.updateBillStatus(map);
		}
		return null;
	}
	
	
	
	
	
	/**   4-13
	  * 启动流程后修改单据状态为审核中 status 为2
	  *
	  */
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void updateBizStatus(DelegateExecution execution, String status) {
		 
		String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
        Map<String, Object> map=new HashMap<String, Object>();
         map.put("billID", bizId);
         map.put("status", status);
         purchaseDao.updateBillStatus(map);

		
	}
   /**
    * 查询经营部审核人
    * @author liao
    * @throws Exception 
    * 
    */
	@Override
	public List<Map<String, Object>> fidbusiness() throws Exception{
		 Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String fields="c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='经营部审核'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name=flowDao.newlis(map);	
		 map.clear();
		 sbf.delete(0,sbf.length());
		String nameString="*";
		sbf.append("uc_user a WHERE a.userid in("+name+")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		List<Map<String, Object>> list=userDao.queryId(map);
		
		return list;
	}

	/**
	 *    
    * 查询总经理审核人
    * @author liao
    * @throws Exception 
    * 
    */
	
	@Override
	public List<Map<String, Object>> manageraAudit() throws Exception {
		 Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sbf=new StringBuffer();
			String fields="c.flow_node_auditors";
			sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
			sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='总经理审核'");
			map.put("fields", fields);
			map.put("where", sbf);
			String name=flowDao.newlis(map);	
			 map.clear();
			 sbf.delete(0,sbf.length());
			 map.clear();
			 sbf.delete(0,sbf.length());
			String nameString="*";
			sbf.append("uc_user a WHERE a.userid in("+name+")");
			map.put("fields", nameString);
			map.put("where", sbf.toString());
			List<Map<String, Object>> list=userDao.queryId(map);
		    return list;
	}

	
	 /**
    * 查询采购核对单价审批人
    * @author liao
    * @throws Exception 
    * 
    */
	@Override
	public List<Map<String, Object>> purchasingcheck() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String fields="c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='采购核对单价'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name=flowDao.newlis(map);	
		 map.clear();
		 sbf.delete(0,sbf.length());
		String nameString="*";
		sbf.append("uc_user a WHERE a.userid in("+name+")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		List<Map<String, Object>> list=userDao.queryId(map);
	    return list;
	}

	
	 /**
    * 查询财务经理审批人
    * @author liao
    * @throws Exception 
    * 
    */
	@Override
	public List<Map<String, Object>> financialcheck() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String fields="c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='财务经理审批'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name=flowDao.newlis(map);	
		 map.clear();
		 sbf.delete(0,sbf.length());
		String nameString="*";
		sbf.append("uc_user a WHERE a.userid in("+name+")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		List<Map<String, Object>> list=userDao.queryId(map);
	    return list;
	}

	
	 /**
    * 查询董事会审批
    * @author liao
    * @throws Exception 
    * 
    */
	@Override
	public List<Map<String, Object>> chairmancheck() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String fields="c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='董事会审批'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name=flowDao.newlis(map);	
		 map.clear();
		 sbf.delete(0,sbf.length());
		String nameString="*";
		sbf.append("uc_user a WHERE a.userid in("+name+")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		List<Map<String, Object>> list=userDao.queryId(map);
	    return list;
	}

	
	
	 /**
    * 查询供应商 配货
    * @author liao
    * @throws Exception 
    * 
    */
	
	@Override
	public List<Map<String, Object>> supplierUser() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String fields="c.flow_node_auditors";
		sbf.append("flow_table b JOIN flow_node_table c on b.flow_id=c.flow_node_parentID");
		sbf.append(" WHERE  b.flow_id=29 AND  c.flow_node_name='配货'");
		map.put("fields", fields);
		map.put("where", sbf);
		String name=flowDao.newlis(map);	
		 map.clear();
		 sbf.delete(0,sbf.length());
		String nameString="*";
		sbf.append("uc_user a WHERE a.userid in("+name+")");
		map.put("fields", nameString);
		map.put("where", sbf.toString());
		List<Map<String, Object>> list=userDao.queryId(map);
	    return list;
	}

	
	//上传
	@Override
	@Transactional
	public Map<String, Object> uploadPhoto(MultipartFile photo,HttpServletRequest request) {
  
		Map<String, Object> json = new HashMap<String, Object>();
		String bizId=request.getParameter("bizId");
		 try {
	            // 获取原始文件名  
	            String fileName = photo.getOriginalFilename();  
		
				if(StringUtils.isEmpty(fileName)){
					json.put("Success", false);
					json.put("Msg", "请选择要导的图片");
					return json;
				}
							       
	        	File file2=new File("e:/uploadfile/photo");	
	            if(!file2.exists()) {  
	            	file2.mkdirs();  
	            }  
	            //定义文件路径
	            String fileUploadBasePath = "e:/uploadfile/photo/";
	            String timeStr = System.currentTimeMillis() + fileName;
	            String newFilePath = fileUploadBasePath + timeStr;
	            File newFile = new File(newFilePath);
	            photo.transferTo(newFile);  // 写入文件到服务器目录
	            System.out.println(timeStr);
	            Map<String, Object> uploadVar=new HashMap<String, Object>();
	            uploadVar.put("filds", timeStr);
	            uploadVar.put("billID", bizId);
	            purchaseDao.updatephoto(uploadVar);

			json.put("Success", true);
			json.put("Msg", "上传成功,可点击查看图片是否正确");
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    json.put("Success", false);
			json.put("Msg", "上传失败"+e);
		}
	    return json;

	
	}
	
	@Transactional
	public Map<String, Object> start_examination(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> vars = new HashMap<String, Object>();
		String version = request.getParameter("version");
		if("2".equals(version)){
			String taskid = request.getParameter("taskid");
		    String bizkey = request.getParameter("bizId");
		    User user = (User)request.getSession().getAttribute("loginUser");
		    String username = String.valueOf(user.getUserid());
		    


		    Map<String, Object> params = new HashMap();
		    String varfields = "b.center_name,c.department_name";
		    String varwhere = "uc_user a";
		    varwhere = varwhere + " LEFT JOIN uc_department  c on c.department_id=a.department";
		    varwhere = varwhere + " LEFT JOIN  uc_center  b on a.center_id=b.center_id";
		    varwhere = varwhere + " WHERE a.userid=" + user.getUserid();
		    
		    params.put("fields", varfields);
		    params.put("where", varwhere);
		    Map<String, Object> mapList = this.userDao.queryUserById(params);
		    String center_name = mapList.get("center_name").toString();
		    String department_name = null;
		    if (mapList.containsKey("department_name")) {
		      department_name = mapList.get("department_name").toString();
		    }
		    String auditor;
		    if (department_name == null) {
		      auditor = center_name;
		    } else {
		      auditor = department_name;
		    }
		    String taskName = request.getParameter("taskName");
		    String nextUser = request.getParameter("username");
		    String options = request.getParameter("options");
		    SendmailUtil sendmailUtil = null;
		    if (taskName.equals("项目经理提出申请"))
		    {
		      updateBill(bizkey, "2");
		      vars.put("dsz", nextUser);
		      vars.put("jingying", nextUser);
		      
		      this.activitiService.comment(taskid, username, options);
		      
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("经营部审核"))
		    {
		      updateBill(bizkey, "3");
		      vars.put("sign", "true");
		      vars.put("zjl", nextUser);
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("总经理审核"))
		    {
		      updateBill(bizkey, "4");
		      vars.put("cghd", nextUser);
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("采购核对单价"))
		    {
		      updateBill(bizkey, "5");
		      vars.put("cwjl", nextUser);
		      
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("财务经理审批"))
		    {
		      updateBill(bizkey, "6");
		      vars.put("dsz", nextUser);
		      
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("董事会审批"))
		    {
		      updateBill(bizkey, "7");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("申请采购单下单"))
		    {
		      updateBill(bizkey, "8");
		      vars.put("peihuo", nextUser);
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("配货"))
		    {
		      updateBill(bizkey, "9");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("核对签收"))
		    {
		      updateBill(bizkey, "10");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("复核价格及数量"))
		    {
		      updateBill(bizkey, "11");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("申请请款"))
		    {
		      updateBill(bizkey, "12");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    else if (taskName.equals("通知财务"))
		    {
		      updateBill(bizkey, "13");
		      this.activitiService.comment(taskid, username, options);
		      this.taskService.complete(taskid, vars);
		    }
		    jsonMap.put("leva", "");
		}else{
			String taskid = request.getParameter("taskid");
			String bizkey = request.getParameter("bizId");
			String sign = request.getParameter("sign");
			User user = (User) request.getSession().getAttribute("loginUser");// 当前办理人
			String userid = user.getUserid() + ""; 
			String reasons = request.getParameter("reasons");// 获取审核意见
			String usertask = request.getParameter("usertask");//驳回节点
			String taskName = request.getParameter("taskName");
			SendmailUtil sendmailUtil = null;
//			updateBill(bizkey, "13");
			activitiService.comment(taskid,userid , reasons == null ? "" : reasons);
			vars.put("type", sign);
			vars.put("usertask", usertask);
			taskService.complete(taskid, vars);
		}

		


		/*if (!StringUtils.isEmpty(nextUser)) {
			String fields = " email ";
			String where = " uc_user where userid='" + nextUser + "'";
			Map<String, Object> useMap = new HashMap<String, Object>();
			useMap.put("fields", fields);
			useMap.put("where", where);
			String content = "您有一张采购申请单需要审批!"; // 请假内容
			String theme = "采购申请单!"; // 邮件标题
			User uc = userDao.login(useMap);

			// 判断Emali的邮箱是否为null
			if (uc.getEmail() != null) {
				sendmailUtil = (SendmailUtil) Class.forName(
						"com.casd.controller.web.utils.SendmailUtil")
						.newInstance();
				sendmailUtil.doSendHtmlEmail(theme, content, uc.getEmail());

			} else {
				jsonMap.put("leva", "邮件发送失败!");
			}
		}*/
		return jsonMap;

	}
	
	  
	/**
	 * 在流程中修改状态
	 * 
	 * */
   @Transactional
   public  void updateBill(String bizkey,String status){
   Map<String, Object> map=new HashMap<String, Object>();
     map.put("status", status);
     map.put("billID", bizkey);
     purchaseDao.updateBillStatus(map);	
	}
	/**
	 * 在流程中修改状态
	 * 
	 * */
  @Transactional
  @Override
  public  void modBill(DelegateExecution execution, String status){
	  String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
      String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
  Map<String, Object> map=new HashMap<String, Object>();
    map.put("status", status);
    map.put("billID", bizId);
    purchaseDao.updateBillStatus(map);	
	}
     @Transactional
     public void  deleteEntry(String where) {
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 map.put("where", where);
    	 purchaseDao.delete(map);
    	 
	}

	@Override
	public Map<String, Object> headData_Audit(Integer flow_bill_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> data=new HashMap<String, Object>();
		map.put("id", flow_bill_id);
		String filds="*";
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" construct_purchase_head head left join construct_project_table pro on head.construct_purchase_projectId=pro.construct_project_id ");
		sBuffer.append(" where head.construct_purchase_id = "+flow_bill_id+"");
		map.put("where", sBuffer);
		map.put("filds", filds);
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		dataList=purchaseDao.getPHead(map);//查询项目信息和材料信息
		data= dataList.get(0);
		return data;
	}
	
	
}
