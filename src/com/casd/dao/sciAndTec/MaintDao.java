package com.casd.dao.sciAndTec;

import java.util.List;
import java.util.Map;

import com.casd.entity.sciAndTec.CheckContent;
import com.casd.entity.sciAndTec.CheckPro;
import com.casd.entity.sciAndTec.CheckRecordEntry;
import com.casd.entity.sciAndTec.CheckRecordHead;

public interface MaintDao {

	void saveCheckPro(CheckPro checkPro);

	void saveCheckCon(CheckContent checkContent);

	CheckPro getCheckPro(Map<String, Object> map);

	List<Map<String, Object>> getCheckContentById(Map<String, Object> map);

	void delete_checkCon(Map<String, Object> map);

	void add_record(CheckRecordHead checkRecordHead);

	void add_recordEntry(Map<String, Object> map);

	void dele_checkRecord(Map<String, Object> map);

	void update_record(CheckRecordEntry checkRecordEntry);
	
	List<CheckRecordEntry> getEntryList(Map<String, Object> map);



}
