package com.casd.entity.supplierMod;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 项目调动
*/
public class WorkerApply implements Serializable {

	
	private int suppliermod_worker_apply_id; 			//单据id 
	private int suppliermod_worker_apply_userId;		//人员id
	private String suppliermod_worker_apply_reason;		//原因
	private int suppliermod_worker_apply_proId;			//调动项目
	private int suppliermod_worker_apply_status;		//状态
	private String suppliermod_worker_apply_creatTime;	//建单时间
	private int suppliermod_worker_apply_oldProId;		//原项目
	private int suppliermod_worker_apply_teamId;		//班组id

	
	
	public int getSuppliermod_worker_apply_id() {
		return suppliermod_worker_apply_id;
	}
	public void setSuppliermod_worker_apply_id(int suppliermod_worker_apply_id) {
		this.suppliermod_worker_apply_id = suppliermod_worker_apply_id;
	}
	public int getSuppliermod_worker_apply_userId() {
		return suppliermod_worker_apply_userId;
	}
	public void setSuppliermod_worker_apply_userId(
			int suppliermod_worker_apply_userId) {
		this.suppliermod_worker_apply_userId = suppliermod_worker_apply_userId;
	}
	public String getSuppliermod_worker_apply_reason() {
		return suppliermod_worker_apply_reason;
	}
	public void setSuppliermod_worker_apply_reason(
			String suppliermod_worker_apply_reason) {
		this.suppliermod_worker_apply_reason = suppliermod_worker_apply_reason;
	}
	public int getSuppliermod_worker_apply_proId() {
		return suppliermod_worker_apply_proId;
	}
	public void setSuppliermod_worker_apply_proId(int suppliermod_worker_apply_proId) {
		this.suppliermod_worker_apply_proId = suppliermod_worker_apply_proId;
	}
	public int getSuppliermod_worker_apply_status() {
		return suppliermod_worker_apply_status;
	}
	public void setSuppliermod_worker_apply_status(
			int suppliermod_worker_apply_status) {
		this.suppliermod_worker_apply_status = suppliermod_worker_apply_status;
	}
	public String getSuppliermod_worker_apply_creatTime() {
		return suppliermod_worker_apply_creatTime;
	}
	public void setSuppliermod_worker_apply_creatTime(
			String suppliermod_worker_apply_creatTime) {
		this.suppliermod_worker_apply_creatTime = suppliermod_worker_apply_creatTime;
	}
	public int getSuppliermod_worker_apply_oldProId() {
		return suppliermod_worker_apply_oldProId;
	}
	public void setSuppliermod_worker_apply_oldProId(
			int suppliermod_worker_apply_oldProId) {
		this.suppliermod_worker_apply_oldProId = suppliermod_worker_apply_oldProId;
	}
	public int getSuppliermod_worker_apply_teamId() {
		return suppliermod_worker_apply_teamId;
	}
	public void setSuppliermod_worker_apply_teamId(
			int suppliermod_worker_apply_teamId) {
		this.suppliermod_worker_apply_teamId = suppliermod_worker_apply_teamId;
	}
	
	
	
	
}
