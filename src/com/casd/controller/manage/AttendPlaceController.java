package com.casd.controller.manage;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.impl.bpmn.data.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.MvcNamespaceHandler;

import com.casd.controller.web.Ref;
import com.casd.entity.construct.ProSch;
import com.casd.entity.manage.AchReview;
import com.casd.entity.manage.AttendPlace;
import com.casd.service.finance.MaterialService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.AchReviewService;
import com.casd.service.manage.AttendPlaceService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class AttendPlaceController {

	@Autowired
	private AchReviewService achReviewService;
	
	@Autowired
	private AttendPlaceService attendPlaceService;
	
	
	/**
	 *  打卡地点列表
	 * 
	 * */
	@RequestMapping(value = "/attendPlaceList", method = RequestMethod.GET)
	public ModelAndView attendPlaceList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/attendPlaceList");
		return mv;

	}

	@RequestMapping(value = "/attendPlaceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attendPlaceList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		StringBuilder sBuilder = new StringBuilder();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		Ref<Integer> record = new Ref<Integer>();
		String fields = "*";
		sBuilder.append(" hr_attend_place place left join construct_project_table pro on pro.construct_project_id=place.hr_attend_place_project ");
		sBuilder.append(" where 1=1");
		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sBuilder.append(" and pro.construct_project_name like '%" + construct_project_name + "%'");
		}

		// 部门列表
		List<Map<String, Object>> data = achReviewService.achReviewList(pageIndex,
				pageSize, record, fields, sBuilder.toString());
		jsonMap.put("rows", data);
		jsonMap.put("total", record.getValue());

		return jsonMap;

	}


	@RequestMapping(value = "/save_place", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject save_place(HttpServletRequest request,AttendPlace attendPlace) {
		JSONObject data=new JSONObject();
		try {
			attendPlaceService.save_place(attendPlace);
			data.put("success", true);
			data.put("Msg", "保存成功");
		} catch (Exception e) {
			data.put("success", false);
			data.put("Msg", "保存失败");
		}
		return data;
	}
	
	
	
	/**
	 * 删除材料
	 * */
	@RequestMapping(value = "/dele_place", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dele_place(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Map<String, Object> deleMap=new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			String ids = request.getParameter("ids");
			ids = ids.substring(1);
			String[] id = ids.split("]");
			sbf.append(" hr_attend_place where hr_attend_place_id in (" + id[0] + ")");
			deleMap.put("where", sbf.toString());
			attendPlaceService.dele_place(deleMap);
			map.put("success", true);
			map.put("msg", "删除成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败,请联系管理员！");
		}

		return map;

	}
}
