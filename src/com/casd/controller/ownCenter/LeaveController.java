package com.casd.controller.ownCenter;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.Leave;
import com.casd.entity.uc.User;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.ownCenter.LeaveService;
import com.casd.service.uc.RoleService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class LeaveController {

	@Autowired
	private FlowService flowService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ActivitiService activitiService;	


	/**
	 * 请假记录表
	 */
	@RequestMapping(value = "/leaveList", method = RequestMethod.GET)
	public ModelAndView leaveList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/leaveList");
		return mv;
	}

	@RequestMapping(value = "/leaveList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> leaveList(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		StringBuffer finds = new StringBuffer();
       
	    finds.append("*,(SELECT SUM(day_count) FROM own_leave_table where 1=1");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String department = request.getParameter("department");
		String applicant = request.getParameter("applicant");
		String status = request.getParameter("status");
		String leave_category = request.getParameter("leave_category");
	
		sbf.append("own_leave_table a ");
		sbf.append("LEFT JOIN uc_company b on a.department=b.company_id where 1=1");
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		if (StringUtils.hasText(start_time)) {
			java.util.Date start_timeDate = format.parse(start_time);
			Date startDate = new Date(start_timeDate.getTime());
			sbf.append(" and start_time >='" + startDate + "'");
			finds.append(" and start_time >='" + startDate + "'");		
		}
		if (StringUtils.hasText(end_time)) {
			java.util.Date end_timeDate = format.parse(end_time);
			Date endDate = new Date(end_timeDate.getTime());
			sbf.append(" and end_time <= '" + endDate + "'");
			finds.append(" and end_time <= '" + endDate + "'");
			
		}
		if (StringUtils.hasText(department)) {
			sbf.append(" and b.company_name like '%" + department + "%'");
			finds.append(" and b.company_name like '%" + department + "%'");
		
		}
		if (StringUtils.hasText(applicant)) {
			sbf.append(" and applicant = '" + applicant + "'");
			finds.append(" and applicant = '" + applicant + "'");
		
		}
		if (StringUtils.hasText(status)) {
			sbf.append(" and status = " + status + "");
			finds.append(" and status = " + status + "");
		}
		if (StringUtils.hasText(leave_category)) {
			sbf.append(" and leave_category =" +leave_category +"");
			finds.append(" and leave_category =" +leave_category + "");
		}
		sbf.append(" order by id desc");
		finds.append(") sumday");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
    
		List<Map<String, Object>> data = leaveService.leaveList(pageIndex,
				pageSize, record, finds.toString(),sbf.toString());
	      double sumday=0.0;
			List<Map<String, Object>> datas =new ArrayList<Map<String,Object>>();
			Map<String, Object> footMap = new HashMap<String, Object>();
			footMap.put("id", "合计");
		if (data.size()>0) {
				sumday=	Double.parseDouble(data.get(0).get("sumday").toString());
		}
	
	
		footMap.put("day_count",sumday);
	    datas.add(footMap);	
		json.put("footer", datas);
		json.put("isFooter",true);
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	/**
	 * 填写请假条页面
	 * 
	 * @throws Exception
	 */

	@RequestMapping(value = "/leave", method = RequestMethod.GET)
	public ModelAndView leave(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String leaveId = request.getParameter("bizId");
		Object startForm = null;
		String fields;
		Map<String, Object> map = new HashMap<>();
		JSONArray jsonObject = new JSONArray();
		String position = null;//申请人角色
		String taskName = request.getParameter("taskName");
		if (StringUtils.hasText(leaveId)) {
			fields = "own.*";
			String where = "own_leave_table own LEFT JOIN uc_company com on own.department=com.company_id";
			where += " where id=" + leaveId;
			map = userService.queryUserById(fields, where);

			position = (String) map.get("position");
			String bizId = String.valueOf(leaveId);
			String beyId = "Leave_flow"; // 流程实例key 请勿改动

			List<Map<String, Object>> historyList = activitiService
					.viewHisComments(bizId, beyId);
			// 审核历史意见
			jsonObject = JSONArray.fromObject(historyList);
			map.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("start_time")));
			map.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("end_time")));
			
		} else {
			startForm = activitiService.getRenderedStartForm("Leave_flow:5:593258");
			User loginUser = (User) request.getSession().getAttribute("loginUser");
			fields = "a.username applicant,b.role_name position,c.center_companyId department";
			StringBuffer sbf = new StringBuffer();
			sbf.append("uc_user a JOIN uc_role b ON a.role_id=b.role_id");
			sbf.append(" JOIN uc_center c ON c.center_id=a.center_id");
			sbf.append(" where a.userid=" + loginUser.getUserid());
			map = userService.queryUserById(fields, sbf.toString());
			
			position = (String) map.get("position");
			
			mv.addObject("taskName", "申请人");
			
		}
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		
		String[] temp = new String[]{};
		if(position.indexOf("总经理")!=-1|| position.indexOf("董事长助理")!=-1){
			temp = new String[] {"申请人", "总裁", "董事长", "人力资源中心"};				
		}else if((position.indexOf("经理")!=-1) || (position.indexOf("项目助理")!=-1)|| (position.indexOf("秘书")!=-1)){
			temp = new String[] {"申请人", "专业公司总经理", "总裁", "人力资源中心"};
		}else {
			temp = new String[] {"申请人", "部门经理", "专业公司总经理", "人力资源中心"};
		}
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		
		
		mv.addObject("history", jsonObject);
		mv.addAllObjects(map);
		mv.addObject("startForm", startForm);
		mv.setViewName("personManagem/leave");
		return mv;
	}

	/**
	 * 提交请假申请
	 */
	@RequestMapping(value = "save_Leave", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> submitLeave(Leave leave,
			HttpServletRequest request) {
		
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			
            java.util.Date utilDate=new java.util.Date();
            java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
            leave.setCreatedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlDate));           
			String position= leave.getPosition();//获取角色
			Map<String, Object> vars = new HashMap<String, Object>();
			if(position.indexOf("总经理")!=-1|| position.indexOf("董事长助理")!=-1){
				vars.put("type", "3");								
			}else if((position.indexOf("经理")!=-1) || (position.indexOf("项目助理")!=-1)|| (position.indexOf("秘书")!=-1)){
				vars.put("type", "2");
			}else {
				vars.put("type", "1");
			}
		
			leaveService.saveLeave(leave,vars);
		    json.put("Success", true);
			json.put("Msg", "已提交!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
    	}		
       return json;
	}

	/**
	 * 跳转到请假条审核界面 leaveAudit.jsp
	 * 
	 * @throws Exception
	 */

	@RequestMapping(value = "/Leave_flow{taskid}", method = RequestMethod.GET)
	public ModelAndView leaveAudit(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();

		String taskid = request.getParameter("taskid");// 获取任务id
		String taskName = request.getParameter("taskName");
		String position = null;//申请人角色
		
		List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
		Map<String, Object> leave = new HashMap<>();
		if(taskid == null){
			String b = request.getParameter("bizId");
			historyList = activitiService.viewHisComments(String.valueOf(b), "Leave_flow");
			String where="own_leave_table a LEFT JOIN uc_company b on a.department=b.company_id";
			where+=" where id="+b;
			leave= userService.queryUserById("*", where);
			
			
		}else {
			
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
	
			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
	
			String where="own_leave_table a LEFT JOIN uc_company b on a.department=b.company_id";
			where+=" where id="+bizId;
			leave= userService.queryUserById("*", where);
			position = (String) leave.get("position");
			historyList = activitiService.getProcessComments(taskid);// 查询审批记录
	
		    Object renderedTaskForm = activitiService.getRenderedTaskForm(taskid); // 获取表单
		    mv.addObject("startForm", renderedTaskForm);
		}
		leave.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(leave.get("start_time")));
		leave.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(leave.get("end_time")));
		// 审核历史意见
	    JSONArray jsonObject = new JSONArray();
		if (historyList!=null) {
			jsonObject=JSONArray.fromObject(historyList);
			
		}
		
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		
		String[] temp = new String[]{};
		if(position.indexOf("总经理")!=-1|| position.indexOf("董事长助理")!=-1){
			temp = new String[] {"申请人", "总裁", "董事长", "人力资源中心"};				
		}else if((position.indexOf("经理")!=-1) || (position.indexOf("项目助理")!=-1)|| (position.indexOf("秘书")!=-1)){
			temp = new String[] {"申请人", "专业公司总经理", "总裁", "人力资源中心"};
		}else {
			temp = new String[] {"申请人", "部门经理", "专业公司总经理", "人力资源中心"};
		}
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		mv.addObject("leave", leave);
		mv.addObject("history", jsonObject);		
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
			
		
		mv.setViewName("personManagem/leaveAudit");
		return mv;
	}

	/**
	 * 跳转查看界面
	 * @throws Exception 
	 */
