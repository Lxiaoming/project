package com.casd.entity.construct;

import java.io.Serializable;


/**
 *     甲供材料采购分录  construct_aparty_purentry
 * */
public class ApartyPurEntry implements Serializable {
	
	private static final long serialVersionUID = 5255411984322554500L;
	private int construct_Aparty_purEntry_id;						//id
	private int construct_Aparty_purEntry_materialId;				//合同材料id
	private String construct_Aparty_category;                       //采购类别
	private String construct_Aparty_entryName;                      //材料名称
	private String construct_Aparty_model;                          //材料规格
	
	private String construct_Aparty_unit;                           //材料单位
	private Double construct_aParty_byedNum;						//累计采购量
	private Double construct_Aparty_purEntry_num;					//采购量
	private String construct_Aparty_purEntry_remark;				//备注	
	private int construct_Aparty_purEntry_parentId;					//父ID
	
	

	public int getConstruct_Aparty_purEntry_materialId() {
		return construct_Aparty_purEntry_materialId;
	}
	public void setConstruct_Aparty_purEntry_materialId(
			int construct_Aparty_purEntry_materialId) {
		this.construct_Aparty_purEntry_materialId = construct_Aparty_purEntry_materialId;
	}
	public Double getConstruct_Aparty_purEntry_num() {
		return construct_Aparty_purEntry_num;
	}
	public void setConstruct_Aparty_purEntry_num(
			Double construct_Aparty_purEntry_num) {
		this.construct_Aparty_purEntry_num = construct_Aparty_purEntry_num;
	}
	public int getConstruct_Aparty_purEntry_parentId() {
		return construct_Aparty_purEntry_parentId;
	}
	public void setConstruct_Aparty_purEntry_parentId(
			int construct_Aparty_purEntry_parentId) {
		this.construct_Aparty_purEntry_parentId = construct_Aparty_purEntry_parentId;
	}
	public int getConstruct_Aparty_purEntry_id() {
		return construct_Aparty_purEntry_id;
	}
	public void setConstruct_Aparty_purEntry_id(int construct_Aparty_purEntry_id) {
		this.construct_Aparty_purEntry_id = construct_Aparty_purEntry_id;
	}
	public String getConstruct_Aparty_purEntry_remark() {
		return construct_Aparty_purEntry_remark;
	}
	public void setConstruct_Aparty_purEntry_remark(
			String construct_Aparty_purEntry_remark) {
		this.construct_Aparty_purEntry_remark = construct_Aparty_purEntry_remark;
	}
	public Double getConstruct_aParty_byedNum() {
		return construct_aParty_byedNum;
	}
	public void setConstruct_aParty_byedNum(Double construct_aParty_byedNum) {
		this.construct_aParty_byedNum = construct_aParty_byedNum;
	}
	public String getConstruct_Aparty_entryName() {
		return construct_Aparty_entryName;
	}
	public void setConstruct_Aparty_entryName(String construct_Aparty_entryName) {
		this.construct_Aparty_entryName = construct_Aparty_entryName;
	}
	public String getConstruct_Aparty_category() {
		return construct_Aparty_category;
	}
	public void setConstruct_Aparty_category(String construct_Aparty_category) {
		this.construct_Aparty_category = construct_Aparty_category;
	}
	public String getConstruct_Aparty_unit() {
		return construct_Aparty_unit;
	}
	public void setConstruct_Aparty_unit(String construct_Aparty_unit) {
		this.construct_Aparty_unit = construct_Aparty_unit;
	}
	public String getConstruct_Aparty_model() {
		return construct_Aparty_model;
	}
	public void setConstruct_Aparty_model(String construct_Aparty_model) {
		this.construct_Aparty_model = construct_Aparty_model;
	}

}
