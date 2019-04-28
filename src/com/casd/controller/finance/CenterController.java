package com.casd.controller.finance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casd.service.finance.CenterService;

@Controller
@RequestMapping("/admin")
public class CenterController {

	
	@Autowired
	private CenterService centerService;
	
	/**
	 * 中心弹框
	 * 
	 * *//*
	@RequestMapping(value = "/centerList", method = RequestMethod.GET)
	public ModelAndView centerList() {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("uc/centerList");
		return mv;

	}

	*//**
	 * 中心弹框
	 * 
	 * *//*
	@RequestMapping(value = "/centerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> centerList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String center_name = request.getParameter("center_name");
		StringBuilder sBuilder = new StringBuilder();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		Ref<Integer> record = new Ref<Integer>();
		String fields = "center_name,center_id";
		sBuilder.append(" uc_center");
		sBuilder.append(" where 1=1");
		
		if (StringUtils.hasText(center_name)) {
			sBuilder.append(" and center_name='" + center_name + "'");
		}

		// 部门列表
		List<Map<String, Object>> data = centerService.centerList(pageIndex,
				pageSize, record, fields, sBuilder.toString());
		jsonMap.put("rows", data);
		jsonMap.put("total", record.getValue());

		return jsonMap;

	}*/

	@RequestMapping(value = "/finance", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject finance(HttpServletRequest request)
			throws Exception {
		int center_id =Integer.valueOf(request.getParameter("center_id").toString());
		String date=request.getParameter("str");
		JSONObject jsonMap = new JSONObject();

		StringBuilder sBuilder = new StringBuilder();
		StringBuilder fields = new StringBuilder();

		fields.append("wages_id,user_name,role_name,attendances,vacation,go_out,leave_day,base_pay,years_wages,post_wage,technical_wages,total_wages, ");
		fields.append("meal_supplement,phone_subsidy,deduction,should_wages,social_security,any_other,payroll,remarks,finance_paylist_id");
		sBuilder.append(" finance_payroll pr LEFT JOIN finance_paylist pl on pr.finance_paylist_id=pl.gad_paylist_id ");
		sBuilder.append(" where pl.gad_paylist_centerid="+center_id);
		sBuilder.append(" and pl.gad_paylist_month='"+date+"'");
		List<Map<String, Object>> data = centerService.finance(fields.toString(), sBuilder.toString());
		jsonMap.put("rows", data);
		return jsonMap;
	}
	
}
