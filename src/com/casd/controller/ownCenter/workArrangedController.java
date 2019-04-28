package com.casd.controller.ownCenter;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.casd.entity.ownCenter.OwnWorkEntry;
import com.casd.entity.ownCenter.OwnWorkHead;
import com.casd.entity.sciAndTec.CheckPro;
import com.casd.entity.uc.User;
import com.casd.service.construct.ConstructService;
import com.casd.service.ownCenter.OwnWorkService;


@Controller
@RequestMapping("/admin")
public class workArrangedController {
	
	@Autowired
	private OwnWorkService ownWorkService;
	
	/**
	 * 任务安排list
	*/
	@RequestMapping(value="/ownWorkList", method = RequestMethod.GET)
	public ModelAndView ownWorkList(HttpServletRequest request) {
		
		ModelAndView mv=new ModelAndView();
		User user=(User) request.getSession().getAttribute("loginUser");
		mv.addObject("userName", "'"+user.getUsername()+"'");
		
		Map<String, Object> map= ownWorkService.selectArrCount(request);
		mv.addObject("creatCount", map.get("creatCount"));
		mv.addObject("finishCount", map.get("finishCount"));
		mv.addObject("dealtCount", map.get("dealtCount"));
		
		mv.setViewName("ownCenter/ownWorkList");
		return mv;
	}
	@RequestMapping(value = "/ownWorkLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ownWorkList(HttpServletRequest request,Integer limit) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		
		String fields="head.*,entry.*,us.username as sponsor,us1.username as coordinator,us2.username as arranger";
		       fields+=",project.construct_project_name";
		StringBuffer sbf = new StringBuffer();
		sbf.append("own_workArrangHead head ");
		sbf.append("INNER JOIN own_workarranged entry ON head.own_workArrangHead_id = entry.own_workArranged_categoryId ") ;
		sbf.append("LEFT JOIN  uc_user us  ON us.userid= entry.own_workArranged_sponsor ");
		sbf.append("LEFT JOIN  uc_user us1  ON us1.userid= entry.own_workArranged_coordinator ");
		sbf.append("LEFT JOIN  uc_user us2  ON us2.userid= entry.own_workArranged_arranger ");
		sbf.append("LEFT JOIN construct_project_table project ON project.construct_project_id= head.own_workArrangHead_projectId ");
		sbf.append(" where 1=1");
		
		String user_finish = request.getParameter("user_finish");
		if (StringUtils.hasText(user_finish)) {
			sbf.append(" and entry.own_workArranged_status>0 and entry.own_workArranged_sponsor = "+user_finish+" or entry.own_workArranged_coordinator="+user_finish);
		}
		
		String user_creat = request.getParameter("user_creat");
		if (StringUtils.hasText(user_creat)) {
			sbf.append(" and entry.own_workArranged_arranger ="+ user_creat);
		}
		
		String user_own = request.getParameter("user_own");
		if (StringUtils.hasText(user_own)) {
			sbf.append(" and entry.own_workArranged_status=0 and entry.own_workArranged_sponsor = "+user_own+" or entry.own_workArranged_coordinator="+user_own);
	 
		}
		String date1 = request.getParameter("date1");
		if (StringUtils.hasText(date1)) {
			sbf.append(" and entry.own_workArranged_creatTime >='"+date1+"'");
	 
		}
		String date2 = request.getParameter("date2");
		if (StringUtils.hasText(date2)) {
			sbf.append(" and entry.own_workArranged_creatTime <='"+date2+"'");
	 
		}
		sbf.append(" order BY own_workArranged_creatTime DESC,own_workArranged_categoryId ");
		List<Map<String, Object>> data = ownWorkService.ownWorkList(
				pageIndex, limit, record,fields,sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	}
	
	
	
	/**
	 * 任务报表
	*/
	@RequestMapping(value="/ownWorkReport", method = RequestMethod.GET)
	public ModelAndView ownWorkReport(HttpServletRequest request) {
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("ownCenter/ownWorkReport");
		return mv;
	}
	@RequestMapping(value = "/ownWorkReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ownWorkReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		String fields="*";
		sbf.append(" ( ");
		sbf.append(" select cen.*, com.*, head.* ,entry.* ,(select username from uc_user us1 where entry.own_workArranged_arranger= us1.userid) arranger, ") ;
		sbf.append(" (select username from uc_user us2 where entry.own_workArranged_sponsor= us2.userid) sponsor,(select username from uc_user us3 where entry.own_workArranged_coordinator= us3.userid) coordinator");
		sbf.append("  from  own_workArrangHead head inner join own_workarranged entry on "
				+ " head.own_workArrangHead_id=entry.own_workArranged_categoryId  ");
		sbf.append(" left join uc_company com on com.company_id= head.own_workArrangHead_companyId ");
		sbf.append(" left join uc_center cen on cen.center_id= head.own_workArrangHead_centerId ");
		sbf.append(" )newTable where 1=1");
		
		String company = request.getParameter("company");
		if (StringUtils.hasText(company)) {
			sbf.append(" and newTable.company_name = '"+company+"' ");
		}
		
		String userName = request.getParameter("userName");
		if (StringUtils.hasText(userName)) {
			sbf.append(" and newTable.sponsor like '%"+ userName + "%'");
		}
		
		
		sbf.append(" order BY newTable.sponsor,newTable.own_workArrangHead_centerId,newTable.own_workArranged_creatTime DESC ");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = ownWorkService.ownWorkList(
				pageIndex, pageSize, record,fields, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}
	
	
	
	@RequestMapping(value="/ownWorkNew", method = RequestMethod.GET)
	public ModelAndView ownWorkNew(HttpServletRequest request) {
		User user=(User) request.getSession().getAttribute("loginUser");
		ModelAndView mv=new ModelAndView();
		OwnWorkHead ownWorkHead=new OwnWorkHead();
		JSONObject dataObject = new JSONObject();
		dataObject.put("entry", "");
		String type = "'new'";
		if (StringUtils.hasText(request.getParameter("own_workArranged_categoryId"))) {
			type = "'update'";
			int own_workArranged_categoryId = Integer.valueOf(request.getParameter(
					"own_workArranged_categoryId").toString());
			Map<String, Object> data = ownWorkService.getOwnWorkById(own_workArranged_categoryId);
			ownWorkHead = (OwnWorkHead) data.get("ownWorkHead");
			List<Map<String, Object>> entry = (List<Map<String, Object>>) data
					.get("ownWorkEntry");
			dataObject.put("entry", entry);
		} else {
			ownWorkHead.setOwn_workArrangHead_id(0);
			ownWorkHead.setOwn_workArrangHead_centerId(user.getCenter_id());
			ownWorkHead.setOwn_workArrangHead_companyId(0);
		}
		mv.addObject("type", type);
		dataObject.put("workArrangHead", ownWorkHead);
		mv.addObject("data", dataObject);
		mv.setViewName("ownCenter/ownWorkNew");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/save_workArrang", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save_workArrang(HttpServletRequest request,
			OwnWorkHead ownWorkHead) throws Exception {

		JSONArray myJsonArray = JSONArray.fromObject(request
				.getParameter("rows"));
		ownWorkService.save_workArrang(ownWorkHead, myJsonArray,request);
		return null;
	}
	
	
	
	@RequestMapping(value = "/update_work", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update_work(OwnWorkEntry ownWorkEntry)  {
		Map<String, Object> json= new HashMap<String, Object>();
		try {
			ownWorkService.update_work(ownWorkEntry);
		    json.put("Success", true);
		    json.put("Msg","保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg","保存失败");
			}
		return json;
	}
	
	/**
	 * 删除分录
	 */
	@RequestMapping(value = "/delete_workerEntry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_workerEntry(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String cid = request.getParameter("cid");
			StringBuffer sbf = new StringBuffer();
			Map<String, Object> map = new HashMap<String, Object>();
			sbf.append(" own_workarranged where own_workArranged_id ="
					+ cid + "");
			map.put("where", sbf.toString());
			ownWorkService.delete_workerEntry(map);
			json.put("success", true);
			json.put("msg", "删除成功！");
		} catch (Exception e) {
			json.put("success", false);
			json.put("msg", "删除失败！");
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 删除分类
	 */
	@RequestMapping(value = "/delete_workerHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_workerHead(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String cid = request.getParameter("own_workArranged_categoryId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cid", cid);
			ownWorkService.delete_workerHead(map);
			json.put("Success", true);
			json.put("Msg", "删除成功！");
		} catch (Exception e) {
			json.put("Success", false);
			json.put("Msg", "删除失败！");
			e.printStackTrace();
		}
		return json;
	}
	
	
}
