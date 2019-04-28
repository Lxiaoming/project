package com.casd.controller.personManagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.dao.uc.UserDao;
import com.casd.entity.hr.Become;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.hr.HregisterService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class BecomeController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;

	@Autowired
	private HregisterService hrService;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private SupOpinionService supOpinionService;

	/**
	 * 
	 * */
	@RequestMapping(value = "/becomeList", method = RequestMethod.GET)
	public ModelAndView becomeList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/becomeList");
		return mv;

	}

	@RequestMapping(value = "/becomeList", method = RequestMethod.POST)
	@ResponseBody
	public Object becomeList(HttpServletRequest request) throws Exception {

		Ref<Integer> record = new Ref<Integer>();
		StringBuilder sbf = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		// 显示字段
		fields.append("a.userid,a.username,a.role_id,c.role_name,a.close_time,a.`status`");

		Map<String, Object> json = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		sbf.append(" uc_user a");

		sbf.append(" LEFT JOIN uc_role c  ON a.role_id=c.role_id");
		sbf.append(" where a.status=" + 3);
		List<Map<String, Object>> data = userService.userList(pageIndex,
				pageSize, record, sbf.toString(), fields.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	/**
	 * 保存转正申请
	 * **/
	@RequestMapping(value = "/submitBecome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitBecome(HttpServletRequest request,
			Become become) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		try {

			User user = (User) request.getSession().getAttribute("loginUser");
			String name = user.getUserid() + "";// 获取申请人

			hrService.insertBecome(become, name);

			json.put("Success", true);
			json.put("Msg", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Msg", "保存失败,请联系技术员！");
			json.put("Success", false);
		}
		return json;

	}


	/**
	 * 转正申请审核
	 * */
	@RequestMapping(value = "/become_pass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> becomepass(HttpServletRequest request)
			throws Exception {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			hrService.becomepass(request);
			json.put("Success", true);
			json.put("Msg", "已审核!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
		}
		return json;
	}

	@RequestMapping(value = "/Become_for{taskid}", method = RequestMethod.GET)
	public ModelAndView becomeView(HttpServletRequest request, Model model,
			String taskid,String taskName) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		StringBuffer sbf = new StringBuffer();
		JSONObject map = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object>  data = new HashMap<>();
		String position = null;
		if (taskid == null) {
			String userid = request.getParameter("userid");
			String status = request.getParameter("status");
			StringBuilder string = new StringBuilder();
			String field = " u.userid,u.username,u.department,u.education,u.major,u.on_trial,u.incorporation_date,u.close_time,b.role_name,c.department_name";
			string.append(" uc_user u LEFT JOIN uc_role b  on u.role_id=b.role_id");
			string.append(" LEFT join  uc_department c on u.department=c.department_id where u.userid=" + userid);
			data = userService.queryUserById(field,string.toString());
			String fields = " b.*";
	
			sbf.append(" uc_user a join uc_become b on a.userid=b.user_id");
	
			sbf.append(" where 1=1");
			if (userid == null) {
				String bizId = request.getParameter("bizId");
				sbf.append(" and b.bc_id=" + bizId);
			} else {
				sbf.append(" and b.user_id=" + userid);
			}
			List<Map<String, Object>> maps = userService.queryId(fields,sbf.toString());
			jsonObject.put("rows", maps);
			mv.addObject("userid", userid);
			mv.addObject("status", status);
			mv.setViewName("personManagem/becomeView");
			position = (String) data.get("role_name");
		}else {
			
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
			String fields = "b.*";
			String where = " uc_become b join uc_user a on a.userid=b.user_id where 1=1 and b.bc_id="+bizId;
			//转正申请数据
			data = userService.queryUserById(fields,where.toString());
			Object startForm = activitiService.getRenderedTaskForm(taskid); // 获取表单
			mv.addObject("startForm", startForm);
			//审批历史记录
			List<?> historyList = activitiService.getProcessComments(taskid);
			mv.addObject("history", JSONArray.fromObject(historyList));
			mv.setViewName("personManagem/auditBecome");
			mv.addObject("taskid", taskid);
			position = (String) data.get("role_name");
		}
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		
		String[] temp = new String[]{};
		if(position.indexOf("总经理")!=-1|| position.indexOf("董事长助理")!=-1){
			temp = new String[] {"当事人", "总裁", "董事长"};				
		}else if((position.indexOf("经理")!=-1) || (position.indexOf("项目助理")!=-1)|| (position.indexOf("秘书")!=-1)){
			temp = new String[] {"当事人", "专业公司总经理", "总裁"};
		}else {
			temp = new String[] {"当事人", "部门经理", "专业公司总经理", "总裁"};
		}
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		map.put("rows1", jsonObject);
		model.addAttribute("rows", map);
		mv.addObject("mpaList", data);
		mv.addObject("taskName", taskName);
		return mv;

	}

	@RequestMapping(value = "/becomehistory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> becomehistory(HttpServletRequest request,
			Model model) throws Exception {

		String bizId = request.getParameter("bizid");
		String fields = " b.*";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" uc_become b ");
		sbf.append(" where b.bc_id=" + bizId);
		Map<String, Object> maps = userService.queryUserById(fields,
				sbf.toString());
		String beyId = "Become_for"; // 流程实例key 请勿改动
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList = activitiService.viewHisComments(bizId, beyId);
		Map<String, Object> maplist = new HashMap<String, Object>();
		maplist.put("dataList", dataList);
		maplist.put("maps", maps);
		return maplist;

	}

	/**
	 * 获取节点审核人
	 * */
	@RequestMapping(value = "/becomeUser", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray becomeUser(String nodeName, HttpServletResponse response)
			throws Exception {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = new JSONArray();

		try {
			if (nodeName != null && nodeName != "") {

				userList = supOpinionService.supOpinionUser(nodeName,
						"Become_for");

				jsonArray = JSONArray.fromObject(userList);
			}
		} catch (Exception e) {
			response.sendError(500, e.getMessage());
		}
		return jsonArray;
	}

}
