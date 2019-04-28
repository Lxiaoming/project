package com.casd.controller.personManagem;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.SendmailUtil;
import com.casd.dao.hr.HrContractDao;
import com.casd.dao.hr.HrSalaryDao;
import com.casd.dao.hr.HregisterDao;
import com.casd.dao.hr.HtrainingrecordDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.tree.Menu;
import com.casd.entity.uc.Company;
import com.casd.entity.uc.Role;
import com.casd.entity.uc.User;
import com.casd.service.flow.FlowService;
import com.casd.service.hr.HregisterService;
import com.casd.service.uc.OrgService;
import com.casd.service.uc.RoleService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class PmHomeController {

	// private Mail mail;
	@Autowired
	private FlowService flowService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private HregisterDao hregisterDao;
	@Autowired
	private HtrainingrecordDao htrainingrecordDao;
	@Autowired
	private HrSalaryDao hrSalaryDao;
	@Autowired
	private HrContractDao hrContractDao;
	@Autowired
	private HregisterService hregisterService;

	
	@Autowired
	private OrgService orgService;
	
	
	@Autowired
	private UserDao userDao;

	/**
	 * 人事首页
	 */
	@RequestMapping(value = "/pmHome", method = RequestMethod.GET)
	public ModelAndView pmHome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/pmHome");
		return mv;
	}

	/**
	 * 发送邮件页面跳转
	 */

	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public ModelAndView sendEmail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/sendEmail");
		return mv;
	}

	/**
	 * 邮件发送
	 */

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	@ResponseBody
	public String email(HttpServletRequest request, Model model) {
		String receive = request.getParameter("receive");
		String theme = request.getParameter("theme");
		String content = request.getParameter("content");

		SendmailUtil sendmailUtil = null;
		try {
			sendmailUtil = (SendmailUtil) Class.forName(
					"com.casd.controller.web.utils.SendmailUtil").newInstance();
			sendmailUtil.doSendHtmlEmail(theme, content, receive);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

	@RequestMapping(value = "personalRecords", method = RequestMethod.GET)
	public ModelAndView personalRecords( HttpServletRequest request)
			throws Exception {
		String cid = request.getParameter("cid");
		String department = request.getParameter("department");
		JSONObject map = new JSONObject();
		ModelAndView mv = new ModelAndView();
		Map<String, Object> param = new HashMap<String, Object>();

		// 查看角色
		StringBuffer sbf = new StringBuffer();
		String fields = "b.role_id,b.role_name";
		sbf.append(" uc_role b");
		List<Map<String, Object>> roles = roleService.seleroleById(fields,
				sbf.toString());
		mv.addObject("roles", roles);

		// 根据用户userid查询用户信息
		if (cid != null) {
			// 表头
			String field = " *";

			sbf.delete(0, sbf.length());
			//sbf.append(" `uc_user` where 1=1");
			
			sbf.append(" uc_user us");
			sbf.append(" LEFT JOIN uc_center centet on us.center_id= centet.center_id");
			sbf.append(" LEFT JOIN uc_company company  ON centet.center_companyId=company.company_id");
			sbf.append(" LEFT JOIN uc_department dep ON us.department = dep.department_id  ");
			sbf.append(" where 1=1");
			sbf.append(" and us.userid="+cid+"");
			if(department!=""){
				sbf.append("  and dep.department_id="+department);
			}
			
			Map<String, Object> data = userService.queryUserById(field, sbf.toString());
			
			mv.addObject("pro", data);

			StringBuilder sBuilder = new StringBuilder();
			JSONObject jsonObject = new JSONObject();

			// 公司职员变更
			param.put("fields", "*");
			sBuilder.append(" hr_register h where 1=1 and user_id=" + cid);
			
			
			param.put("where", sBuilder.toString());
			List<Map<String, Object>> listregister = hregisterDao
					.selectHregister(param);
			jsonObject.put("rows", listregister);
			map.put("rows1", jsonObject);

			// 转正
			sBuilder.delete(0, sBuilder.length());
			
			  

			sBuilder.append(" uc_user a join uc_become b on a.userid=b.user_id");
			sBuilder.append(" where b.user_id="+cid);
	        	List<Map<String, Object>> listrecord=userService.queryId("b.*", sBuilder.toString());
			
		
			jsonObject.clear();
			jsonObject.put("rows", listrecord);
			map.put("rows2", jsonObject);

			// 薪资调整记录
			sBuilder.delete(0, sBuilder.length());
			param.put("fields", "*");
			sBuilder.append(" hr_salary  h where 1=1 and user_id=" + cid);
			param.put("where", sBuilder.toString());
			List<Map<String, Object>> listSalary = hrSalaryDao
					.selectSalary(param);
			jsonObject.clear();
			jsonObject.put("rows", listSalary);
			map.put("rows3", jsonObject);

			// 合同期限表
			sBuilder.delete(0, sBuilder.length());// 清空StringBuilder
			sBuilder.append(" hr_contract userid where user_id=" + cid);
			param.put("fields", "out_stime,start_time,remark,pact_id");
			param.put("where", sBuilder.toString());
			List<Map<String, Object>> paramss = hrContractDao
					.selectgetAll(param);
			jsonObject.clear();
			jsonObject.put("rows", paramss);
			map.put("rows4", jsonObject);
						
			// 辞职
			sBuilder.delete(0, sBuilder.length());// 清空StringBuilder
			sBuilder.append(" hr_resign  where  hr_resign_userid =" + cid);
			List<Map<String, Object>> resign=userService.queryId("*", sBuilder.toString());
			jsonObject.clear();
			jsonObject.put("rows", resign);
			map.put("resignRows", jsonObject);
			
	     	}else {
			Map<String, Object> data=new HashMap<String, Object>();
			data.put("userid", 0);
			data.put("sex", 0);
			data.put("age", 0);
			data.put("marital_status", 0);
			data.put("education", 0);
			data.put("level", 0);
			data.put("blood", 0);
			data.put("weight", 0);
			data.put("user_num", 0);
			data.put("status", "0");
			mv.addObject("pro", data);
		}
		mv.addObject("rows", map);
		mv.addObject("save", request.getParameter("save"));
		mv.setViewName("personManagem/personalRecords");
		return mv;
	}
	// 人事档案保存
	//@SuppressWarnings("null")
	@RequestMapping(value = "savePersonalRecords", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject savePersonalRecords(HttpServletRequest request,User user)
			throws Exception {
		
		JSONArray myJsonArray = JSONArray.fromObject(request
				.getParameter("rows"));
	/*	JSONArray myJsonArray2 = JSONArray.fromObject(request
				.getParameter("rows2"));*/
		JSONArray myJsonArray3 = JSONArray.fromObject(request
				.getParameter("rows3"));
		JSONArray myJsonArray4 = JSONArray.fromObject(request
				.getParameter("rows4"));
		
		JSONObject erro=hregisterService.savexistence(user, myJsonArray,/* myJsonArray2,*/
				myJsonArray3, myJsonArray4);
		return erro;
		
	}

	@RequestMapping(value = "/pmuserList", method = RequestMethod.GET)
	public ModelAndView userList(String type) {
		ModelAndView mv = new ModelAndView();
		if("add".equals(type)){
			mv.setViewName("uc/personalUser");
		}else {
			mv.setViewName("personManagem/pmuserList");
		}
		   return mv;
		
	}

	/**
	 * 用户列表信息
	 * 
	 * */
	@RequestMapping(value = "/pmuserList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Ref<Integer> record = new Ref<Integer>();
		StringBuilder sbf = new StringBuilder();
		StringBuilder fields = new StringBuilder();

		// 显示字段
		fields.append(" * ");	
		Map<String, Object> json = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		String username = request.getParameter("username");
		String userid = request.getParameter("userid");
		String department_name = request.getParameter("department_name");
		String center_name = request.getParameter("center_name");
		String company_name = request.getParameter("company_name");
		int status = Integer.valueOf(request.getParameter("status"));
		
		sbf.append(" ( ");
		sbf.append(" SELECT ");
		sbf.append(" newtable.* ,(select dep.department_name FROM uc_department dep where dep.department_id=newtable.department )   as  department_name from ");
		sbf.append(" ( ");
		sbf.append(" select  a.user_num,a.userid,a.username,a.phone_number,a.sex,a.email,a.user_card,a.card_address,a.status,a.department,c.center_name,d.company_name,d.company_id ");
		sbf.append(" from uc_user a ");
		sbf.append(" LEFT JOIN uc_center c ON a.center_id = c.center_id ");
		sbf.append(" LEFT JOIN uc_department b ON c.center_id = b.department_centerId ");
		sbf.append(" LEFT JOIN uc_company d ON d.company_id = c.center_companyId ");
		sbf.append(" GROUP BY a.userid ORDER BY a.user_num asc  ");
		sbf.append(" ) newtable ) secedtable ");
		sbf.append(" where 1=1 and company_id!=17 ");
		
		
		if(!company_name.equals("诚安时代（1）")){
			if (status != 0) {
				sbf.append(" and `status`="+status+"");
			}
			if (StringUtils.hasText(company_name)) {
				sbf.append(" and company_name = '" + company_name + "'");
			}
			if (StringUtils.hasText(center_name)) {
				sbf.append(" and center_name = '" + center_name + "'");
			}
			if (StringUtils.hasText(department_name)) {
				sbf.append(" and department_name = '" + department_name + "'");
			}
			
			if (StringUtils.hasText(username)) {
				sbf.append(" and username like '%" + username + "%'");
			}
			if (StringUtils.hasText(userid)) {
				sbf.append(" and userid like '%" + userid + "%'");
			}
		}
		List<Map<String, Object>> data = userService.userList(pageIndex,
				pageSize, record, sbf.toString(), fields.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		
		return json;
		
	}
	
	@RequestMapping(value = "/personalUser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> personalUser(Integer limit,Integer page,HttpServletRequest request) throws Exception{
		Ref<Integer> record = new Ref<Integer>();
		page = page==null ? 1: page;
	       page=page-1;
		
	       User loginUser = (User) request.getSession().getAttribute("loginUser");
			int userId = loginUser.getUserid();
		StringBuilder sbf = new StringBuilder();
		StringBuilder fields = new StringBuilder();

		// 显示字段
		fields.append("us.user_num,us.userid,us.username,us.phone_number,us.sex,");
		fields.append("us.email,us.user_card,us.card_address,us.`STATUS`,us.department,");
		fields.append("center.center_name,d.company_name,d.company_id");
		
		sbf.append(" uc_user us");
		sbf.append(" LEFT JOIN uc_center center ON us.center_id =center.center_id");
		sbf.append(" LEFT JOIN uc_department b ON us.department = b.department_id");
		sbf.append(" LEFT JOIN uc_company d ON d.company_id =center.center_companyId");
		sbf.append(" where us.userid="+userId);
		List<Map<String, Object>> data = userService.userList(page,
				limit, record, sbf.toString(), fields.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
     	return result;
		
		
		
	}
	
	
	

	// 删除用户资料
	@RequestMapping(value = "/deleteContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteContract(HttpServletRequest request,
			@RequestParam("type") String type) {
		Map<String, Object> josn = new HashMap<String, Object>();

		String cid = request.getParameter("cid");
		cid = cid.substring(1);
		String[] ids = cid.split("]");
		StringBuffer sbf = new StringBuffer();
		// 删除合同记录
		if ("contract".equals(type)) {
			sbf.append(" where pact_id=" + ids[0]);
			josn.put("where", sbf.toString());
			hregisterService.deleteContract(josn);
			// 删除职位变更记录
		} else if ("hregister".equals(type)) {
			sbf.append(" where job_id=" + ids[0]);
			josn.put("where", sbf.toString());
			hregisterService.deleteHregister(josn);
			// 薪资调整记录
		} else if ("salary".equals(type)) {
			sbf.append(" where salary_id=" + ids[0]);
			josn.put("where", sbf.toString());
			hregisterService.deleteHrSalary(josn);

			// 删除培训记录
		} else if ("record".equals(type)) {
			sbf.append(" where id=" + ids[0]);
			josn.put("where", sbf.toString());
			hregisterService.deleteHrecord(josn);
		}
		return null;
	}
	
	
	/**
	 * 生成树
	*/
	@RequestMapping(value="/userTreeList" , method = RequestMethod.GET)
	@ResponseBody
	public void userTreeList(HttpServletResponse response){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			//公司
		    map.put("where", "uc_company WHERE company_id !=17");
		    map.put("what", "company_id,company_name");
			List<Map<String, Object>> companies=orgService.queryData(map);
			//中心
			map.put("where", "uc_center");
			map.put("what", "center_id,center_name,center_companyId");
			List<Map<String, Object>> centers=orgService.queryData(map);
			
			//部门
			map.put("where", "uc_department");
			map.put("what", "department_id,department_name,department_centerId");
			List<Map<String, Object>> departments=orgService.queryData(map);
			
			JSONArray arr = new JSONArray();
			// 一级菜单
			for (int i = 0; i < companies.size(); i++) {
					JSONObject node = new JSONObject();
					int company_id = Integer.valueOf(companies.get(i).get("company_id").toString());
					node.put("id", company_id);
					node.put("name", companies.get(i).get("company_name"));
					JSONArray childrenArr = new JSONArray();
					// 二级菜单
					for (int j = 0; j < centers.size(); j++) {
							if(company_id==Integer.valueOf(centers.get(j).get("center_companyId").toString())){
									int center_id = Integer.valueOf(centers.get(j).get("center_id").toString());
									JSONObject children = new JSONObject();
									children.put("id", center_id);
									children.put("name",centers.get(j).get("center_name"));
									//三级
									JSONArray threeArr = new JSONArray();
									for (int k = 0; k < departments.size(); k++) {
										if(center_id==Integer.valueOf(departments.get(k).get("department_centerId").toString())){
												JSONObject threeChildren = new JSONObject();
												threeChildren.put("id", departments.get(k).get("department_id"));
												threeChildren.put("name", departments.get(k).get("department_name"));
												threeArr.add(threeChildren) ;
										}
									}
									children.put("children",threeArr);
									childrenArr.add(children) ;
								}
							}
					
					node.put("children", childrenArr);
					arr.add(node);
			}
			response.setCharacterEncoding("UTF-8");
		
			response.getWriter().write(arr.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
