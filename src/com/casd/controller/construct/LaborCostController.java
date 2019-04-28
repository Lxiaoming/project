package com.casd.controller.construct;

import groovy.ui.Console;

import java.util.Calendar;
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

import com.casd.Utils;
import com.casd.controller.web.Ref;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.construct.LaborCostService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class LaborCostController {

	@Autowired
	private LaborCostService  laborCostService;
	@Autowired
	private UserService  userService;
	
	private String hr_attend_workTeamId;
	/**
	 * 劳动力费用月份报表
	 */
	@RequestMapping(value = "/laborCostMon", method = RequestMethod.GET)
	public ModelAndView aPartyConList(HttpServletRequest request,String companyId) {
		ModelAndView mv = new ModelAndView();
		Calendar c1 = Calendar.getInstance();
		// 获得年份
		int year = c1.get(Calendar.YEAR);
		mv.addObject("yearNum", year);
		mv.addObject("companyId", companyId);
		mv.setViewName("construct/laborCostMon");
		return mv;
	}

	@RequestMapping(value = "/laborCostMons", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> laborCostMon(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
        String  hr_attend_date=request.getParameter("hr_attend_date");
        String  construct_project_name=request.getParameter("construct_project_name");

		Calendar cal = Calendar.getInstance();
	     int year = cal.get(Calendar.YEAR);
		if(hr_attend_date == null){
			hr_attend_date = String.valueOf(year);
		}

		StringBuffer  fields=new StringBuffer();
		fields.append("*");

		StringBuffer sbf = new StringBuffer();
		sbf.append("( select tableAll.construct_project_name,tableAll.construct_project_workTeam_category,tableAll.username,tableAll.construct_project_workTeam_amount, tableAll.construct_project_workTeam_price,tableAll.hr_attend_workTeamId, tableAll.manage_contract_num,");
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"01' THEN num ELSE 0 END) AS jan,");
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"02' THEN num ELSE 0 END) AS feb,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"03' THEN num ELSE 0 END) AS mar,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"04' THEN num ELSE 0 END) AS apr,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"05' THEN num ELSE 0 END) AS may,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"06' THEN num ELSE 0 END) AS jun,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"07' THEN num ELSE 0 END) AS jul,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"08' THEN num ELSE 0 END) AS aug,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"09' THEN num ELSE 0 END) AS sep,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"10' THEN num ELSE 0 END) AS oct,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"11' THEN num ELSE 0 END) AS nov,"); 
		sbf.append("MAX(CASE attend_date WHEN '"+hr_attend_date+"12' THEN num ELSE 0 END) AS december");
		
		sbf.append(" from ( select attend.hr_attend_workTeamId,attend.hr_attend_WTLength,users.username,team.construct_project_workTeam_category,pro.construct_project_name, team.construct_project_workTeam_price,"); 
		sbf.append(" team.construct_project_workTeam_amount,sum(hr_attend_WTLength) num,contract.manage_contract_num,DATE_FORMAT(attend.hr_attend_date, '%Y%m') as attend_date"); 
		sbf.append(" from hr_attend  attend "); 
		sbf.append(" LEFT JOIN construct_project_workteam team on attend.hr_attend_workTeamId=team.construct_project_workTeam_id "); 
		sbf.append(" LEFT JOIN uc_user users on users.userid=team.construct_project_workTeam_userId "); 
		sbf.append(" LEFT JOIN construct_project_table pro on attend.hr_attend_projectId =pro.construct_project_id"); 
		sbf.append(" LEFT JOIN manage_contract contract on contract.manage_contract_id =pro.construct_project_contractId"); 
		sbf.append(" where attend.hr_attend_workTeamId !=0"); 
		String companyId = request.getParameter("companyId");
		if(StringUtils.hasText(companyId)){
			sbf.append(" and contract.manage_contract_company = " + companyId); 
		}
		
		if(StringUtils.hasText(hr_attend_date)){
		  sbf.append(" and attend.hr_attend_date like '"+hr_attend_date+"%'"); 
		}
		if(StringUtils.hasText(construct_project_name)){
		  sbf.append(" and pro.construct_project_name like '%"+construct_project_name+"%'"); 
		}
