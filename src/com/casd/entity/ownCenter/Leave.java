package com.casd.entity.ownCenter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 请假实体类
*/
public class Leave implements Serializable {

	
	private int id; 				//单据id 
	private String applicant;		//申请人
	private String department;		//部门
	private String position;		//职位
	private int leave_category;		//请假类别
	private String start_time;		//起始时间
	private String end_time;			//结束时间
	private String reason;			//请假缘由
	private int status;				//流程id	
	private String day_count;		//天数
	private String time_count;		//小时
	private String createdate;        //创建单据时间
	private String leaveFile_name;		//文件名称
	
	//非持久
	private MultipartFile leaveFile;	//文件
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getLeave_category() {
		return leave_category;
	}
	public void setLeave_category(int leave_category) {
		this.leave_category = leave_category;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTime_count() {
		return time_count;
	}
	public void setTime_count(String time_count) {
		this.time_count = time_count;
	}
	public String getDay_count() {
		return day_count;
	}
	public void setDay_count(String day_count) {
		this.day_count = day_count;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getLeaveFile_name() {
		return leaveFile_name;
	}
	public void setLeaveFile_name(String leaveFile_name) {
		this.leaveFile_name = leaveFile_name;
	}
	public MultipartFile getLeaveFile() {
		return leaveFile;
	}
	public void setLeaveFile(MultipartFile leaveFile) {
		this.leaveFile = leaveFile;
	}
}
