package com.casd.controller.construct;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.DownloadUtil;
import com.casd.dao.activiti.ActivitiDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.PurchaseService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class PurchaseController {

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ConstructService constructService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivitiDao activitiDao;

	// private int projectId = 0;

	private int flow_bill_id; // 跳转审核界面才有值

	/**
	 * 申请采购list
	 */
	@RequestMapping(value = "/purchaseList", method = RequestMethod.GET)
	public ModelAndView purchaseList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projectId",
				Integer.valueOf(request.getParameter("construct_project_id")));
		mv.setViewName("construct/purchaseList");
		return mv;
	}

	@RequestMapping(value = "/purchaseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> purchaseList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		int projectId = Integer.valueOf(request
				.getParameter("construct_project_id"));
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String filds = "*";

		sbf.append(" construct_purchase_head pur left join construct_project_table pro on pur.construct_purchase_projectId=pro.construct_project_id where 1=1 and pro.construct_project_id="
				+ projectId + "");
		sbf.append(" ORDER BY pur.construct_purchase_id DESC");

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = purchaseService.purchaseList(filds,
				pageIndex, pageSize, record, sbf.toString());
		json.put("projectId", projectId);
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	// 申请采购，修改
	@RequestMapping(value = "/purchaseNew", method = RequestMethod.GET)
	public ModelAndView purchaseNew(HttpServletRequest request, Model model)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		StringBuffer sbf = new StringBuffer();
		Construct construct = new Construct();
		int projectId = Integer.valueOf(request.getParameter("projectId")
				.toString());

		String fields = "construct_project_id,construct_project_name,construct_project_addr,construct_project_leader,construct_project_leaderTel";
		sbf.append(" construct_project_table where construct_project_id="
				+ projectId + "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		// 项目信息
		Map<String, Object> data= constructService.getConstructById(map);
		construct =(Construct) data.get("construct");
		mv.addObject("construct", construct);

		// 审核人初始化 采购申请流程标签purApp001
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", "purApp001");
		paramMap.put("order", 1);
		List<Map<String, Object>> auditorList = flowService
				.getAuditors(paramMap);
		mv.addObject("auditor", auditorList);

		JSONObject json = new JSONObject();
		Map<String, Object> maps = new HashMap<String, Object>();
		// 修改初始化
		if (!request.getParameter("construct_purchase_id").toString().isEmpty()) {
			int construct_purchase_status = Integer.valueOf(request
					.getParameter("construct_purchase_status"));
			maps = purchaseService.initPurchase(
					request.getParameter("construct_purchase_id").toString(),
					"update", construct_purchase_status);
			mv.addObject("purchaseHead", maps.get("purchaseHead"));
			json.put("rows", maps.get("rows"));
			model.addAttribute("rows", json);

		}
		mv.addObject("projectId", projectId);
		model.addAttribute("rows", json);
		mv.setViewName("construct/purchaseNew");
		return mv;
	}

	// 查看
	@RequestMapping(value = "/Purchase_payment", method = RequestMethod.GET)
	public ModelAndView purchaseView(HttpServletRequest request, Model model)
			throws Exception {
		ModelAndView mv = new ModelAndView();

		String bizId = request.getParameter("bizId");
		String beyId = "Purchase_payment"; // 流程实例key 请勿改动

		List<Map<String, Object>> historyList = activitiService
				.viewHisComments(bizId, beyId);

		Map<String, Object> maps = new HashMap<String, Object>();
		if (!bizId.isEmpty()) {
			maps = purchaseService.initPurchase(bizId, "view", 0);
			mv.addObject("purchaseHead", maps.get("purchaseHead"));
			JSONObject json = new JSONObject();
			json.put("rows", maps.get("rows"));
			model.addAttribute("rows", json);

			PurchaseHead head = (PurchaseHead) maps.get("purchaseHead");
			StringBuffer sbf = new StringBuffer();
			Construct construct = new Construct();
			String fields = "construct_project_id,construct_project_name,construct_project_addr,construct_project_leader,construct_project_leaderTel";
			sbf.append(" construct_project_table where construct_project_id="
					+ head.getConstruct_purchase_projectId() + "");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fields", fields);
			map.put("where", sbf);
			Map<String, Object> data= constructService.getConstructById(map);
			construct =(Construct) data.get("construct");
			mv.addObject("construct", construct);

			// 审核历史意见
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("history", historyList);
			model.addAttribute("history", jsonObject);
		}
		
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		String[] temp = new String[]{"项目助理","项目经理","专业公司","成本中心经理","总裁","成本中心采购","供应商","项目部签收","成本中心核对","供应商结算申请","成本中心提交结算申请","财务结算中心"};
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
//		mv.addObject("activityList", activityList);	
		mv.setViewName("construct/purchaseView");
		return mv;
	}

	// 选择合同工程量
	@RequestMapping(value = "/proQuantyCheck", method = RequestMethod.GET)
	public ModelAndView proQuantyCheck(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		int index = Integer.valueOf(request.getParameter("index").toString());
		mv.addObject("index", index);
		mv.setViewName("checkBox/proQuantyCheck");
		return mv;
	}

	@RequestMapping(value = "/proQuantyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> proQuantyCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int materialSerId = Integer.valueOf(request.getParameter(
				"materialSerId").toString());
		int projectId = Integer.valueOf(request.getParameter("projectId")
				.toString());
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();

		String filds = "construct_project_quantities_id,construct_project_quantities_name,construct_project_quantities_model,construct_material_model_name,construct_material_name,construct_material_model_unit,"
				+ " construct_project_quantities_num,construct_project_quantities_unit,construct_project_quantities_price,"
				+ " construct_project_quantities_remarks,construct_project_quantities_conId, sum ";
		
		sbf.append(" (select q.construct_project_quantities_id,q.construct_project_quantities_name,q.construct_project_quantities_model, ");
		sbf.append(" q.construct_project_quantities_num,q.construct_project_quantities_unit,q.construct_project_quantities_price,model.construct_material_model_name,model.construct_material_model_unit,material.construct_material_name, ");
		sbf.append(" q.construct_project_quantities_remarks,q.construct_project_quantities_conId,SUM(pur.construct_purchase_applyNum) sum ");
		sbf.append(" from construct_project_quantities q ");
		sbf.append(" LEFT JOIN construct_purchase_entry pur on q.construct_project_quantities_id=pur.construct_purchase_quantitiesId ");
		sbf.append(" LEFT JOIN construct_material_model model on model.construct_material_model_id=q.construct_project_quantities_modelId  ");
		sbf.append(" LEFT JOIN construct_material_table material on material.construct_material_id =model.construct_material_model_parentid  ");
		sbf.append(" LEFT JOIN construct_material_series series on series.construct_material_seriesID =material.construct_material_seriesId  ");
		sbf.append(" where  construct_project_quantities_conId= ");
		sbf.append(" " + projectId+ " and series.construct_material_seriesID= ");
		sbf.append("" + materialSerId );
		sbf.append("  GROUP BY q.construct_project_quantities_id ) tableAll where 1=1 ");
		
		String construct_project_quantities_name = request.getParameter("construct_project_quantities_name");
		if (StringUtils.hasText(construct_project_quantities_name)) {
			sbf.append(" and  tableAll.construct_project_quantities_name like '%" + construct_project_quantities_name
					+ "%'");
		}
		String construct_project_quantities_model = request.getParameter("construct_project_quantities_model");
		if (StringUtils.hasText(construct_project_quantities_model)) {
			sbf.append(" and  tableAll.construct_project_quantities_model like '%" + construct_project_quantities_model
					+ "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = purchaseService.purchaseList(filds,
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	// 保存、修改后保存
	@RequestMapping(value = "/submitPurchase", method = RequestMethod.POST)
	@ResponseBody
	public String submitPurchase(HttpServletRequest request) throws Exception {
		PurchaseHead purchaseHead = new PurchaseHead();
		if (!request.getParameter("construct_purchase_arriveDate").toString()
				.isEmpty()) {
			purchaseHead.setConstruct_purchase_arriveDate(Date.valueOf(request
					.getParameter("construct_purchase_arriveDate").toString()));
		}
		purchaseHead.setConstruct_purchase_id(Integer.valueOf(request
				.getParameter("construct_purchase_id").toString() == "" ? "0"	
				: request.getParameter("construct_purchase_id").toString()));
		if (!request.getParameter("construct_purchase_arriveDate").toString()
				.isEmpty()) {
			purchaseHead.setConstruct_purchase_planDate(Date.valueOf(request
					.getParameter("construct_purchase_planDate")));
		}
		purchaseHead.setConstruct_purchase_planMan(request
				.getParameter("construct_purchase_planMan"));
		purchaseHead.setConstruct_purchase_projectId(Integer.valueOf(request
				.getParameter("construct_project_id").toString()));
		purchaseHead.setConstruct_purchase_reviewer(request
				.getParameter("construct_purchase_reviewer"));
		purchaseHead.setConstruct_purchase_supplier(request
				.getParameter("construct_purchase_supplier"));
		purchaseHead.setConstruct_purchase_supplierTel(request
				.getParameter("construct_purchase_supplierTel"));
		purchaseHead.setConstruct_purchase_materialSerId(Integer
				.valueOf(request.getParameter(
						"construct_purchase_materialSerId").toString()));
		purchaseHead.setConstruct_purchase_materialSerName(request
				.getParameter("construct_purchase_materialSerName"));
		String rows = request.getParameter("rows");
		purchaseService.submitPurchase(purchaseHead, rows);
		return "";
	}

	// 提交（每张单只允许提交一次）
	@RequestMapping(value = "/auditPurchase", method = RequestMethod.POST)
	@ResponseBody
	public String auditPurchase(HttpServletRequest request) throws Exception {
		PurchaseHead purchaseHead = new PurchaseHead();
		if (!request.getParameter("construct_purchase_arriveDate").toString()
				.isEmpty()) {
			purchaseHead.setConstruct_purchase_arriveDate(Date.valueOf(request
					.getParameter("construct_purchase_arriveDate").toString()));
		}
		purchaseHead.setConstruct_purchase_id(Integer.valueOf(request
				.getParameter("construct_purchase_id").toString() == "" ? "0"
				: request.getParameter("construct_purchase_id").toString()));
		if (!request.getParameter("construct_purchase_arriveDate").toString()
				.isEmpty()) {
			purchaseHead.setConstruct_purchase_planDate(Date.valueOf(request
					.getParameter("construct_purchase_planDate")));
		}
		purchaseHead.setConstruct_purchase_planMan(request
				.getParameter("construct_purchase_planMan"));
		purchaseHead.setConstruct_purchase_projectId(Integer.valueOf(request
				.getParameter("construct_project_id").toString()));
		purchaseHead.setConstruct_purchase_reviewer(request
				.getParameter("construct_purchase_reviewer"));
		purchaseHead.setConstruct_purchase_supplier(request
				.getParameter("construct_purchase_supplier"));
		purchaseHead.setConstruct_purchase_supplierTel(request
				.getParameter("construct_purchase_supplierTel"));
		purchaseHead.setConstruct_purchase_materialSerId(Integer
				.valueOf(request.getParameter(
						"construct_purchase_materialSerId").toString()));
		purchaseHead.setConstruct_purchase_materialSerName(request
				.getParameter("construct_purchase_materialSerName"));
		purchaseHead.setConstruct_purchase_status(1);
		String rows = request.getParameter("rows");
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		String username = loginUser.getUsername();
		String auditor = request.getParameter("auditor");
		purchaseService.auditPurchase(purchaseHead, rows, username, auditor);
		return "";
	}

	// 删除
	@RequestMapping(value = "/purchaseDele", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> purchaseDele(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String headId = request.getParameter("construct_purchase_id");
		int construct_purchase_status = Integer.valueOf(request
				.getParameter("construct_purchase_status"));
		return purchaseService.delePurchase(headId, construct_purchase_status);
	}

	/**
	 * 采购申请单审核界面 审核
	 */
	@RequestMapping(value = "/purchaseAudit", method = RequestMethod.POST)
	@ResponseBody
	public String purchaseAudit(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		// PurchaseHeadClass purchaseHeadClass=new PurchaseHeadClass();
		// String rows =request.getParameter("rows");
		int construct_purchase_id = Integer.valueOf(request.getParameter(
				"construct_purchase_id").toString());
		String auditor = request.getParameter("auditor").toString();
		int flow_node_order = Integer.valueOf(request.getParameter(
				"flow_node_order").toString());

		/*
		 * if(auditor==""){
		 * 
		 * int construct_purchase_projectId
		 * =Integer.valueOf(request.getParameter
		 * ("construct_purchase_projectId").toString()); String
		 * construct_purchase_planMan
		 * =request.getParameter("construct_purchase_planMan"); String
		 * construct_purchase_reviewer
		 * =request.getParameter("construct_purchase_reviewer");
		 * 
		 * purchaseHeadClass.setConstruct_purchase_applyId(construct_purchase_id)
		 * ;
		 * 
		 * if(!request.getParameter("construct_purchase_planDate").toString().
		 * isEmpty()){
		 * purchaseHeadClass.setConstruct_purchase_arriveDate(Date.valueOf
		 * (request.getParameter("construct_purchase_planDate").toString())); }
		 * if(!request.getParameter("construct_purchase_arriveDate").toString().
		 * isEmpty()){
		 * purchaseHeadClass.setConstruct_purchase_planDate(Date.valueOf
		 * (request.getParameter("construct_purchase_arriveDate").toString()));
		 * }
		 * 
		 * purchaseHeadClass.setConstruct_purchase_planMan(
		 * construct_purchase_planMan);
		 * purchaseHeadClass.setConstruct_purchase_projectId
		 * (construct_purchase_projectId);
		 * purchaseHeadClass.setConstruct_purchase_reviewer
		 * (construct_purchase_reviewer); }
		 */
		String option = request.getParameter("option");
		int auditID = Integer.valueOf(request.getParameter("auditID"));
		Date currentDate = new Date(System.currentTimeMillis());
		map.put("billID", construct_purchase_id);
		map.put("auditor", auditor);
		map.put("option", option);
		map.put("auditID", auditID);
		map.put("flow_node_order", flow_node_order);
		map.put("flow_audit_time", currentDate);
		map.put("where", "flow_audit_table");
		// String
		// message=purchaseService.auditBill(map,auditor,rows,purchaseHeadClass);
		String message = purchaseService.auditBill(map, auditor);
		return message;
	}

	/**
	 * 跳转审核界面，初始化数据
	 */
	@RequestMapping(value = "/purAuditView", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView purAuditView(HttpServletRequest request, Model model) {

		ModelAndView mv = new ModelAndView();
		// 取前台参数
		int auditID = Integer.parseInt(request.getParameter("auditID")
				.toString());
		flow_bill_id = Integer.parseInt(request.getParameter("flow_bill_id")
				.toString());
		int flow_status = Integer.parseInt(request.getParameter("flow_status")
				.toString());
		int flow_audit_next_auditid = Integer.parseInt(request.getParameter(
				"flow_audit_next_auditid").toString());
		int flow_audit_last_auditid = Integer.parseInt(request.getParameter(
				"flow_audit_last_auditid").toString());
		int flow_node_order = Integer.parseInt(request.getParameter(
				"flow_node_order").toString());

		// 初始化单据信息
		Map<String, Object> data = new HashMap<String, Object>();
		data = purchaseService.getHeadData(flow_bill_id);
		mv.addObject("head", data.get("pHead"));
		mv.addObject("supplier", data.get("supplier"));
		JSONObject json = new JSONObject();
		json.put("rows", data.get("Entries"));
		json.put("columns", data.get("columns"));

		List<Map<String, Object>> entryDataList = (List<Map<String, Object>>) data
				.get("Entries");
		List<Map<String, Object>> supplierDataList = (List<Map<String, Object>>) data
				.get("supplier");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		footMap.put("construct_purchase_entryId", "合计");
		for (int i = 0; i < supplierDataList.size(); i++) {
			Double total = 0.0;
			String construct_supplier_name = supplierDataList.get(i)
					.get("construct_supplier_name").toString();
			for (int m = 0; m < entryDataList.size(); m++) {
				total = total
						+ (Double
								.valueOf(entryDataList.get(m)
										.get("construct_purchase_applyNum")
										.toString() == "" ? "0.0"
										: entryDataList
												.get(m)
												.get("construct_purchase_applyNum")
												.toString()) * Double
								.valueOf(entryDataList.get(m)
										.get(construct_supplier_name)
										.toString() == "" ? "0.0"
										: entryDataList.get(m)
												.get(construct_supplier_name)
												.toString()));
			}
			footMap.put(construct_supplier_name, total);
		}
		list.add(footMap);
		json.put("footer", list);
		model.addAttribute("rows", json);

		// 参数传到页面
		mv.addObject("auditID", auditID);
		mv.addObject("flow_status", flow_status);
		mv.addObject("flow_audit_next_auditid", flow_audit_next_auditid);
		mv.addObject("flow_audit_last_auditid", flow_audit_last_auditid);
		mv.addObject("flow_node_order", flow_node_order);

		// 审核人初始化 采购申请流程标签purApp001
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", "purApp001");
		paramMap.put("order", flow_node_order);
		List<Map<String, Object>> auditorList = flowService
				.getAuditors(paramMap);
		mv.addObject("auditor", auditorList);
		mv.addObject("length", auditorList.size());
		// 审核历史意见
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flow_bill_id", flow_bill_id);
		List<Map<String, Object>> listsMaps = purchaseService
				.getAuditOption(param);
		// jsonObject.put("data", listsMaps);
		model.addAttribute("audit", jsonObject);
		mv.addObject("data", listsMaps);
		mv.setViewName("construct/purchaseAudit");
		return mv;
	}


	/**
	 * 选择供应商
	 */
	@RequestMapping(value = "/chooseSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chooseSupplier(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String rows = request.getParameter("rows");
		int purchase_id = Integer.valueOf(request.getParameter("purchase_id"));
		int supplier = Integer.valueOf(request.getParameter("supplier"));
		String supplierName = request.getParameter("supplierName");
		String erro = purchaseService.chooseSupplier(rows, supplier,
				supplierName, purchase_id);
		map.put("erro", erro);
		return map;
	}

	/**
	 * 启动采购申请流程
	 * 
	 * @param 申请人
	 *            name
	 * @param 业务单据id
	 *            bizId
	 * @param 流程定义的key值
	 *            processDefinitioKey
	 * @author liao
	 */
	@RequestMapping(value = "/start_up", method = RequestMethod.POST)
	@ResponseBody
	public Object start_up(HttpServletRequest request) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			User user = (User) request.getSession().getAttribute("loginUser");
			String userid = user.getUserid() + "";// 获取申请人

			String processDefinitioKey = "Purchase_payment"; // 定义流程的key,不可修改

			String bizId = processDefinitioKey + "."
					+ request.getParameter("purchase_id"); // 获取业务id
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("applyUserId", userid);
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(
					processDefinitioKey, bizId, vars);
//	ProcessInstance	pi=	activitiService.startProcesses(bizId, userid, processDefinitioKey,null);
	DataProcinst dataProcinst=new  DataProcinst();
	dataProcinst.setProc_inst_id_(pi.getId());
	dataProcinst.setApplicant(user.getUsername());
	dataProcinst.setIllustrate(request.getParameter("project_name")+"/"+request.getParameter("materialSerName"));
	activitiDao.insertDataProcinst(dataProcinst);
			json.put("Success", "启动成功,请审核！");
		} catch (Exception e) {
			json.put("Msg", "启动失败,请联系技术员！");
			json.put("Success", false);
		}
		return json;
	}

	/**
	 * 查看采购申请单据
	 * 
	 * @param 获取流程实例id
	 *            processInstanceId
	 * @throws Exception
	 */
	@RequestMapping(value = "/Purchase_payment{taskid}", method = RequestMethod.GET)
	public ModelAndView purchaseTask(HttpServletRequest request, Model model)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String taskid = request.getParameter("taskid");// 获取任务id
		String processInstanceId=request.getParameter("processInstanceId");
		//临时兼容旧流程
		List<Map<String, Object>> aa  = userService.queryId(" PROC_DEF_ID_ ", " act_ru_task where PROC_INST_ID_= " + processInstanceId);
		String version = aa.get(0).get("PROC_DEF_ID_").toString().split(":")[1];
		mv.addObject("version", version);
		List<String> bot = this.activitiService.findOutComeListByTaskId(taskid);//留下
	    if (bot.size() > 1){
	      model.addAttribute("bot", "批准");
	      model.addAttribute("both", "驳回");
	    }else{
	      model.addAttribute("bot", "批准");
	    }
		//临时兼容结束
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		String taskName = request.getParameter("taskName");

		List<Map<String, Object>> userList = activitiService
				.backTaskTab(taskid);// 查询审核人
	

		List<Map<String, Object>> historyList = activitiService.getProcessComments(taskid);// 查询审批记录
		try {
			Object startForm = activitiService.getRenderedTaskForm(taskid); // 获取表单
			mv.addObject("startForm", startForm);
		} catch (Exception e) {
			
		}
		
		List<Map<String,Object>> activityList= new ArrayList<Map<String,Object>>();//获取节点
		String[] temp = new String[]{};
		if("2".equals(version)){
			 temp = new String[]{"项目经理提出申请","经营部审核","总经理审核","采购核对单价","财务经理审批","董事会审批","申请采购单下单"
					 ,"配货","核对签收","复核价格及数量","申请请款","通知财务"};
		}else{
			 temp = new String[]{"项目助理","项目经理","专业公司","成本中心经理","总裁","成本中心采购","供应商","项目部签收","成本中心核对","供应商结算申请","成本中心提交结算申请","财务结算中心"};
		}
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);		
		// 审核历史意见
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("history", historyList);
		model.addAttribute("history", jsonObject);
		
		// model.addAttribute("comments", comments);
		model.addAttribute("userList", userList);
		// 初始化单据信息
		Map<String, Object> data = new HashMap<String, Object>();
		data = purchaseService.getHeadData(Integer.parseInt(bizId));
		mv.addObject("head", data.get("pHead"));
		mv.addObject("supplier", data.get("supplier"));
		JSONObject json = new JSONObject();
		json.put("rows", data.get("Entries"));
		json.put("footer", data.get("footer"));
		json.put("columns", data.get("columns"));

		List<Map<String, Object>> entryDataList = (List<Map<String, Object>>) data
				.get("Entries");
		List<Map<String, Object>> supplierDataList = (List<Map<String, Object>>) data
				.get("supplier");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		footMap.put("construct_purchase_entryId", "合计(元)");
		for (int i = 0; i < supplierDataList.size(); i++) {
			Double total = 0.00;
			String construct_supplier_name = supplierDataList.get(i)
					.get("construct_supplier_name").toString();
			for (int m = 0; m < entryDataList.size(); m++) {

				/*
				 * total = total + (Double.valueOf(entryDataList.get(m)
				 * .get("construct_purchase_applyNum").toString()) * Double
				 * .valueOf(entryDataList.get(m) .get(construct_supplier_name)
				 * .toString()));
				 */

				if (entryDataList.get(m).get("" + construct_supplier_name + "") != null) {

					String priceString = entryDataList.get(m)
							.get("" + construct_supplier_name + "").toString();
					total = total
							+ (Double.valueOf(entryDataList.get(m)
									.get("construct_purchase_applyNum")
									.toString()) * Double.valueOf(priceString));
				}
				footMap.put(construct_supplier_name, total);

			}
		}
		list.add(footMap);
		json.put("selectFooter", list);
		model.addAttribute("rows", json);
		
		mv.addObject("bizId", bizId);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.addObject("processInstanceId", processInstanceId);
		mv.setViewName("construct/purchaseTask");
		return mv;

	}

	/**
	 * 采购流程开始办理任务
	 * 
	 * @param 申请人
	 *            name
	 * @param 业务单据id
	 *            bizId
	 * @param 流程定义的key值
	 *            processDefinitioKey
	 * @author liao
	 */
	@RequestMapping(value = "/start_examination", method = RequestMethod.POST)
	@ResponseBody
	public Object start_examination(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			json = purchaseService.start_examination(request);
			json.put("Success", true);
			json.put("Msg", "已审核!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
		}
		return json;

	}

	/**
	 * 驳回
	 * 
	 * @param
	 * @param
	 * @param
	 * @author
	 */

	@RequestMapping(value = "/rejects", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rejects(HttpServletRequest request) {

		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> json = new HashMap<String, Object>();
		try {

			User user = (User) request.getSession().getAttribute("loginUser");
			String username = user.getUserid() + "";// 当前办理人

			String taskid = request.getParameter("taskid");// 任务id
			String options = request.getParameter("options");// 审核意见
			vars.put("sign", "false");
			if (options != null) {
				activitiService.comment(taskid, username, options);// 添加审核记录
			}
			taskService.complete(taskid, vars);// 完成任务
			json.put("Success", true);
			json.put("Msg", "已驳回");
		} catch (Exception ex) {
			ex.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错");
		}
		return json;

	}

	

      /**
       * 选择性回退
       * 
       * */
	@RequestMapping(value = "/reject_order", method = RequestMethod.POST)
	@ResponseBody
	  public Map<String, Object> reject_order(HttpServletRequest request) {
		Map<String,Object> json=new HashMap<String, Object>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute("loginUser");
			String userid = user.getUserid() + "";// 当前办理人
			 String taskid=request.getParameter("taskid");
			 String activityId=request.getParameter("activityId");
			 String options = request.getParameter("options");// 审核意见
			String bizid= request.getParameter("construct_purchase_id");
			 if (bizid==null) {
					throw new Exception("没有单据编号");
			}
			  //修改单据状态
				map.put("billID", bizid);
			 if (activityId.equals("usertask1")) {
				    map.put("status", 1);
					purchaseService.updateBillStatus(map);
			}else if(activityId.equals("usertask4")){
			    map.put("status", 2);
			    purchaseService.updateBillStatus(map);
		   }else if(activityId.equals("usertask4")){
				    map.put("status", 5);
				    purchaseService.updateBillStatus(map);
			   }

			 if (options != null) {
					activitiService.comment(taskid, userid, options);// 添加审核记录
				}
			    json=activitiService.rejectTaskTo(taskid, activityId);
			    json.put("Success", true);
				json.put("Msg", "已驳回");
		} catch (Exception e) {
		   e.printStackTrace();
		}	
		return json;
		
	}
	
	
	/**
	 * 方法说明：上传采购单图片
	 * 
	 * 
	 * */
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadPhoto(MultipartFile photo,
			HttpServletRequest request) throws IOException {

		Map<String, Object> json = purchaseService.uploadPhoto(photo, request);

		return json;
	}

	// 删除
	@RequestMapping(value = "/deleteEntry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEntry(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();

		try {

			String bizId = request.getParameter("bizId");
			StringBuffer sbf = new StringBuffer();
			sbf.append("construct_purchase_entry where construct_purchase_entryId="
					+ bizId);
			purchaseService.deleteEntry(sbf.toString());

			json.put("Success", true);
			json.put("Msg", "已删除!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "删除失败！程序出错!");

		}

		return json;
	}

	// 下载分类说明
	@RequestMapping(value = "/downloadSeries", method = RequestMethod.POST)
	@ResponseBody
	public void downloadSeries(HttpServletRequest request,
			HttpServletResponse response) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		// 模板路径
		realPath = realPath.replaceAll("\\\\", "/");
		String fileurl = realPath + "res/seriesExcel/series.xlsx";
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.saveUrlAs(fileurl, request, response);
	}

}
