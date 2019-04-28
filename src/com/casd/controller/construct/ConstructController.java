package com.casd.controller.construct;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
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
import com.casd.entity.construct.ConstructDep;
import com.casd.entity.construct.PurchaseHead;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.PurchaseService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.uc.UserService;


@Controller
@RequestMapping("/admin")
public class ConstructController {

	@Autowired
	private ConstructService constructService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private PurchaseService purchaseService;

	
	/**
	 *  建设公司项目部list
	 */
	@RequestMapping(value = "/build_projectDepList", method = RequestMethod.GET)
	public ModelAndView build_projectDepList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		//mv.addObject("company", 1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Calendar now = Calendar.getInstance();
		int yearNum = now.get(Calendar.YEAR) - 2016;
		for (int j = 0; j < yearNum + 1; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yearMon", 2016 + j);
			list.add(map);
		}

		mv.addObject("yearMon", list);
		mv.setViewName("construct/constructDepList");
		return mv;
	}

	@RequestMapping(value = "/build_projectDepList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> build_projectDepList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();

		sbf.append(" ( ");
		sbf.append(" select tableall.*, ");
		sbf.append(" (select SUM(entry.construct_purchase_purchaseTotal) from  construct_purchase_head head  ");
		sbf.append(" LEFT JOIN construct_purchase_entry entry on head.construct_purchase_id=entry.construct_purchase_parentId ");
		sbf.append(" LEFT JOIN construct_project_table proTable on head.construct_purchase_projectId=proTable.construct_project_id ");
		sbf.append(" where proTable.construct_project_dep=tableall.constuct_project_dep_id ");
		sbf.append(" ) prutotal, ");
		sbf.append(" ( select SUM(att.hr_attend_WTLength * team.construct_project_workTeam_price) from hr_attend  att ");
		sbf.append(" LEFT JOIN construct_project_table proT on att.hr_attend_projectId=proT.construct_project_id  ");
		sbf.append(" LEFT JOIN construct_project_workteam team on team.construct_project_workTeam_projectId=proT.construct_project_id ");
		sbf.append(" where proT.construct_project_dep=tableall.constuct_project_dep_id ");
		sbf.append("  )workertotal ");
		sbf.append(" from( ");
		sbf.append(" SELECT ");
		sbf.append(" dep.*,SUM(con.manage_contract_amount+con.manage_contract_visaAmount) conAmount ");
		sbf.append(" FROM ");
		sbf.append(" construct_project_dep dep ");
		sbf.append(" LEFT JOIN construct_project_table pro on dep.constuct_project_dep_id=pro.construct_project_dep  ");
		sbf.append(" LEFT JOIN manage_contract  con on con.manage_contract_id=pro.construct_project_contractId ");
		sbf.append(" WHERE ");
		sbf.append("  constuct_project_dep_company = 1");
		
		
		String yearMon = request.getParameter("yearMon");
		if (StringUtils.isEmpty(yearMon)) {
			sbf.append(" and  con.manage_contract_startTime like '%" + yearMon
					+ "%'");
		}
		
		sbf.append(" GROUP BY dep.constuct_project_dep_id ");
		sbf.append(" )tableall ");
		sbf.append(" )tables ");
		sbf.append(" WHERE 1 = 1 ");
		
		String dep_name = request.getParameter("dep_name");
		if (StringUtils.hasText(dep_name)) {
			sbf.append(" and  constuct_project_dep_name like '%" + dep_name
					+ "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructDepList(
				pageIndex, pageSize, record, sbf.toString());
		
		
		Double conAmountSum=0.00;
		Double prutotalSum=0.00;
		Double workertotalSum=0.00;
	    for (Map<String, Object> m : data){
	    	conAmountSum =conAmountSum + Double.valueOf(StringUtils.isEmpty(m.get("conAmount"))?"0.00":m.get("conAmount").toString());
	    	prutotalSum =prutotalSum + Double.valueOf(StringUtils.isEmpty(m.get("prutotal"))?"0.00":m.get("prutotal").toString());
	    	workertotalSum =workertotalSum + Double.valueOf(StringUtils.isEmpty(m.get("workertotal"))?"0.00":m.get("workertotal").toString());
	    }
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		footMap.put("conAmount", conAmountSum);
		footMap.put("prutotal", prutotalSum);
		footMap.put("workertotal", workertotalSum);
		list.add(footMap);
		json.put("footer", list);
		
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}
	
	
	/**
	 * 项目部弹框
	 */
	@RequestMapping(value = "/constructDepCheck", method = RequestMethod.GET)
	public ModelAndView constructDepCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/constructDepCheck");
		return mv;
	}

	@RequestMapping(value = "/constructDepCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> constructDepCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();

		sbf.append(" construct_project_dep where 1=1  ");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructDepList(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}
	
	
	
	/**
	 *  发展公司改造项目部list
	 */
	@RequestMapping(value = "/deveDepList", method = RequestMethod.GET)
	public ModelAndView deve_projectDepList(HttpServletRequest request) {
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
		
		mv.setViewName("construct/deveDepList");
		return mv;
	}

	@RequestMapping(value = "/deveDepList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deveDepList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		
		int constuct_project_dep_company = Integer.parseInt(request.getParameter("dep_company"));

		sbf.append(" ( ");
		sbf.append(" select tableall.*, ");
		sbf.append(" (select SUM(entry.construct_purchase_purchaseTotal) from  construct_purchase_head head  ");
		sbf.append(" LEFT JOIN construct_purchase_entry entry on head.construct_purchase_id=entry.construct_purchase_parentId ");
		sbf.append(" LEFT JOIN construct_project_table proTable on head.construct_purchase_projectId=proTable.construct_project_id ");
		sbf.append(" where proTable.construct_project_dep=tableall.constuct_project_dep_id ");
		sbf.append(" ) prutotal, ");
		sbf.append(" ( select SUM(att.hr_attend_WTLength * team.construct_project_workTeam_price) from hr_attend  att ");
		sbf.append(" LEFT JOIN construct_project_table proT on att.hr_attend_projectId=proT.construct_project_id  ");
		sbf.append(" LEFT JOIN construct_project_workteam team on team.construct_project_workTeam_projectId=proT.construct_project_id ");
		sbf.append(" where proT.construct_project_dep=tableall.constuct_project_dep_id ");
		sbf.append("  )workertotal ");
		sbf.append(" from( ");
		sbf.append(" SELECT ");
		sbf.append(" dep.*,SUM(con.manage_contract_amount+con.manage_contract_visaAmount) conAmount ");
		sbf.append(" FROM ");
		sbf.append(" construct_project_dep dep ");
		sbf.append(" LEFT JOIN construct_project_table pro on dep.constuct_project_dep_id=pro.construct_project_dep  ");
		sbf.append(" LEFT JOIN manage_contract  con on con.manage_contract_id=pro.construct_project_contractId ");
		sbf.append(" WHERE ");
		sbf.append("  constuct_project_dep_company = "+constuct_project_dep_company+" ");
		
/*		String yearMon = request.getParameter("yearMon");
		if (StringUtils.hasText(yearMon)) {
			sbf.append(" and  con.manage_contract_startTime like '%" + yearMon
					+ "%'");
		}*/
		
		sbf.append(" GROUP BY dep.constuct_project_dep_id ");
		sbf.append(" )tableall ");
		sbf.append(" )tables ");
		sbf.append(" WHERE 1 = 1 ");
		
		String dep_name = request.getParameter("dep_name");
		if (StringUtils.hasText(dep_name)) {
			sbf.append(" and  constuct_project_dep_name like '%" + dep_name
					+ "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructDepList(
				pageIndex, pageSize, record, sbf.toString());
		
		
		Double conAmountSum=0.00;
		Double prutotalSum=0.00;
		Double workertotalSum=0.00;
	    for (Map<String, Object> m : data){
	    	conAmountSum =conAmountSum + Double.valueOf(StringUtils.isEmpty(m.get("conAmount"))?"0.00":m.get("conAmount").toString());
	    	prutotalSum =prutotalSum + Double.valueOf(StringUtils.isEmpty(m.get("prutotal"))?"0.00":m.get("prutotal").toString());
	    	workertotalSum =workertotalSum + Double.valueOf(StringUtils.isEmpty(m.get("workertotal"))?"0.00":m.get("workertotal").toString());
	    }
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		footMap.put("conAmount", conAmountSum);
		footMap.put("prutotal", prutotalSum);
		footMap.put("workertotal", workertotalSum);
		list.add(footMap);
		json.put("footer", list);
		
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
		
	}
	
	
	/*
	@RequestMapping(value = "/deve_projectDepList", method = RequestMethod.GET)
	public ModelAndView deve_projectDepList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("company", 2);
		mv.setViewName("construct/constructDepList");
		return mv;
	}

	@RequestMapping(value = "/deve_projectDepList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deve_projectDepList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(" construct_project_dep where 1=1  and constuct_project_dep_company=2");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data = constructService.constructDepList(
				pageIndex, pageSize, record, sbf.toString());
		
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
		
	}*/
	
	
	/**
	 *  发展公司加盟项目部list
	 */
	@RequestMapping(value = "/join_projectDepList", method = RequestMethod.GET)
	public ModelAndView join_projectDepList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("company", 3);
		mv.setViewName("construct/constructDepList");
		return mv;
	}

	@RequestMapping(value = "/join_projectDepList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> join_projectDepList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(" construct_project_dep where 1=1  and constuct_project_dep_company=3");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data = constructService.constructDepList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}
	
	
	
	/**
	*  科技采购报表
	*/
	@RequestMapping(value = "/devePurReport", method = RequestMethod.GET)
	public ModelAndView devePurReport(HttpServletRequest request,Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/devePurReport");
		return mv;
	}
	
	
	@RequestMapping(value = "/devePurReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> devePurReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String filds = "*";
		
		sbf.append(" construct_purchase_entry entry left join construct_purchase_head pur on pur.construct_purchase_id=entry.construct_purchase_parentId  ");
		sbf.append(" left join construct_project_table pro on pur.construct_purchase_projectId=pro.construct_project_id  ");
		sbf.append(" left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep where dep.constuct_project_dep_company=2 ");
		
		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and  pro.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		String construct_purchase_material = request.getParameter("construct_purchase_material");
		if (StringUtils.hasText(construct_purchase_material)) {
			sbf.append(" and  entry.construct_purchase_material like '%" + construct_purchase_material
					+ "%'");
		}
		
		String construct_purchase_status = request.getParameter("construct_purchase_status");
		if (StringUtils.hasText(construct_purchase_status)) {
			sbf.append(" and  pur.construct_purchase_status = " + construct_purchase_status+ "");
		}
		
		sbf.append(" ORDER BY pur.construct_purchase_creatTime DESC");

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data = purchaseService.purchaseList(filds,
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
		
	}
	
	
	
	
	

	/**
	 * 项目list
	 */
	@RequestMapping(value = "/constructList", method = RequestMethod.GET)
	public ModelAndView constructList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		int projectDep=Integer.valueOf(request.getParameter("constuct_project_dep_id"));
		/*if(!request.getParameter("constuct_project_dep_id").isEmpty()){   //当返回list界面时是空的，用上一个
			String dep_id = request.getParameter("constuct_project_dep_id");
			projectDep=Integer.valueOf(dep_id);
		}*/
		
		// 获取用户
		/*User loginUser = (User) request.getSession().getAttribute("loginUser");
		String userId = String.valueOf(loginUser.getUserid());

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields = "a.constuct_project_dep_list";
		sbf.append(" construct_project_dep a");
		sbf.append(" WHERE a.constuct_project_dep_id =" + projectDep);

		params.put("where", sbf.toString());
		params.put("fields", fields);
		params.put("userId", userId);

		Boolean isExist = constructService.isExist(params);
		mv.addObject("projectDep", projectDep);
		// 判断是否有权限入
		if (isExist) {
			mv.setViewName("construct/constructList");
		} else {
			mv.setViewName("common/noAuthority");
		}*/
		
		mv.addObject("projectDep", projectDep);
		mv.setViewName("construct/constructList");
		return mv;
	}

	@RequestMapping(value = "/constructList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> constructList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		int projectDep=Integer.valueOf(request.getParameter("constuct_project_dep_id"));
		
		sbf.append("  construct_project_table project left join manage_contract contract on project.construct_project_contractId=contract.manage_contract_id where 1=1 and construct_project_dep="
				+ projectDep + "");
		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and project.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	/**
	 * 项目新增,修改
	 */

	@RequestMapping(value = "/constructNew", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView constructNew(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Construct construct=new Construct();
		JSONObject dataObject=new JSONObject();
		int projectDep=Integer.valueOf(request.getParameter("projectDep"));
		String type="'new'";
		if(StringUtils.hasText(request.getParameter("construct_project_id"))){
			type="'update'";
			Map<String, Object> map=new HashMap<String, Object>();
			int construct_project_id=Integer.valueOf(request.getParameter("construct_project_id").toString());
			StringBuffer sbf = new StringBuffer();
			sbf.append(" construct_project_table project left join manage_contract contract on project.construct_project_contractId=contract.manage_contract_id "
					+ " left join construct_project_dep dep on dep.constuct_project_dep_id=project.construct_project_dep  where 1=1 and project.construct_project_id="
					+ construct_project_id + "");
			String fields="project.*,dep.*,contract.*,(IFNULL(contract.manage_contract_amount,0) + IFNULL(contract.manage_contract_visaAmount,0)) total ";
			map.put("where", sbf.toString());
			map.put("fields", fields);
			Map<String, Object> data= constructService.getConstructById(map);
			construct =(Construct) data.get("construct");
			List<Map<String, Object>> entry =(List<Map<String, Object>>) data.get("workTeamList");
			dataObject.put("construct", construct);
			dataObject.put("entry", entry);
			
			
		}else{
			construct.setConstruct_project_id(0);
			construct.setConstruct_project_dep(projectDep);
			dataObject.put("construct", construct);
			dataObject.put("entry", "");
		}
		mv.addObject("type",type );
		mv.addObject("projectDep", projectDep);
		mv.addObject("construct", dataObject);
		mv.setViewName("construct/constructNew");
		
		return mv;
	}

	
	@RequestMapping(value = "/constructView", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView constructView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		JSONObject dataObject=new JSONObject();
		Construct construct=new Construct();

		int projectDep=Integer.valueOf(request.getParameter("projectDep"));
		if(StringUtils.hasText(request.getParameter("construct_project_id"))){
			Map<String, Object> map=new HashMap<String, Object>();
			int construct_project_id=Integer.valueOf(request.getParameter("construct_project_id").toString());
			StringBuffer sbf = new StringBuffer();
			sbf.append(" construct_project_table project left join manage_contract contract on project.construct_project_contractId=contract.manage_contract_id where 1=1 and construct_project_id="
					+ construct_project_id + "");
			String fields="project.*,contract.*,(IFNULL(contract.manage_contract_amount,0) + IFNULL(contract.manage_contract_visaAmount,0)) total ";
			map.put("where", sbf.toString());
			map.put("fields", fields);
			Map<String, Object> data= constructService.getConstructById(map);
			construct =(Construct) data.get("construct");
			List<Map<String, Object>> entry =(List<Map<String, Object>>) data.get("workTeamList");
			dataObject.put("construct", construct);
			dataObject.put("entry", entry);
		}
		

		mv.addObject("projectDep", projectDep);
		mv.addObject("construct", dataObject);
		mv.setViewName("construct/constructView");
		
		return mv;
	}
	
	
	/**
	 * 项目保存
	 */

	@RequestMapping(value = "/saveConstruct", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveConstruct(HttpServletRequest request,
			Construct construct) throws Exception {
		
		JSONArray myJsonArray = JSONArray.fromObject(request
				.getParameter("rows"));
		JSONObject jsonObject=constructService.saveConstruct(construct,myJsonArray);
		return jsonObject;
	}
	
	
	/**
	 * 项目作业列表
	 */
	@RequestMapping(value = "/taskList", method = RequestMethod.GET)
	public ModelAndView taskList(HttpServletRequest request) {
		String construct_project_id = request.getParameter(
				"construct_project_id").toString();
		HttpSession session = request.getSession();
		session.setAttribute("projectId", construct_project_id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/taskList");
		return mv;
	}

	// 修改数据初始化
	@RequestMapping(value = "/queryId_dep", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryId_dep(HttpServletRequest request,
			Model model) throws Exception {

		StringBuffer sbf = new StringBuffer();
		String cid = request.getParameter("cid");
		Map<String, Object> json = new HashMap<String, Object>();
		sbf.append(" construct_project_dep  WHERE constuct_project_dep_id="
				+ cid);
		ConstructDep dep = constructService.findDepById(sbf.toString());

		if (StringUtils.hasText(dep.getConstuct_project_dep_list())) {
			String fields = " userid,username";
			sbf.delete(0, sbf.length());
			sbf.append(" uc_user where userid in("
					+ dep.getConstuct_project_dep_list() + ")");
			List<Map<String, Object>> listMap = userService.queryId(fields,
					sbf.toString());
			json.put("listMap", listMap);
		}
		json.put("dep", dep);
		return json;
	}

	/**
	 * 项目列表修改
	 * */
	@RequestMapping(value = "/updata_dep", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updata_dep(HttpServletRequest request, ConstructDep dep,
			String rowData) throws Exception {

		JSONObject json = new JSONObject();
		try {
			/*rowData = rowData.substring(1);
			String[] ids = rowData.split("]");
			dep.setConstuct_project_dep_list(ids[0]);*/
			constructService.updatectDep(dep);
		} catch (Exception e) {
			json.put("msg", "操作有误!");
			
			e.printStackTrace();
		}
		return json;

	}

	// 项目部列表删除
	@RequestMapping(value = "/delete_Dep", method = RequestMethod.POST)
	@ResponseBody
	public Object delete_Dep(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		String dep_id = request.getParameter("dep_id");
		
		try {

			StringBuffer sbf = new StringBuffer();
			sbf.append(" where constuct_project_dep_id="+ dep_id);
			constructService.delete_Dep(sbf.toString());

		} catch (Exception e) {
			json.put("msg", "删除失败!");  
			e.printStackTrace();
		}

		return json;

	}
	
	/**
	 * 删除分录
	*/
	@RequestMapping(value = "/delete_WorkTeam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_WorkTeam(HttpServletRequest request) throws Exception {
		Map<String, Object> json=new HashMap<String, Object>();
		try {	
		String cid = request.getParameter("cid");
		StringBuffer sbf = new StringBuffer();
		sbf.append(" where construct_project_workTeam_id =" + cid + "");
		constructService.delete_WorkTeam(sbf.toString());
		json.put("msg", "删除成功！");
		} catch (Exception e) {
			json.put("msg", "删除失败！");
			e.printStackTrace();
		}
		return json;
	}
	
	
	//删除
	@RequestMapping(value="/delePro" , method = RequestMethod.POST)
	@ResponseBody
	public String delePro(HttpServletRequest request, Model model) {
		Map<String, Object> map=new HashMap<String, Object>();
		String ids =request.getParameter("ids");
		ids=ids.substring(1);
		String[] id=ids.split("]");
		map.put("what",id[0]);        
	    map.put("where", "construct_project_table");
	    constructService.delePro(map);
		return null;
	}

	
	/* @RequestMapping(value="/mytest" , method = RequestMethod.GET)
	 public  ModelAndView mytest(){
		 ModelAndView mView=new ModelAndView();
		 mView.setViewName("test");
		return mView;
		 
	 }
	 
	 @RequestMapping(value="/mytest" , method = RequestMethod.POST)
	 @ResponseBody
	public  Object mytest(HttpServletRequest request,File pic_path) throws IOException {
			Map<String, Object> json = new HashMap<String, Object>();
			String rootPath = request.getContextPath();
			String realpath = request.getServletContext().getRealPath("");
			List<List<Object>> list = new ArrayList<List<Object>>();
			ImportExcel ie = new ImportExcel();
			String url = request.getParameter("filUrl");
			if(StringUtils.isEmpty(url)){
				json.put("Success", false);
				json.put("Msg", "请选择要导入的文件");
				return json;
			}

			if (url.startsWith(rootPath)) {
				url = url.substring(rootPath.length());
			}
			
			if(!url.substring(url.lastIndexOf(".")).equals(".xlsx")){
				json.put("Success", false);
				json.put("Msg", "请选择Excel2007以上版本文件.xlsx");
				return json;
			}
			File file = new File(realpath + url);
			list = ie.read2007Excel(file);
			//读取完信息删除上传文件 
		    // 判断文件存在,删除  
		    if (file.exists()) {
		       file.delete();
		    }
				return null;
		  
		
	}
	

	 }*/
	 
	
	
	@RequestMapping(value = "/excelprint", method = RequestMethod.GET)
	 public void excelprint(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String taskid = request.getParameter("taskid");// 获取任务id
		OutputStream ous =null;
	  try {
		
		String bizId = null; // 业务编号
		if (taskid != null) {
		    String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
			String[] strs = bizkey.split("\\.");
		
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
		}else {
			bizId=request.getParameter("bizId");
		}
	
	
		double total =0;

		String beyId = "Purchase_payment"; // 流程实例key 请勿改动

		List<Map<String, Object>> list = activitiService.viewHisComments(bizId, beyId);
	
		Construct construct =null;
		PurchaseHead head =null;
		Map<String, Object> maps = new HashMap<String, Object>();
		if (!bizId.isEmpty()) {
			maps = purchaseService.initPurchase(bizId,
					"view", 0);

			 head = (PurchaseHead) maps.get("purchaseHead");
			StringBuffer sbf = new StringBuffer();
	
			String fields = "pro.construct_project_id,pro.construct_project_name,pro.construct_project_addr,pro.construct_project_leader,pro.construct_project_leaderTel,dep.constuct_project_dep_company";
			sbf.append(" construct_project_dep dep left join  construct_project_table pro on dep.constuct_project_dep_id=pro.construct_project_dep  where pro.construct_project_id="
					+ head.getConstruct_purchase_projectId() + "");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fields", fields);
			map.put("where", sbf);
			Map<String, Object> data= constructService.getConstructById(map);
			construct =(Construct) data.get("construct");
		}
		
		
		JSONArray rows4 = JSONArray.fromObject(maps.get("rows"));//材料表集合
		
	
		JSONArray rows = JSONArray.fromObject(list); //历史记录集合
	
		 HSSFWorkbook wkb = new HSSFWorkbook();
		//建立新的sheet对象（excel的表单）
		HSSFSheet sheet=wkb.createSheet("材料单明细");
		HSSFCellStyle style = wkb.createCellStyle();
		 HSSFFont font = wkb.createFont();
	        font.setFontHeightInPoints((short) 12);//字号
	        font.setFontName("宋体");
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
	        style.setFont(font);
	        style.setWrapText(true);
		//标题行
		  HSSFRow row = sheet.createRow(0);
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell=row.createCell(0);
		      //设置单元格内容
		cell.setCellValue("诚安时代材料计划采购单");
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
        cell.setCellStyle(style);
        row.setHeight((short) (30*20));
      

		  //在sheet里创建第2行
		  HSSFRow row2=sheet.createRow(1);
		  cell=row2.createCell(0);		 
		  row2.setHeight((short) (15*20));
		  cell.setCellValue("所属公司");
		  cell=row2.createCell(1);		 
		  row2.setHeight((short) (15*20));
		  cell.setCellValue(String.valueOf(construct.getConstuct_project_dep_company()).equals("1")?"建设公司":"科技公司");
		  
		  
		  row2.createCell(5).setCellValue("计划单单号：");
		  row2.setHeight((short) (15*20));
		 
		//在sheet里创建第3行
		  HSSFRow row3=sheet.createRow(2);
		  HSSFCell  cellrow3 = row3.createCell(0); 
		  style=sts1(wkb);
		
		  cellrow3.setCellStyle(style);
		  cellrow3.setCellValue("一、项目信息");
		  sheet.addMergedRegion(new CellRangeAddress(2,2,0,9));
		  row3.setHeight((short) (15*20));
		 
		  
		  HSSFRow row4=sheet.createRow(3);
		  row4.createCell(0).setCellValue("工程名称：");
		   row4.createCell(1).setCellValue(construct.getConstruct_project_name());
		    row4.createCell(5).setCellValue("工程地址：");
		  row4.createCell(6).setCellValue(construct.getConstruct_project_addr()); 
		  sheet.addMergedRegion(new CellRangeAddress(3,3,1,3));
		  
		  sheet.addMergedRegion(new CellRangeAddress(3,3,6,10));
		  row4.setHeight((short) (15*20));
		
		  cell.setCellStyle(style);
		  
		  HSSFRow row5=sheet.createRow(4);
		  row5.createCell(0).setCellValue("项目经理：");
		  row5.createCell(1).setCellValue(construct.getConstruct_project_leader());
		  row5.createCell(5).setCellValue("联系方式：");
         row5.createCell(6).setCellValue(construct.getConstruct_project_leaderTel());
         sheet.addMergedRegion(new CellRangeAddress(4,4,1,3));
         sheet.addMergedRegion(new CellRangeAddress(4,4,6,10));
         row5.setHeight((short) (15*20));
		
		  cell.setCellStyle(style);
		  
		
	
		  
		  HSSFRow row7=sheet.createRow(5);
		  
		  HSSFCell  cellrow7 = row7.createCell(0); 
		  style=sts1(wkb);
		  cellrow7.setCellStyle(style);
		  cellrow7.setCellValue("二、材料清单信息");

		  sheet.addMergedRegion(new CellRangeAddress(5,5,0,10));
		  row7.setHeight((short) (15*20));
		
		 
		  
		  HSSFRow row8=sheet.createRow(6);
		  row8.createCell(0).setCellValue("计划日期：");
		  row8.createCell(5).setCellValue("送达日期：");
		  if (head.getConstruct_purchase_planDate()!=null && head.getConstruct_purchase_arriveDate()!=null) {
			    row8.createCell(1).setCellValue(head.getConstruct_purchase_planDate().toString());
			    row8.createCell(6).setCellValue(head.getConstruct_purchase_arriveDate().toString());
		 }else {
			 row8.createCell(1).setCellValue(0);
			  row8.createCell(6).setCellValue(0);
			 
		}
		
		  sheet.addMergedRegion(new CellRangeAddress(6,6,6,9));
		  row8.setHeight((short) (15*20));
		  
		  cell.setCellStyle(style);
		   
		  row=sheet.createRow(7);
		  row.createCell(0).setCellValue("材料计划员：");
		  row.createCell(1).setCellValue(head.getConstruct_purchase_planMan());
//		  sheet.addMergedRegion(new CellRangeAddress(7,7,0,1));
		  row.createCell(5).setCellValue("材料复核员：");
		 // sheet.addMergedRegion(new CellRangeAddress(8,8,4,1));
		  row.createCell(6).setCellValue(head.getConstruct_purchase_reviewer());
		// sheet.addMergedRegion(new CellRangeAddress(8,8,7,9));
		 
		  row.createCell(8).setCellValue("材料品牌");
		  row.setHeight((short) (15*20));
		  
		  
		  
		  HSSFRow row6=sheet.createRow(8);
		  HSSFCell cellrow6 = row6.createCell(0);
		
		   style=sts1(wkb);
		  cellrow6.setCellStyle(style);
		  cellrow6.setCellValue("供应商名称：");
		  
		  cellrow6 = row6.createCell(1);
		  style=sts1(wkb);
		  cellrow6.setCellStyle(style);
		  cellrow6.setCellValue(head.getConstruct_purchase_supplier());
		  
		
		  
		  cellrow6 = row6.createCell(8); 
		  style=sts1(wkb);
		
		  cellrow6.setCellStyle(style);
		  cellrow6.setCellValue("联系方式：");
		  row6.createCell(6).setCellValue(head.getConstruct_purchase_supplierTel());
		  sheet.addMergedRegion(new CellRangeAddress(8,8,1,5));
	      sheet.addMergedRegion(new CellRangeAddress(8,8,8,9));
		
		  row6.setHeight((short) (15*20));
		  style=sts(wkb);
		    //在sheet里创建第十行
		      row=sheet.createRow(9);    		     
		      //创建单元格并设置单元格内容
		    
		   
		      HSSFCell cell0 = row.createCell(1);
		    
		      cell0.setCellStyle(style);
		      cell0.setCellValue("材料名称");
		      
		      HSSFCell cell1 = row.createCell(2);
		      cell1.setCellStyle(style);
		      cell1.setCellValue("型号规格");
		        		      
		      HSSFCell cell2 = row.createCell(3);
		      cell2.setCellStyle(style);
		      cell2.setCellValue("单位"); 
		    
		      
		      HSSFCell cell3 = row.createCell(4);
		      cell3.setCellStyle(style);
		      cell3.setCellValue("合同工程量"); 
		   
		      HSSFCell cell4 = row.createCell(5);
		      cell4.setCellStyle(style);
		      cell4.setCellValue("累计审批量");
		      	      
		      HSSFCell cell5 = row.createCell(6);
		      cell5.setCellStyle(style);
		      cell5.setCellValue("计划采购量");
		      
		
		      HSSFCell cell6 = row.createCell(7);
		      cell6.setCellStyle(style);
		      cell6.setCellValue("合同单价");
		      
		    
		      HSSFCell cell7 = row.createCell(8);
		      cell7.setCellStyle(style);
		      cell7.setCellValue("采购单价");
		      
		

		      HSSFCell cell8 = row.createCell(9);
		      cell8.setCellStyle(style);
		      cell8.setCellValue("采购小计");
		      
		    
		      HSSFCell cell9 = row.createCell(0);
		      cell9.setCellStyle(style);
		      cell9.setCellValue("编号");
		      style.setWrapText(true);
		      row.setHeight((short) (22*20));
			
		      HSSFCell cell10 = row.createCell(10);
		      cell10.setCellStyle(style);
		      cell10.setCellValue("备注");
	
			
			 int i=0;
			 JSONObject myjObject =null;
			for (i = 0; i <rows4.size(); i++){
				 myjObject = rows4.getJSONObject(i);
			
				   HSSFRow rowList = sheet.createRow(9+i+1); 
		    
	                HSSFCell cell9List = rowList.createCell(0);
	             
	                cell9List.setCellStyle(style);
	              
	                if (!myjObject.containsKey("num")) {

	                	 cell9List.setCellValue(0);
					}else{
						 cell9List.setCellValue(myjObject.getString("num"));
					}
									   
	                HSSFCell cell0List = rowList.createCell(1);
	                cell0List.setCellStyle(style);
	                cell0List.setCellValue(myjObject.getString("construct_purchase_material"));
	                
	                HSSFCell cell1List = rowList.createCell(2);
	                cell1List.setCellStyle(style);
	                cell1List.setCellValue(myjObject.getString("construct_purchase_model"));
	                
	                HSSFCell cell2List = rowList.createCell(3);
	                cell2List.setCellStyle(style);
	                cell2List.setCellValue(myjObject.getString("construct_purchase_unit"));
	                
	                HSSFCell cell3List = rowList.createCell(4);
	                cell3List.setCellStyle(style);
	                cell3List.setCellValue(myjObject.getString("construct_purchase_quantities"));

	                HSSFCell cell4List = rowList.createCell(5);
	                cell4List.setCellStyle(style);
	                cell4List.setCellValue(myjObject.getString("construct_purchase_approvalNum"));
	                
	                HSSFCell cell5List = rowList.createCell(6);
	                cell5List.setCellStyle(style);
	                cell5List.setCellValue(myjObject.getString("construct_purchase_applyNum"));
			
	                HSSFCell cell6List = rowList.createCell(7);
	                cell6List.setCellStyle(style);
	                cell6List.setCellValue(myjObject.getString("construct_purchase_contractPrice"));
			
	                HSSFCell cell7List = rowList.createCell(8);
	                cell7List.setCellStyle(style);
	                cell7List.setCellValue(myjObject.getString("construct_purchase_purchasePrice"));
			
	                HSSFCell cell8List = rowList.createCell(9);
	                cell8List.setCellStyle(style);
	                cell8List.setCellValue(myjObject.getString("construct_purchase_purchaseTotal"));
	                
	                HSSFCell cell10List = rowList.createCell(10);
	                cell10List.setCellStyle(style);
	                cell10List.setCellValue(myjObject.getString("construct_purchase_remarks"));
	                
                   total+=Double.parseDouble(myjObject.getString("construct_purchase_purchaseTotal"));
	                // 行高
	                rowList.setHeight((short) (26*20));
	                
	            
			}
			   row = sheet.createRow(9+i+1);
			   HSSFCell cell0List = row.createCell(0);
			   
			   cell0List.setCellValue("合计");
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(1);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(2);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(3);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(4);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(5);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(6);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(7);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(8);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(9);
			   cell0List.setCellValue(total);
			   cell0List.setCellStyle(style);
			   cell0List = row.createCell(10);
			   cell0List.setCellStyle(style);
			   sheet.addMergedRegion(new CellRangeAddress(9+i+1,9+i+1,1,8));
			   cell0List.setCellStyle(style);
			   
			   row.setHeight((short) (26*20));
		
			   for (int j = 0; j < list.size(); j++) {

				   myjObject = rows.getJSONObject(j);
				/*   Iterator<String> it = myjObject.keys(); 
				   while(it.hasNext()){
				   // 获得key
				   String key = it.next(); 
				   String value = myjObject.getString("username");    
				   System.out.println("key: "+key+",value:"+value);
				   }*/
				   row=sheet.createRow(9+i+j+4);  
				    HSSFCell cell10List = row.createCell(0);
				    cell10List.setCellStyle(style);
		           cell10List.setCellValue(list.get(j).get("username")==null?"未审核":list.get(j).get("username").toString());
		           cell10List = row.createCell(1);
		           cell10List.setCellStyle(style);

		           cell10List.setCellValue(list.get(j).get("MESSAGE_")==null?"未审核":list.get(j).get("MESSAGE_").toString());
		           sheet.addMergedRegion(new CellRangeAddress(9+i+j+4,9+i+j+4,1,5));
		           cell10List = row.createCell(2);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(3);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(4);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(5);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(6);
		           cell10List.setCellStyle(style);
		       
		           if(list.get(j).get("department_name")==null){
		        	   cell10List.setCellValue(list.get(j).get("center_name")==null?"未审核":list.get(j).get("center_name").toString());
		        	
		        	 }else{
		        		   cell10List.setCellValue(list.get(j).get("department_name").toString());
		        	 }
		           sheet.addMergedRegion(new CellRangeAddress(9+i+j+4,9+i+j+4,6,10));
		         
		           cell10List = row.createCell(7);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(8);
		           cell10List.setCellStyle(style);
		           
		       
		           cell10List = row.createCell(9);
		           cell10List.setCellStyle(style);
		           cell10List = row.createCell(10);
		           cell10List.setCellStyle(style);
			}
			   	     
			sheet.setColumnWidth(0, 13 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 6 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(6, 10 * 256);
			sheet.setColumnWidth(7, 13 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 10 * 256);
		


           HSSFPrintSetup ps = sheet.getPrintSetup();
			ps.setLandscape(false); // 打印方向，true：横向，false：纵向
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
			sheet.setMargin(HSSFSheet.BottomMargin,( double ) 0.5 );// 页边距（下）
			sheet.setMargin(HSSFSheet.LeftMargin,( double ) 0.0 );// 页边距（左）
			sheet.setMargin(HSSFSheet.RightMargin,( double ) 0.0 );// 页边距（右）
			sheet.setMargin(HSSFSheet.TopMargin,( double ) 0.5 );// 页边距（上）
		    ps.setScale((short)90);//自定义缩放①，此处100为无缩放
			sheet.setHorizontallyCenter(true);//设置打印页面为水平居中
			//sheet.setVerticallyCenter(true);//设置打印页面为垂直居中使用POI
		
		
			response.reset();
			String filename = "材料计划采购单.xls";// 设置下载时客户端Excel的名称

			response.setContentType("application/vnd.ms-excel");
			response.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ java.net.URLEncoder.encode(filename, "UTF-8"));

			 ous = new BufferedOutputStream(
					response.getOutputStream());

			wkb.write(ous);
			ous.flush();
	    } catch (Exception e) {
             e.printStackTrace();
             response.sendError(500, e.getMessage());
		
		}finally{
			
			if (ous!=null) {
				ous.close();
			}

		}
			
	}
	
	//查询已完成的任务 act_hi_taskinst
	@RequestMapping(value = "/findtaskinst", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findtaskinst(String processInstanceId) throws Exception {
		String fields="aht.TASK_DEF_KEY_,aht.NAME_,aht.ASSIGNEE_,count(distinct aht.id_)";
		String where="act_hi_taskinst aht WHERE aht.PROC_INST_ID_ = "+processInstanceId+" AND aht.assignee_ IS NOT NULL";
		       where+=" group by aht.TASK_DEF_KEY_";
		return userService.queryId(fields, where);	
	}
	
	
	
	
	  public static HSSFCellStyle  sts(HSSFWorkbook workbook){
   	  HSSFFont font = workbook.createFont();
   	   HSSFCellStyle styleList = workbook.createCellStyle();
           HSSFFont fontList = workbook.createFont();
           fontList.setFontHeightInPoints((short) 12);//字号
           fontList.setFontName("宋体"); 
           styleList.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
           styleList.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
           styleList.setFont(font);
           styleList.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
           styleList.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
           styleList.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
           styleList.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边   
			return styleList;
      }
	  
	  public static HSSFCellStyle  sts1(HSSFWorkbook workbook){
   	  HSSFFont font = workbook.createFont();
   	   HSSFCellStyle styleList = workbook.createCellStyle();
           HSSFFont fontList = workbook.createFont();
           fontList.setFontHeightInPoints((short) 8);//字号
           fontList.setFontName("宋体");
           font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
	      
           styleList.setFont(font);
           styleList.setWrapText(true);
          
			return styleList;
   }

	 
}
