package com.casd.entity.uc;

import java.io.Serializable;
import java.sql.Date;


/**
 * 
 * 用户信息表
 * 
 * */
public class User implements Serializable {

	private int userid; // 用户id
	private String username; // 用户名
	private String password; // 用户密码
	private String phone_number; // 联系方式
	private String email; // 邮箱
	private int sex; // 性别
	private String user_card; // 身份证
	private String card_address; // 身份证地址
	private int role_id; // 角色编号
	//private int verification ; // 验证码
	private int age ;//年龄
	private int marital_status ;//婚姻
	private String birth_date ;//出生日期
	private String incorporation_date ;//入公司日期
	private String emergent_contact ;//紧急联系人
	private int education ;//学历
	private String profl_certificate ;//职业资格证
	private String permanent_address ;//常住地址
	private String department; //部门
    private String major;  //专业
    private String on_trial;//试用期待遇
    private String worker;  //转正待遇
    private String close_time;//转正日期
    private String status;//状态
    private String emergent_phone;//紧急联系人电话
    private int level;//级别
    private String remarks;//备注
    private String renew;//续签时间
    private int blood;//血型
    private String native_place;//籍贯
    private String birth;//生日
    private String nation;//民族
    private double weight;//体重
    private String health;//健康
    private int user_num;//员工编号
    
    private String age_limit;//健康
    private String sign;//年限
    private String expiry;//到期时间
    
    private int center_id;//中心id
    
    public String getEmergent_phone() {
		return emergent_phone;
	}

	public void setEmergent_phone(String emergent_phone) {
		this.emergent_phone = emergent_phone;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public String getNative_place() {
		return native_place;
	}

	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUser_card() {
		return user_card;
	}

	public void setUser_card(String user_card) {
		this.user_card = user_card;
	}

	public String getCard_address() {
		return card_address;
	}

	public void setCard_address(String card_address) {
		this.card_address = card_address;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(int marital_status) {
		this.marital_status = marital_status;
	}


	public String getEmergent_contact() {
		return emergent_contact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEmergent_contact(String emergent_contact) {
		this.emergent_contact = emergent_contact;
	}


	public String getProfl_certificate() {
		return profl_certificate;
	}

	public void setProfl_certificate(String profl_certificate) {
		this.profl_certificate = profl_certificate;
	}

	public String getPermanent_address() {
		return permanent_address;
	}

	public void setPermanent_address(String permanent_address) {
		this.permanent_address = permanent_address;
	}


	public String getOn_trial() {
		return on_trial;
	}

	public void setOn_trial(String on_trial) {
		this.on_trial = on_trial;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getClose_time() {
		return close_time;
	}

	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public String getAge_limit() {
		return age_limit;
	}

	public void setAge_limit(String age_limit) {
		this.age_limit = age_limit;
	}


	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getIncorporation_date() {
		return incorporation_date;
	}

	public void setIncorporation_date(String incorporation_date) {
		this.incorporation_date = incorporation_date;
	}

	public String getRenew() {
		return renew;
	}

	public void setRenew(String renew) {
		this.renew = renew;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public int getCenter_id() {
		return center_id;
	}

	public void setCenter_id(int center_id) {
		this.center_id = center_id;
	}

	

}

