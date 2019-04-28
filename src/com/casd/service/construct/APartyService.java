package com.casd.service.construct;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateExecution;

import com.casd.controller.web.Ref;
import com.casd.entity.construct.ApartyMaterial;
import com.casd.entity.construct.ApartyPur;


public interface APartyService {

	List<Map<String, Object>> getList(int pageIndex, int pageSize,
			Ref<Integer> record, String string) throws Exception;

	void save_aPartyMaterial(ApartyMaterial apartyMaterial);

	void delete_aPartyMaterial(String string);

	void cp_aParty_exl(List<List<Object>> list, String construct_project_id);

	Map<String, Object> getConstruct(String fields,String where);

	int  save_aPartyPur(ApartyPur apartyPur,HttpServletRequest request) throws Exception;

	void dele_apartyPur(String string);

	void delete_aPartyEntry(String string);
	
	public void updateApartyPur(ApartyPur apartyPur);
	
	public void apartyPurStatus(DelegateExecution execution,String status);
	
	public void aPartyPurPass(HttpServletRequest request) throws Exception;
	
	public List<Map<String, Object>> dataList(String fields,String where);
	
    public String aPartyNextUser(DelegateExecution execution);
	
}
