package com.casd.controller.ownCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.FieldPersonnel;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.ownCenter.FieldPersonnelSevrice;
import com.casd.service.uc.CostappService;
import com.casd.service.uc.UserService;
import com.itextpdf.awt.geom.misc.RenderingHints.Key;


@Controller
@RequestMapping("/admin")
public class FieldPersonneController {
	
	@Autowired
	private FieldPersonnelSevrice fPersonnelSevrice;
	@Autowired
	private CostappService costappService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private SupOpinionService  supOpinionService;
	
	@Autowired
	private FormService  formService;
	
	
	/** @author Administrator
	 *  所以外勤申请数据列表
	 * */
	@RequestMapping(value = "findFieldpList", method = RequestMethod.GET)
	public String findFieldpList() {		
		return "ownCenter/findFieldpList";
     	}

	@RequestMapping(value = "/findFieldpLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> templatelList(HttpServletRequest request)
			throws Exception {
		
		User user = (User) request.getSession().getAttribute("loginUser");
		Ref<Integer> record = new Ref<Integer>();
	
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
	
	
		String username = request.getParameter("username");
		String field_personnel_company = request.getParameter("field_personnel_company");
		String fields = " ofp.*,us.username";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" own_field_personnel ofp");
		sbf.append(" LEFT JOIN uc_user us ON ofp.field_personnel_userid = us.userid");
		sbf.append(" where ");
		sbf.append(" ofp.field_personnel_userid='"+user.getUserid()+"' ");
		if(StringUtils.isNoneBlank(username)){
			sbf.append(" and us.username like '%"+username+"%'");
		}
		if(StringUtils.isNoneBlank(field_personnel_company)){
			sbf.append(" and ofp.field_personnel_company like '%"+field_personnel_company+"%'");
		}
	
	    sbf.append(" ORDER BY ofp.field_personnel_id DESC");
		List<Map<String, Object>> ListData = fPersonnelSevrice.findFieldpList(page, pageSize, record, fields, sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(ListData);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	}
	
	
	/**  
	 *  跳转新增页面
	 * @throws Exception 
	 * */
	 @RequestMapping(value = "newFieldPersonnel", method = RequestMethod.GET)
	   public ModelAndView newFieldPersonnel(HttpServletRequest request) throws Exception {
		ModelAndView mv=new ModelAndView();
		
		String biz=request.getParameter("biz");
		String fields = "";
		StringBuffer sbf=new StringBuffer();
		Map<String, Object> dataMap= null;
	
		Object startForm=null;
		String beyId = "findFieldpView"; // 流程实例key 请勿改动
		List<Map<String, Object>> historyList = activitiService.viewHisComments(String.valueOf(biz), beyId);
		JSONArray jsonObject = new JSONArray();
		
		 //添加时查询
		if (biz.equals("add")) {

			User loginUser = (User) request.getSession().getAttribute("loginUser");
			int userid=loginUser.getUserid();
			fields+="us.userid,us.username,role.role_name,company.company_name";
			sbf.append(" uc_user us LEFT JOIN  uc_center center on us.center_id=center.center_id");
			sbf.append(" LEFT JOIN uc_role role on role.role_id=us.role_id");
			sbf.append(" LEFT JOIN uc_company company  on company.company_id=center.center_companyId");
			sbf.append(" WHERE us.userid="+userid);
			
		    dataMap= userService.queryUserById(fields, sbf.toString());
			startForm = formService.getRenderedStartForm("findFieldpView:1:532536");
			mv.addObject("taskName", "申请人");
		 //编辑时查询
		}else{
			fields +="ofp.*,us.username,us.userid,ur.role_name,company.company_name";
			sbf.append("own_field_personnel ofp");
			sbf.append(" LEFT JOIN uc_user us on ofp.field_personnel_userid=us.userid");
			sbf.append(" LEFT JOIN uc_role ur on us.role_id=ur.role_id");
			sbf.append(" LEFT JOIN uc_center center ON center.center_id= us.center_id");
			sbf.append(" LEFT JOIN uc_company company on  center.center_companyId=company.company_id");
			sbf.append(" where ofp.field_personnel_id="+biz);
			dataMap= userService.queryUserById(fields, sbf.toString());
			jsonObject = JSONArray.fromObject(historyList);
		 }
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		String[] temp = new String[] { "申请人", "人力资源中心"};
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
        mv.addAllObjects(dataMap);
        mv.addObject("history", jsonObject);
        mv.addObject("startForm",startForm);
		mv.setViewName("ownCenter/newFieldPersonnel");
	
		return mv;
	}
	
	 
	    /**
	     * 外勤申请数据提交
	     * 
	     * */
	  @RequestMapping(value = "/addFieldPersonne", method = RequestMethod.POST)
	  @ResponseBody
	   public Map<String, Object> addFieldPersonne(FieldPersonnel fpl,String username) {
		
		  Map<String, Object> json=new HashMap<String, Object>(); 
		 try {
	           
	            fPersonnelSevrice.addFieldPersonne(fpl,username);
				json.put("Success", true);
				json.put("Msg", "提交成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "保存失败");
	        }
		        return json;
	     }
	  
	     /**
	     * 删除外勤数据
	     * 
	     * */
	  @RequestMapping(value = "/deleteFieldPersonne", method = RequestMethod.POST)
	  @ResponseBody
	   public Map<String, Object> deleteFieldPersonne(String biz) {
		  Map<String, Object> json=new HashMap<String, Object>();
		  try {
			  String where=" where field_personnel_id="+biz;
			 fPersonnelSevrice.deleteFieldPsl(where);
			 String beyId="findFieldpView";
            activitiService.deleteRecord(biz, beyId);
				json.put("Success", true);
				json.put("Msg", "已删除");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "删除失败");
	        }
	     	return json;

	  }
	  
	  @RequestMapping(value = "/findFieldpView{taskid}", method = RequestMethod.GET)
	  public ModelAndView findFieldpAudit(String taskid,String taskName) throws Exception {
			ModelAndView mv=new ModelAndView();
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
           
			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
			String fields = "";
			StringBuffer sbf=new StringBuffer();	
			fields +="ofp.*,us.username,us.userid,ur.role_name,company.company_name";
			sbf.append("own_field_personnel ofp");
			sbf.append(" LEFT JOIN uc_user us on ofp.field_personnel_userid=us.userid");
			sbf.append(" LEFT JOIN uc_role ur on us.role_id=ur.role_id");
			sbf.append(" LEFT JOIN uc_center center ON center.center_id= us.center_id");
			sbf.append(" LEFT JOIN uc_company company on  center.center_companyId=company.company_id");
			sbf.append(" where ofp.field_personnel_id="+bizId);
		    Map<String, Object>	dataMap= userService.queryUserById(fields, sbf.toString());
		    
		    Object renderedTaskForm = formService.getRenderedTaskForm(taskid); // 获取表单
		    
		    String beyId = "findFieldpView"; // 流程实例key 请勿改动
			Object historyList = activitiService.viewHisComments(String.valueOf(bizId), beyId);
			JSONArray jsonObject = JSONArray.fromObject(historyList);
			mv.addObject("history", jsonObject);
			
			List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
			String[] temp = new String[] { "申请人", "人力资源中心"};
			for (String t : temp) {
				Map<String, Object> activityMap = new HashMap<String, Object>();
				activityMap.put("name", t);
				activityList.add(activityMap);
			}
			mv.addObject("activityList", activityList);
			
		    mv.addAllObjects(dataMap);
			mv.addObject("taskid", taskid);
			mv.addObject("taskName", taskName);
			mv.addObject("startForm", renderedTaskForm);	
		   mv.setViewName("ownCenter/findFieldpAudit");
		   return mv;
		
	}
	  
	  /**
	   * 外出审核
	   * */
	  @RequestMapping(value = "/fieldPersonnelPass", method = RequestMethod.POST)
	  @ResponseBody
	  public Map<String, Object> fieldPersonnelPass(String taskid) {
		  Map<String, Object> json=new HashMap<String, Object>();
	          try {
		          fPersonnelSevrice.fieldPersonnelPass(taskid);
				json.put("Success", true);
				json.put("Msg", "已审核!");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");
			}
		   return json;
	     }
	 /**
	  * 查询单据
	  * 单据未结束时 不可在申请外出
	  * */
	  @RequestMapping(value = "/findaddofp", method = RequestMethod.GET)
	  @ResponseBody
	  public Map<String, Object> findaddofp(HttpServletRequest request) throws Exception {
		  User  user = (User) request.getSession().getAttribute("loginUser");
		  Map<String, Object> json=new HashMap<String, Object>();
		  try {
		  String finde="ofp.field_personnel_id";
		  StringBuilder sbd=new StringBuilder();
		  sbd.append("own_field_personnel ofp");
		  sbd.append(" JOIN uc_user uc ON ofp.field_personnel_userid=uc.userid");
		  sbd.append(" where ofp.field_personnel_status IN (0,2)");
		  sbd.append(" AND ofp.field_personnel_userid="+user.getUserid());   
		  List<Map<String, Object>> ofpList=userService.queryId(finde, sbd.toString());
		  
		    json.put("ofpList", ofpList.size());
			json.put("Success", true);
			json.put("Msg", "已审核!");
		  } catch (Exception e) {
			   e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");	
			}
		return json;
		
	}
	  /**
	   * 取消外请申请
	   * 同时结束流程
	   * 
	   * */
	  @RequestMapping(value = "/cancelofps", method = RequestMethod.POST)
	  @ResponseBody
	  public Map<String, Object> cancelofps(String bizId) throws Exception {
		  Map<String, Object> json=new HashMap<String, Object>();
     try {
		  FieldPersonnel fPl=new FieldPersonnel();
		  fPl.setField_personnel_status(4);
		  fPl.setField_personnel_id(Integer.parseInt(bizId));
		  fPersonnelSevrice.updateFieldPsl(fPl);
		  
		  String beyId="findFieldpView";
          activitiService.deleteprocessInstance(bizId, beyId);  

             json.put("Success", true);
			 json.put("Msg", "外请申请已取消!");
		    } catch (Exception e) {
			   e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");	
			}
		return json;
	  }
	  
	 
	  
}