/*
	@RequestMapping(value = "/Leave_flow", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView leaveView(HttpServletRequest request,@PathVariable("taskid")String taskid, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();

		int bill_id = Integer.parseInt(request.getParameter("bizId").toString());

		
		
		// 查询请假信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", bill_id);
		String where="own_leave_table a LEFT JOIN uc_company b on a.department=b.company_id";
		where+=" where id="+bill_id;
		Map<String, Object> leave= userService.queryUserById("*", where);
	//	Leave leave = leaveService.findLeaveByID(map);
		mv.addObject("leave", leave);

		String bizId = String.valueOf(bill_id);
		String beyId = "Leave_flow"; // 流程实例key 请勿改动

		List<Map<String, Object>> historyList = activitiService.viewHisComments(bizId, beyId);
		String bizkey =beyId+"."+bizId;	
		JSONArray jsonObject=new JSONArray();
		//审核历史意见
     	jsonObject=JSONArray.fromObject(historyList);
		mv.addObject("history",jsonObject);

		mv.setViewName("personManagem/leaveView");
		return mv;
	}
*/
	/**
	 * 请假条删除
	 * @throws Exception 
	 */
	@RequestMapping(value = "/deleLeave", method = RequestMethod.POST)
	@ResponseBody
	public String deleLeave(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String leaveId = request.getParameter("leaveId");
		String leaveFile_name = request.getParameter("leaveFile_name");
		File file2 = new File("E:/file/casd/leaveFile/"+leaveFile_name);
		if (file2.exists()) {
			file2.delete();
		}
         activitiService.deleteRecord(leaveId, "Leave_flow");
		map.put("what", leaveId);
		map.put("where", "own_leave_table");
		leaveService.deleLeave(map);
		return null;
	}

	/**
	 * 个人中心的请假list
	 */
	@RequestMapping(value = "/leavePersonal", method = RequestMethod.GET)
	public ModelAndView leavePersonal() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/leavePersonal");
		return mv;
	}

	@RequestMapping(value = "/leavePersonals", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> leavePersonal(Integer limit,Integer page,HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		String username = loginUser.getUsername();
		String  finds=" id,applicant,department,position,leave_category,start_time,end_time,reason";
		        finds +=",status,day_count,time_count";
		sbf.append(" own_leave_table where applicant='" + username + "'");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		if (StringUtils.hasText(start_time)) {
			java.util.Date start_timeDate = format.parse(start_time);
			Date startDate = new Date(start_timeDate.getTime());
			sbf.append(" and start_time >='" + startDate + "'");
		}
		if (StringUtils.hasText(end_time)) {
			java.util.Date end_timeDate = format.parse(end_time);
			Date endDate = new Date(end_timeDate.getTime());
			sbf.append(" and end_time <= '" + endDate + "'");
		} 
		sbf.append(" order by id desc");
		page = page==null ? 1: page;
	       page=page-1;
		List<Map<String, Object>> data = leaveService.leaveList(page,
				limit, record,finds, sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		json.put("code", 0);
		json.put("msg", "");
		json.put("count", record.getValue());
		json.put("data", jsonArray);
		return json;

	}

	/**
	 * 请假条审核通过
	 * 
	 * @throws Exception
	 * */
	@RequestMapping(value = "/leave_pass", method = RequestMethod.POST)
	@ResponseBody
	public Object leave_pass(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			
		   json=leaveService.leave_pass(request);
			json.put("Success", true);
			json.put("Msg", "已审核!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
		}

		return json;

	}

	// 不批准
	@RequestMapping(value = "/rejects_leave", method = RequestMethod.POST)
	@ResponseBody
	public Object rejects(HttpServletRequest request) {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			leaveService.rejects_leave(request);
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
