package com.casd.entity.ownCenter;

import java.io.Serializable;
import java.sql.Date;

import org.activiti.engine.task.Task;

/**
 * own_purchase_brand 表格
 * */
public class OwnPurchaseHead implements Serializable{
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer own_purchase_id;             //单据编号
	private Integer own_purchase_companyId;      //公司编号
	private Integer own_purchase_projectId;      //项目编号
	private String own_purchase_planDate;        //计划日期
	private String own_purchase_arriveDate;      //希望送达日期
	private String  own_purchase_planMan;        //复核员
	private String own_purchase_brand;           //品牌
	private int own_purchase_status;             //状态
	private int own_purchase_type;               //采购类型
	private String own_purchase_time;            //建单时间
	
	

	
	public Integer getOwn_purchase_id() {
		return own_purchase_id;
	}
	public void setOwn_purchase_id(Integer own_purchase_id) {
		this.own_purchase_id = own_purchase_id;
	}
	public Integer getOwn_purchase_companyId() {
		return own_purchase_companyId;
	}
	public void setOwn_purchase_companyId(Integer own_purchase_companyId) {
		this.own_purchase_companyId = own_purchase_companyId;
	}
	public Integer getOwn_purchase_projectId() {
		return own_purchase_projectId;
	}
	public void setOwn_purchase_projectId(Integer own_purchase_projectId) {
		this.own_purchase_projectId = own_purchase_projectId;
	}
	
	public String getOwn_purchase_planMan() {
		return own_purchase_planMan;
	}
	public void setOwn_purchase_planMan(String own_purchase_planMan) {
		this.own_purchase_planMan = own_purchase_planMan;
	}
	public String getOwn_purchase_brand() {
		return own_purchase_brand;
	}
	public void setOwn_purchase_brand(String own_purchase_brand) {
		this.own_purchase_brand = own_purchase_brand;
	}
	public int getOwn_purchase_status() {
		return own_purchase_status;
	}
	public void setOwn_purchase_status(int own_purchase_status) {
		this.own_purchase_status = own_purchase_status;
	}
	public String getOwn_purchase_planDate() {
		return own_purchase_planDate;
	}
	public void setOwn_purchase_planDate(String own_purchase_planDate) {
		this.own_purchase_planDate = own_purchase_planDate;
	}
	public String getOwn_purchase_arriveDate() {
		return own_purchase_arriveDate;
	}
	public void setOwn_purchase_arriveDate(String own_purchase_arriveDate) {
		this.own_purchase_arriveDate = own_purchase_arriveDate;
	}
	public int getOwn_purchase_type() {
		return own_purchase_type;
	}
	public void setOwn_purchase_type(int own_purchase_type) {
		this.own_purchase_type = own_purchase_type;
	}
	public String getOwn_purchase_time() {
		return own_purchase_time;
	}
	public void setOwn_purchase_time(String own_purchase_time) {
		this.own_purchase_time = own_purchase_time;
	}
	


}
