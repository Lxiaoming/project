package com.casd.controller.personManagem;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.dao.activiti.ActivitiDao;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.hr.Resign;
import com.casd.service.hr.ActivitiService;
import com.casd.service.hr.ResignService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.UserService;

import Decoder.BASE64Decoder;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin")
public class ResignController {

	@Autowired
	private ResignService resignService;

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private SupOpinionService supOpinionService;
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;

	private String autoPath = "";
	private String resignId;
	private String taskid;

	@Autowired
	private  ActivitiDao activitiDao;
	/**
	 * 添加离职申请
	 */
	@RequestMapping(value = "/addResign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResign(Resign ressign, String username) {

		Map<String, Object> json = new HashMap<String, Object>();
		try {

			resignService.addResign(ressign);

			String name = ressign.getHr_resign_userid() + "";
			if (StringUtils.isEmpty(name)) {
				json.put("Success", false);
				json.put("Msg", "没有离职用户！请联系管理员查看");
				return json;
			}

			String processDefinitioKey = "resignView"; // 定义流程的key,不可修改

			String bizId = processDefinitioKey + "."
					+ ressign.getHr_resign_id(); // 获取业务id
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("applyUserId", ressign.getHr_resign_userid() + "");
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(
					processDefinitioKey, bizId, vars);

			DataProcinst dataProcinst = new DataProcinst();
			dataProcinst.setProc_inst_id_(pi.getId());
			dataProcinst.setApplicant(username);
			dataProcinst.setIllustrate("离职单据编号:" + ressign.getHr_resign_id());
			activitiDao.insertDataProcinst(dataProcinst);

			String content = "你有一张离职申请单需要审批！";
			json = activitiService.sendEmail("离职申请", content, name);

			json.put("Success", true);
			json.put("Msg", "保存成功，流程已启动");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "保存失败，流程未启动");
		}
		return json;

	}

	/**
	 * 查看
	 * @throws Exception 
	 */
	@RequestMapping(value = "/resignView{taskid}", method = RequestMethod.GET)
	public ModelAndView resignView(HttpServletRequest request,String taskid,String taskName) throws Exception {
		ModelAndView mv = new ModelAndView();
		String beyId = "resignView"; // 流程实例key 请勿改动
		//审核历史意见
		List<Map<String, Object>> historyList = new ArrayList<>();
		String position = null;
		if(taskid == null){
			String hr_resign_id = request.getParameter("hr_resign_id").toString();
			String bizId = String.valueOf(hr_resign_id);
			StringBuffer sbf = new StringBuffer();
			String fields = "*";
			sbf.append(" hr_resign where hr_resign_id=" + bizId + "");
			List<Resign> resign = resignService.getData(fields, sbf.toString());
			mv.addObject("resign", resign.get(0));
			
			//审核历史意见
			historyList = activitiService.viewHisComments(bizId, beyId);
			mv.setViewName("personManagem/resignView");
			position = "";
		}else{
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}

			String fields = "a.*,b.username,r.role_name ";
			StringBuffer sbf = new StringBuffer();
			sbf.append(" hr_resign a JOIN  uc_user b ON a.hr_resign_userid=b.userid ");
			sbf.append(" left join uc_role r on b.role_id = r.role_id WHERE 1=1 ");
			sbf.append(" and a.hr_resign_id=" + bizId);
			
			Map<String, Object> resign = userService.queryUserById(fields, sbf.toString());
			
			historyList = activitiService.getProcessComments(taskid);// 查询审批记录
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("history", historyList);
			mv.addObject("history", jsonObject);
			
			mv.addAllObjects(resign);
			
			mv.addObject("taskid", taskid);
			Object startForm = activitiService.getRenderedTaskForm(taskid); // 获取表单
			mv.addObject("startForm",startForm);
			mv.setViewName("personManagem/resignAudit");
			position = (String) resign.get("role_name");
		}
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		
		String[] temp = new String[]{};
		if(position.indexOf("总经理")!=-1|| position.indexOf("董事长助理")!=-1){
			temp = new String[] {"当事人申请", "总裁", "董事长","部门经理","人力资源中心","财务中心","申请人确认签名","技术部注销账户"};				
		}else if((position.indexOf("经理")!=-1) || (position.indexOf("项目助理")!=-1)|| (position.indexOf("秘书")!=-1)){
			temp = new String[] {"当事人申请", "人力资源中心", "专业公司总经理","总裁","部门经理","人力资源中心","财务中心","申请人确认签名","技术部注销账户"};
		}else {
			temp = new String[] {"当事人申请", "部门经理", "人力资源中心", "专业公司总经理","部门经理","人力资源中心","财务中心","申请人确认签名","技术部注销账户"};
		}
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		//审核历史意见
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("history", historyList);
		mv.addObject("taskName", taskName);
		mv.addObject("history", jsonObject);
		
		return mv;
	}

	/**
	 * 签名确认
	 */
	@RequestMapping(value = "/save_auto", method = RequestMethod.POST)
	@ResponseBody
	public void save_auto(HttpServletRequest request,
			HttpServletResponse response, String lines) throws Exception {

		File path = null;
		boolean isSave = false;
		String fileName = "";
		if (StringUtils.hasText(autoPath)) {
			path = new File("E://uploadfile/photo/" + autoPath + "");
		} else {
			fileName = String.valueOf(new Date().getTime()) + ".png";
			path = new File("E://uploadfile/photo/" + fileName + "");
			isSave = true;
		}

		BASE64Decoder decoder = new BASE64Decoder();
		String imageDataurl = lines.substring(22);
		byte[] b = decoder.decodeBuffer(imageDataurl);// 转码得到图片数据

		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		BufferedImage bi = ImageIO.read(bais);

		ImageIO.write(bi, "png", path);
		if (isSave){
			resignService.addAutoPath(fileName, resignId);
		}
		response.sendRedirect("resignView.do?hr_resign_id=" + resignId + "&taskid="+taskid);
	   }

	/**
	 * 签名
	 */
	@RequestMapping(value = "/auto", method = RequestMethod.GET)
	public ModelAndView auto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		resignId = request.getParameter("hr_resign_id");
		autoPath = request.getParameter("hr_resign_autoPath");
		taskid = request.getParameter("taskid");
		mv.setViewName("manage/auto");
		return mv;

	}
       
	/**
	 * @param 获取任务id
	 * @throws Exception
	 * 
	 * */
	@RequestMapping(value = "/resignAudit", method = RequestMethod.GET)
	public ModelAndView resignAudit(String taskid, String taskName) throws Exception {
		ModelAndView mv = new ModelAndView();
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}

		String fields = "a.*,b.username";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" hr_resign a JOIN  uc_user b ON a.hr_resign_userid=b.userid  WHERE 1=1");
		sbf.append(" and a.hr_resign_id=" + bizId);
		
		Map<String, Object> resign = userService.queryUserById(fields, sbf.toString());
		List<String> bot = activitiService.findOutComeListByTaskId(taskid);// 查看当前有几条线
		
		List<Map<String, Object>> userList = supOpinionService.supOpinionUser(
				taskName, "resignView");
	
		
		if (taskName.equals("董事长")) {
			
			ActivityImpl activityImpl=	activitiService.usertask(taskid);
			String nextUser= null;
		
			if ((activityImpl.getId()).equals("usertask6")) {
				nextUser=  taskService.getVariable(taskid,"bmjl").toString();
			}else if ((activityImpl.getId()).equals("usertask10")) {
			    nextUser= taskService.getVariable(taskid,"username").toString();
			}
			String field = "userid,username";
			String where = " uc_user where userid="+nextUser;
			userList=userService.queryId(field, where);
		
		}

		List<Map<String, Object>> historyList = activitiService
				.getProcessComments(taskid);// 查询审批记录
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("history", historyList);
		mv.addObject("history", jsonObject);
		
		mv.addAllObjects(resign);
		
		mv.addObject("userList",userList);
		mv.addObject("bot",bot);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("personManagem/resignAudit");
		return mv;

	}
	
	@RequestMapping(value = "/resign_pass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resign_pass(HttpServletRequest request ,Resign resign) throws Exception {
		Map<String, Object> json=new HashMap<String, Object>();
		
		try {
		  resignService.passResign(request, resign);
		json.put("Success", true);
		json.put("Msg", "已审核!");
	} catch (Exception e) {
		e.printStackTrace();
		json.put("Success", false);
		json.put("Msg", "程序出错,请联系技术员");
    	}
		return json;
		
		/*	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			 User user= (User) request.getSession().getAttribute("loginUser");
	System.out.println(user.getUsername());*/
	}

}
