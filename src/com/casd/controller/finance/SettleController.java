package com.casd.controller.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.dao.finance.SettleDao;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.PurchaseHeadClass;
import com.casd.entity.finance.Settle;
import com.casd.entity.finance.SettlePay;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.FinalPurchaseService;
import com.casd.service.finance.SettleService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;

@Controller
@RequestMapping("/admin")
public class SettleController {

	@Autowired
	private SettleService settleService;
	@Autowired
	private FinalPurchaseService finalPurchaseService;

	@Autowired
	private ConstructService constructService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private SettleDao settleDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	
	
	private String construct_supplier_name;
	private int construct_project_id;
	
	private int company=0;
	
	/**
	 *  建设公司供应商列表
	 */
	@RequestMapping(value = "/build_settlement", method = RequestMethod.GET)
	public ModelAndView build_settlement() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/settleSupplierList");
		return mv;
	}

	@RequestMapping(value = "/build_settlements", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> build_settlement(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		
		StringBuffer sbf = new StringBuffer();
		String fields = " ( select SUM(num.finance_settlepay_purNum_nowpay)  from "
				+ " finance_settlepay_table settlepay LEFT JOIN finance_settlepay_paynum num "
				+ " on num.finance_settlepay_purNum_parentid=settlepay.finance_settlepay_id  "
				+ " LEFT JOIN construct_project_table project ON settlepay.finance_settlepay_projectid = project.construct_project_id "
				+ " LEFT JOIN construct_project_dep dep ON project.construct_project_dep = dep.constuct_project_dep_id "
				+ " where settlepay.finance_settlepay_supplier = supp.construct_supplier_name and dep.constuct_project_dep_company=1 and num.finance_settlepay_purNum_status=5) as payedTotal " 
				+ " ,supp.construct_supplier_id,supp.construct_supplier_name,supp.construct_supplier_addr,supp.construct_supplier_tel,";
		fields+="SUM(entry.construct_purchase_purchaseTotal) total";

		sbf.append(" construct_purchase_head head");

		sbf.append(" LEFT JOIN construct_purchase_entry entry  on head.construct_purchase_id=entry.construct_purchase_parentId ");
		
		sbf.append(" LEFT JOIN construct_project_table project  on head.construct_purchase_projectId=project.construct_project_id ");
		
		sbf.append(" LEFT JOIN construct_project_dep dep  on project.construct_project_dep=dep.constuct_project_dep_id ");
		
		sbf.append(" LEFT JOIN construct_supplier_table supp ON head.construct_purchase_supplier = supp.construct_supplier_name "
				+ " where dep.constuct_project_dep_company=1 and head.construct_purchase_status >12 GROUP BY  supp.construct_supplier_id ");

		List<Map<String, Object>> data = settleService.projectSuppliers(page,
				pageSize, record, fields,sbf.toString());
		
		company=1;
		
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		
		return result;
	}
	
	
	/**
	 *  发展公司供应商列表
	 */
	@RequestMapping(value = "/deve_settlement", method = RequestMethod.GET)
	public ModelAndView deve_settlement(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/settleSupplierList");
		return mv;
	}

	@RequestMapping(value = "/deve_settlement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deve_settlement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields = " ( select SUM(num.finance_settlepay_purNum_nowpay)  from "
				+ " finance_settlepay_table settlepay LEFT JOIN finance_settlepay_paynum num "
				+ " on num.finance_settlepay_purNum_parentid=settlepay.finance_settlepay_id "
				
				+ " LEFT JOIN construct_project_table project ON settlepay.finance_settlepay_projectid = project.construct_project_id "
				+ " LEFT JOIN construct_project_dep dep ON project.construct_project_dep = dep.constuct_project_dep_id "
				+ " where settlepay.finance_settlepay_supplier = supp.construct_supplier_name and dep.constuct_project_dep_company=2 and num.finance_settlepay_purNum_status=5 ) as payedTotal "
				+ " ,supp.construct_supplier_id,supp.construct_supplier_name,supp.construct_supplier_addr,supp.construct_supplier_tel,";
		fields+="SUM(entry.construct_purchase_purchaseTotal) total";

		sbf.append(" construct_purchase_head head");

		sbf.append(" LEFT JOIN construct_purchase_entry entry  on head.construct_purchase_id=entry.construct_purchase_parentId ");
		
		sbf.append(" LEFT JOIN construct_project_table project  on head.construct_purchase_projectId=project.construct_project_id ");
		
		sbf.append(" LEFT JOIN construct_project_dep dep  on project.construct_project_dep=dep.constuct_project_dep_id ");
		
		sbf.append(" LEFT JOIN construct_supplier_table supp ON head.construct_purchase_supplier = supp.construct_supplier_name "
				+ " where dep.constuct_project_dep_company=2 and head.construct_purchase_status >12 GROUP BY  supp.construct_supplier_id ");

	
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = settleService.projectSuppliers(pageIndex,
				pageSize, record, fields,sbf.toString());
		
		company=2;
		
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}
	
	
	
	/**
	 * 项目列表
	 */
	@RequestMapping(value = "/settleConstructList", method = RequestMethod.GET)
	public ModelAndView settleConstructList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		construct_supplier_name = request
				.getParameter("construct_supplier_name");
		
		mv.setViewName("finance/settleConstructList");
		return mv;
	}

	@RequestMapping(value = "/settleConstructList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> settleConstructList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		
		String construct_supplier_id = request
				.getParameter("construct_supplier_id");

		
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields = "( select SUM(num.finance_settlepay_purNum_nowpay)  from finance_settlepay_table settlepay "
				+ " LEFT JOIN finance_settlepay_paynum num on num.finance_settlepay_purNum_parentid=settlepay.finance_settlepay_id "
				+ " where settlepay.finance_settlepay_supplier = supp.construct_supplier_name "
				+ " and settlepay.finance_settlepay_projectid= pro.construct_project_id and num.finance_settlepay_purNum_status=5) as payedTotal,"
				+ " pro.construct_project_name,pro.construct_project_addr,pro.construct_project_leader,";
		       fields+="supp.construct_supplier_name,pro.construct_project_id projectId,pro.construct_project_leaderTel,";
		       fields+="pro.construct_project_startDate,pro.construct_project_endDate,";
		       fields+="SUM(entry.construct_purchase_purchaseTotal) total";
				

		sbf.append(" construct_purchase_head head");

		sbf.append(" LEFT JOIN construct_purchase_entry entry  on head.construct_purchase_id=entry.construct_purchase_parentId");
		sbf.append(" LEFT JOIN construct_project_table pro ON head.construct_purchase_projectId = pro.construct_project_id");
		sbf.append(" LEFT JOIN construct_project_dep dep  on pro.construct_project_dep=dep.constuct_project_dep_id ");
		sbf.append(" LEFT JOIN construct_supplier_table supp ON head.construct_purchase_supplier = supp.construct_supplier_name");
		sbf.append(" WHERE dep.constuct_project_dep_company="+company+" and supp.construct_supplier_id ="+construct_supplier_id+" and  head.construct_purchase_status >12");


		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = settleService.projectList(pageIndex,
				pageSize, record, fields,sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
	
		return json;

	}

	/**
	 * 付款单列表
	 */
	@RequestMapping(value = "/settlePayList", method = RequestMethod.GET)
	public ModelAndView settlePayList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/settlePayList");
		return mv;
	}

	@RequestMapping(value = "/settlePayList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> settlePayList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();

		construct_project_id = Integer.valueOf(request.getParameter("construct_project_id"));

		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields = "  ( SELECT SUM(num.finance_settlepay_purNum_nowpay) FROM finance_settlepay_paynum num WHERE num.finance_settlepay_purNum_status = 5 ) payed, "
				+ " pay.finance_settlepay_paytime,pay.finance_settlepay_id, pay.finance_settlepay_owe, pay.finance_settlepay_payed, "
				+ "pay.finance_settlepay_supplier,pro.construct_project_name,pro.construct_project_id";

		sbf.append(" finance_settlepay_table pay   ");
		sbf.append(" LEFT JOIN construct_project_table pro on pay.finance_settlepay_projectid=pro.construct_project_id  ");
		sbf.append(" WHERE pay.finance_settlepay_supplier='" + construct_supplier_name
				+ "' and pay.finance_settlepay_projectid="
				+ construct_project_id );


		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data = settleService.supplierList(pageIndex,
				pageSize, record, sbf.toString(), fields);
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	/**
	 * 新增付款单 修改付款单
	 */
	@RequestMapping(value = "/settlePayNew", method = RequestMethod.GET)
	public ModelAndView settlePayNew(HttpServletRequest request, Model model) {
		ModelAndView mv = new ModelAndView();

		
		String projectName = settleService.getProject(construct_project_id);
		Map<String, Object> head = new HashMap<String, Object>();
       
		head.put("finance_settlepay_id", 0);
		head.put("construct_project_name", projectName);
		head.put("finance_settlepay_projectid", construct_project_id);
		
		mv.addObject("projectId", construct_project_id);
		mv.addObject("projectName", projectName);
		mv.addObject("supplierName", construct_supplier_name);

		JSONObject json = new JSONObject();
		if (!request.getParameter("id").toString().isEmpty()) {
			Map<String, Object> maps = new HashMap<String, Object>();
			String id = request.getParameter("id").toString();
			maps = settleService.initSettle(id);

			List<Map<String, Object>> headList = (List<Map<String, Object>>) maps
					.get("heads");
			List<Map<String, Object>> settlePays = (List<Map<String, Object>>) maps
					.get("settlePays");
			List<Map<String, Object>> settlePurs = (List<Map<String, Object>>) maps
					.get("settlePurs");

			json.put("settlePurs", settlePurs);
			json.put("settlePays", settlePays);
			head = headList.get(0);
			mv.addObject("head", head);
			model.addAttribute("rows", json);
		}
		
		mv.addObject("head", head);
		model.addAttribute("rows", json);
		mv.setViewName("finance/settlePayNew");
		return mv;
	}

	@RequestMapping(value = "/settlePayNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> settlePayNew(HttpServletRequest request) {
		return null;
	}

	/**
	 * 付款单新增界面双击选择采购单
	 */
	@RequestMapping(value = "/purSelect", method = RequestMethod.GET)
	public ModelAndView purSelect(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		int index = Integer.valueOf(request.getParameter("index").toString());
		mv.addObject("index", index);
		mv.setViewName("checkBox/finalPurchaseCheck");
		return mv;
	}
   //选择支付子页面
	@RequestMapping(value = "/purSelect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> purSelect(HttpServletRequest request,
			HttpServletResponse response) {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		
		String fields = "*";

		sbf.append(" ( ");
		sbf.append(" select head.*,pro.construct_project_name,pro.construct_project_id,SUM(entry.construct_purchase_purchaseTotal) as arrived_money from ");
		sbf.append(" construct_purchase_head  head");
		sbf.append(" LEFT JOIN construct_project_table pro on head.construct_purchase_projectId=pro.construct_project_id");
		
		sbf.append(" LEFT JOIN construct_project_dep dep  on pro.construct_project_dep=dep.constuct_project_dep_id ");
		
		
		sbf.append(" LEFT JOIN construct_purchase_entry entry on head.construct_purchase_id=entry.construct_purchase_parentId");
		
		sbf.append(" where dep.constuct_project_dep_company="+company+" and head.construct_purchase_status=13");
		
		 String construct_purchase_materialSerName= request.getParameter("construct_purchase_materialSerName");
		  if (StringUtils.hasText(construct_purchase_materialSerName)) {
			  sbf.append(" and construct_purchase_materialSerName like '%"+construct_purchase_materialSerName+"%'");
		  }
		
		  String construct_purchase_id= request.getParameter("construct_purchase_id");
		  if (StringUtils.hasText(construct_purchase_id)) {
			  sbf.append(" and construct_purchase_id like '%"+construct_purchase_id+"%'");
		  }
		  
		sbf.append(" GROUP BY head.construct_purchase_id )tableAll ");        
	
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {
			data = settleService.purSelect(pageIndex, pageSize, record,
					sbf.toString(), fields);
		} catch (Exception e) {
			e.printStackTrace();
		}

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	// 查看
	@RequestMapping(value = "/purchaseDetailed", method = RequestMethod.GET)
	public ModelAndView purchaseDetailed(HttpServletRequest request, Model model)
			throws Exception {
		ModelAndView mv = new ModelAndView();

		Map<String, Object> maps = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		maps = finalPurchaseService.initFinalPurchase(request.getParameter(
				"construct_purchase_id").toString());
		mv.addObject("purchaseHead", maps.get("purchaseHeadClass"));
		json.put("rows", maps.get("rows"));
		model.addAttribute("rows", json);

		PurchaseHeadClass purchaseHeadClass = (PurchaseHeadClass) maps
				.get("purchaseHeadClass");
		int projectId = purchaseHeadClass.getConstruct_purchase_projectId();
		StringBuffer sbf = new StringBuffer();
		Construct construct = new Construct();
		String fields = "construct_project_id,construct_project_name,construct_project_addr,construct_project_leader,construct_project_leaderTel";
		sbf.append(" construct_project_table where construct_project_id="
				+ projectId + "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		Map<String, Object> data= constructService.getConstructById(map);
		construct =(Construct) data.get("construct");
		mv.addObject("construct", construct);
		

		mv.setViewName("construct/purchaseView");
		return mv;
	}
	//保存
	@RequestMapping(value = "/saveSettlePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSettlePay(HttpServletRequest request,
			Settle settle) throws Exception {

		String purRows = request.getParameter("purRows");

		settleService.saveSettlePay(settle, purRows);

		return null;
	}

	// 付款明细
	@RequestMapping(value = "/settlePayView", method = RequestMethod.GET)
	public ModelAndView settlePayView(HttpServletRequest request, Model model) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> head = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		Map<String, Object> maps = new HashMap<String, Object>();

		String id = request.getParameter("id").toString();
		maps = settleService.initSettle(id);

		List<Map<String, Object>> headList = (List<Map<String, Object>>) maps
				.get("heads");
		List<Map<String, Object>> settlePays = (List<Map<String, Object>>) maps
				.get("settlePays");
		List<Map<String, Object>> settlePurs = (List<Map<String, Object>>) maps
				.get("settlePurs");

		json.put("settlePurs", settlePurs);
		json.put("settlePays", settlePays);
		head = headList.get(0);

		mv.addObject("head", head);
		model.addAttribute("rows", json);
		mv.setViewName("finance/settlePayView");
		return mv;
	}

	
		// 初始化流程
		@RequestMapping(value = "initSettleFlow", method = RequestMethod.POST)
		@ResponseBody
		private JSONObject initSettleFlow(HttpServletRequest request, Model model) {
			JSONObject jsonObject = new JSONObject();
			User loginUser = (User) request.getSession().getAttribute("loginUser");
			String username = loginUser.getUsername();
			List<Map<String, Object>> flow = flowService.initFlows(1);
			jsonObject.put("flow", flow);
			jsonObject.put("userName", username);
			return jsonObject;
		}

		/**
		 * 跳转审核界面
		 * @throws Exception 
		 */
		@RequestMapping(value = "/settlePayAudit.do", method = RequestMethod.GET)
		@ResponseBody
		public ModelAndView settlePayAudit(HttpServletRequest request,
				Model model) throws Exception {
			ModelAndView mv = new ModelAndView();

			String taskid = request.getParameter("taskid");// 获取任务id
			String taskName = request.getParameter("taskName");
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
			
			Map<String, Object> head = new HashMap<String, Object>();
			JSONObject json = new JSONObject();
			Map<String, Object> maps = new HashMap<String, Object>();

			List<String> bot = activitiService.findOutComeListByTaskId(taskid);// 查看当前有几条线
			if (bot.size() >1) {
			
					model.addAttribute("bot", "批准");
					model.addAttribute("both", "不批准");
				
				
			} else {
				model.addAttribute("bot", "批准");
			}
			
			maps = settleService.initAuditSettle(bizId);

			List<Map<String, Object>> headList = (List<Map<String, Object>>) maps
					.get("heads");
			List<Map<String, Object>> settlePays = (List<Map<String, Object>>) maps
					.get("settlePays");
			List<Map<String, Object>> settlePurs = (List<Map<String, Object>>) maps
					.get("settlePurs");
			
			List<Map<String, Object>> userList =settleService.Pumtask(taskid);
		/*	
			//审核人初始化  采购付款流程标签pur_pay
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("code", "pur_pay");
			paramMap.put("order", flow_node_order);
			List<Map<String, Object>> auditorList =flowService.getAuditors(paramMap);*/
			mv.addObject("auditor", userList);
			mv.addObject("length", userList.size());
			
			List<Map<String, Object>> historyList = activitiService.getProcessComments(taskid);// 查询审批记录

		/*	List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
			for (Comment history : comments) {
				Map<String, Object> vars = new HashMap<String, Object>();

				vars.put("userId", history.getUserId()); // 审核人
				vars.put("fullMessage", history.getFullMessage()); // 审核意见
				historyList.add(vars);

			}*/
			// 审核历史意见
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("history", historyList);
			model.addAttribute("history", jsonObject);
			
			json.put("settlePurs", settlePurs);
			json.put("settlePays", settlePays);
			head = headList.get(0);

			model.addAttribute("rows", json);
			mv.addObject("head", head);
		/*	
			mv.addObject("auditID", auditID);
			mv.addObject("flow_node_order", flow_node_order);
			mv.addObject("flow_audit_last_auditid", flow_audit_last_auditid);
			mv.addObject("flow_status", flow_status);*/
			mv.addObject("taskid", taskid);
			mv.addObject("taskName", taskName);
			mv.setViewName("finance/settlePayAudit");
			return mv;
		}
		
		/**
		 * 审核页面，点击审核
		 *//*
		@RequestMapping(value = "/auditSettlePay", method = RequestMethod.POST)
		@ResponseBody
		public String auditSettlePay(HttpServletRequest request, Model model) {
			Map<String, Object> map = new HashMap<String, Object>();
			String billID = request.getParameter("finance_settlepay_payNum_id");
			String option = request.getParameter("option");
			String auditor = request.getParameter("auditor");
			int auditID = Integer.valueOf(request.getParameter("auditID"));
			int flow_node_order =Integer.valueOf(request.getParameter("flow_node_order").toString());
			int status=1;
			if(auditor!=""){
				status=2;
			}else{
				status=3;
			}
			map.put("billID", billID);
			map.put("option", option);
			map.put("auditID", auditID);
			map.put("flow_node_order", flow_node_order);
			map.put("where", "flow_audit_table");
			settleService.auditBill(map,auditor,status);
			return null;
		}*/
		/**
		 * 审核页面，点击退回
		 */
		@RequestMapping(value="/back_settlepay" , method = RequestMethod.POST)
		@ResponseBody
		public String back_settlepay(HttpServletRequest request) {
			
			Map<String, Object> map=new HashMap<String, Object>();
			String billID =request.getParameter("id");
			String option =request.getParameter("option");
			int auditID =Integer.valueOf(request.getParameter("auditID"));
			int flow_audit_last_auditid =Integer.valueOf(request.getParameter("flow_audit_last_auditid"));
			int flow_node_order = Integer.parseInt(request.getParameter("flow_node_order").toString());

			map.put("flow_audit_last_auditid",flow_audit_last_auditid);
			map.put("billID",billID);
			map.put("option",option);
			map.put("auditID",auditID);
		    map.put("where", "flow_audit_table");
		    flowService.backBill(map);
		    
			//如果flow_node_order=1时，单据状态改成4审核不通过
		    if((flow_node_order-1)==1){
			    map.put("status", 4);
			    map.put("billID", billID);
			    settleDao.update_settlePayNum(map);
		    }
		    
			return null;
		}
		
		
		
		/**
		 * 付款列表
		 */
		@RequestMapping(value = "/settlePayNumList", method = RequestMethod.GET)
		public ModelAndView settlePayNumList(HttpServletRequest request) {
			ModelAndView mv = new ModelAndView();
			String id = request.getParameter("id");
			String payOwe = request.getParameter("payOwe");
			mv.addObject("payOwe", payOwe);
			mv.addObject("finance_settlepay_id", id);
			mv.setViewName("finance/settlePayNumList");
			
	/*		//审核人初始化  采购付款流程标签pur_pay
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("code", "pur_pay");
			paramMap.put("order", 1);
			List<Map<String, Object>> auditorList =flowService.getAuditors(paramMap);
			mv.addObject("auditor", auditorList);
			mv.addObject("size", auditorList.size());*/
			return mv;
		}

		@RequestMapping(value = "/settlePayNumList", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> settlePayNumList(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");

 			Ref<Integer> record = new Ref<Integer>();
			Map<String, Object> json = new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			String fields = " * ";

			sbf.append(" finance_settlepay_paynum  ");
			sbf.append(" where finance_settlepay_purNum_parentid =(select finance_settlepay_id from finance_settlepay_table where finance_settlepay_id='"+id+"') ");
			
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
			
			List<Map<String, Object>> data = settleService.settlePayNumList(pageIndex,
					pageSize, record, sbf.toString(), fields);
			
			json.put("rows", data);
			json.put("total", record.getValue());
			return json;

		}

		
		/**
		 * 新增付款金额
		 * */
		@RequestMapping(value = "/add_payNumList", method = RequestMethod.POST)
		@ResponseBody
		public void add_payNumList(HttpServletRequest request, SettlePay settlePay) {
			int parentId=Integer.valueOf(request.getParameter("id").toString());
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				settlePay.setFinance_settlepay_purNum_parentid(parentId);
				settlePay.setFinance_settlepay_purNum_status(0);
				settleService.add_payNumList(settlePay);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("msg", "操作失败！");
			}
		}

		
		
		/**
		 * 提交付款金额
		 * */
		@RequestMapping(value = "/audit_payNum", method = RequestMethod.POST)
		@ResponseBody
		public void audit_payNum(HttpServletRequest request, SettlePay settlePay) {
			int parentId=Integer.valueOf(request.getParameter("id").toString());
			String auditor=request.getParameter("auditor");
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				settlePay.setFinance_settlepay_purNum_parentid(parentId);
				settlePay.setFinance_settlepay_purNum_status(1);
				User loginUser = (User) request.getSession().getAttribute(
						"loginUser");
				String username = loginUser.getUsername();
				settleService.audit_payNum(settlePay,auditor,username);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("msg", "操作失败！");
			}
		}
		
		/**
		 * 删除新增付款批次分录
		*/
		@RequestMapping(value = "/delePur", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> delePur(HttpServletRequest request) throws Exception {
			
			if(request.getParameter("finance_settlepay_pur_id")!=null){
			Map<String, Object> json=new HashMap<String, Object>();
			Map<String, Object> params=new HashMap<String, Object>();
			try {	
			int finance_settlepay_pur_id =Integer.valueOf(request.getParameter("finance_settlepay_pur_id").toString());
			String purchaseid=request.getParameter("purchaseid"); //采购单据编号
			StringBuffer sbf = new StringBuffer();
			sbf.append(" where finance_settlepay_pur_id in(" + finance_settlepay_pur_id + ")");
			params.put("where", sbf.toString());
			params.put("billid", purchaseid);
			settleService.delePur(params);
			} catch (Exception e) {
				json.put("msg", "删除失败！");
				e.printStackTrace();
			}
			
			}
			return null;
		}
		
		
		/**
		 * 删除付款批次
		*/
		@RequestMapping(value = "/delectPayList", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object>  delectPayList(HttpServletRequest request) throws Exception {
			   
			Map<String, Object> json=new HashMap<String, Object>();
			try {	
			int finance_settlepay_id =Integer.valueOf(request.getParameter("finance_settlepay_id").toString());
			StringBuffer sbf = new StringBuffer();
			Map<String, Object> map=new HashMap<String, Object>();
			
			String table="finance_settlepay_table";
			sbf.append(" where finance_settlepay_id in(" + finance_settlepay_id + ")");
			map.put("finance_settlepay_id", finance_settlepay_id);
			map.put("table", table);
			map.put("where", sbf.toString());
			settleService.delePayList(map);
			} catch (Exception e) {
				json.put("msg", "删除失败！");
				e.printStackTrace();
			}
			return null;
		}
		
		
		/**
		 * 删除付款
		*/
		@RequestMapping(value = "/delect_payNum", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object>  delect_payNum(HttpServletRequest request) throws Exception {
			   
			Map<String, Object> json=new HashMap<String, Object>();
			try {	
			StringBuffer sbf = new StringBuffer();
			Map<String, Object> map=new HashMap<String, Object>();
			
			String cid =request.getParameter("cid");
			cid=cid.substring(1);
			String[] ids=cid.split("]");
			
			String table="finance_settlepay_paynum";
			sbf.append(" where finance_settlepay_payNum_id in(" + ids[0].toString() + ")");
			map.put("table", table);
			map.put("where", sbf.toString());
			settleService.delect_payNum(map);
			} catch (Exception e) {
				json.put("msg", "删除失败！");
				e.printStackTrace();
			}
			return null;
		}
		
		
		/**
		 * 确认付款
		*/
		@RequestMapping(value = "/confirmPay", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object>  confirmPay(HttpServletRequest request) throws Exception {
			String finance_settlepay_payNum_id = request.getParameter("finance_settlepay_payNum_id");
			String noPay = request.getParameter("noPay");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", 5);
			map.put("noPay", noPay);
		    map.put("billID", finance_settlepay_payNum_id);
		    settleDao.update_settlePayNum(map);
			return null;
		}
		
		/**
		 * 审核意见
		*/
		@RequestMapping(value = "/auditOption", method = RequestMethod.GET)
		@ResponseBody
		public 	List<Map<String, Object>>  auditOption(HttpServletRequest request,Model model) throws Exception {
			String bizId = request.getParameter("bizId");
			String beyId = "Purchase_order"; // 流程实例key 请勿改动
			List<Map<String, Object>>  historyList = activitiService.viewHisComments(bizId, beyId);
			return historyList;
		}
		
		
		/**
		 * 启动采购付款流程
		 * */
		@RequestMapping(value = "/payNumStart", method = RequestMethod.POST)
		@ResponseBody 
		public Object  payNumStart(HttpServletRequest request){
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				User user = (User) request.getSession().getAttribute("loginUser");
				String name = user.getUserid()+"";// 获取申请人

				String processDefinitioKey = "Purchase_order"; // 定义流程的key,不可修改
																	// 请注意
			
				String bizId = processDefinitioKey + "."+ request.getParameter("bizId"); // 获取业务id
				activitiService.startProcesses(bizId, name, processDefinitioKey,null);
				  json.put("Success", true);
				  json.put("Msg", "启动成功,请审核！");
			} catch (Exception e) {
				json.put("Msg", "启动失败,请联系技术员！");
				json.put("Success", false);
			}
			return json;
			
		}
		
		@RequestMapping(value = "/Pum_pass", method = RequestMethod.POST)
		@ResponseBody
		public Object Pum_pass(HttpServletRequest request) {
			Map<String, Object> json=new HashMap<String, Object>();
		     try {
		    	
		    	 json= settleService.Pum_pass(request);
		    	    json.put("Success", true);
					json.put("Msg", "已审核!");
				} catch (Exception e) {
					e.printStackTrace();
					json.put("Success", false);
					json.put("Msg", "程序出错,请联系技术员");
			    }
				
				return json;

			
		}

}
