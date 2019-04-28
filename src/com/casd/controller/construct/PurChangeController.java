package com.casd.controller.construct;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.construct.Construct;
import com.casd.entity.construct.PurChange;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.PurChangeService;
import com.casd.service.flow.FlowService;


@Controller
@RequestMapping("/admin")
public class PurChangeController {
	
	@Autowired
	private PurChangeService purChangeService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private ConstructService constructService;
	private int projectId=0;
	
	/**
	 * list列表
	*/
	@RequestMapping(value="/purChangeList" , method = RequestMethod.GET)
	public ModelAndView purChangeList(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("construct/purChangeList");
		return mv;
	}
	
	@RequestMapping(value="/purChangeList" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> purChangeList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		String filds="*";
		if(!request.getParameter("construct_project_id").isEmpty()){//当返回list界面时是空的，用上一个
			projectId=Integer.valueOf(request.getParameter("construct_project_id"));
		}
		
		sbf.append(" purChangeTab pur left join construct_project_table pro on pur.purChangeTab_proId=pro.construct_project_id where 1=1 and pro.construct_project_id="+projectId+"");
		
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data=purChangeService.purchaseList( filds, pageIndex, pageSize, record, sbf.toString());
		json.put("projectId", projectId);
	    json.put("rows", data);
	    json.put("total", record.getValue());
	    return json;
	    
	}
	
	
	
	/**
	 * 新增
	*/
	@RequestMapping(value="/purChangeNew" , method = RequestMethod.GET)
	public ModelAndView purChangeNew(HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		
		StringBuffer sbf=new StringBuffer();
		Construct construct=new Construct();
		String fields = "construct_project_id,construct_project_name,construct_project_addr,construct_project_leader,construct_project_leaderTel";
		sbf.append(" construct_project_table where construct_project_id="+projectId+"");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		//项目信息
		Map<String, Object> data= constructService.getConstructById(map);
		construct =(Construct) data.get("construct");
		mv.addObject("construct", construct);
		
		//审核人初始化  采购申请流程标签purApp001
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("code", "purApp001");
		paramMap.put("order", 1);
		List<Map<String, Object>> auditorList =flowService.getAuditors(paramMap);
		mv.addObject("auditor", auditorList);
		mv.addObject("projectId", projectId);
		
		List<Map<String, Object>> entries=new ArrayList<Map<String,Object>>();
		JSONObject jsonObject=new JSONObject();
		if(StringUtils.hasText(request.getParameter("purChangeTab_id"))){
			
			map.put("purChangeTab_id", request.getParameter("purChangeTab_id"));
			Map<String,List<Map<String, Object>>> listMap = purChangeService.getPurChangeData(map);
			List<Map<String, Object>> head=listMap.get("head");
			entries=listMap.get("entries");
			mv.addObject("purChange", head.get(0));
			jsonObject.put("rows", entries);
			model.addAttribute("rows",jsonObject);
		}else{
			model.addAttribute("rows",jsonObject);
		}
		
		mv.setViewName("construct/purChangeNew");
		return mv;
	}
	
