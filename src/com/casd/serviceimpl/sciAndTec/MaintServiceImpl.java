package com.casd.serviceimpl.sciAndTec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casd.dao.sciAndTec.MaintDao;
import com.casd.entity.sciAndTec.CheckContent;
import com.casd.entity.sciAndTec.CheckPro;
import com.casd.entity.sciAndTec.CheckRecordEntry;
import com.casd.entity.sciAndTec.CheckRecordHead;
import com.casd.service.sciAndTec.MaintService;

@Service
public class MaintServiceImpl implements MaintService {

	@Autowired
	private MaintDao maintDao;

	@Override
	public void saveCheckCon(CheckPro checkPro, JSONArray myJsonArray) {
		int checkPro_id=checkPro.getMaintain_checkPro_id();
		if (checkPro_id==0) {
			maintDao.saveCheckPro(checkPro);
			checkPro_id=checkPro.getMaintain_checkPro_id();
		}else {
			maintDao.saveCheckPro(checkPro);
		}
		
		for (int i = 0; i < myJsonArray.size(); i++) {
			CheckContent checkContent = new CheckContent();
			JSONObject myjObject = myJsonArray.getJSONObject(i);
			int maintain_checkContent_id=0;
			if (!myjObject.get("maintain_checkContent_id").toString().equals("")) {
				maintain_checkContent_id=Integer.valueOf(myjObject.get("maintain_checkContent_id").toString());
			}
			
			checkContent.setMaintain_checkContent_id(maintain_checkContent_id);
			checkContent.setMaintain_checkContent_name(myjObject.get("maintain_checkContent_name").toString());
			checkContent.setMaintain_checkContent_parentId(checkPro_id);
			
			maintDao.saveCheckCon(checkContent);
		}
		
		
		
	}

	@Override
	public Map<String, Object> getCheckContentById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Map<String, Object> data=new HashMap<String, Object>();
		CheckPro checkPro= maintDao.getCheckPro(map);
		int checkPro_id = checkPro.getMaintain_checkPro_id();
		map.clear();
		map.put("maintain_checkContent_parentId", checkPro_id);
		List<Map<String, Object>> checkConList= maintDao.getCheckContentById(map);
		data.put("checkPro", checkPro);
		data.put("checkConList", checkConList);
		return data;
	}

	@Override
	public void delete_checkCon(Map<String, Object> map) {
		
		maintDao.delete_checkCon(map);
	}

	@Override
	@Transactional
	public void dele_checkPro(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String id= map.get("cid").toString();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" maintain_checkPro where maintain_checkPro_id =" + id + "");
		map.put("where", sbf.toString());
		maintDao.delete_checkCon(map);
		sbf.delete(0, sbf.length());
		sbf.append(" maintain_checkContent where maintain_checkContent_parentId =" + id + "");
		map.put("where", sbf.toString());
		maintDao.delete_checkCon(map);
		
	}

	@Override
	@Transactional
	public void add_record(CheckRecordHead checkRecordHead) {
		// TODO Auto-generated method stub
		maintDao.add_record(checkRecordHead);
		int maintain_checkRecord_head_id = checkRecordHead.getMaintain_checkRecord_head_id();
		
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sbBuffer=new StringBuffer();
		//sbBuffer.append(" INSERT INTO maintain_checkrecord_entry ");
		sbBuffer.append(" (maintain_checkRecord_entry_id,maintain_checkRecord_entry_parentId,maintain_checkRecord_entry_status,maintain_checkRecord_entry_system,maintain_checkRecord_entry_content) ");
		sbBuffer.append(" select 0,"+maintain_checkRecord_head_id+",1,checkpro.maintain_checkPro_name, ");
		sbBuffer.append(" checkcontent.maintain_checkContent_name ");
		sbBuffer.append(" from maintain_checkpro checkpro ");
		sbBuffer.append(" INNER JOIN maintain_checkcontent checkcontent on ");
		sbBuffer.append(" checkpro.maintain_checkPro_id=checkcontent.maintain_checkContent_parentId ");
		map.put("what", sbBuffer.toString());
		maintDao.add_recordEntry(map);
	}

	@Override
	public void dele_checkRecord(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(" maintain_checkrecord_head LEFT JOIN maintain_checkrecord_entry");
		sBuffer.append(" ON maintain_checkrecord_head.maintain_checkRecord_head_id = maintain_checkrecord_entry.maintain_checkRecord_entry_parentId");
		sBuffer.append(" WHERE maintain_checkRecord_head_id = "+id);
		map.put("what",sBuffer.toString()); 
		maintDao.dele_checkRecord(map);
	}

	@Override
	public void update_record(CheckRecordEntry checkRecordEntry) {
		// TODO Auto-generated method stub
		maintDao.update_record(checkRecordEntry);

	}

	@Override
	public List<CheckRecordEntry> getEntryList(String fields, String where) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fields", fields);
		map.put("where", where);
		return maintDao.getEntryList(map);
	}

	
}
