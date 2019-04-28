package com.casd.entity.finance;

import java.io.Serializable;


/**
 *    基本工资  uc_wage
 * */
public class Base_wages implements Serializable{
	
	private static final long serialVersionUID = -9118054034360584863L;
	
	private Integer uc_wage_id;					//id
	private Integer uc_wage_userId;				//人员id
	private Double uc_wage_base;				//基本工资
	private Double uc_wage_post;				//岗位工资
	private Double uc_wage_achieve;				//绩效工资
	private Double uc_wage_subsidy;				//津贴补助
	private Double uc_wage_socSec;				//代扣社保
	private Double uc_wage_accFund;				//代扣公积金
	
	private Integer uc_wage_status;				//
	private Double uc_wage_actualDay;				//实际出勤天数
	private Double uc_wage_tax;					//代扣个税
	
	public Integer getUc_wage_id() {
		return uc_wage_id;
	}
	public void setUc_wage_id(Integer uc_wage_id) {
		this.uc_wage_id = uc_wage_id;
	}
	public Integer getUc_wage_userId() {
		return uc_wage_userId;
	}
	public void setUc_wage_userId(Integer uc_wage_userId) {
		this.uc_wage_userId = uc_wage_userId;
	}
	public Double getUc_wage_base() {
		return uc_wage_base;
	}
	public void setUc_wage_base(Double uc_wage_base) {
		this.uc_wage_base = uc_wage_base;
	}
	public Double getUc_wage_post() {
		return uc_wage_post;
	}
	public void setUc_wage_post(Double uc_wage_post) {
		this.uc_wage_post = uc_wage_post;
	}
	public Double getUc_wage_achieve() {
		return uc_wage_achieve;
	}
	public void setUc_wage_achieve(Double uc_wage_achieve) {
		this.uc_wage_achieve = uc_wage_achieve;
	}
	public Double getUc_wage_subsidy() {
		return uc_wage_subsidy;
	}
	public void setUc_wage_subsidy(Double uc_wage_subsidy) {
		this.uc_wage_subsidy = uc_wage_subsidy;
	}
	public Double getUc_wage_socSec() {
		return uc_wage_socSec;
	}
	public void setUc_wage_socSec(Double uc_wage_socSec) {
		this.uc_wage_socSec = uc_wage_socSec;
	}
	public Double getUc_wage_accFund() {
		return uc_wage_accFund;
	}
	public void setUc_wage_accFund(Double uc_wage_accFund) {
		this.uc_wage_accFund = uc_wage_accFund;
	}
	
	
	public Double getUc_wage_actualDay() {
		return uc_wage_actualDay;
	}
	public void setUc_wage_actualDay(Double uc_wage_actualDay) {
		this.uc_wage_actualDay = uc_wage_actualDay;
	}
	public Integer getUc_wage_status() {
		return uc_wage_status;
	}
	public void setUc_wage_status(Integer uc_wage_status) {
		this.uc_wage_status = uc_wage_status;
	}
	public Double getUc_wage_tax() {
		return uc_wage_tax;
	}
	public void setUc_wage_tax(Double uc_wage_tax) {
		this.uc_wage_tax = uc_wage_tax;
	}
	


}
