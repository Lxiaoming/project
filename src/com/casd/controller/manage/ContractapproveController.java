package com.casd.controller.manage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.DownloadUtil;
import com.casd.entity.manage.Contractapprove;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.ContractapproveService;
import com.casd.service.manage.SupOpinionService;

@Controller
@RequestMapping("/admin")
public class ContractapproveController {

	@Autowired
	private ContractapproveService contractapproveService;
	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private SupOpinionService supOpinionService;

	/**
	 * 合同审批列表
	 * 
	 * */
	@RequestMapping(value = "/contractapproveList", method = RequestMethod.GET)
	public ModelAndView contractapproveList() {
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
		mv.setViewName("manage/contractapproveList");
		return mv;

	}

	@RequestMapping(value = "/contractapproveLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> contractapproveList(HttpServletRequest request)
			throws Exception {
	
		StringBuilder sbf = new StringBuilder();
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		Ref<Integer> record = new Ref<Integer>();
		String fields = "*";
		sbf.append(" manage_contractapprove ");
		sbf.append(" where 1=1");

		String manage_contractapprove_num = request
				.getParameter("manage_contractapprove_num");
		if (StringUtils.hasText(manage_contractapprove_num)) {
			sbf.append(" and manage_contractapprove_num like '%"
					+ manage_contractapprove_num + "%'");
		}
		String manage_contractapprove_name = request
				.getParameter("manage_contractapprove_name");
		if (StringUtils.hasText(manage_contractapprove_name)) {
			sbf.append(" and manage_contractapprove_name like '%"
					+ manage_contractapprove_name + "%'");
		}

		String manage_contractapprove_company = request
				.getParameter("manage_contractapprove_company");
		if (StringUtils.hasText(manage_contractapprove_company))
			sbf.append(" and manage_contractapprove_company like '%"
					+ manage_contractapprove_company + "%'");
		
		sbf.append(" order by manage_contractapprove_id DESC");

		List<Map<String, Object>> data = contractapproveService.contractapproveList(pageIndex, pageSize, record, fields,
				sbf.toString());
/*		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> footMap = new HashMap<String, Object>();
		list.add(footMap);
		jsonMap.put("footer", list);
		jsonMap.put("rows", data);
		jsonMap.put("total", record.getValue());*/
		
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	}

	/**
	 * 新增，修改合同
	 * 
	 * */
	@RequestMapping(value = "/contractapproveNew", method = RequestMethod.GET)
	public ModelAndView contractapproveNew(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String bizId = request.getParameter("manage_contractapprove_id");

		JSONArray jsonObject = new JSONArray();
		JSONObject json=new JSONObject();
		List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
		String type = "";
	  Object startForm=null;
		if (bizId.equals("")) {
			 startForm = activitiService.getRenderedStartForm("contractapproveView:4:590034");

			type = "'new'";
		} else {
			Map<String, Object> contractapprove = contractapproveService.getData(bizId);
			json=JSONObject.fromObject(contractapprove);
			type = request.getParameter("type");
			String beyId = "contractapproveView"; // 流程实例key 请勿改动
			historyList = activitiService.viewHisComments(bizId, beyId);
			jsonObject = JSONArray.fromObject(historyList);

		}
		//流程线
		List<List> activityList = new ArrayList<List>();// 获取节点
		String[][] temp = new String[][]{};
		temp = new String[][]{{"申请人","专业公司总经理", "董事长"},
				{"申请人","专业公司总经理","总裁"},
				{"申请人","董事长助理","总裁"}};
		for(int i = 0; i < temp.length; i++){ //遍历二维数组，遍历出来的每一个元素是一个一维数组
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();// 
			for(int j = 0; j < temp[i].length; j++){ //遍历对应位置上的一维数组
				Map<String, Object> activityMap = new HashMap<String, Object>();
				activityMap.put("name", temp[i][j]);
				tempList.add(activityMap);
			}
			activityList.add(tempList);
		}
			
		mv.addObject("activityList", activityList);
		
		mv.addObject("history", jsonObject);
		mv.addObject("type", type);
		mv.addObject("startForm", startForm);
		mv.addObject("contractapprove",json);
		mv.setViewName("manage/contractapproveNew");
		return mv;
	}

	/**
	 * 
	 * 保存提交
	 * 
	 * */
	@RequestMapping(value = "/save_conApprove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save_conApprove(Contractapprove contractapprove,
			HttpServletRequest request) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();

		try {
	
		
			contractapproveService.saveContractapprove(contractapprove,request);

			json.put("Success", true);
			json.put("Msg", "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "上传失败" + e);
		}
		return json;

	}

	/**
	 * 合同审批删除
	 * 
	 * */
	@RequestMapping(value = "/delete_Contractapprove", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delete_Contractapprove(HttpServletRequest request) {
		
		JSONObject json=new JSONObject();
		
		try {
			String bizId = request.getParameter("manage_contractapprove_id");
			 contractapproveService.delete_Contractapprove(bizId);
			json.put("Success", true);
			json.put("Msg", "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "操作失败");
		
		}
	
		return json;

	}

	/**
	 * 合同审批
	 * 
	 * @throws Exception
	 * 
	 * */
	@RequestMapping(value = "/contractapproveView{taskid}", method = RequestMethod.GET)
	public ModelAndView contractapproveAut(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String taskid = request.getParameter("taskid");// 获取任务id
		String taskName = request.getParameter("taskName");
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}


	

		String string = "*";
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" manage_contractapprove  where manage_contractapprove_id="
				+ bizId + "");
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> contractapprove = contractapproveService
				.findContractapprove(string, sBuffer.toString());
		jsonObject=JSONObject.fromObject(contractapprove);
	
       JSONArray jsonArray=new JSONArray();
		List<Map<String, Object>> historyList = activitiService
				.getProcessComments(taskid);// 查询审批记录
		jsonArray=JSONArray.fromObject(historyList);
		 Object startForm = activitiService.getRenderedTaskForm(taskid); // 获取表单

		mv.addObject("history", jsonArray);
		mv.addObject("startForm", startForm);

		//流程线
		List<List> activityList = new ArrayList<List>();// 获取节点
		String[][] temp = new String[][]{};
		temp = new String[][]{{"申请人","专业公司总经理", "董事长"},
				{"申请人","专业公司总经理","总裁"},
				{"申请人","董事长助理","总裁"}};
		for(int i = 0; i < temp.length; i++){ //遍历二维数组，遍历出来的每一个元素是一个一维数组
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();// 
			for(int j = 0; j < temp[i].length; j++){ //遍历对应位置上的一维数组
				Map<String, Object> activityMap = new HashMap<String, Object>();
				activityMap.put("name", temp[i][j]);
				tempList.add(activityMap);
			}
			activityList.add(tempList);
		}
			
		mv.addObject("activityList", activityList);
		
		mv.addObject("contractapprove", jsonObject);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("manage/contractapproveAut");
		return mv;

	}

	/**
	 * 项目合同审核
	 * 
	 * */
	@RequestMapping(value = "/contractapprovePass", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> contractapprovePass(HttpServletRequest request,Contractapprove contractapprove) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			contractapproveService.contractapprovePass(request,contractapprove);
			json.put("Success", true);
			json.put("Msg", "已审核");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "操作失败,请联系管理员");
		}
		return json;

	}

	// 下载
	@RequestMapping(value = "/downloadCon", method = RequestMethod.POST)
	@ResponseBody
	public void downloadCon(HttpServletRequest request,
			HttpServletResponse response) {
		String fileurl = "e:/uploadfile/photo/contractFile/"+request.getParameter("attachAddress");
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.saveUrlAs(fileurl, request, response);
	}

}