	@RequestMapping(value="/purChangeNew",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> purChangeNew(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
		
	}
	
	//查看
	@RequestMapping(value="/purChangeView" , method = RequestMethod.GET)
	public ModelAndView purChangeView(HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		
		StringBuffer sbf=new StringBuffer();
		Construct construct=new Construct();
		String fields = "construct_project_id,construct_project_name,construct_project_addr,construct_project_leader,construct_project_leaderTel";
		sbf.append(" construct_project_table where construct_project_id="+projectId+"");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", sbf);
		//项目信息
		Map<String, Object> data= constructService.getConstructById(map);
		construct =(Construct) data.get("construct");
		mv.addObject("construct", construct);
		
		//审核人初始化  采购申请流程标签purApp001
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("code", "pur_change");
		paramMap.put("order", 1);
		List<Map<String, Object>> auditorList =flowService.getAuditors(paramMap);
		mv.addObject("auditor", auditorList);
		mv.addObject("projectId", projectId);
		
		List<Map<String, Object>> entries=new ArrayList<Map<String,Object>>();
		JSONObject jsonObject=new JSONObject();
		if(StringUtils.hasText(request.getParameter("purChangeTab_id"))){
			
			map.put("purChangeTab_id", request.getParameter("purChangeTab_id"));
			Map<String,List<Map<String, Object>>> listMap = purChangeService.getPurChangeData(map);
			List<Map<String, Object>> head=listMap.get("head");
			entries=listMap.get("entries");
			mv.addObject("purChange", head.get(0));
			jsonObject.put("rows", entries);
			model.addAttribute("rows",jsonObject);
		}else{
			model.addAttribute("rows",jsonObject);
		}
		
		mv.setViewName("construct/purChangeView");
		return mv;
	}
	
	
	
	//提交（每张单只允许提交一次）
	@RequestMapping(value="/submitPurChange" , method = RequestMethod.POST)
	@ResponseBody
	public String submitPurChange(HttpServletRequest request) throws Exception {
		PurChange purChange=new PurChange();
		purChange.setPurChangeTab_id(Integer.valueOf(request.getParameter("purChangeTab_id").toString().isEmpty()?"0":request.getParameter("purChangeTab_id").toString()));
		purChange.setPurChangeTab_dep(request.getParameter("purChangeTab_dep"));
		purChange.setPurChangeTab_matter(request.getParameter("purChangeTab_matter"));
		purChange.setPurChangeTab_proName(request.getParameter("purChangeTab_proName"));
		//purChange.setPurChangeTab_total(Integer.valueOf(request.getParameter("purChangeTab_total").toString().isEmpty()?"0":request.getParameter("purChangeTab_total").toString()));
		purChange.setPurChangeTab_reason(request.getParameter("purChangeTab_reason"));
		purChange.setPurChangeTab_proId(projectId);
		String rows = request.getParameter("rows");
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		String username = loginUser.getUsername();
		String auditor = request.getParameter("auditor");
		purChangeService.submitPurChange(purChange,rows,username,auditor);
		return "";
	}
	
	//列表删除
	@RequestMapping(value="/delete_purChange" , method = RequestMethod.POST)
	@ResponseBody
	public String delete_purChange(HttpServletRequest request) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		String purChangeTab_id =request.getParameter("purChangeTab_id");
		map.put("purChangeTab_id", purChangeTab_id);
		return purChangeService.delete_purChange(map);
		
	}
	//分录删除
	@RequestMapping(value="/delete_purChangeEntry" , method = RequestMethod.POST)
	@ResponseBody
	public String delete_purChangeEntry(HttpServletRequest request) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		String purChangeEntryTab_id =request.getParameter("purChangeEntryTab_id");
		map.put("purChangeEntryTab_id", purChangeEntryTab_id);
		return purChangeService.delete_purChangeEntry(map);
		
	}
	
	
	/**
	 * 跳转审核界面，初始化数据
	 */
	@RequestMapping(value = "/purChangeAudit", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView purChangeAudit(HttpServletRequest request, Model model) {
		
		ModelAndView mv = new ModelAndView();
		//取前台参数
		int auditID = Integer.parseInt(request.getParameter("auditID").toString());
		int  flow_bill_id = Integer.parseInt(request.getParameter("flow_bill_id").toString());
		int flow_status = Integer.parseInt(request.getParameter("flow_status").toString());
		int flow_audit_next_auditid = Integer.parseInt(request.getParameter("flow_audit_next_auditid").toString());
		int flow_audit_last_auditid = Integer.parseInt(request.getParameter("flow_audit_last_auditid").toString());
		int flow_node_order = Integer.parseInt(request.getParameter("flow_node_order").toString());
		
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject=new JSONObject();
		map.put("purChangeTab_id", flow_bill_id);
		Map<String,List<Map<String, Object>>> listMap = purChangeService.getPurChangeData(map);
		List<Map<String, Object>> head=listMap.get("head");
		List<Map<String, Object>> entries=listMap.get("entries");
		mv.addObject("purChange", head.get(0));
		jsonObject.put("rows", entries);
		model.addAttribute("rows",jsonObject);
		
		
		//参数传到页面
		mv.addObject("auditID", auditID);
		mv.addObject("flow_status", flow_status);
		mv.addObject("flow_audit_next_auditid", flow_audit_next_auditid);
		mv.addObject("flow_audit_last_auditid", flow_audit_last_auditid);
		mv.addObject("flow_node_order", flow_node_order);

		//审核人初始化  采购申请流程标签purApp001
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("code", "pur_change");
		paramMap.put("order", flow_node_order);
		List<Map<String, Object>> auditorList =flowService.getAuditors(paramMap);
		mv.addObject("auditor", auditorList);
		mv.addObject("length", auditorList.size());
		
		
		//审核历史意见
		JSONObject data = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flow_bill_id", flow_bill_id);
		param.put("flow_bill_url", "purChangeAudit.do");
		List<Map<String, Object>> listsMaps= flowService.getAuditOption(param);
		data.put("data", listsMaps);
		model.addAttribute("audit", data);
		
		mv.setViewName("construct/purChangeAudit");
		return mv;
	}
	
	/**
	 * 采购申请单审核界面 审核
	*/
	@RequestMapping(value="/audit_purChange" , method = RequestMethod.POST)
	@ResponseBody
	public String audit_purChange(HttpServletRequest request) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		int purChangeTab_id =Integer.valueOf(request.getParameter("purChangeTab_id").toString());
		String auditor =request.getParameter("auditor").toString();
		String option =request.getParameter("option");
		int auditID =Integer.valueOf(request.getParameter("auditID"));
		Date currentDate = new Date(System.currentTimeMillis());   
		map.put("billID",purChangeTab_id);
		map.put("auditor",auditor);
		map.put("option",option);
		map.put("auditID",auditID);
		map.put("flow_audit_time",currentDate);
	    map.put("where", "flow_audit_table");
	    String message=purChangeService.audit_purChange(map);
		return message;
	}
	
	/**
	 * 驳回
	*/
	@RequestMapping(value="/back_purChange" , method = RequestMethod.POST)
	@ResponseBody
	public String back_purChange(HttpServletRequest request) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		String billID =request.getParameter("purChangeTab_id");
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
	    /*if((flow_node_order-1)==1){
		    map.put("status", 4);
		    map.put("billID", flow_bill_id);
		    purchaseService.updateBillStatus(map);
	    }
	    */
		return null;
	}
	
	
}
