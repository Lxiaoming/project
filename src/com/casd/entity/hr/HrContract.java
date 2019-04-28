package com.casd.entity.hr;

import java.io.Serializable;
import java.sql.Date;

/**
 *  合同期限表
 * */
public class HrContract implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2484793118757937943L;
	private int pact_id;//合同编号
	private String start_time;//起时间
	private String out_stime;//止时间
	private String remark;//备注
	private int user_id;//用户编号
	public int getPact_id() {
		return pact_id;
	}
	public void setPact_id(int pact_id) {
		this.pact_id = pact_id;
	}

	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getOut_stime() {
		return out_stime;
	}
	public void setOut_stime(String out_stime) {
		this.out_stime = out_stime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
