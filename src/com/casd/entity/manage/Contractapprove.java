package com.casd.entity.manage;

import java.io.File;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 */

public class Contractapprove implements Serializable {

	/**
	 *  合同管理
	 */
	private static final long serialVersionUID = -1359978093791375770L;
	
		private Integer manage_contractapprove_id; 					//合同id 
		private String manage_contractapprove_name;				//项目名称
		private int manage_contractapprove_company; 			//所属公司
		private String manage_contractapprove_firstParty;		//甲方
		private Double manage_contractapprove_amount;			//合同金额
		private String manage_contractapprove_address;			//项目地址
		private String manage_contractapprove_startTime;		//合同开始时间
		private String manage_contractapprove_endTime;			//合同结束时间
		private String manage_contractapprove_attachAddress;	//附件地址
		private String manage_contractapprove_remark;			//备注
		private String manage_contractapprove_num;				//编号
		public  int manage_status;                              //状态
		private String manage_contractapprove_payment;			//付款比例
		private int manage_contractapprove_taxIncluded;			//是否含税
		private String manage_contractapprove_secondParty;		//乙方
		private int category; 
		private MultipartFile contractFile;						//文件

		
		public int getManage_status() {
			return manage_status;
		}
		public void setManage_status(int manage_status) {
			this.manage_status = manage_status;
		}
		public Integer getManage_contractapprove_id() {
			return manage_contractapprove_id;
		}
		public void setManage_contractapprove_id(Integer manage_contractapprove_id) {
			this.manage_contractapprove_id = manage_contractapprove_id;
		}
		public String getManage_contractapprove_name() {
			return manage_contractapprove_name;
		}
		public void setManage_contractapprove_name(String manage_contractapprove_name) {
			this.manage_contractapprove_name = manage_contractapprove_name;
		}
		public int getManage_contractapprove_company() {
			return manage_contractapprove_company;
		}
		public void setManage_contractapprove_company(int manage_contractapprove_company) {
			this.manage_contractapprove_company = manage_contractapprove_company;
		}
		public String getManage_contractapprove_firstParty() {
			return manage_contractapprove_firstParty;
		}
		public void setManage_contractapprove_firstParty(String manage_contractapprove_firstParty) {
			this.manage_contractapprove_firstParty = manage_contractapprove_firstParty;
		}
		public Double getManage_contractapprove_amount() {
			return manage_contractapprove_amount;
		}
		public void setManage_contractapprove_amount(Double manage_contractapprove_amount) {
			this.manage_contractapprove_amount = manage_contractapprove_amount;
		}
		public String getManage_contractapprove_address() {
			return manage_contractapprove_address;
		}
		public void setManage_contractapprove_address(String manage_contractapprove_address) {
			this.manage_contractapprove_address = manage_contractapprove_address;
		}
		public String getManage_contractapprove_startTime() {
			return manage_contractapprove_startTime;
		}
		public void setManage_contractapprove_startTime(String manage_contractapprove_startTime) {
			this.manage_contractapprove_startTime = manage_contractapprove_startTime;
		}
		public String getManage_contractapprove_endTime() {
			return manage_contractapprove_endTime;
		}
		public void setManage_contractapprove_endTime(String manage_contractapprove_endTime) {
			this.manage_contractapprove_endTime = manage_contractapprove_endTime;
		}
		public String getManage_contractapprove_attachAddress() {
			return manage_contractapprove_attachAddress;
		}
		public void setManage_contractapprove_attachAddress(String manage_contractapprove_attachAddress) {
			this.manage_contractapprove_attachAddress = manage_contractapprove_attachAddress;
		}
		public String getManage_contractapprove_remark() {
			return manage_contractapprove_remark;
		}
		public void setManage_contractapprove_remark(String manage_contractapprove_remark) {
			this.manage_contractapprove_remark = manage_contractapprove_remark;
		}
		public String getManage_contractapprove_num() {
			return manage_contractapprove_num;
		}
		public void setManage_contractapprove_num(String manage_contractapprove_num) {
			this.manage_contractapprove_num = manage_contractapprove_num;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public MultipartFile getContractFile() {
			return contractFile;
		}
		public void setContractFile(MultipartFile contractFile) {
			this.contractFile = contractFile;
		}
		public String getManage_contractapprove_payment() {
			return manage_contractapprove_payment;
		}
		public void setManage_contractapprove_payment(
				String manage_contractapprove_payment) {
			this.manage_contractapprove_payment = manage_contractapprove_payment;
		}
		public int getManage_contractapprove_taxIncluded() {
			return manage_contractapprove_taxIncluded;
		}
		public void setManage_contractapprove_taxIncluded(
				int manage_contractapprove_taxIncluded) {
			this.manage_contractapprove_taxIncluded = manage_contractapprove_taxIncluded;
		}
		public String getManage_contractapprove_secondParty() {
			return manage_contractapprove_secondParty;
		}
		public void setManage_contractapprove_secondParty(
				String manage_contractapprove_secondParty) {
			this.manage_contractapprove_secondParty = manage_contractapprove_secondParty;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		
	
	
}