/*		User user = (User) request.getSession().getAttribute("loginUser");
		sbf.append(" and pro.construct_project_leader = '"+user.getUsername()+"' ");*/
		
		sbf.append(" group by  attend.hr_attend_workTeamId,attend_date");
		sbf.append(" ) tableAll group by  hr_attend_workTeamId  order by tableAll.construct_project_name ) tab where 1=1");
		Utils utils = new Utils();
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields.toString(),sbf.toString());
		  for (Map<String, Object> map : data) {
			  	Double sumMoney1,sumMoney2,sumMoney3,sumMoney4,sumMoney5,sumMoney6,
			  	sumMoney7,sumMoney8,sumMoney9,sumMoney10,sumMoney11,sumMoney12;
			  	
			  	sumMoney1 = utils.mul(Double.valueOf(map.get("jan").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney2 = utils.mul(Double.valueOf(map.get("feb").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney3 = utils.mul(Double.valueOf(map.get("mar").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString())) ;
			  	sumMoney4 = utils.mul(Double.valueOf(map.get("apr").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney5 = utils.mul(Double.valueOf(map.get("may").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney6 = utils.mul(Double.valueOf(map.get("jun").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney7 = utils.mul(Double.valueOf(map.get("jul").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney8 = utils.mul(Double.valueOf(map.get("aug").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney9 = utils.mul(Double.valueOf(map.get("sep").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney10 = utils.mul(Double.valueOf(map.get("oct").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney11 = utils.mul(Double.valueOf(map.get("nov").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	sumMoney12 = utils.mul(Double.valueOf(map.get("december").toString()), 
			  			Double.valueOf(map.get("construct_project_workTeam_price").toString()));
			  	map.put("sumMoney1", sumMoney1);
			    map.put("sumMoney2", sumMoney2);
			    map.put("sumMoney3", sumMoney3);
			    map.put("sumMoney4", sumMoney4);    
			    map.put("sumMoney5", sumMoney5);
			    map.put("sumMoney6", sumMoney6);
			    map.put("sumMoney7", sumMoney7);
			    map.put("sumMoney8", sumMoney8);
			    map.put("sumMoney9", sumMoney9);
			    map.put("sumMoney10", sumMoney10);
			    map.put("sumMoney11", sumMoney11);
			    map.put("sumMoney12", sumMoney12);
			  Double sum =(sumMoney1+sumMoney2+sumMoney3+sumMoney4+sumMoney5+sumMoney6+
					  sumMoney7+sumMoney8+sumMoney9+sumMoney10+sumMoney11+sumMoney12
					     );
			             map.put("sumMoney13", sum);
			    
	        }
		 
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	
	}

	
	/**
	 * 劳动力费用日报表
	 */
	@RequestMapping(value = "/laborCostDate", method = RequestMethod.GET)
	public ModelAndView laborCostDate(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/laborCostDate");
		hr_attend_workTeamId=request.getParameter("hr_attend_workTeamId");
	
		return mv;
	}
	
	
	@RequestMapping(value = "/laborCostDates", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> laborCostDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH );
		String yearMon;
		if (month>0&&month<10) {
			yearMon=year+"-0"+month;
		}else if(month==0) {
			yearMon=year-1+"-12"; 
		}else {
			yearMon=year+"-"+month;
		}
	
		String hr_attend_date = request.getParameter("hr_attend_date");
			
		StringBuffer sbf = new StringBuffer();

		sbf.append(" (select attend.hr_attend_workTeamId,attend.hr_attend_WTLength,users.username,team.construct_project_workTeam_category,pro.construct_project_name, team.construct_project_workTeam_price,");
		sbf.append(" team.construct_project_workTeam_amount,contract.manage_contract_num,count(distinct attend.hr_attend_userId) 'workingPeopleNum',sum(hr_attend_WTLength) num,left(attend.hr_attend_date,7) hr_attend_date ");
		sbf.append(" from hr_attend  attend ");
		sbf.append(" LEFT JOIN construct_project_workteam team on attend.hr_attend_workTeamId=team.construct_project_workTeam_id");
		sbf.append(" LEFT JOIN uc_user users on users.userid=team.construct_project_workTeam_userId");
		sbf.append(" LEFT JOIN construct_project_table pro on attend.hr_attend_projectId =pro.construct_project_id");
		sbf.append(" LEFT JOIN manage_contract contract on contract.manage_contract_id =pro.construct_project_contractId");
		sbf.append(" where 1=1 and attend.hr_attend_workTeamId ="+hr_attend_workTeamId);
		
		if (!StringUtils.isEmpty(hr_attend_date)) {
			sbf.append(" and attend.hr_attend_date like '"+hr_attend_date+"%'");
		}else {
			sbf.append(" and attend.hr_attend_date like '"+yearMon+"%'");
		}
		sbf.append(" group by  left(attend.hr_attend_date,7) ");
		sbf.append(" ) tableAll where 1=1 ");

		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record, "*", sbf.toString());
		Utils utils = new Utils();
		  for (Map<String, Object> map : data) {
			map.put("sumMoney", utils.mul(Double.valueOf(map.get("num").toString()),Double.valueOf(map.get("construct_project_workTeam_price").toString())));
		}
		
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	}
	
	
	/**
	 * 劳动力打卡详细
	 */
	@RequestMapping(value = "/laborCostAttend", method = RequestMethod.GET)
	public ModelAndView laborCostAttend(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String hr_attend_date = request.getParameter("hr_attend_date");
		String hr_attend_workTeamId = request.getParameter("hr_attend_workTeamId");
		mv.addObject("hr_attend_date",hr_attend_date);
		mv.addObject("hr_attend_workTeamId",hr_attend_workTeamId);
		mv.setViewName("construct/laborCostAttend");
		return mv;
	}
	@RequestMapping(value = "/laborCostAttends", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> laborCostAttend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		String hr_attend_date = request.getParameter("hr_attend_date");
		String labor = request.getParameter("labor");
		String hr_attend_workTeamId = request.getParameter("hr_attend_workTeamId");
		
		StringBuffer sbf = new StringBuffer();

		sbf.append("  ( select attend.hr_attend_workTeamId,attend.hr_attend_WTLength,team.construct_project_workTeam_category,pro.construct_project_name, ");
		sbf.append("  contract.manage_contract_num,attend.hr_attend_date,attend.hr_attend_startWork,attend.hr_attend_knockOff,attend.hr_attend_workAddress,attend.hr_attend_offWorkAddress,attendUser.username labor ");
		sbf.append("  from hr_attend  attend ");
		sbf.append("  LEFT JOIN uc_user attendUser on attendUser.userid=attend.hr_attend_userId ");
		sbf.append("  LEFT JOIN construct_project_workteam team on attend.hr_attend_workTeamId=team.construct_project_workTeam_id ");
		//sbf.append("  LEFT JOIN uc_user users on users.userid=team.construct_project_workTeam_userId ");
		sbf.append("  LEFT JOIN construct_project_table pro on attend.hr_attend_projectId =pro.construct_project_id  ");
		sbf.append("  LEFT JOIN manage_contract contract on contract.manage_contract_id =pro.construct_project_contractId  ");
		sbf.append("  where attend.hr_attend_workTeamId ="+hr_attend_workTeamId+" and attend.hr_attend_date like '"+hr_attend_date + "%'");
		
		String attend_date = request.getParameter("attend_date");
		if (StringUtils.hasText(attend_date)) {
			sbf.append(" and attend.hr_attend_date = '"+attend_date + "' ");
		}
		
		sbf.append(" group by attendUser.username,attend.hr_attend_date ");
		sbf.append(" ) tableAll where 1=1 ");
		if (StringUtils.hasText(labor)) {
			sbf.append(" and tableAll.labor like '%" + labor + "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = userService.userList(pageIndex, pageSize, record, sbf.toString(),"*");

		json.put("code", 0);
		json.put("msg", "");
		json.put("count", record.getValue());
		json.put("data", data);
	    
		return json;
	}
	
	
	
}
