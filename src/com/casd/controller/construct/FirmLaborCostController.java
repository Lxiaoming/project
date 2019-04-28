package com.casd.controller.construct;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;










import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.service.construct.FirmLaborCostService;
import com.casd.service.construct.LaborCostService;

@Controller
@RequestMapping("/admin")
public class FirmLaborCostController {

	@Autowired
	private LaborCostService  laborCostService;
	@Autowired
	private FirmLaborCostService  firmLaborCostService;
	
	/**
	 * 按项目部查看劳动力费用报表
	 */
	@RequestMapping(value = "/firmLaborCost", method = RequestMethod.GET)
	public ModelAndView firmLaborCost(HttpServletRequest request,String companyId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/firmLaborCost");
		return mv;
	}

	@RequestMapping(value = "/firmLaborCosts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> firmLaborCosts(HttpServletRequest request) throws Exception {
		String year = request.getParameter("firmYear");
		String projectName = request.getParameter("constuct_project_dep_name");
		Calendar date = Calendar.getInstance();
		if(year == null){
			year = String.valueOf(date.get(Calendar.YEAR));
		}
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		StringBuffer sql = new StringBuffer();
		String fields = "*";
		sql.append("(select * from ( ");
		sql.append("SELECT ");
		sql.append("  dep.*, ");
		sql.append("  SUM(con.manage_contract_amount + con.manage_contract_visaAmount) conAmount ");
		sql.append("FROM construct_project_dep dep LEFT JOIN construct_project_table pro ");
		sql.append("    on dep.constuct_project_dep_id = pro.construct_project_dep ");
		sql.append("  LEFT JOIN manage_contract con on con.manage_contract_id = pro.construct_project_contractId ");
		sql.append("WHERE constuct_project_dep_company = 1 ");
		if(projectName != null){
			sql.append(" and constuct_project_dep_name like '%"+projectName+"%' ");
		}
		sql.append("GROUP BY dep.constuct_project_dep_id) a left join (select ");
		sql.append("  prot.construct_project_dep, ");
		sql.append("  SUM(att.hr_attend_WTLength * team.construct_project_workTeam_price) totalLaborCost, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-01' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) jan, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-02' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) feb, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-03' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) mar, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-04' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) apr, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-05' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) may, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-06' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) jun, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-07' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) jul, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-08' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) aug, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-09' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) sep, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-10' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) oct, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-11' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) nov, ");
		sql.append("  sum(case substr(att.hr_attend_date, 1, 7) when '"+year+"-12' then att.hr_attend_WTLength * team.construct_project_workTeam_price else 0 end) december ");
		sql.append("from ");
		sql.append("  hr_attend att LEFT JOIN construct_project_table proT on att.hr_attend_projectId = proT.construct_project_id ");
		sql.append("  LEFT JOIN construct_project_workteam team ");
		sql.append("    on team.construct_project_workTeam_projectId = proT.construct_project_id and substr(att.hr_attend_date, 1, 4) like '"+year+"%' ");
		sql.append("group by proT.construct_project_dep) b ");
		sql.append("    on a.constuct_project_dep_id = b.construct_project_dep) c ");
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields.toString(),sql.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray jsonArray=JSONArray.fromObject(data);
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", record.getValue());
		result.put("data", jsonArray);
		return result;
		
	
	}
	/**
	 * 按项目查看劳动力费用报表
	 */
	@RequestMapping(value = "/firmLaborCostPro", method = RequestMethod.GET)
	public ModelAndView firmLaborCostPro(HttpServletRequest request,String projectDep) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projectDep", projectDep);
		mv.setViewName("construct/firmLaborCostPro");
		return mv;
	}
	/**
	 * 按项目查看劳动力费用报表
	 * @throws Exception 
	 */
	@RequestMapping(value = "/firmLaborCostPros", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> firmLaborCostPros(HttpServletRequest request,String projectDep) throws Exception {
		String year = request.getParameter("firmYear");
		String projectName = request.getParameter("constuct_project_dep_name");
		Calendar date = Calendar.getInstance();
		if(year == null){
			year = String.valueOf(date.get(Calendar.YEAR));
		}
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		StringBuffer sql = new StringBuffer();
		sql.append("(select ");
		sql.append("tableAll.construct_project_name, ");
		sql.append("tableAll.construct_project_workTeam_category, ");
		sql.append("tableAll.construct_project_leader, ");
		sql.append("tableAll.tableAll.construct_project_id, ");
		sql.append("tableAll.construct_project_workTeam_price, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"01' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS jan, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"02' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS feb, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"03' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS mar, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"04' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS apr, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"05' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS may, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"06' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS jun, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"07' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS jul, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"08' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS aug, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"09' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS sep, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"10' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS oct, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"11' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS nov, ");
		sql.append("sum(CASE attend_date WHEN '"+year+"12' THEN tableAll.construct_project_workTeam_price * num ELSE 0 END) AS december ");
		sql.append("from (select ");
		sql.append("pro.construct_project_leader, ");
		sql.append("team.construct_project_workTeam_category, ");
		sql.append("pro.construct_project_name, ");
		sql.append("pro.construct_project_id, ");
		sql.append("team.construct_project_workTeam_price, ");
		sql.append("sum(hr_attend_WTLength) num, ");
		sql.append("contract.manage_contract_num, ");
		sql.append("DATE_FORMAT(attend.hr_attend_date, '%Y%m') as attend_date ");
		sql.append("from hr_attend attend LEFT JOIN construct_project_workteam team ");
		sql.append("on attend.hr_attend_workTeamId = team.construct_project_workTeam_id ");
		sql.append("LEFT JOIN uc_user users on users.userid = team.construct_project_workTeam_userId ");
		sql.append("LEFT JOIN construct_project_table pro on attend.hr_attend_projectId = pro.construct_project_id ");
		sql.append("LEFT JOIN manage_contract contract on contract.manage_contract_id = pro.construct_project_contractId ");
		sql.append("where attend.hr_attend_workTeamId != 0 and contract.manage_contract_company = 1 ");
		sql.append("and attend.hr_attend_date like '"+year+"%' ");
		if(!StringUtils.isEmpty(projectName)){
			sql.append("and pro.construct_project_name like '%"+projectName+"%' ");
		}
		sql.append("and pro.construct_project_dep = " + projectDep);
		sql.append(" group by attend.hr_attend_workTeamId, attend_date) tableAll ");
		sql.append("group by construct_project_name ");
		sql.append("order by construct_project_name ) tableAll where 1=1 ");
		String fields = "*";
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields.toString(),sql.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray jsonArray=JSONArray.fromObject(data);
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", record.getValue());
		result.put("data", jsonArray);
		return result;
	}
	/**
	 * 按班组查看劳动力费用报表
	 */
	@RequestMapping(value = "/firmLaborCostTeam", method = RequestMethod.GET)
	public ModelAndView firmLaborCostTeam(HttpServletRequest request,String projectId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projectId", projectId);
		mv.setViewName("construct/firmLaborCostTeam");
		return mv;
	}
	/**
	 * 按班组查看劳动力费用报表
	 * @throws Exception 
	 */
	@RequestMapping(value = "/firmLaborCostTeams", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> firmLaborCostTeams(HttpServletRequest request) throws Exception {
		String year = request.getParameter("firmYear");
		String projectId = request.getParameter("projectId");
		Calendar date = Calendar.getInstance();
		if(year == null){
			year = String.valueOf(date.get(Calendar.YEAR));
		}
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		StringBuffer sql = new StringBuffer();
		sql.append("( select tableAll.construct_project_name,tableAll.construct_project_workTeam_category,tableAll.username,tableAll.construct_project_workTeam_amount, tableAll.construct_project_workTeam_price,tableAll.hr_attend_workTeamId, tableAll.manage_contract_num,");
		sql.append("MAX(CASE attend_date WHEN '"+year+"01' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS jan,");
		sql.append("MAX(CASE attend_date WHEN '"+year+"02' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS feb,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"03' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS mar,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"04' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS apr,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"05' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS may,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"06' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS jun,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"07' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS jul,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"08' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS aug,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"09' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS sep,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"10' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS oct,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"11' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS nov,"); 
		sql.append("MAX(CASE attend_date WHEN '"+year+"12' THEN num * tableAll.construct_project_workTeam_price ELSE 0 END) AS december");
		
		sql.append(" from ( select attend.hr_attend_workTeamId,attend.hr_attend_WTLength,users.username,team.construct_project_workTeam_category,pro.construct_project_name, team.construct_project_workTeam_price,"); 
		sql.append(" team.construct_project_workTeam_amount,sum(hr_attend_WTLength) num,contract.manage_contract_num,DATE_FORMAT(attend.hr_attend_date, '%Y%m') as attend_date"); 
		sql.append(" from hr_attend  attend "); 
		sql.append(" LEFT JOIN construct_project_workteam team on attend.hr_attend_workTeamId=team.construct_project_workTeam_id "); 
		sql.append(" LEFT JOIN uc_user users on users.userid=team.construct_project_workTeam_userId "); 
		sql.append(" LEFT JOIN construct_project_table pro on attend.hr_attend_projectId =pro.construct_project_id"); 
		sql.append(" LEFT JOIN manage_contract contract on contract.manage_contract_id =pro.construct_project_contractId"); 
		sql.append(" where attend.hr_attend_workTeamId !=0"); 
		sql.append(" and pro.construct_project_id = " + projectId); 
		sql.append(" group by  attend.hr_attend_workTeamId,attend_date");
		sql.append(" ) tableAll group by  hr_attend_workTeamId order by tableAll.construct_project_name) tab where 1=1 ");
		String fields = "*";
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields.toString(),sql.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray jsonArray=JSONArray.fromObject(data);
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", record.getValue());
		result.put("data", jsonArray);
		return result;
	}
	/**
	 * 按个人查看劳动力费用报表
	 */
	@RequestMapping(value = "/firmLaborCostPerson", method = RequestMethod.GET)
	public ModelAndView firmLaborCostPerson(HttpServletRequest request,String projectId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projectId", projectId);
		mv.setViewName("construct/firmLaborCostPerson");
		return mv;
	}
	/**
	 * 按个人查看劳动力费用报表
	 * @throws Exception 
	 */
	@RequestMapping(value = "/firmLaborCostPersons", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> firmLaborCostPersons(HttpServletRequest request) throws Exception {
		String year = request.getParameter("firmYear");
		String projectId = request.getParameter("projectId");
		Calendar date = Calendar.getInstance();
		if(year == null){
			year = String.valueOf(date.get(Calendar.YEAR));
		}
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		StringBuffer sql = new StringBuffer();
		sql.append("(select ");
		sql.append("prot.construct_project_name, ");
		sql.append("prow.construct_project_workTeam_category, ");
		sql.append("user.username, ");
		sql.append("att.hr_attend_date, ");
		sql.append("att.hr_attend_startWork, ");
		sql.append("att.hr_attend_knockOff, ");
		sql.append("att.hr_attend_workAddress, ");
		sql.append("att.hr_attend_offWorkAddress, ");
		sql.append("att.hr_attend_WTLength ");
		sql.append("from hr_attend att ");
		sql.append("left join construct_project_table prot on att.hr_attend_projectId = prot.construct_project_id ");
		sql.append("left join construct_project_workteam prow on att.hr_attend_workTeamId = prow.construct_project_workTeam_id ");
		sql.append("left join uc_user user on hr_attend_userId = user.userid ");
		sql.append("where prot.construct_project_id = 135 ");
		sql.append("order by user.userid,att.hr_attend_date) tableAll ");
		String fields = "*";
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields.toString(),sql.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray jsonArray=JSONArray.fromObject(data);
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", record.getValue());
		result.put("data", jsonArray);
		return result;
	}
	
}
