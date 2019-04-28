package com.casd.serviceimpl.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.utils.JsonUtil;
import com.casd.dao.finance.MaterialDao;
import com.casd.dao.finance.MaterialPriceDao;
import com.casd.dao.finance.SupplierDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.finance.Construct_change_head;
import com.casd.entity.finance.Construct_change_price;
import com.casd.entity.finance.Supplier;
import com.casd.entity.uc.User;
import com.casd.service.finance.SupplierService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;



@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private UserDao userDao;
	@Autowired
	private MaterialPriceDao  materialPriceDao;

	@Autowired
	private MaterialDao materialDao;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
    private ActivitiService activitiService;
	
	 @Autowired    
	private TaskService taskService; 
	


	@Override
	public void saveSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		supplierDao.saveSupplier(supplier);
	}

	@Override
	public void deleSupplier(Map<String, Object> map) {
		// TODO Auto-generated method stub
		supplierDao.deleSupplier(map);
	}


	@Override
	public int add_change_head(Construct_change_head cch) {
		supplierDao.add_change_head(cch);

		return 0;
	}
	
	//启动流程更改状态
	@Transactional
	public void start_change_price(HttpServletRequest request) {
		
		    User user=(User) request.getSession().getAttribute("loginUser");
	 		Map<String, Object> paramMap=new HashMap<String, Object>();
	 		String processDefinitioKey = "change_price"; // 定义流程的key,不可修改
			String title ="价格变更"; // 标题
			String bizId = request.getParameter("bizId");// 获取业务id
			 bizId = processDefinitioKey + "."
					+ bizId; //key
			Map<String, Object> vars=new HashMap<String, Object>();
	 	    vars.put("username", user.getUserid()+"");//设置第一个人审批人为申请人 
	 	    vars.put("title", title); //任务类型 
	 	    
	 	runtimeService.startProcessInstanceByKey(processDefinitioKey,bizId,vars);
			
	 		Map<String, Object> linkMap = new HashMap<String, Object>();
	    

			 linkMap.put("filds","status="+1);
			 linkMap.put("bizId",request.getParameter("bizId"));
			 supplierDao.updateStatus(linkMap);

	}


	@Override
	@Transactional
	public void change_examine(HttpServletRequest request) {
		
		
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		Map<String, Object> vars=new HashMap<String, Object>();
		  String taskid=request.getParameter("taskid");
	
		User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
		String username=user.getUserid()+"";
	
		String taskName=request.getParameter("taskName");
		String nextUser=request.getParameter("username");//下一个审核人
		String options=request.getParameter("options");//获取审核意见

		 /** 查询用户所在部门和所在中心*/
		  Map<String, Object> params=new HashMap<String, Object>();
		  String  varfields="b.center_name,c.department_name";
		  String  varwhere ="uc_user a";
		          varwhere+=" LEFT JOIN uc_department  c on c.department_id=a.department";
		          varwhere+=" LEFT JOIN  uc_center  b on a.center_id=b.center_id";
		          varwhere+=" WHERE a.userid="+user.getUserid();

		  params.put("fields", varfields);
		  params.put("where", varwhere);
		 Map<String, Object> mapList=userDao.queryUserById(params);
		String center_name =mapList.get("center_name").toString();
		String department_name = null;
		if(mapList.containsKey("department_name")){
			department_name=mapList.get("department_name").toString();
		}
		String auditor;
		
		if(department_name==null){
			auditor=center_name;
		}else {
			auditor=department_name;
		}
		  Map<String, Object> map=new HashMap<String, Object>();
		if(taskName.equals("供应商")){
			vars.put("cgzx", nextUser);				
			Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
			//2.使用任务ID，获取实例ID
			String processInstanceId = task.getProcessInstanceId();
			//3.使用流程实例，查询
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//4.使用流程实例对象获取BusinessKey
			String business_key = pi.getBusinessKey();
			//5.获取Business_key对应的主键ＩＤ
			String id = "";
			if(StringUtils.isNotBlank(business_key)){
				//截取字符串
				id = business_key.split("\\.")[1].toString();
				
			}	
			 int status=1;
			 map.put("filds","status="+status);
			 map.put("bizId",id);
			
			 supplierDao.updateStatus(map);
			
		}else if(taskName.equals("采购中心")){
			vars.put("sign", "true");
			vars.put("cwzx", nextUser);
		}else if(taskName.equals("财务中心")){
			vars.put("dsh", nextUser);	
		}else if(taskName.equals("董事会")){
         

			Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
			//2.使用任务ID，获取实例ID
			String processInstanceId = task.getProcessInstanceId();
			//3.使用流程实例，查询
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//4.使用流程实例对象获取BusinessKey
			String business_key = pi.getBusinessKey();
			//5.获取Business_key对应的主键ＩＤ
			String id = "";
			if(StringUtils.isNotBlank(business_key)){
				//截取字符串
				id = business_key.split("\\.")[1].toString();
				
			}
			/** 查询变更列表***/
			Map<String, Object> param=new HashMap<String, Object>();
			String fields="a.construct_material_price ,a.construct_latest_price,a.construct_lowest_price,a.construct_material_priceId,"
					    + "b.construct_material_price as new_price,b.construct_latest_price as latest_price,"
					    + "b.construct_lowest_price as lowest_price";	
			StringBuffer sbf=new StringBuffer();
			 sbf.append(" construct_material_price a JOIN construct_change_price b"
			 		  + " ON a.construct_material_priceId = b.construct_material_priceId"
			 		  + " where b.construct_change_headId="+id);
			 param.put("fields", fields);
			 param.put("where", sbf.toString());
		   List<Map<String, Object>>dataList= supplierDao.findPriceratio(param);	
		  
		   for (Map<String, Object> c:dataList) {
			   double material_price=Double.parseDouble(c.get("new_price").toString());
			   double lowest_price=Double.parseDouble(c.get("lowest_price").toString());
			   int material_priceId=Integer.parseInt(c.get("construct_material_priceId").toString());
			   String wath = "";
			   if (material_price<lowest_price||lowest_price==0) {
				          wath+=" construct_material_price="+material_price;
				          wath+=",construct_lowest_price="+material_price;
				   
			   }else {
				      wath+=" construct_material_price="+material_price;

		        	}
			   wath+="  where construct_material_priceId="+material_priceId;
			   param.put("wath", wath);
			   materialPriceDao.update_price(param);
			
		   }
		     int status=3;
			 map.put("filds","status="+status);
			 map.put("bizId",id);
			
			 supplierDao.updateStatus(map);
			
		}
		activitiService.comment(taskid,auditor,options);
		taskService.complete(taskid,vars);

	  	
	}


	@Override
	@Transactional
	public void new_update_price(String id,String price) {
		Map<String, Object> map=new HashMap<String, Object>();
	    String wath=" construct_material_price="+price
	    		  + " where construct_material_priceId="+id;	 
	    map.put("wath", wath);
		supplierDao.new_update_price(map);
		
		
	}


	@Override
	@Transactional
	public Object add_price(HttpServletRequest request) throws Exception {
		String list=	request.getParameter("datas");
	 	   
 	 	List<Map<String, Object>> dataList=JsonUtil.parseJsonArrayStrToListForMaps(list);
 	 	int bizId=Integer.parseInt(request.getParameter("bizId"));
 	 	String  where="construct_change_price a where 1=1 ";
 	 	Map<String, Object> json=new HashMap<String, Object>();
 	 	Map<String, Object> map=new HashMap<String, Object>();
 	 	
 	 	for (Map<String, Object> c: dataList) {
 	 	   map.clear();
			Construct_change_price cch=new Construct_change_price();
			cch.setConstruct_change_id(Integer.parseInt(c.get("construct_material_priceId").toString()));
			cch.setConstruct_change_headId(Integer.parseInt(c.get("construct_material_priceId").toString()));
			cch.setConstruct_material_priceId(Integer.parseInt(c.get("construct_material_priceId").toString()));
			cch.setConstruct_material_name(c.get("construct_material_name").toString());
			cch.setConstruct_material_model(c.get("construct_material_model").toString());
			cch.setConstruct_material_unit(c.get("construct_material_unit").toString());

			cch.setConstruct_material_price(Double.parseDouble(c.get("construct_material_price").toString()));
			cch.setConstruct_material_supplierId(Integer.parseInt(c.get("construct_material_supplierId").toString()));
			cch.setConstruct_lowest_price(Double.parseDouble(c.get("construct_lowest_price").toString()));
			cch.setConstruct_latest_price(Double.parseDouble(c.get("construct_latest_price").toString()));
			cch.setConstruct_change_headId(bizId);
			where+=" and a.construct_change_headId="+bizId
					+ " AND construct_material_priceId="+c.get("construct_material_priceId");
			map.put("where",where);
	     int count= materialPriceDao.getMaterialPriceCount(map);
	  
	     if(count>0){
	    	 throw new Exception("数据重复编号："+c.get("construct_material_priceId"));
	     }
			
	        materialPriceDao.add_NewPrice(cch);
		
 	 	}
		return json;
	}
	@Transactional
	public void deleteNewPrice(Map<String, Object> map) {
		materialPriceDao.deleteNewPrice(map);
		
	}
   
	
	//驳回价格
	@Transactional
	public void back_Newprice(HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();

		User user = (User) request.getSession().getAttribute("loginUser");
	

		/** 查询用户所在部门和所在中心 */
		Map<String, Object> params = new HashMap<String, Object>();
		String varfields = "b.center_name,c.department_name";
		String varwhere = "uc_user a";
		varwhere += " LEFT JOIN uc_department  c on c.department_id=a.department";
		varwhere += " LEFT JOIN  uc_center  b on a.center_id=b.center_id";
		varwhere += " WHERE a.userid=" + user.getUserid();

		params.put("fields", varfields);
		params.put("where", varwhere);
		Map<String, Object> mapList = userDao.queryUserById(params);
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
		String taskid = request.getParameter("taskid");// 任务id
		String options = request.getParameter("options");// 审核意见
		vars.put("sign", "false");
		activitiService.comment(taskid, auditor,options);// 添加审核记录
		taskService.complete(taskid, vars);// 完成任务

	}
	

	
	// 循环所有分类插入
	@Override
	@Transactional
	public Map<String, Object> toMaterialPrice() {
		String seriesID="";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", "*");
		map.put("where", "construct_material_series");
		dataList = materialDao.selectData(map);

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> data = dataList.get(i);
			String seriesSuppliers = data.get(
					"construct_material_seriesSuppliers").toString();
			String[] supplierIds = seriesSuppliers.split(",");
			
			 seriesID = data.get("construct_material_seriesID")
					.toString();
			
			List<Map<String, Object>> materialData = new ArrayList<Map<String, Object>>();
			map.put("fields", " model.construct_material_model_id,model.construct_material_model_unit,model.construct_material_model_name,material.construct_material_name ");
			map.put("where", "construct_material_model model "
					+ " LEFT JOIN construct_material_table material on model.construct_material_model_parentid =material.construct_material_id  "
					+ " LEFT JOIN construct_material_series series on material.construct_material_seriesId=series.construct_material_seriesID "
					+ " where series.construct_material_seriesID="+seriesID+" ");
			materialData = materialDao.selectData(map);
			
			
			for (int j = 0; j < supplierIds.length; j++) {
				int construct_material_supplierId = Integer.valueOf(supplierIds[j].toString());
				map.clear();
				map.put("table", "construct_material_price");
				for (int k = 0; k < materialData.size(); k++) {
					Map<String, Object> materialMap = materialData.get(k);
					
					map.put("fieds", "("+construct_material_supplierId+","+materialMap.get("construct_material_model_id")+","
							+ "'"+materialMap.get("construct_material_model_unit")+"','"+materialMap.get("construct_material_model_name")+"','"
									+ ""+materialMap.get("construct_material_name")+"')");
					materialDao.insertData(map);
				}
			}
		}
	} catch (Exception e) {
		System.out.println("错误是"+e+"类别是"+seriesID);
		jsonMap.put("msg", "操作失败,错误是"+e+"类别是"+seriesID);
	}
		return jsonMap;
	}

	
	//流程修改单据状态
	@Override
	public void updateBizStatus(DelegateExecution execution, String status) {
	String bizkey= execution.getProcessBusinessKey();
	Map<String, Object> linkMap = new HashMap<String, Object>();
		String[] strs=bizkey.split("\\.");
        String bizId=null;
		for(int i=0,len=strs.length;i<len;i++){
			bizId=strs[i].toString();
		}


         linkMap.put("filds","status="+status);
		 linkMap.put("bizId",bizId);
		 supplierDao.updateStatus(linkMap);
		
	}

}
