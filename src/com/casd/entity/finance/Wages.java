package com.casd.entity.finance;

import java.io.Serializable;


/**
 *    工资 finance_wage_table
 * */
public class Wages implements Serializable{
	
	private static final long serialVersionUID = -9118054034360584863L;
	
	private int finance_wage_id;					//id
	private String uc_wage_userid;				//职员姓名

	private Double finance_wages_attCount;			//出勤天数
	private Double finance_wages_vacaCount;			//休假天数
	private Double finance_wages_leaveCount;		//请假天数
	private Double uc_wage_base;				//基本工资
	private Double uc_wage_post;				//岗位工资
	private Double uc_wage_achieve;			//绩效工资
	private Double uc_wage_subsidy;			//津贴补助
	private Double uc_wage_socSec;			//代扣社保
	private Double uc_wage_accFund;			//代扣公积金
	private Double uc_wage_wages;				//应税工资
	private Double uc_wage_tax;				//代扣个税
	private Double uc_wage_dedu;				//考勤扣除
	private String uc_wage_yearMon;			//月份
	private Double uc_wage_baseTotal;			//应付小计
	private Double uc_wage_realhair;			//始发小计
	private String uc_wage_company_name;		//公司名称
	private String uc_wage_center_name;		//公司名称
	private Double uc_wage_actualDay;		//实际天数
	
	

	public int getFinance_wage_id() {
		return finance_wage_id;
	}
	public String getUc_wage_userid() {
		return uc_wage_userid;
	}
	public Double getFinance_wages_attCount() {
		return finance_wages_attCount;
	}
	public Double getFinance_wages_vacaCount() {
		return finance_wages_vacaCount;
	}
	public Double getFinance_wages_leaveCount() {
		return finance_wages_leaveCount;
	}
	public Double getUc_wage_base() {
		return uc_wage_base;
	}
	public Double getUc_wage_post() {
		return uc_wage_post;
	}
	public Double getUc_wage_achieve() {
		return uc_wage_achieve;
	}
	public Double getUc_wage_subsidy() {
		return uc_wage_subsidy;
	}
	public Double getUc_wage_socSec() {
		return uc_wage_socSec;
	}
	public Double getUc_wage_accFund() {
		return uc_wage_accFund;
	}
	
	public Double getUc_wage_tax() {
		return uc_wage_tax;
	}
	public Double getUc_wage_dedu() {
		return uc_wage_dedu;
	}
	public String getUc_wage_yearMon() {
		return uc_wage_yearMon;
	}
	public Double getUc_wage_baseTotal() {
		return uc_wage_baseTotal;
	}
	public void setFinance_wage_id(int finance_wage_id) {
		this.finance_wage_id = finance_wage_id;
	}
	public void setUc_wage_userid(String uc_wage_userid) {
		this.uc_wage_userid = uc_wage_userid;
	}
	public void setFinance_wages_attCount(Double finance_wages_attCount) {
		this.finance_wages_attCount = finance_wages_attCount;
	}
	public void setFinance_wages_vacaCount(Double finance_wages_vacaCount) {
		this.finance_wages_vacaCount = finance_wages_vacaCount;
	}
	public void setFinance_wages_leaveCount(Double finance_wages_leaveCount) {
		this.finance_wages_leaveCount = finance_wages_leaveCount;
	}
	public void setUc_wage_base(Double uc_wage_base) {
		this.uc_wage_base = uc_wage_base;
	}
	public void setUc_wage_post(Double uc_wage_post) {
		this.uc_wage_post = uc_wage_post;
	}
	public void setUc_wage_achieve(Double uc_wage_achieve) {
		this.uc_wage_achieve = uc_wage_achieve;
	}
	public void setUc_wage_subsidy(Double uc_wage_subsidy) {
		this.uc_wage_subsidy = uc_wage_subsidy;
	}
	public void setUc_wage_socSec(Double uc_wage_socSec) {
		this.uc_wage_socSec = uc_wage_socSec;
	}
	public void setUc_wage_accFund(Double uc_wage_accFund) {
		this.uc_wage_accFund = uc_wage_accFund;
	}

	public void setUc_wage_tax(Double uc_wage_tax) {
		this.uc_wage_tax = uc_wage_tax;
	}
	public void setUc_wage_dedu(Double uc_wage_dedu) {
		this.uc_wage_dedu = uc_wage_dedu;
	}
	public void setUc_wage_yearMon(String uc_wage_yearMon) {
		this.uc_wage_yearMon = uc_wage_yearMon;
	}
	public void setUc_wage_baseTotal(Double uc_wage_baseTotal) {
		this.uc_wage_baseTotal = uc_wage_baseTotal;
	}
	public Double getUc_wage_wages() {
		return uc_wage_wages;
	}
	public void setUc_wage_wages(Double uc_wage_wages) {
		this.uc_wage_wages = uc_wage_wages;
	}
	public Double getUc_wage_realhair() {
		return uc_wage_realhair;
	}
	public void setUc_wage_realhair(Double uc_wage_realhair) {
		this.uc_wage_realhair = uc_wage_realhair;
	}
	public String getUc_wage_company_name() {
		return uc_wage_company_name;
	}
	public void setUc_wage_company_name(String uc_wage_company_name) {
		this.uc_wage_company_name = uc_wage_company_name;
	}
	public String getUc_wage_center_name() {
		return uc_wage_center_name;
	}
	public void setUc_wage_center_name(String uc_wage_center_name) {
		this.uc_wage_center_name = uc_wage_center_name;
	}
	public Double getUc_wage_actualDay() {
		return uc_wage_actualDay;
	}
	public void setUc_wage_actualDay(Double uc_wage_actualDay) {
		this.uc_wage_actualDay = uc_wage_actualDay;
	}

	

	
	

}
