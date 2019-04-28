package com.casd.controller.manage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
import com.casd.entity.manage.AchReview;
import com.casd.entity.uc.User;
import com.casd.service.finance.MaterialService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.AchReviewService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class AchReviewController {

	@Autowired
	private AchReviewService achReviewService;
	
	@Autowired
	private SupOpinionService supOpinionService;
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private UserService userService;
	
	/**
	 *  绩效考核列表
	 * 
	 * */
	@RequestMapping(value = "/achReviewList", method = RequestMethod.GET)
	public ModelAndView achReviewList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/achReviewList");
		return mv;

	}

	@RequestMapping(value = "/achReviewLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> achReviewList(HttpServletRequest request)
			throws Exception {
		
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		
		StringBuilder sBuilder = new StringBuilder();
		Ref<Integer> record = new Ref<Integer>();
		String fields = "achReview_id,achReview_company,achReview_month,achReview_creatDate";
		sBuilder.append(" manage_achReview ");
		sBuilder.append(" where 1=1");
		
		String achReview_company = request.getParameter("achReview_company");
		if (StringUtils.hasText(achReview_company)) {
			sBuilder.append(" and achReview_company like '%" + achReview_company + "%'");
		}
		String achReview_month = request.getParameter("achReview_month");
		if (StringUtils.hasText(achReview_month)) {
			sBuilder.append(" and achReview_month like '%" + achReview_month + "%'");
		}

		// 部门列表
		List<Map<String, Object>> data = achReviewService.achReviewList(pageIndex,
				pageSize, record, fields, sBuilder.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	}

	
	
	/**
	 *  绩效考核新增界面,修改界面
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping(value = "/achReviewNew", method = RequestMethod.GET)
	public ModelAndView achReviewNew(HttpServletRequest request ,Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		
	List<Map<String, Object>>userList =	supOpinionService.supOpinionUser("绩效考核申请","achReviewView");
	
		if (request.getParameter("achReview_id")!="") {
			int achReview_id = Integer.valueOf(request.getParameter("achReview_id").toString());
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("achReview_id", achReview_id);
			data= achReviewService.getData(map);
			model.addAttribute("data", data.get(0));
			mv.addObject("edit", false);
		}else {
			mv.addObject("edit", true);
		}
		mv.addObject("userList", userList);
		mv.setViewName("manage/achReviewNew");
		return mv;

	}
	
	/**
	 *  绩效考核查看界面
	 * 
	 * */
	@RequestMapping(value = "/achReviewView", method = RequestMethod.GET)
	public ModelAndView achReviewView(HttpServletRequest request ,Model model) {
		int achReview_id = Integer.valueOf(request.getParameter("bizId").toString());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("achReview_id", achReview_id);
		List<Map<String, Object>> data= achReviewService.getData(map);
		model.addAttribute("data", data.get(0));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/achReviewView");
		return mv;
	}
	
	/**
	 *  绩效考核删除
	 * 
	 * */
	@RequestMapping(value = "/delect_achReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delect_achReview(HttpServletRequest request) {
		String achReview_id = request.getParameter("achReview_id");
		Map<String, Object> json = new HashMap<String, Object>();

		try {
			Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			
			sbf.append(" manage_achreview where achReview_id=" + achReview_id);
			map.put("where", sbf);

			achReviewService.delete_data(map);
			   json.put("Success", true);
				json.put("Msg", "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "删除失败");
			}
			return json;

	    }
	
	
	
	/**
	 *  绩效考核保存
	 * 
	 * */
	@RequestMapping(value = "/save_achReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save_achReview(HttpServletRequest request)
			throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		String username = user.getUsername();// 获取申请人
		AchReview achReview=new AchReview();
		String userid=request.getParameter("username");
		String achReview_id=request.getParameter("achReview_id");

		if (achReview_id ==null || StringUtils.isEmpty(achReview_id)) {
			Date date=new Date(System.currentTimeMillis());
			achReview.setAchReview_id(0);
			achReview.setAchReview_creatDate(date);
		}else {
			Date date=new Date(System.currentTimeMillis());
			achReview.setAchReview_creatDate(date);//要修改
			achReview.setAchReview_id(Integer.valueOf(request.getParameter("achReview_id").toString()));
		}
		
		achReview.setAchReview_comOpinion(request.getParameter("achReview_comOpinion"));
		achReview.setAchReview_company(request.getParameter("achReview_company"));
		achReview.setAchReview_dirOpinion(request.getParameter("achReview_dirOpinion"));
		achReview.setAchReview_month(request.getParameter("achReview_month"));
		achReview.setAchReview_supCenter(request.getParameter("achReview_supCenter"));
		achReviewService.save_achReview(achReview,userid,username);
		return null;
		
	}
	
	
	@RequestMapping(value = "/achReviewAut", method = RequestMethod.GET)
	public Object supOpinionAut(@Param("taskid")String taskid,String taskName) throws Exception{
		ModelAndView mv = new ModelAndView();

		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("achReview_id", bizId);
	
		List<Map<String, Object>> data= achReviewService.getData(map);
		mv.addObject("data", data.get(0));	
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("manage/achReviewAut");
		return mv;
	   }
	@RequestMapping(value = "/sava_ReviewAut", method = RequestMethod.POST)
	@ResponseBody
	public Object  sava_ReviewAut(HttpServletRequest request) {
	 Map<String, Object> json=new HashMap<String, Object>();
		try {
			achReviewService.sava_ReviewAut(request);
			json.put("Success", true);
			json.put("Msg", "已审核!");
		} catch (Exception e) {
			e.printStackTrace(); 
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");	
			}
			return json;
		}
	
	
}
