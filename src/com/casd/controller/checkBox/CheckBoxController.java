package com.casd.controller.checkBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.service.checkBox.CheckBoxService;
import com.casd.service.construct.ConstructService;
import com.casd.service.finance.MaterialPriceService;
import com.casd.service.finance.MaterialService;

@Controller
@RequestMapping("/admin")
public class CheckBoxController {
	
	
	@Autowired
	private CheckBoxService checkBoxService;
	@Autowired
	private MaterialPriceService materialPriceService;
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private ConstructService constructService;
	
	@RequestMapping(value = "/userListCheck", method = RequestMethod.GET)
	public ModelAndView userListCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("index",request.getParameter("index"));
		mv.setViewName("checkBox/userListCheck");
		return mv;

	}
	
	@RequestMapping(value="/userListCheck" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userListCheck(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String username=request.getParameter("username");
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		StringBuffer fields=new StringBuffer();
		fields.append("us.userid,us.username,us.phone_number, role.role_name,dep.department_name,cen.center_name,com.company_name,dep.department_id");
		sbf.append("  uc_user us ");
		sbf.append("LEFT JOIN uc_role role on us.role_id=role.role_id ");
		sbf.append("LEFT JOIN uc_department dep on us.department=dep.department_id ");
		sbf.append("LEFT JOIN uc_center cen on dep.department_centerId=cen.center_id ");
		sbf.append("LEFT JOIN uc_company com on com.company_id=dep.department_companyId ");
		sbf.append(" where 1=1");

		if (StringUtils.hasText(username)) {
			sbf.append(" and username like '%" + username + "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data=checkBoxService.getList(pageIndex, pageSize, record, sbf.toString(),fields.toString());
		
	    json.put("rows", data);
	    json.put("total", record.getValue());
	    return json;
	}
	
	//后续版本
	@RequestMapping(value = "/userListCheck1", method = RequestMethod.GET)
	public ModelAndView userListCheck1(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("index",request.getParameter("index"));
		mv.setViewName("checkBox/userListCheck1");
		return mv;

	}
	
	@RequestMapping(value="/userListChecks" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userListCheck1(Integer limit,Integer page,String username) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		page = page==null ? 1: page;
		page=page-1;
	
		StringBuffer sbf=new StringBuffer();
		StringBuffer fields=new StringBuffer();
		fields.append("us.userid,us.username,us.phone_number, role.role_name,dep.department_name,cen.center_name,com.company_name,dep.department_id");
		sbf.append("  uc_user us ");
		sbf.append("LEFT JOIN uc_role role on us.role_id=role.role_id ");
		sbf.append("LEFT JOIN uc_department dep on us.department=dep.department_id ");
		sbf.append("LEFT JOIN uc_center cen on dep.department_centerId=cen.center_id ");
		sbf.append("LEFT JOIN uc_company com on com.company_id=dep.department_companyId ");
		sbf.append(" where 1=1");
		
		if (StringUtils.hasText(username)) {
			sbf.append(" and username like '%" + username + "%'");
		}
		
		List<Map<String, Object>> data=checkBoxService.getList(page, limit, record, sbf.toString(),fields.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
	    return result;
	}
	
	
	@RequestMapping(value = "/constructCheck", method = RequestMethod.GET)
	public ModelAndView constructCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/constructCheck");
		return mv;

	}
	
	@RequestMapping(value="/constructCheck" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> constructCheck(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_project_workteam team left join construct_project_table pro on team.construct_project_workTeam_projectId=pro.construct_project_id   ");
		sbf.append(" left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep  ");
		sbf.append(" left join uc_user us on us.userid=team.construct_project_workTeam_userId where 1=1 ");

		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and pro.construct_project_name like '%" + construct_project_name
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
	
	
	@RequestMapping(value = "/deveConCheck", method = RequestMethod.GET)
	public ModelAndView deveConCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/deveConCheck");
		return mv;

	}
	
	@RequestMapping(value="/deveConCheck" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deveConCheck(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_project_table pro   ");
		sbf.append(" left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep  ");
		sbf.append(" where dep.constuct_project_dep_company=2 ");

		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and pro.construct_project_name like '%" + construct_project_name
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
	
	@RequestMapping(value = "/orgCheck", method = RequestMethod.GET)
	public ModelAndView orgCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/orgCheck");
		return mv;

	}
	
	@RequestMapping(value="/orgChecks" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orgChecks(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String department_name=request.getParameter("department_name");
		Ref<Integer> record = new Ref<Integer>();
		StringBuffer sbf=new StringBuffer();
		StringBuffer fields=new StringBuffer();
		fields.append("*");
		sbf.append("  uc_center cen ");
		sbf.append("LEFT JOIN uc_department dep on dep.department_centerId=cen.center_id ");
		sbf.append("LEFT JOIN uc_company com on com.company_id=cen.center_companyId ");
		sbf.append(" where 1=1");
		
		if (StringUtils.hasText(department_name)) {
			sbf.append(" and department_name like '%" + department_name + "%'");
		}
		Integer page = Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		
		List<Map<String, Object>> data=checkBoxService.getList(page, pageSize, record, sbf.toString(),fields.toString());
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("code", 0);
	    result.put("msg", "");
	    result.put("count", record.getValue());
	    result.put("data", data);
	    return result;
	}
	
	
	/**
	 * 材料单价基础资料
	*/
	
	@RequestMapping(value="/materialPriceCheck" , method = RequestMethod.GET)
	public ModelAndView materialPriceCheck(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		mv.addObject("index", request.getParameter("index"));
		mv.setViewName("checkBox/materialPriceCheck");
		return mv;
	}
	
	
	@RequestMapping(value="/materialPriceCheck" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialPriceCheck(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		sbf.append(" construct_material_price where 1=1 ");
		
		String searchName=request.getParameter("construct_material_name");
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and construct_material_name like'%"+searchName+"%'");
		}
		
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data=materialPriceService.materialPrice(pageIndex, pageSize, record, sbf.toString());
		
		
	    json.put("rows", data);
	    json.put("total", record.getValue());
	    return json;
		
	}
	
	
	/**
	 * 供应商
	 */

	@RequestMapping(value = "/supplierCheck", method = RequestMethod.GET)
	public ModelAndView supplierCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/supplierCheck");
		return mv;
	}

	@RequestMapping(value = "/supplierCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supplierCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_supplier_table where 1=1 ");

		String construct_supplier_name = request.getParameter("construct_suppliername");
		if (StringUtils.hasText(construct_supplier_name)) {
			sbf.append(" and construct_supplier_name like'%" + construct_supplier_name
					+ "%'");
		}

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = materialService.materialList(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}
	
	@RequestMapping(value = "/projectTableList", method = RequestMethod.GET)
	public ModelAndView projectCheck() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("checkBox/projectTableList");
		return mv;
	}
	
	
	@RequestMapping(value = "/projectTableLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> projectTableList(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(" construct_project_table where 1=1 ");
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
	    return result;
	    }
	
}
