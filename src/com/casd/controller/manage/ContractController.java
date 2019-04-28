package com.casd.controller.manage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.manage.Contract;
import com.casd.entity.manage.Reqfunds;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.ContractService;
import com.casd.service.manage.SupOpinionService;

@Controller
@RequestMapping("/admin")
public class ContractController {

	@Autowired
	private ContractService contractService;
	@Autowired
	private SupOpinionService supOpinionService;
	@Autowired
	private ActivitiService activitiService;

	/**
	 * 合同管理列表
	 * 
	 * */
	@RequestMapping(value = "/contractList", method = RequestMethod.GET)
	public ModelAndView contractList(HttpServletRequest request,Model model) {
		ModelAndView mv = new ModelAndView();
	/*	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Calendar now = Calendar.getInstance();
		int yearNum = now.get(Calendar.YEAR) - 2016;
		for (int j = 0; j < yearNum + 1; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yearMon", 2016 + j);
			list.add(map);
		}
		mv.addObject("yearMon", list);*/
		mv.setViewName("manage/contractList");
		return mv;

	}

	@RequestMapping(value = "/contractLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> contractList(HttpServletRequest request)
			throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));

		
		
		StringBuilder sBuilder = new StringBuilder();
		String fields =" tableAll.*, SUM(req.manage_reqfunds_receiveAmount) receiveAmount ";
		     
		sBuilder.append(" (SELECT contract.manage_contract_id,contract.manage_contract_num,contract.manage_contract_amount,contract.manage_contract_address,");
		sBuilder.append("contract.manage_contract_visaAmount,contract.manage_contract_firstParty,contract.manage_contract_name,");
		sBuilder.append("contract.manage_contract_startTime,contract.manage_contract_endTime");
		sBuilder.append(" FROM manage_contract contract LEFT JOIN construct_project_table pro ON contract.manage_contract_id = pro.construct_project_contractId");
		sBuilder.append(" where 1=1");
		//项目编号
		String manage_contract_num =request.getParameter("manage_contract_num"); 
		if (StringUtils.hasText(manage_contract_num)) {
			sBuilder.append(" and contract.manage_contract_num = '"
					+ manage_contract_num + "'");
		}
		
		//项目名称
		String manage_contract_name = request.getParameter("manage_contract_name");
		if (StringUtils.hasText(manage_contract_name)) {
			sBuilder.append(" and contract.manage_contract_name like '%"
					+ manage_contract_name+"%'");
		}
		
		//发包方
		String manage_contract_firstParty = request.getParameter("manage_contract_firstParty");
		if (StringUtils.hasText(manage_contract_firstParty)) {
			sBuilder.append(" and contract.manage_contract_firstParty like '%"
					+ manage_contract_firstParty+"%'");
		}
		
		//合同开发时间
		String manage_contract_startTime= request.getParameter("manage_contract_startTime"); 
		if(StringUtils.hasText(manage_contract_startTime)){
			sBuilder.append(" and contract.manage_contract_startTime like '%"
					+ manage_contract_startTime+"%'"); 
		}
		
		//公司
		String manage_contract_company = request.getParameter("manage_contract_company"); 
		if (StringUtils.hasText(manage_contract_company)) {
			sBuilder.append(" and contract.manage_contract_company="+manage_contract_company);
		}
		sBuilder.append(") tableAll");
		sBuilder.append(" LEFT JOIN manage_reqfunds req ON req.manage_reqfunds_contractId = tableAll.manage_contract_id");
		sBuilder.append(" GROUP BY tableAll.manage_contract_id order by manage_contract_startTime DESC");
		
		List<Map<String, Object>> data = contractService.contractList(
				pageIndex, pageSize, record, fields, sBuilder.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;

	}
	/**
	 * 收款报表
	 * 
	 * */
	@RequestMapping(value = "/contractReport", method = RequestMethod.GET)
	public ModelAndView contractReport() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Calendar now = Calendar.getInstance();
		int yearNum = now.get(Calendar.YEAR) - 2016;
		for (int j = 0; j < yearNum + 1; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yearMon", 2016 + j);
			list.add(map);
		}

		mv.addObject("yearMon", list);

		mv.setViewName("manage/contractReport");
		return mv;

	}

	@RequestMapping(value = "/contractReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> contractReport(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		StringBuilder sBuilder = new StringBuilder();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		Ref<Integer> record = new Ref<Integer>();
		String fields = "*";
		sBuilder.append(" manage_contract contract left join manage_reqfunds req on  contract.manage_contract_id=req.manage_reqfunds_contractId ");
		sBuilder.append(" where 1=1");

		String manage_contract_num = request
				.getParameter("manage_contract_num");
		if (StringUtils.hasText(manage_contract_num)) {
			sBuilder.append(" and manage_contract_num like '%"
					+ manage_contract_num + "%'");
		}
		String manage_contract_name = request
				.getParameter("manage_contract_name");
		if (StringUtils.hasText(manage_contract_name)) {
			sBuilder.append(" and manage_contract_name like '%"
					+ manage_contract_name + "%'");
		}

		String manage_contract_company = request
				.getParameter("manage_contract_company");
		if (StringUtils.hasText(manage_contract_company)) {
			sBuilder.append(" and manage_contract_company like '%"
					+ manage_contract_company + "%'");
		}

		String yearMon = request.getParameter("yearMon");
		if (StringUtils.hasText(yearMon) && !yearMon.equals("0")) {
			sBuilder.append(" and manage_contract_startTime like '%" + yearMon
					+ "%'");
		}
		sBuilder.append(" order by manage_contract_num ");
		List<Map<String, Object>> data = contractService.contractList(
				pageIndex, pageSize, record, fields, sBuilder.toString());
		jsonMap.put("rows", data);
		jsonMap.put("total", record.getValue());

		return jsonMap;

	}

	/**
	 * 新增，修改合同
	 * 
	 * */
	@RequestMapping(value = "/contractNew", method = RequestMethod.GET)
	public ModelAndView contractNew(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String manage_contract_id = request.getParameter("manage_contract_id");
		Map<String, Object> contract = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		String type = "";
		String fields = "*";
		if (manage_contract_id.equals("")) {
			contract.put("manage_contract_id", 0);
			contract.put("manage_contract_amount", 0);
			contract.put("manage_contract_visaAmount", 0);
			jsonObject.put("rows", "");
			type = "'new'";
		} else {
			String where = "manage_reqfunds where manage_reqfunds_contractId="
					+ manage_contract_id;
			List<Map<String, Object>> data = contractService.contractData(
					fields, where);
			String str = "manage_contract  where manage_contract_id="
					+ manage_contract_id;
			
			Double receiveAmount=0.00;
		    for (Map<String, Object> m : data){
		    	receiveAmount =receiveAmount + Double.valueOf(m.get("manage_reqfunds_receiveAmount")==null?"0.00":m.get("manage_reqfunds_receiveAmount").toString());
		    }
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> footMap = new HashMap<String, Object>();
			footMap.put("manage_reqfunds_receiveAmount", receiveAmount);
			footMap.put("manage_reqfunds_receiveDate", "收款合计：");
			list.add(footMap);
			jsonObject.put("footer", list);
			
			jsonObject.put("rows", data);
			contract = contractService.findContract(fields, str);
			type = request.getParameter("type");
		}
		mv.addObject("type", type);
		mv.addObject("rows", jsonObject);
		mv.addAllObjects(contract);
		mv.setViewName("manage/contractNew");
		return mv;

	}

	/**
	 * 合同管理保存
	 * 
	 * */
	@RequestMapping(value = "/saveContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveContract(HttpServletRequest request,
			Contract contract) throws Exception {
		 String type= request.getParameter("type");
		 if (type.equals("view")) {	
			 
			     JSONObject reqfundsJson = JSONObject.fromObject(request
					.getParameter("rows"));
				Reqfunds reqfunds=(Reqfunds)JSONObject.toBean(reqfundsJson, Reqfunds.class);
						 reqfunds.setManage_reqfunds_contractId(contract.getManage_contract_id());
						 reqfunds.setManage_first_party(contract.getManage_contract_firstParty());
						 reqfunds.setManage_ticket_content(contract.getManage_contract_name());
			    contractService.saveReqfunds(reqfunds);
			 
		}else if(type.equals("sava")){
			
			  JSONArray reqfundsJson =JSONArray.fromObject(request
						.getParameter("rows"));
			contractService.saveContract(contract,reqfundsJson);
		}
		
		return null;

	}
	
	
	@RequestMapping(value = "/delete_Pay_Reqfunds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_Pay_Reqfunds(HttpServletRequest request)
			throws Exception {

			Map<String, Object> json=new HashMap<String, Object>();
			try {
				contractService.delete_Reqfunds(request.getParameter("id"));
				json.put("Success", true);
				json.put("Msg", "已删除!");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");
			}
			return json;
	}
	

	/**
	 * 收款进度删除
	 * 
	 * */
	@RequestMapping(value = "/delete_Reqfunds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_Reqfunds(String bizId)
			throws Exception {

			Map<String, Object> json=new HashMap<String, Object>();
			try {
				contractService.delete_Reqfunds(bizId);
				String beyId ="reqfundsView";
				activitiService.deleteRecord(bizId, beyId);
				json.put("Success", true);
				json.put("Msg", "已删除!");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");
			}
			return json;
	}

	/**
	 * 合同管理删除
	 * 
	 * */
	@RequestMapping(value = "/delete_Contract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_Contract(HttpServletRequest request) {
		 Map<String, Object> params =new HashMap<String, Object>();
		try {
		String manage_contract_id = request.getParameter("manage_contract_id");
				contractService.delete_Contract(manage_contract_id);
				params.put("Success",true);
				params.put("Msg", "修改成功！");
	    } catch (Exception e) {
				e.printStackTrace();
				params.put("Msg", "操作失败！");
				params.put("Success", false);
	      }
		return params;
		
	}

	/**
	 * @author mr.Liao 查看视图
	 * 
	 * */
	@RequestMapping(value = "/findContractData", method = RequestMethod.GET)
	public ModelAndView findContractData(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		String type = request.getParameter("type");
		if ("view".equals(type)) {
			String biz = request.getParameter("biz");
			JSONObject jsonObject = new JSONObject();
			String fields = "*";
			String where = "manage_reqfunds rfs JOIN construct_project_table";
			where += " cpt on rfs.manage_reqfunds_contractId =cpt.construct_project_contractId";
			where += " where rfs.manage_reqfunds_contractId=" + biz;

			List<Map<String, Object>> data = contractService.contractData(
					fields, where);
			String str = "manage_contract  where manage_contract_id=" + biz;
			jsonObject.put("rows", data);
			Map<String, Object> contract = contractService.findContract(fields,
					str);
			mv.addObject("type", type);
			mv.addObject("rows", jsonObject);
			mv.addAllObjects(contract);
			mv.setViewName("construct/constructdata");
		}

		return mv;
	}

	/**
	 * 开票视图
	 * */
	@RequestMapping(value = "/reqfundsView{taskid}", method = RequestMethod.GET)
	public ModelAndView reqfundsView(HttpServletRequest request,String taskid,String taskName)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		String biz = request.getParameter("biz");
		List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>(); 
		
		// 审核历史意见
		JSONObject jsonObject = new JSONObject();
		String fields = "";
		String where="";
		if ("json".equals(type)) {
			 fields += "*";
			 where += "manage_reqfunds rfs where rfs.manage_reqfunds_id ="
					+ biz;
			Map<String, Object> contract = contractService.findContract(fields,
					where);
			List<Map<String, Object>> userList = supOpinionService
					.supOpinionUser("申请人", "reqfundsView");
			mv.addAllObjects(contract);
			mv.addObject("userList", userList);
			mv.setViewName("manage/reqfundsView");
		} else if ("view".equals(type)) {
			 fields += "*";
			 where += "manage_reqfunds rfs where rfs.manage_reqfunds_id ="
					+ biz;
			Map<String, Object> contract = contractService.findContract(fields,
					where);
			String beyId = "reqfundsView"; // 流程实例key 请勿改动
			historyList = activitiService.viewHisComments(biz, beyId);
			mv.setViewName("manage/reqfundsView");
			mv.addAllObjects(contract);
		}else{
			String bizid = activitiService.getBusinessBizId(taskid);// 获取业务编号
			if(!bizid.isEmpty()){
				bizid = bizid.split("\\.")[1];
			}
			historyList = activitiService.getProcessComments(taskid);// 查询审批记录
			fields += "*";
			 where += "manage_reqfunds rfs where rfs.manage_reqfunds_id ="
					+ bizid;
			Map<String, Object> contract = contractService.findContract(fields,
					where);
			mv.setViewName("manage/reqfundsApply");
			List<Map<String, Object>> userList = supOpinionService.supOpinionUser(
					taskName, "reqfundsView"); // 查询审核人
			mv.addAllObjects(contract);
			mv.addObject("taskid",taskid);
			mv.addObject("userList", userList);
		}
		jsonObject.put("history", historyList);
		mv.addObject("history", jsonObject);
		return mv;
	}

	/**
	 * 发票申请流程启动
	 * 
	 * **/
	@RequestMapping(value = "/updateReqfunds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReqfunds(HttpServletRequest request,
			Reqfunds reqfunds) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			reqfunds.setManage_status(1);// 审核中
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			reqfunds.setManage_reqfunds_ticketDate(formatter.format(currentTime));
			User user = (User) request.getSession().getAttribute("loginUser");
			contractService.updateReqfunds(reqfunds,user);
		
			json.put("Success", true);
			json.put("Msg", "已保存!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
		}
		return json;
	}

	@RequestMapping(value = "/reqfundsApply", method = RequestMethod.GET)
	public ModelAndView reqfundsApply(String taskid, String taskName)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}

		List<Map<String, Object>> historyList = activitiService
				.getProcessComments(taskid);// 查询审批记录

		// 审核历史意见
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("history", historyList);

		List<Map<String, Object>> userList = supOpinionService.supOpinionUser(
				taskName, "reqfundsView"); // 查询审核人
		String fields = "*";
		String where = "manage_reqfunds rfs where rfs.manage_reqfunds_id ="
				+ bizId;
		Map<String, Object> reqfunds = contractService.findContract(fields,
				where);
		mv.addAllObjects(reqfunds);
		mv.addObject("userList", userList);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.addObject("history", jsonObject);
		mv.setViewName("manage/reqfundsApply");

		return mv;
	}

	// 开始审核
	@RequestMapping(value = "/reqfundspass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reqfundspass(HttpServletRequest request,
			Reqfunds reqfunds) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			contractService.reqfundspass(request, reqfunds);
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
