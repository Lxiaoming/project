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

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
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
import com.casd.entity.manage.Notice;
import com.casd.entity.manage.SupOpinion;
import com.casd.entity.uc.User;
import com.casd.service.finance.MaterialService;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.AchReviewService;
import com.casd.service.manage.NoticeService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class SupOpinionController {

	@Autowired
	private SupOpinionService supOpinionService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private UserService userService;
	@Autowired
	private NoticeService noticeService;
	 @Autowired    
	 private HistoryService historyService; 
	 
	 @Autowired    
	 private FormService formService;    
	
	/**
	 *  监察意见列表
	 * 
	 * */
	@RequestMapping(value = "/supOpinionList", method = RequestMethod.GET)
	public ModelAndView supOpinionList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/supOpinionList");
		return mv;

	}

	@RequestMapping(value = "/supOpinionLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> supOpinionList(HttpServletRequest request)
			throws Exception {

		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		
		StringBuilder sBuilder = new StringBuilder();
		Ref<Integer> record = new Ref<Integer>();
		
		String fields = "supOpinion_id,supOpinion_company,supOpinion_title,supOpinion_creatDate";
		sBuilder.append(" manage_supopinion ");
		sBuilder.append(" where 1=1");
		String supOpinion_company = request.getParameter("supOpinion_company");
		if (StringUtils.hasText(supOpinion_company)) {
			sBuilder.append(" and supOpinion_company like '%" + supOpinion_company + "%'");
		}
		String supOpinion_title = request.getParameter("supOpinion_title");
		if (StringUtils.hasText(supOpinion_title)) {
			sBuilder.append(" and supOpinion_title like '%" + supOpinion_title + "%'");
		}

		List<Map<String, Object>> data = supOpinionService.supOpinionList(pageIndex,
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
	 *  监察意见新增界面,修改界面
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping(value = "/supOpinionNew", method = RequestMethod.GET)
	public ModelAndView supOpinionNew(HttpServletRequest request ,Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		
		//查询审核人
		List<Map<String, Object>> userList=supOpinionService.supOpinionUser("提交申请","supOpinionView");
		
		if (request.getParameter("supOpinion_id")!="") {
			int supOpinion_id = Integer.valueOf(request.getParameter("supOpinion_id").toString());
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("supOpinion_id", supOpinion_id);
			data= supOpinionService.getData(map);
			model.addAttribute("data", data.get(0));
			mv.addObject("edit", false);
		}else {
			mv.addObject("edit", true);
		}
		model.addAttribute("userList", userList);
		mv.setViewName("manage/supOpinionNew");
		return mv;

	}
	/**
	 *  监察意见查看界面
	 * 
	 * */
	@RequestMapping(value = "/supOpinionView", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView supOpinionView(HttpServletRequest request ,Model model) {
		int supOpinion_id = Integer.valueOf(request.getParameter("bizId").toString());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supOpinion_id", supOpinion_id);
		List<Map<String, Object>> data= supOpinionService.getData(map);
		model.addAttribute("data", data.get(0));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/supOpinionView");
		return mv;
	}
	
	/**
	 *  监察意见删除
	 * 
	 * */
	@RequestMapping(value = "/delect_supOpinion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delect_supOpinion(HttpServletRequest request) {
		String supOpinion_id = request.getParameter("supOpinion_id");
		Map<String, Object> json = new HashMap<String, Object>();

		try {
			Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			sbf.append(" manage_supopinion where supOpinion_id=" + supOpinion_id);
			map.put("where", sbf);
			supOpinionService.delete_data(map);
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
	 *   监察意见保存
	 * 
	 * */
	@RequestMapping(value = "/save_supOpinion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save_supOpinion(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json=new HashMap<String, Object>();
		try {
			User user = (User) request.getSession().getAttribute("loginUser");
			String username = user.getUsername();// 获取申请人
	
		SupOpinion supOpinion=new SupOpinion();
		String userid=request.getParameter("username");
		if (request.getParameter("supOpinion_id")=="") {
			Date date=new Date(System.currentTimeMillis());
			supOpinion.setSupOpinion_id(0);
			supOpinion.setSupOpinion_creatDate(date);
		}else {
			Date date=new Date(System.currentTimeMillis());
			supOpinion.setSupOpinion_creatDate(date);//要修改
			supOpinion.setSupOpinion_id(Integer.valueOf(request.getParameter("supOpinion_id").toString()));
		}
		
		supOpinion.setSupOpinion_company(request.getParameter("supOpinion_company"));
		supOpinion.setSupOpinion_supProblem(request.getParameter("supOpinion_supProblem"));
		supOpinion.setSupOpinion_supPropose(request.getParameter("supOpinion_supPropose"));
		supOpinion.setSupOpinion_praiOrPun(request.getParameter("supOpinion_praiOrPun"));
		supOpinion.setSupOpinion_selfEva(request.getParameter("supOpinion_selfEva"));
		supOpinion.setSupOpinion_result(request.getParameter("supOpinion_result"));
		supOpinion.setSupOpinion_title(request.getParameter("supOpinion_title"));

		supOpinionService.save_supOpinion(supOpinion,userid,username);
		json.put("Success", true);
		json.put("Msg", "已提交!");
	} catch (Exception e) {
		e.printStackTrace();
		json.put("Success", false);
		json.put("Msg", "程序出错,请联系技术员");
		}
		return json;
		
	}
	
	@RequestMapping(value = "/supOpinionAut", method = RequestMethod.GET)
	public Object supOpinionAut(@Param("taskid")String taskid,String taskName) throws Exception{
		ModelAndView mv = new ModelAndView();

		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supOpinion_id", bizId);
		List<Map<String, Object>> data= supOpinionService.getData(map);
		mv.addObject("data", data.get(0));
		
	/*	String  fields="a.username"; 
		String  where="uc_user a JOIN uc_role b on a.role_id=b.role_id WHERE b.role_name='监察中心经理'"; 
        Map<String, Object> userlist=userService.queryUserById(fields, where);
		mv.addAllObjects(userlist);*/
		
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("manage/supOpinionAut");
		
		return mv;
		
		
	}
	@RequestMapping(value = "/sava_opinionAut", method = RequestMethod.POST)
	@ResponseBody
	public Object  sava_opinionAut(HttpServletRequest request) {
	 Map<String, Object> json=new HashMap<String, Object>();
		try {
		supOpinionService.sava_opinionAut(request);
		json.put("Success", true);
		json.put("Msg", "已审核!");
	} catch (Exception e) {
		e.printStackTrace();
		json.put("Success", false);
		json.put("Msg", "程序出错,请联系技术员");	
		}
		return json;
	}
	
	
	
	@RequestMapping(value = "/noticeList", method = RequestMethod.GET)
	public ModelAndView noticeList() {
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("manage/noticeList");
		return mv;
		
	}
	
	

	@RequestMapping(value = "/noticeLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> noticeList(HttpServletRequest request) throws Exception{
		
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));

		StringBuilder sbf = new StringBuilder();// 拼接sql

		StringBuilder fields = new StringBuilder();// 需要显示字段
		fields.append(" notice.notice_id,notice.start_time,notice.`status`,us.username");

		sbf.append(" manage_notice notice JOIN uc_user us on notice.user_id=us.userid where 1=1");
		String username=request.getParameter("username");
		if (StringUtils.hasText(username)) {
			 sbf.append(" and us.username like '%"+username+"%'");
		 }
		   
		List<Map<String, Object>> data=noticeService.noticeList(pageIndex, pageSize, record, fields.toString(), sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	
		
	}
	
	
	

	@RequestMapping(value = "/noticeListView", method = RequestMethod.GET)
	public ModelAndView noticeListView() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("manage/noticeListView");
		return mv;
	   }

	@RequestMapping(value = "/noticeListViews", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> noticeListView(HttpServletRequest request) throws Exception{
		
		Ref<Integer> record = new Ref<Integer>();
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		
		User user=(User) request.getSession().getAttribute("loginUser");
		
		StringBuilder sbf = new StringBuilder();// 拼接sql
		
		String  string="uc_user a JOIN uc_center b on a.center_id = b.center_id";
		string +=" JOIN uc_company c on b.center_companyId=c.company_id";
		string +=" WHERE a.userid="+user.getUserid();

		String  field=" c.company_id";
		Map<String, Object> maps=userService.queryUserById(field, string);
		String company_id=maps.get("company_id").toString();
		StringBuilder fields = new StringBuilder();// 需要显示字段
		fields.append(" a.notice_id,a.start_time,a.`status`,b.username");
		sbf.append(" manage_notice a JOIN uc_user b on a.user_id=b.userid where 1=1");
		sbf.append(" and a.`status`=3");
		sbf.append(" and locate('"+company_id+"', a.company_id)>0 order by a.notice_id DESC");
		List<Map<String, Object>> data=noticeService.noticeList(page, pageSize, record, fields.toString(), sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	
		
	}
	
	
	
	
	
	
	@RequestMapping(value = "/noticeNew", method = RequestMethod.GET)
	public ModelAndView noticeview() throws Exception {
		ModelAndView mv = new ModelAndView();
		String fields=" *";
		String where=" uc_company";
		List<Map<String, Object>> masdList=userService.queryId(fields, where);
		mv.addObject("masdList", masdList);
		
		 SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
       String start_time=df.format(new java.util.Date());// new Date()为获取当前系统时间
		String field="COUNT(1) as notice_id";
		String wheres=" manage_notice a where a.start_time LIKE '"+start_time+"%'";
		Map<String, Object> notice=	userService.queryUserById(field, wheres);
		
		String count=notice.get("notice_id").toString();
		//Object startForm = formService.getRenderedStartForm("ownHeadView:1:542509");
		 List<?>  activityList= activitiService.getActivityImpl("ownHeadView:4:532559");//获取节点
		int sum=0;
		if (count.length()>2){
		
			count=(sum+1)+"";
		}else if(count.length()>1){
			sum=Integer.valueOf(count)+1;
			count="0"+sum;
		}else {
			sum=Integer.valueOf(count)+1;
			count="00"+sum;
		}
        mv.addObject("count", count);
         mv.addObject("activityList", activityList);
		mv.addObject("masdList", masdList);		
		mv.setViewName("manage/noticeNew");
		return mv;
	
	}
	
	//添加公告
	@RequestMapping(value = "/add_Notice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNotice(HttpServletRequest request) throws Exception{
		Map<String, Object> json=new HashMap<String, Object>();
      try {
    	  
	    noticeService.savaNotice(request);
		json.put("Success", true);
		json.put("Msg", "流程已启动");
	} catch (Exception e) {
		json.put("Msg", "保存失败");
		json.put("Success", false);
	
    }
	    	
		return json;
	}
	  
	/***
	 * 
	 * 审核页面
	 * */
	@RequestMapping(value = "/notice_view{taskid}", method = RequestMethod.GET)
	public ModelAndView notice_audit(HttpServletRequest request) throws Exception{
		
		ModelAndView mv=new ModelAndView();
		String taskid = request.getParameter("taskid");// 获取任务id
		String taskName = request.getParameter("taskName");
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
	
	
		   Object renderedTaskForm = formService.getRenderedTaskForm(taskid); // 获取表单
		String fields=" * ";
		String where =" manage_notice where notice_id="+bizId;
	    Map<String, Object> notice=	userService.queryUserById(fields, where);

		mv.addAllObjects(notice);
		mv.addObject("renderedTaskForm", renderedTaskForm);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.setViewName("manage/notice_audit");
		return mv;	
	}
	
	
	/**
	 *   通知公告审核
	 * 
	 * */
	@RequestMapping(value = "/notice_pass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> notice_pass(HttpServletRequest request) {
		Map<String, Object> json=new HashMap<String, Object>();
		try {
			noticeService.notice_pass(request);
			    json.put("Success", true);
				json.put("Msg","已审核");
			} catch (Exception ex) {
				ex.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错");
			}
			return json;

	}
	@RequestMapping(value = "/updateNot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNot(HttpServletRequest request){
		Map<String, Object> json=new HashMap<String, Object>();
		try {
	
		String content=request.getParameter("content");
		String taskid=request.getParameter("taskid");
        String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		Notice notice=new Notice();
		notice.setNotice_id(Integer.valueOf(bizId));
		notice.setNotice_content(content);
		noticeService.updete(notice);	
		    json.put("Success", true);
			json.put("Msg", "修改内容成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错");
		}
		return json;
				
	}
	
	/**
	 *  删除公告的同时
	 *  删除流程
	 * 
	 * */
	@RequestMapping(value = "/deleteNotice", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteNotice(String bizId) {
		JSONObject json=new JSONObject();
		try {
	
		  String beyId="notice_view";
		  String where=" manage_notice where notice_id ="+bizId;
		noticeService.delete(where);
     	activitiService.deleteRecord(bizId, beyId);
                json.put("Success", true);
     			json.put("Msg", "已删除");
     	} catch (Exception e) {
     			e.printStackTrace();
     			json.put("Success", false);
     			json.put("Msg", "程序出错");
	  }
		return json;
	}
	
	/**
	 * 查看公告
	 * 
	 * */
	@RequestMapping(value = "/noticeviewss", method = RequestMethod.GET)
	 public ModelAndView noticeviewss(HttpServletRequest request) throws Exception{
		ModelAndView mv=new ModelAndView();
		
	  String bizId =request.getParameter("bizId");
	  String fields=" * ";
	  String where =" manage_notice where notice_id="+bizId;
        Map<String, Object> notice=	userService.queryUserById(fields, where);
	   mv.addAllObjects(notice);
		mv.setViewName("manage/noticeview");
		
		return mv;
		
	}
}
