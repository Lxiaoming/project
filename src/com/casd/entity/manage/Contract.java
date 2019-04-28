package com.casd.entity.manage;

import java.io.Serializable;

public class Contract implements Serializable {

	/**
	 *  合同管理
	 */
	private static final long serialVersionUID = -1359978093791375770L;
	
		private int manage_contract_id; 				//合同id 
		private String manage_contract_name;			//项目名称
		private int manage_contract_company; 			//所属公司
		private String manage_contract_firstParty;		//甲方
		private Double manage_contract_amount;			//合同金额
		private String manage_contract_address;			//项目地址
		private String manage_contract_startTime;		//合同开始时间
		private String manage_contract_endTime;			//合同结束时间
		private Double manage_contract_visaAmount;		//签证金额
		private String manage_contract_remark;			//备注
		private String manage_contract_num;				//编号
		private int manage_contract_payCompany;			//付款 公司分类
		
		public int getManage_contract_id() {
			return manage_contract_id;
		}
		public void setManage_contract_id(int manage_contract_id) {
			this.manage_contract_id = manage_contract_id;
		}
		public String getManage_contract_name() {
			return manage_contract_name;
		}
		public void setManage_contract_name(String manage_contract_name) {
			this.manage_contract_name = manage_contract_name;
		}
		public int getManage_contract_company() {
			return manage_contract_company;
		}
		public void setManage_contract_company(int manage_contract_company) {
			this.manage_contract_company = manage_contract_company;
		}
		public String getManage_contract_firstParty() {
			return manage_contract_firstParty;
		}
		public void setManage_contract_firstParty(String manage_contract_firstParty) {
			this.manage_contract_firstParty = manage_contract_firstParty;
		}
		public Double getManage_contract_amount() {
			return manage_contract_amount;
		}
		public void setManage_contract_amount(Double manage_contract_amount) {
			this.manage_contract_amount = manage_contract_amount;
		}
		public String getManage_contract_address() {
			return manage_contract_address;
		}
		public void setManage_contract_address(String manage_contract_address) {
			this.manage_contract_address = manage_contract_address;
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
		public Double getManage_contract_visaAmount() {
			return manage_contract_visaAmount;
		}
		public void setManage_contract_visaAmount(Double manage_contract_visaAmount) {
			this.manage_contract_visaAmount = manage_contract_visaAmount;
		}
		public String getManage_contract_remark() {
			return manage_contract_remark;
		}
		public void setManage_contract_remark(String manage_contract_remark) {
			this.manage_contract_remark = manage_contract_remark;
		}
		public String getManage_contract_num() {
			return manage_contract_num;
		}
		public void setManage_contract_num(String manage_contract_num) {
			this.manage_contract_num = manage_contract_num;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public int getManage_contract_payCompany() {
			return manage_contract_payCompany;
		}
		public void setManage_contract_payCompany(int manage_contract_payCompany) {
			this.manage_contract_payCompany = manage_contract_payCompany;
		}
		
	
	
}
