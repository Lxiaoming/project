package com.casd.entity.construct;

import java.io.Serializable;
import java.sql.Date;

/**
 *    项目 construct_project_table
 * */
public class Construct implements Serializable {
	
	private int construct_project_id;				//id
	private String construct_project_name;			//项目名
	private String construct_project_addr;			//工程地址
	private String construct_project_leader;		//项目经理
	private String construct_project_leaderTel;		//项目经理联系方式
	private String construct_project_startDate;		//合同项目开始日期
	private String construct_project_endDate;		//合同项目结束日期
	private int construct_project_dep;				//所属项目部编号
	private String construct_project_smoke;			//排烟班组
	private String construct_project_eleTeam;		//电班组
	private String construct_project_waterTeam;		//水班组
	private int construct_project_status;			//状态
	private int construct_project_contractId;       //合同id
	
	//非持久
	private String total;						//总金额
	private String manage_contract_name;		//合同项目名
	private String manage_contract_address;		//合同项目地址
	private String manage_contract_firstParty;	//甲方
	private String manage_contract_startTime;	//开始时间
	private String manage_contract_endTime;		//结束日期
	private String constuct_project_dep_name;	//项目部
	
	private String constuct_project_dep_company;		//公司
	
	public String getManage_contract_name() {
		return manage_contract_name;
	}
	public void setManage_contract_name(String manage_contract_name) {
		this.manage_contract_name = manage_contract_name;
	}
	public String getManage_contract_address() {
		return manage_contract_address;
	}
	public void setManage_contract_address(String manage_contract_address) {
		this.manage_contract_address = manage_contract_address;
	}
	public String getManage_contract_firstParty() {
		return manage_contract_firstParty;
	}
	public void setManage_contract_firstParty(String manage_contract_firstParty) {
		this.manage_contract_firstParty = manage_contract_firstParty;
	}
	public String getManage_contract_startTime() {
		return manage_contract_startTime;
	}
	public void setManage_contract_startTime(String manage_contract_startTime) {
		this.manage_contract_startTime = manage_contract_startTime;
	}
	public String getManage_contract_endTime() {
		return manage_contract_endTime;
	}
	public void setManage_contract_endTime(String manage_contract_endTime) {
		this.manage_contract_endTime = manage_contract_endTime;
	}
	
	
	
	
	
	
	
	public String getConstruct_project_smoke() {
		return construct_project_smoke;
	}
	public void setConstruct_project_smoke(String construct_project_smoke) {
		this.construct_project_smoke = construct_project_smoke;
	}
	public String getConstruct_project_eleTeam() {
		return construct_project_eleTeam;
	}
	public void setConstruct_project_eleTeam(String construct_project_eleTeam) {
		this.construct_project_eleTeam = construct_project_eleTeam;
	}
	public String getConstruct_project_waterTeam() {
		return construct_project_waterTeam;
	}
	public void setConstruct_project_waterTeam(String construct_project_waterTeam) {
		this.construct_project_waterTeam = construct_project_waterTeam;
	}
	public int getConstruct_project_id() {
		return construct_project_id;
	}
	public void setConstruct_project_id(int construct_project_id) {
		this.construct_project_id = construct_project_id;
	}
	public String getConstruct_project_name() {
		return construct_project_name;
	}
	public void setConstruct_project_name(String construct_project_name) {
		this.construct_project_name = construct_project_name;
	}
	public String getConstruct_project_addr() {
		return construct_project_addr;
	}
	public void setConstruct_project_addr(String construct_project_addr) {
		this.construct_project_addr = construct_project_addr;
	}
	public String getConstruct_project_leader() {
		return construct_project_leader;
	}
	public void setConstruct_project_leader(String construct_project_leader) {
		this.construct_project_leader = construct_project_leader;
	}
	public String getConstruct_project_leaderTel() {
		return construct_project_leaderTel;
	}
	public void setConstruct_project_leaderTel(String construct_project_leaderTel) {
		this.construct_project_leaderTel = construct_project_leaderTel;
	}
	public String getConstruct_project_startDate() {
		return construct_project_startDate;
	}
	public void setConstruct_project_startDate(String construct_project_startDate) {
		this.construct_project_startDate = construct_project_startDate;
	}
	public String getConstruct_project_endDate() {
		return construct_project_endDate;
	}
	public void setConstruct_project_endDate(String construct_project_endDate) {
		this.construct_project_endDate = construct_project_endDate;
	}
	public int getConstruct_project_dep() {
		return construct_project_dep;
	}
	public void setConstruct_project_dep(int construct_project_dep) {
		this.construct_project_dep = construct_project_dep;
	}
	public int getConstruct_project_status() {
		return construct_project_status;
	}
	public void setConstruct_project_status(int construct_project_status) {
		this.construct_project_status = construct_project_status;
	}
	public int getConstruct_project_contractId() {
		return construct_project_contractId;
	}
	public void setConstruct_project_contractId(int construct_project_contractId) {
		this.construct_project_contractId = construct_project_contractId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getConstuct_project_dep_company() {
		return constuct_project_dep_company;
	}
	public void setConstuct_project_dep_company(
			String constuct_project_dep_company) {
		this.constuct_project_dep_company = constuct_project_dep_company;
	}
	public String getConstuct_project_dep_name() {
		return constuct_project_dep_name;
	}
	public void setConstuct_project_dep_name(String constuct_project_dep_name) {
		this.constuct_project_dep_name = constuct_project_dep_name;
	}
	

}
