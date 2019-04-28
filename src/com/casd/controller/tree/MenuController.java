package com.casd.controller.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.common.Authorize;
import com.casd.controller.web.common.Authorize.ResultType;
import com.casd.controller.web.common.Authorize.RoleEnum;
import com.casd.entity.tree.Menu;
import com.casd.entity.uc.Role;
import com.casd.entity.uc.User;
import com.casd.service.tree.MenuService;
import com.casd.service.uc.UserService;

@Controller
@RequestMapping("/admin")
public class MenuController {
	@Autowired
	private TaskService taskService;

	/**
	 * 菜单加载
	 */
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String queryMenuList(HttpServletRequest request, Model model) {
		// 原始的数据
		/*
		 * Map<String, Object> map=new HashMap<String, Object>(); StringBuilder
		 * sbf=new StringBuilder(); StringBuilder sb=new StringBuilder(); User
		 * loginUser =(User) request.getSession().getAttribute("loginUser");
		 * String username = loginUser.getUsername();
		 * sbf.append("uc_role_menu rm ");
		 * sbf.append("LEFT JOIN uc_role role on rm.role_id=role.role_id ");
		 * sbf.append("LEFT JOIN uc_user uc on role.role_id=uc.role_id ");
		 * sbf.append("where uc.username='"+username+"' "); map.put("where",
		 * sbf.toString()); map.put("what", "rm.menu_id"); String
		 * menu_id=menuService.findMenu(map);
		 * 
		 * sb.append("uc_menu menu where id in ("+menu_id+
		 * ") and ismenu=1 order by menu.`order`"); map.put("where",
		 * sb.toString()); map.put("what", "*"); List<Menu> rootMenu =
		 * menuService.queryMenuList(map); // 最后的结果 List<Menu> menuList = new
		 * ArrayList<Menu>(); // 先找到所有的一级菜单 for (int i = 0; i < rootMenu.size();
		 * i++) { // 一级菜单没有parentId(数据库存为-1) if
		 * ((rootMenu.get(i).getParent_id())==-1) {
		 * menuList.add(rootMenu.get(i)); } } // 为一级菜单设置子菜单，getChild是递归调用的 for
		 * (Menu menu : menuList) { menu.setChildMenus(getChild(menu.getId(),
		 * rootMenu)); }
		 * 
		 * int count=(int)taskService.createTaskQuery()
		 * .taskCandidateUser(username) .count(); model.addAttribute("count",
		 * count);
		 * 
		 * model.addAttribute("menuList", menuList); model.addAttribute("title",
		 * "用户");
		 */

		User loginUser = (User) request.getSession().getAttribute("loginUser");
		String userid = loginUser.getUserid() + "";
		  int count = (int) taskService.createTaskQuery()
				   .taskCandidateOrAssigned(userid).active().orderByTaskId().desc().count();
		model.addAttribute("count", count);
		return "index";

	}

	/**
	 * 递归查找子菜单
	 * 
	 * @param id
	 *            当前菜单id
	 * @param rootMenu
	 *            要查找的列表
	 * @return
	 */
	private List<Menu> getChild(int id, List<Menu> rootMenu) {
		// 子菜单
		List<Menu> childList = new ArrayList<Menu>();
		for (Menu menu : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (!StringUtils.isEmpty(menu.getParent_id())) {
				if (menu.getParent_id() == id) {
					childList.add(menu);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		/*
		 * for (Menu menu : childList) {// 没有url子菜单还有子菜单 if
		 * (StringUtils.isEmpty(menu.getMenu_url())) { // 递归
		 * menu.setChildMenus(getChild(menu.getId(), rootMenu)); } } // 递归退出条件
		 * if (childList.size() == 0) { return null; }
		 */
		return childList;
	}

	/**
	 * 菜单列表
	 */
	@Authorize(code = "ARTICLE_DELE", type = ResultType.REDIRECT, role = RoleEnum.ADMIN)
	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	public ModelAndView menuList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("uc/menuList");
		return mv;

	}

	/**
	 * 菜单信息表查询 分页 模糊查询
	 * */
	@RequestMapping(value = "/menuLists", method = RequestMethod.GET)
	// @Authorize(code = "ARTICLE_DELE", type =ResultType.REDIRECT, role =
	// RoleEnum.ADMIN)
	@ResponseBody
	public Map<String, Object> menuList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));

