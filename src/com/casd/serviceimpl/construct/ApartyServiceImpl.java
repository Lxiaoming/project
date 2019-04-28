package com.casd.serviceimpl.construct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.casd.controller.web.Ref;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.dao.construct.APartyDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.construct.ApartyMaterial;
import com.casd.entity.construct.ApartyPur;
import com.casd.entity.construct.ApartyPurEntry;
import com.casd.entity.finance.Wages;
import com.casd.entity.hr.Hregister;
import com.casd.entity.uc.User;
import com.casd.service.construct.APartyService;
import com.casd.service.hr.ActivitiService;

@Service
public class ApartyServiceImpl implements APartyService {
	
	
	@Autowired
	private APartyDao apartyDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private  ActivitiDao activitiDao;
	
	@Autowired
	private  RuntimeService  runtimeService;
	

	@Override
	public List<Map<String, Object>> getList(int page, int pageSize,
			Ref<Integer> record, String where) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			int count = apartyDao.getCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return apartyDao.getList(param);
	}

	

	@Override
	public void save_aPartyMaterial(ApartyMaterial material) {
/*	      Map<String, Object> map=new HashMap<String, Object>();
	     	String fields="construct_Aparty_material_id,construct_Aparty_material_model,construct_Aparty_material_num" ;
			    String where="construct_aparty_material"; 
			          where+=" where construct_Aparty_material_model='"+material.getConstruct_Aparty_material_model()+"'";
					  map.put("fields", fields);
					  map.put("where", where);
		      Map<String, Object> dataMap=apartyDao.getMap(map);*/
		      int material_id=material.getConstruct_Aparty_material_id();
			if (material_id!=0) {
				apartyDao.update_aPartyMaterial(material);
			}else if(material_id==0){
				 apartyDao.save_aPartyMaterial(material);
			}
	       
		
	}

	@Override
	public void delete_aPartyMaterial(String where) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("where", where);
		apartyDao.delete_aPartyMaterial(map);
		
	}

	@Override
	public void cp_aParty_exl(List<List<Object>> list,String construct_project_id) {

		 
		for (int i = 1; i < list.size(); i++) {
			List<Object> eobj = list.get(i);
			ApartyMaterial apartyMaterial=new ApartyMaterial();
			String model=eobj.get(1).toString().trim(); //获取规格
			 Map<String, Object> map=new HashMap<String, Object>();
		     	String fields="construct_Aparty_material_id,construct_Aparty_material_model,construct_Aparty_material_num" ;
				    String where="construct_aparty_material"; 
				          where+=" where construct_Aparty_material_model='"+model+"'";
				          where+=" and construct_Aparty_material_constructId="+construct_project_id;
						  map.put("fields", fields);
						  map.put("where", where);
			      Map<String, Object> dataMap=apartyDao.getMap(map);

			if(dataMap!=null){
					Double numNew=Double.valueOf(eobj.get(3).toString().trim().equals("")?"0.00":eobj.get(3).toString().trim());
				    Double num= Double.parseDouble(dataMap.get("construct_Aparty_material_num").toString())+numNew;
					Integer party_material_id= Integer.valueOf(dataMap.get("construct_Aparty_material_id")+"");
					apartyMaterial.setConstruct_Aparty_material_id(party_material_id);
					apartyMaterial.setConstruct_Aparty_material_num(num);
					map.put(eobj.get(1).toString().trim(),num);
		         	apartyDao.update_aPartyMaterial(apartyMaterial);
				}else {
					apartyMaterial.setConstruct_Aparty_material_id(0);
					apartyMaterial.setConstruct_Aparty_material_constructId(Integer.valueOf(construct_project_id));
					apartyMaterial.setConstruct_Aparty_material_category(eobj.get(4).toString().trim());
					apartyMaterial.setConstruct_Aparty_material_model(eobj.get(1).toString().trim());
					apartyMaterial.setConstruct_Aparty_material_name(eobj.get(0).toString().trim());
					apartyMaterial.setConstruct_Aparty_material_num(Double.valueOf(eobj.get(3).toString().trim().equals("")?"0.00":eobj.get(3).toString().trim()));
					apartyMaterial.setConstruct_Aparty_material_unit(eobj.get(2).toString().trim());
					apartyDao.save_aPartyMaterial(apartyMaterial);
				}
			
		}
	}

	@Override
	public Map<String, Object> getConstruct(String fields,String where) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fields", fields);
		param.put("where", where);	
		return apartyDao.getMap(param);
	}

	@Override
	@Transactional
	public int save_aPartyPur(ApartyPur apartyPur,HttpServletRequest request) throws Exception {
		String rows = request.getParameter("rows");
		User user = (User) request.getSession().getAttribute("loginUser");
	
		String project_name=request.getParameter("project_name");
		int construct_Aparty_purchase_id = apartyPur.getConstruct_Aparty_purchase_id();
		if (construct_Aparty_purchase_id==0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			apartyPur.setConstruct_Aparty_purchase_creatTime(df.format(new Date()).toString());
			apartyPur.setConstruct_Aparty_purchase_status("1");
			apartyPur.setConstruct_Aparty_purchase_orderNum("JG"+System.currentTimeMillis());
			apartyDao.save_aPartyPurHead(apartyPur);
			construct_Aparty_purchase_id = apartyPur.getConstruct_Aparty_purchase_id();
			
			//启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("applyUserId",user.getUserid()+"");
			String processDefinitioKey = "aPartyPurView"; // 定义流程的key,不可修改
			String bizId = processDefinitioKey + "." + construct_Aparty_purchase_id;; // 获取业务id
		ProcessInstance pi=runtimeService.startProcessInstanceByKey(processDefinitioKey, bizId,vars);

		 	DataProcinst dataProcinst=new  DataProcinst();
				dataProcinst.setProc_inst_id_(pi.getId());
				dataProcinst.setApplicant(user.getUsername());
				dataProcinst.setIllustrate(project_name+"/订单编号:"+apartyPur.getConstruct_Aparty_purchase_orderNum());
			    activitiDao.insertDataProcinst(dataProcinst);
			
		}else {
			apartyDao.save_aPartyPurHead(apartyPur);
		}
		JSONArray myJsonArray = JSONArray.fromObject(rows);
		for (int i = 0; i < myJsonArray.size(); i++) {
			ApartyPurEntry apartyPurEntry = (ApartyPurEntry) JSONObject.toBean(myJsonArray.getJSONObject(i),  
					ApartyPurEntry.class);
			apartyPurEntry.setConstruct_Aparty_purEntry_parentId(apartyPur.getConstruct_Aparty_purchase_id());
			apartyDao.save_aPartyPurEntry(apartyPurEntry);
		}
		return construct_Aparty_purchase_id;
	}

	@Override
	@Transactional
	public void dele_apartyPur(String id) {
		// TODO Auto-generated method stub
		StringBuffer sbf = new StringBuffer();
		sbf.append(" where construct_Aparty_purchase_id = "+id);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("where", sbf);
		apartyDao.dele_apartyPur(map);
		
		sbf.delete(0, sbf.length());
		sbf.append(" where construct_Aparty_purEntry_parentId = "+id);
		delete_aPartyEntry(sbf.toString());
	}

	@Override
	public void delete_aPartyEntry(String where) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("where", where);
		apartyDao.delete_aPartyEntry(map);
	}

	/**
	 * 更新  construct_aparty_purchase 表
	 * 
	 * */
	@Override
	public void updateApartyPur(ApartyPur apartyPur) {
		
		apartyDao.updateApartyPur(apartyPur); 
		
	}

	/***
	 *  @param execution 流程设置参数
	 *  @param  status 状态
	 *  
	 * */
	@Override
	@Transactional
	public void apartyPurStatus(DelegateExecution execution, String status) {
		String bizkey= execution.getProcessBusinessKey();
		
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}
		ApartyPur apartyPur=new ApartyPur();
		apartyPur.setConstruct_Aparty_purchase_id(Integer.valueOf(bizId));
		apartyPur.setConstruct_Aparty_purchase_status(status);
		updateApartyPur(apartyPur);
			
	}

	@SuppressWarnings("unused")
	@Transactional
	public void aPartyPurPass(HttpServletRequest request) throws Exception {
		 
			String taskid=request.getParameter("taskid");
			String taskName=request.getParameter("taskName");
			String options=request.getParameter("reason");//获取审核意见
			String type=request.getParameter("type");//获取审核意见
			String data=request.getParameter("data");//获取审核意见
			User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
		    String userid=user.getUserid()+"";  //当前审核人id
		   String  nextUser=null;
		   
		   if(taskName.equals("项目经理")){
		     JSONArray myJsonArray = JSONArray.fromObject(data);
				for (int i = 0; i < myJsonArray.size(); i++) {
				int  pid =Integer.valueOf(myJsonArray.getJSONObject(0).get("construct_Aparty_purEntry_parentId")+"");
					ApartyPurEntry apartyPurEntry = (ApartyPurEntry) JSONObject.toBean(myJsonArray.getJSONObject(i),  
							ApartyPurEntry.class);
					apartyPurEntry.setConstruct_Aparty_purEntry_parentId(pid);
					apartyDao.save_aPartyPurEntry(apartyPurEntry);
				}
		   }
	
			Map<String, Object> vars=new HashMap<String, Object>();
			 if(StringUtils.isNotEmpty(type)){
					vars.put("type",type);	
					
			 }
				activitiService.comment(taskid,userid,options);
				taskService.complete(taskid,vars);	
			
		 
		
	}

	@Override
	public List<Map<String, Object>> dataList(String fields, String where) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);
		return apartyDao.getData(map);
	}
	
	//流程定义类方法
	  public  String aPartyNextUser(DelegateExecution execution) {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String nextUser = request.getParameter("username");// 下一个审核人	
			return nextUser;		
		
	    }
	
}
