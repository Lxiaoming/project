package com.casd.controller.ownCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.uc.User;
import com.casd.service.flow.FlowService;


@Controller
@RequestMapping("/admin")
public class AuditController {
	
	
	@Autowired
	private FlowService flowService;
	
	/**
	 * 审核list
	*/
	
	@RequestMapping(value="/auditList" , method = RequestMethod.GET)
	public ModelAndView auditList(HttpServletRequest request) {
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ownCenter/auditList");
		return mv;
	}
	
	
	@RequestMapping(value="/auditList" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		User loginUser =(User) request.getSession().getAttribute("loginUser");
        String username = loginUser.getUsername();
		sbf.append(" flow_audit_table where 1=1  and flow_auditer='"+username+"' and flow_status=1 ORDER BY flow_status");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		List<Map<String, Object>> data = flowService.auditList(pageIndex, pageSize, record, sbf.toString());
		
	    json.put("rows", data);
	    json.put("total", record.getValue());
	    return json;
		
	}
	
	
}
