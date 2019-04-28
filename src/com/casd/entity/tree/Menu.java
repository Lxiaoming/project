package com.casd.entity.tree;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private Integer id; 			//菜单id 
	private String menu_name;	//菜单名
	private Integer permit_id; 		//权限id 
	private Integer parent_id; 		//父菜单id
	private String menu_url;	//菜单url
	private Integer order;			//菜单顺序
	private Integer ismenu;			//是否菜单项
	private String program_code;  //权限标签 code

	
	
    private List<Menu> childMenus;// 子菜单
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public Integer getPermit_id() {
		return permit_id;
	}
	public void setPermit_id(Integer permit_id) {
		this.permit_id = permit_id;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public List<Menu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}
	public Integer getIsmenu() {
		return ismenu;
	}
	public void setIsmenu(Integer ismenu) {
		this.ismenu = ismenu;
	}
	public String getProgram_code() {
		return program_code;
	}
	public void setProgram_code(String program_code) {
		this.program_code = program_code;
	}

	
}
