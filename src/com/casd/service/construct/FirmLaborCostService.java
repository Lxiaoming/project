package com.casd.service.construct;

import java.util.List;
import java.util.Map;


public interface FirmLaborCostService {
	
	List<Map<String, Object>> queryFirmLaborCosts(Map<String, Object> param);
	
}
