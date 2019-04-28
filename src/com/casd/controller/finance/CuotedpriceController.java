package com.casd.controller.finance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.dao.uc.UserDao;
import com.casd.entity.finance.Construct_change_head;
import com.casd.entity.uc.User;
import com.casd.service.finance.MaterialPriceService;
import com.casd.service.finance.SettleService;
import com.casd.service.finance.SupplierService;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;

/**
 * 供应商模块
 * 
 * */
@Controller
@RequestMapping("/admin")
public class CuotedpriceController {

	@Autowired
	private MaterialPriceService materialPriceService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private SettleService settleService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private UserDao userDao;

	// 供应商报价首页
	@RequestMapping(value = "/supplier_price", method = RequestMethod.GET)
	public ModelAndView supplier_price() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("supplierMod/supplier_price");
		return mv;
	}

	//
	@RequestMapping(value = "/supplier_price", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supplier_price(HttpServletRequest request)
			throws Exception {

		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("loginUser");
		String material_name=request.getParameter("construct_material_name");
		String material_model=request.getParameter("construct_material_model");
		
		StringBuffer sbf = new StringBuffer();

		sbf.append(" construct_material_price price left join construct_supplier_table supplier on supplier.construct_supplier_id=price.construct_material_supplierId  where 1=1 and supplier.construct_supplier_charger ='"
				+ user.getUserid() + "'");
		if(!StringUtils.isEmpty(material_name)){
			sbf.append(" and construct_material_name LIKE '%"+material_name+"%'");
		}
		
		if(!StringUtils.isEmpty(material_model)){
			sbf.append(" and construct_material_model LIKE '%"+material_model+"%'");
		}
	

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		List<Map<String, Object>> data = materialPriceService.materialPrice(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	@RequestMapping(value = "/change_head_list", method = RequestMethod.GET)
	public ModelAndView change_head_list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("supplierMod/change_head_list");
		return mv;
	}

	@RequestMapping(value = "/change_head_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> change_head_list(HttpServletRequest request)
			throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		String change_price_name=request.getParameter("change_price_name");
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();

		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_change_head");
		
		sbf.append(" where material_supplierId ="+user.getUserid());
		if (!StringUtils.isEmpty(change_price_name)) {
			sbf.append(" and change_price_name  like '%"+change_price_name+"%'");
			
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		List<Map<String, Object>> data = materialPriceService.materialPrice(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	// 添加单头
	@RequestMapping(value = "/add_change_head", method = RequestMethod.POST)
	@ResponseBody
	public Object add_changehead(HttpServletRequest request,
			Construct_change_head cch) {
		Map<String, Object> json = new HashMap<String, Object>();

		try {
			
			User user = (User) request.getSession().getAttribute("loginUser");
          
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(date);
            cch.setMaterial_supplierId(user.getUserid());
			cch.setChange_price_name(dateNowStr);
			supplierService.add_change_head(cch);
			json.put("Success", true);
			json.put("Msg", "添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}
		return json;

	}

	@RequestMapping(value = "/change_price", method = RequestMethod.GET)
	public ModelAndView change_price(HttpServletRequest request, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		String bizId = request.getParameter("change_headId");
		String status = request.getParameter("status");

		String beyId = "change_price"; // 流程实例key 请勿改动

		List<Map<String, Object>>  historyList = activitiService.viewHisComments(bizId,
				beyId);
		// 审核历史意见
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("history", historyList);
		model.addAttribute("history", jsonObject);

		mv.addObject("status", status);// 单据编号
		mv.addObject("bizId", bizId);// 单据编号
		mv.setViewName("supplierMod/change_price");

		return mv;

	}

	@RequestMapping(value = "/change_price", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> change_price(HttpServletRequest request)
			throws Exception {

		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("loginUser");
		String material_name=request.getParameter("construct_material_name");
		String material_model=request.getParameter("construct_material_model");
		
		StringBuffer sbf = new StringBuffer();

		sbf.append(" construct_material_price price left join construct_supplier_table supplier on supplier.construct_supplier_id=price.construct_material_supplierId  where 1=1 and supplier.construct_supplier_charger ='"
				+ user.getUserid() + "'");
		if(!StringUtils.isEmpty(material_name)){
			sbf.append(" and construct_material_name LIKE '%"+material_name+"%'");
		}
		
		if(!StringUtils.isEmpty(material_model)){
			sbf.append(" and construct_material_model LIKE '%"+material_model+"%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		List<Map<String, Object>> data = materialPriceService.materialPrice(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());

		return json;
	}

	@RequestMapping(value = "/add_price", method = RequestMethod.POST)
	@ResponseBody
	public Object add_price(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			supplierService.add_price(request);
			json.put("Success", true);
			json.put("Msg", "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", e.getMessage());
		}
		return json;

	}

	@RequestMapping(value = "/findchangeprice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findchangeprice(HttpServletRequest request)
			throws Exception {

		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("loginUser");
		StringBuffer sbf = new StringBuffer();
		String cid = request.getParameter("cid");
		sbf.append(" construct_change_price a where 1=1 and a.construct_change_headId="
				+ cid);

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		List<Map<String, Object>> data = materialPriceService.materialPrice(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	/** 启动流程 */
	@RequestMapping(value = "/start_change_price", method = RequestMethod.POST)
	@ResponseBody
	public Object start_change_price(HttpServletRequest request) {

		Map<String, Object> json = new HashMap<String, Object>();

		try {
			supplierService.start_change_price(request);
			json.put("Success", true);
			json.put("Msg", "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}
		return json;

	}

	/*** 价格变更审核页面 */
	@RequestMapping(value = "/change_priceaudit", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView change_priceaudit(HttpServletRequest request,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView();

		String taskid = request.getParameter("taskid");// 获取任务id
		String taskName = request.getParameter("taskName");// 任务节点
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}

		Ref<Integer> record = new Ref<Integer>();

		StringBuffer sbf = new StringBuffer();

		sbf.append(" construct_change_head a");
		sbf.append(" JOIN construct_change_price  b on a.id=b.construct_change_headId");
		sbf.append(" WHERE b.construct_change_headId=" + bizId);

		int pageSize = Integer.parseInt("10");
		int pageIndex = Integer.parseInt("0") - 1;
		List<Map<String, Object>> dataList = materialPriceService
				.materialPrice(pageIndex, pageSize, record, sbf.toString());// 查询数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dataList", dataList);
		model.addAttribute("dataList", jsonObject);

		List<Map<String, Object>> userList = settleService.findPumcheck(
				"change_price", taskName);// 查询审核人

		List<String> bot = activitiService.findOutComeListByTaskId(taskid);// 查看当前有几条线

		if (bot.size() > 1) {
			model.addAttribute("bot", "批准");
			model.addAttribute("both", "驳回");
		} else {
			model.addAttribute("bot", "批准");
		}

		List<Map<String, Object>> comments = activitiService.getProcessComments(taskid);// 查询审批记录
		jsonObject.put("history", comments);
		model.addAttribute("history", jsonObject);
		/*
		 * 
		 * //审核人初始化 价格变更流程标签change_price_flow Map<String, Object> paramMap=new
		 * HashMap<String, Object>(); paramMap.put("code", "change_price_flow");
		 * paramMap.put("order", flow_node_order);
		 * 
		 * mv.addObject("auditID", auditID); mv.addObject("flow_bill_id",
		 * flow_bill_id); mv.addObject("flow_node_order", flow_node_order);
		 * mv.addObject("flow_audit_last_auditid", flow_audit_last_auditid);
		 * mv.addObject("flow_status", flow_status);
		 * 
		 * 
		 * List<Map<String, Object>> userList
		 * =flowService.getAuditors(paramMap); mv.addObject("userList",
		 * userList); mv.addObject("length", userList.size());
		 * 
		 * 
		 * Map<String, Object> param = new HashMap<String, Object>();
		 * param.put("flow_bill_id", flow_bill_id); param.put("flow_bill_url",
		 * "change_price_audit.do"); List<Map<String, Object>> listsMaps=
		 * flowService.getAuditOption(param); jsonObject.put("history",
		 * listsMaps); model.addAttribute("history", jsonObject);
		 */
		mv.addObject("userList", userList);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("supplierMod/change_price_audit");
		return mv;

	}

	// 审核变更单据
	@RequestMapping(value = "/change_examine", method = RequestMethod.POST)
	@ResponseBody
	public Object change_examine(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {

			supplierService.change_examine(request);
			json.put("Success", true);
			json.put("Msg", "提交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}
		return json;
	}

	// 报价
	@RequestMapping(value = "/quoted_price", method = RequestMethod.POST)
	@ResponseBody
	public Object quoted_price(HttpServletRequest request) {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String new_price = request.getParameter("new_price");
			supplierService.new_update_price(id, new_price);
			json.put("Success", true);
			json.put("Msg", "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}

		return json;

	}

	@RequestMapping(value = "/deleteNewPrice", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteNewPrice(HttpServletRequest request) {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String ids = request.getParameter("id");
			ids = ids.substring(1);
			String[] id = ids.split("]");

			String where = "where construct_change_id=" + id[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("where", where);
			supplierService.deleteNewPrice(map);

			json.put("Success", true);
			json.put("Msg", "已删除!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}
		return json;

	}
    
	
	/**
	 * @author Administrator
	 * 变更价格驳回操作
	 * 
	 **/
	@RequestMapping(value = "/back_Newprice", method = RequestMethod.POST)
	@ResponseBody
	public Object back_Newprice(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();

		try {
			supplierService.back_Newprice(request);

			json.put("Success", true);
			json.put("Msg", "已退回!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错!");
		}
		return json;

	}

}
