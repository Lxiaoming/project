package com.casd.controller.uc;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.tree.Menu;
import com.casd.entity.uc.Role;
import com.casd.entity.uc.User;
import com.casd.service.tree.MenuService;
import com.casd.service.uc.RoleService;
import com.casd.service.uc.UserService;


@Controller
@RequestMapping("/admin")
public class RoleController {
	
	

	/**
	 * 菜单列表
	*/
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/roleList" , method = RequestMethod.GET)
	public ModelAndView menuList(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("uc/roleList");
		return mv;
		
	 }
	
	/**
	 * 菜单信息表查询
	 * 分页
	 * 模糊查询
	 * */
	@RequestMapping(value="/roleLists" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> roleLists(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));

		StringBuffer sbf=new StringBuffer();
		sbf.append(" uc_role where 1=1");

		String searchId=request.getParameter("roleid");
		String searchName=request.getParameter("role_name");
		if (StringUtils.hasText(searchId)) {
			sbf.append(" and role_id='"+searchId+"'");
		}
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and role_name like'%"+searchName+"%'");
		}
		List<Map<String, Object>> data=roleService.roleList(pageIndex, pageSize, record, sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	}
	
	
	/**
	 * 初始化编辑,新增界面 
	*/
	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public ModelAndView editRole(Model model, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> json=new HashMap<String, Object>();
		String cid = request.getParameter("cid");
		if (cid != null) {
			map.put("where", "uc_role ro left join uc_role_menu me on me.role_id=ro.role_id  where ro.role_id="+cid+"");
		    map.put("fields", "ro.role_id,ro.role_name,ro.state,me.id");
		    json = roleService.findById(map);
			mv.addObject("role", json);
		}
		mv.setViewName("uc/editRole");
		return mv;
	}
	
	
	/**
	 * 生成树
	*/
	@RequestMapping(value="/menuTreeList" , method = RequestMethod.POST)
	@ResponseBody
	public void getMenuTree(Role role, HttpServletRequest request, HttpServletResponse response ){
		try {
			// 查询所有的菜单
			Map<String, Object> map=new HashMap<String, Object>();
		    map.put("where", "uc_menu");
		    map.put("what", "*");
			List<Menu> allMenu=menuService.queryMenuList(map);
			String roleName = request.getParameter("roleName");
			StringBuilder sbf=new StringBuilder();
			StringBuilder sb=new StringBuilder();

			sbf.append("uc_role_menu rm ");
	        sbf.append("LEFT JOIN uc_role role on rm.role_id=role.role_id ");
			sbf.append("where role.role_name ='"+roleName+"'");
		    map.put("where", sbf.toString());
		    map.put("what", "rm.menu_id");
		    String menu_id=menuService.findMenu(map);
		    
		    sb.append("uc_menu where id in ("+menu_id+")");
		    map.put("where", sb.toString());
		    map.put("what", "*");
			List<Menu> owmMenu = menuService.queryMenuList(map);
			
			
			List<Integer> listIds = new ArrayList<Integer>();
			for(Menu menu : owmMenu){
				listIds.add(menu.getId());
			}
			
			
			
			JSONArray arr = new JSONArray();
			// 遍历角色类型拥有的一级菜单
			for (Menu parentMenu : allMenu) {
				if(parentMenu.getParent_id()==-1){
					JSONObject node = new JSONObject();
					node.put("id", parentMenu.getId());
					node.put("name", parentMenu.getMenu_name());
					if(listIds.contains(parentMenu.getId())){
						node.put("checked", true);
					}
					
				
					JSONArray childrenArr = new JSONArray();
					// 遍历角色类型拥有的二级菜单
					for (Menu childrenMenu : allMenu) {
						if(childrenMenu.getParent_id()!=-1){
							JSONObject children = new JSONObject();
							if(parentMenu.getId()==childrenMenu.getParent_id()){
								children.put("id", childrenMenu.getId());
								children.put("name", childrenMenu.getMenu_name());
								// 角色中拥有的二级菜单在树上选中
								if(listIds.contains(childrenMenu.getId())){
										children.put("checked", true);
								}
								
								//三级
								JSONArray threeArr = new JSONArray();
								for (Menu threeMenu: allMenu) {
									JSONObject threeChildren = new JSONObject();
									if(childrenMenu.getId().equals(threeMenu.getParent_id())){
										threeChildren.put("id", threeMenu.getId());
										threeChildren.put("name", threeMenu.getMenu_name());
										if(listIds.contains(threeMenu.getId())){
											threeChildren.put("checked", true);
										}
										threeArr.add(threeChildren) ;
									}
								}
								children.put("children",threeArr);
								
								
								childrenArr.add(children) ;
							}
						}
					}
					node.put("children", childrenArr);
					arr.add(node);
				}
			}
			response.setCharacterEncoding("UTF-8");
			System.out.println(arr.toString());
			response.getWriter().write(arr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 新增修改
	*/
	
	@RequestMapping(value="/saveRole" , method = RequestMethod.POST)
	@ResponseBody
	public String saveRole(HttpServletRequest request, Model model) {
		String roleName = request.getParameter("roleName");
		String state = request.getParameter("state");
		String nodes = request.getParameter("nodes").isEmpty()?"0":request.getParameter("nodes");
		String menuId = request.getParameter("menuId");
		int role_id=Integer.parseInt(request.getParameter("roleId").toString().isEmpty()?"0":request.getParameter("roleId").toString());
		Map<String, Object> map=new HashMap<String, Object>();
		Role role=new Role();
		role.setRole_name(roleName);
		role.setState(state);
		role.setRole_id(role_id);
		map.put("role", role);
		map.put("nodes", nodes);
		map.put("menuId", menuId);
		roleService.addRole(map);
		return "";
	}
	
	/**
	 * 角色删除
	*/
	@RequestMapping(value="/deleRole" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleRole(HttpServletRequest request) {
		  Map<String, Object> json=new HashMap<String, Object>();
		try {
		Map<String, Object> map=new HashMap<String, Object>();
		String roleId =request.getParameter("roleId");
	
		  map.put("what",roleId); 
		
	       roleService.deleRole(map);
            json.put("Success", true);
			json.put("Msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "删除失败");
		}
		return json;
	}
	
	
}