		StringBuffer sbf = new StringBuffer();
		sbf.append(" uc_menu where 1=1");
		String searchId = request.getParameter("searchId");
		String searchName = request.getParameter("searchName");
		String parent_id = request.getParameter("parent_id");

		if (StringUtils.hasText(searchId)) {
			sbf.append(" and id='" + searchId + "'");
		}
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and menu_name like'%" + searchName + "%'");
		}
		if (StringUtils.hasText(parent_id)) {
			sbf.append(" and parent_id=" + parent_id);
		}
		sbf.append(" order by id desc");

		List<Map<String, Object>> data = menuService.menuList(pageIndex,
				pageSize, record, sbf.toString(), searchId, searchName);
		  JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;

	}

	/**
	 * 菜单新增修改
	 */

	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	@ResponseBody
	public String addMenu(HttpServletRequest request,Menu menu) {
		
		int parent_id = menu.getParent_id()==null ? -1 : menu.getParent_id();
		int order = menu.getOrder()==null ? 1 : menu.getOrder();
		menu.setParent_id(parent_id);
		menu.setOrder(order);
		
		if (menu.getId() ==null ) {
			menu.setId(0);
			menuService.addMenu(menu);
		} else {
			menuService.updateMenu(menu);
		}

		return "";
	}

	/**
	 * 菜单删除
	 */
	@RequestMapping(value = "/deleMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleMenu(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> json = new HashMap<String, Object>();
		try {
		map.put("what", id);
		map.put("where", "uc_menu");
		menuService.deleMenu(map);
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
	 * 生成树
	 */
	@RequestMapping(value = "/menuTree", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray menuTree(HttpServletRequest request,
			HttpServletResponse response) {
		// 原始的数据
		String parentId = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sbf = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		String username = loginUser.getUsername();
		sbf.append("uc_role_menu rm ");
		sbf.append("LEFT JOIN uc_role role on rm.role_id=role.role_id ");
		sbf.append("LEFT JOIN uc_user uc on role.role_id=uc.role_id ");
		sbf.append("where uc.username='" + username + "' ");
		map.put("where", sbf.toString());
		map.put("what", "rm.menu_id");
		String menu_id = menuService.findMenu(map);

		sb.append("uc_menu menu where id in (" + menu_id + ")");
		if (!parentId.equals("0")) {
			sb.append(" and  parent_id=" + parentId
					+ " and ismenu=1 order by menu.`order`");

		} else {
			sb.append(" and  parent_id=" + -1
					+ " and ismenu=1 order by menu.`order`");
		}

		map.put("where", sb.toString());
		map.put("what", "*");
		List<Menu> rootMenu = menuService.queryMenuList(map);

		JSONArray data = new JSONArray();
		for (int i = 0; i < rootMenu.size(); i++) {
			
			JSONObject menuObject = new JSONObject();
			menuObject.put("id", rootMenu.get(i).getId());
			menuObject.put("text", rootMenu.get(i).getMenu_name());
			menuObject.put("state","closed");
			
			if (!parentId.equals("0")) {
				JSONArray childern = new JSONArray();
				sb.delete(0, sb.length());
				sb.append(" uc_menu menu where id in (" + menu_id + ")");
				sb.append(" and  parent_id=" + rootMenu.get(i).getId()
						+ " and ismenu=1 order by menu.`order`");
				map.put("where", sb.toString());
				map.put("what", "*");
				List<Menu> childrenMenu = menuService.queryMenuList(map);
				for (int j = 0; j < childrenMenu.size(); j++) {
					Map<String, Object> childernMap = new HashMap<String, Object>();
					childernMap.put("id", childrenMenu.get(j).getId());
					childernMap.put("text", childrenMenu.get(j).getMenu_name());

					Map<String, Object> urlMap = new HashMap<String, Object>();
					urlMap.put("url", childrenMenu.get(j).getMenu_url());
					childernMap.put("attributes", urlMap);

					childern.add(childernMap);
				}
				menuObject.put("children", childern);
			}

			if (rootMenu.get(i).getMenu_url() != null
					&& !rootMenu.get(i).getMenu_url().equals("")) {
				Map<String, Object> urlMap = new HashMap<String, Object>();
				urlMap.put("url", rootMenu.get(i).getMenu_url());
				menuObject.put("attributes", urlMap);
			}
			data.add(menuObject);
		}
		return data;

	}

}
