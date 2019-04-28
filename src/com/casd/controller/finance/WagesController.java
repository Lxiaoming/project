package com.casd.controller.finance;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.finance.Base_wages;
import com.casd.entity.finance.Wages;
import com.casd.entity.uc.User;
import com.casd.service.construct.LaborCostService;
import com.casd.service.finance.WagesService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class WagesController {

	@Autowired
	private LaborCostService  laborCostService;
	@Autowired
	private WagesService  wagesService;
	@Autowired
	private UserService  userService;
	/**
	 *  工资列表
	 */
	@RequestMapping(value = "/userWagesList", method = RequestMethod.GET)
	public ModelAndView userWagesList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/userWagesList");
		return mv;
	}
	
	@RequestMapping(value="/userWagesLists" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userWagesList(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		

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
		
		String fields =" *";
		StringBuffer sbf=new StringBuffer();
		sbf.append("(SELECT us.username,us.userid,us.center_id,SUM(att.hr_attend_WTLength) finance_wages_attCount,");
		sbf.append("(select SUM(lea.day_count) from own_leave_table lea where lea.createdate like '"+yearMon+"%' and lea.applicant=us.username and lea.`status`=3) finance_wages_leaveCount,");
		sbf.append(" (select  fina.finance_wages_id from finance_wages_table fina where fina.uc_wage_yearMon  like '"+yearMon+"%' and fina.uc_wage_userid=us.userid ) finance_wages_id  from ");

		sbf.append(" hr_attend att LEFT JOIN uc_user us on us.userid=att.hr_attend_userId ");
		sbf.append(" WHERE att.hr_attend_date LIKE '"+yearMon+"%' GROUP BY  att.hr_attend_userId) tabels ");
		sbf.append(" join uc_center center ON tabels.center_id=center.center_id LEFT JOIN uc_company company ON company.company_id = center.center_companyId");
		sbf.append(" LEFT JOIN  uc_wage wage ON tabels.userid=wage.uc_wage_userId");
		sbf.append(" where center.center_companyId !=17 ");
    
		String username=request.getParameter("username");
		if (StringUtils.hasText(username)) {
			sbf.append(" and tabels.username ='"+username+"'");
		}
		String company_id=request.getParameter("company_id");
		if (StringUtils.hasText(company_id)) {
			sbf.append(" and company.company_id ="+company_id);
		}
		
		
		List<Map<String, Object>> data = laborCostService.getList(pageIndex, pageSize, record,fields,sbf.toString());
		 JSONArray jsonArray=new JSONArray();
	
		  jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;

	}
	
	
	/**
	 *  工资历史记录
	 */
	@RequestMapping(value = "/userWagesLib", method = RequestMethod.GET)
	public ModelAndView userWagesLib(String type) {
		ModelAndView mv = new ModelAndView();
	
		//查看全部历史工资
       if(type.equals("whole")){
		  mv.setViewName("finance/userWagesLib");
		 //查看个人历史工资
		}else if(type.equals("individual")){
			 mv.setViewName("finance/userWagesLib1");
		}
		return mv;
	}
	
	@RequestMapping(value="/userWagesLibs" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userWagesLib(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
	
		Calendar c1 = Calendar.getInstance();
		// 获得年份
		int year = c1.get(Calendar.YEAR);
		// 获得月份
		int month = c1.get(Calendar.MONTH);
		String monString=String.valueOf(month);
		if (month<10) {
			monString="0"+month;
		}
		String yearmonth=year+"-"+monString;
		
		String fields ="*";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" finance_wages_table fwt");
		sbf.append(" JOIN uc_user us on fwt.uc_wage_userid=us.userid");
		sbf.append(" where 1=1");
		
		String uc_wage_yearMon=request.getParameter("uc_wage_yearMon");
		if (StringUtils.hasText(uc_wage_yearMon)) {
			sbf.append(" and fwt.uc_wage_yearMon='"+uc_wage_yearMon+"'");
		}else {
			sbf.append(" and fwt.uc_wage_yearMon='"+yearmonth+"'");	
		}
		String username=request.getParameter("username");
		if (StringUtils.hasText(username)) {
			sbf.append(" and us.username = '"+username+"'");	
			
		}
		String uc_company_name=request.getParameter("uc_company_name");
		if (StringUtils.hasText(uc_company_name)) {
			sbf.append(" and fwt.uc_wage_company_name like '%"+uc_company_name+"%'");	
			
		}
		
		List<Map<String, Object>> data = userService.userList(pageIndex, pageSize, record, sbf.toString(), fields);
		JSONArray jsonArray=new JSONArray();
		jsonArray=JSONArray.fromObject(data);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", record.getValue());
		result.put("data", jsonArray);
		return result;

	}
	
	//查看个人工资条
	@RequestMapping(value="/userWagesLibs1" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userWagesLibs1(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
	
	     User user =(User) request.getSession().getAttribute("loginUser");
	     
		String fields ="*";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" finance_wages_table fwt");
		sbf.append(" JOIN uc_user us on fwt.uc_wage_userid=us.userid");
		sbf.append(" where 1=1 and fwt.uc_wage_userid="+user.getUserid());
		
		List<Map<String, Object>> data = userService.userList(pageIndex, pageSize, record, sbf.toString(), fields);
		 JSONArray jsonArray=new JSONArray();
		  jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	}
	
	
	
	/**
	 * 保存工资
	*/
	@RequestMapping(value = "save_userWages", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject save_userWages(HttpServletRequest request)
			throws Exception {
		JSONObject jsonObject=new JSONObject();
		try {
			
			
			Double  finance_wages_vacaCount = Double.valueOf(request.getParameter("finance_wages_vacaCount"));
			Double  uc_wages_dedu = Double.valueOf(request.getParameter("uc_wages_dedu"));
			Double  uc_wages_baseTotal = Double.valueOf(request.getParameter("uc_wages_baseTotal"));
			Double  uc_wage_tax = Double.valueOf(request.getParameter("uc_wage_tax"));
			Double  uc_wage_realhair = Double.valueOf(request.getParameter("uc_wage_realhair"));
			String  company_name = request.getParameter("company_name");	
			String  userid = request.getParameter("userid");
			Double  uc_wage_actualDay = Double.valueOf(request.getParameter("uc_wage_actualDay"));
			String  uc_wage_center_name = request.getParameter("uc_wage_center_name");
			
			Calendar c1 = Calendar.getInstance();
			// 获得年份
			int year = c1.get(Calendar.YEAR);
			// 获得月份
			int month = c1.get(Calendar.MONTH);
			String monString=String.valueOf(month);
			if (month<10) {
				monString="0"+month;
			}
			String yearmonth=year+"-"+monString;
			String  fields="finance_wages_id";
			String  where="finance_wages_table  where uc_wage_userid="+userid;
			        where+=" and uc_wage_yearMon='"+yearmonth+"'";
			List<Map<String, Object>> listData =userService.queryId(fields, where);
			if (listData.size()>0) {
				
				throw new Exception("已存在该人员工资条，不可重复添加！");
				
			}
			JSONObject myJsonArray = JSONObject.fromObject(request
					.getParameter("data"));			
			Wages wages = (Wages) JSONObject.toBean(myJsonArray,  
					Wages.class);
			wages.setFinance_wages_vacaCount(finance_wages_vacaCount);
			wages.setUc_wage_dedu(uc_wages_dedu);
			wages.setUc_wage_baseTotal(uc_wages_baseTotal);
			wages.setUc_wage_tax(uc_wage_tax);
			wages.setUc_wage_realhair(uc_wage_realhair);
			wages.setUc_wage_company_name(company_name);
			wages.setUc_wage_userid(userid);
			wages.setUc_wage_actualDay(uc_wage_actualDay);
			wages.setUc_wage_center_name(uc_wage_center_name);
	         wagesService.save_userWages(wages);
			jsonObject.put("Success", true);
			jsonObject.put("Msg","保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("Success", false);
			jsonObject.put("Msg", "保存失败:"+e);
		}
		  return jsonObject;
	}
	
	
	/**
	 * 删除工资存档
	*/
	@RequestMapping(value = "dele_userWages", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject dele_userWages(HttpServletRequest request)
			throws Exception {
		JSONObject jsonObject=new JSONObject();
		try {
			
			int finance_wages_id = Integer.valueOf(request.getParameter("finance_wages_id").toString());
			wagesService.dele_userWages(finance_wages_id);
			jsonObject.put("Success", true);
			jsonObject.put("Msg", "删除成功");
		} catch (Exception e) {
			jsonObject.put("Success", true);
			jsonObject.put("Msg", "删除失败");
		}
		return jsonObject;
	}
	/**
	 * 保存基本工资
	*/
	@RequestMapping(value = "submitWages", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submitWages(HttpServletRequest request,Base_wages base_wages)
			throws Exception {
		JSONObject jsonObject=new JSONObject();
		try {
			base_wages.setUc_wage_status(0);
			wagesService.submitWages(base_wages);
			jsonObject.put("Success", true);
			jsonObject.put("Msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("Success", true);
			jsonObject.put("Msg", "保存失败");
		}
		
		
		return jsonObject;
	}
	
	
	/**
	 * 获取基本工资
	*/
	@RequestMapping(value = "base_Wages", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject base_Wages(HttpServletRequest request)
			throws Exception {
		int userid=Integer.valueOf(request.getParameter("userid").toString());
		
		JSONObject jsonObject=new JSONObject();
		Map<String, Object> data=wagesService.base_Wages(userid);
		if(data!=null){
			 jsonObject=JSONObject.fromObject(data);
		}else{
			jsonObject.put("uc_wage_userId", userid);
		}
		
		
		return jsonObject;
	}

	  //保存历史工资
	@RequestMapping(value = "saveHistoricalWage", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveHistoricalWage(Wages wages) {
		
		   JSONObject jsonObject=new JSONObject() ;
		try {
		Calendar c1 = Calendar.getInstance();
		// 获得年份
		int year = c1.get(Calendar.YEAR);
		// 获得月份
		int month = c1.get(Calendar.MONTH);
		String monString=String.valueOf(month);
		if (month<10) {
			monString="0"+month;
		}
		String yearmonth=year+"-"+monString;
		String  fields="finance_wages_id";
		String  where="finance_wages_table  where uc_wage_userid="+wages.getUc_wage_userid();
		        where+=" and uc_wage_yearMon='"+yearmonth+"'";
		List<Map<String, Object>> listData =userService.queryId(fields, where);
		
		if (listData.size()>0) {
			throw new Exception("已存在该人员工资条，不可重复添加！");
	     }
		  wagesService.save_userWages(wages);
			jsonObject.put("Success", true);
			jsonObject.put("Msg", "添加成功");
		} catch (Exception e) {
			jsonObject.put("Success", false);
			jsonObject.put("Msg", "保存失败"+e);
		}

		return jsonObject;

	}
	
	@RequestMapping(value = "update_Wages", method = RequestMethod.POST)
	@ResponseBody
	 public JSONObject update_Wages() {
		JSONObject jsonObject=new JSONObject();
		try {
			
	   StringBuffer sbf=new StringBuffer();
	   sbf.append(" uc_wage SET uc_wage_actualDay =0,uc_wage_achieve=0,uc_wage_subsidy=0,uc_wage_status=0");
	
		wagesService.updateUcwages(sbf.toString());
		jsonObject.put("Success", true);
		jsonObject.put("Msg", "更新成功");
	  } catch (Exception e) {
		jsonObject.put("Success", false);
		jsonObject.put("Msg", "更新失败");
	  }
		return jsonObject;
		
	}
	
	
}
