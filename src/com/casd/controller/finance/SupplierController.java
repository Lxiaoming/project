package com.casd.controller.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.finance.Supplier;
import com.casd.entity.uc.User;
import com.casd.service.construct.FinalPurchaseService;
import com.casd.service.finance.MaterialService;
import com.casd.service.finance.SupplierService;

@Controller
@RequestMapping("/admin")
public class SupplierController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private FinalPurchaseService finalPurchaseService;
	/**
	 * 供应商基础资料查询
	 */

	@RequestMapping(value = "/supplierList", method = RequestMethod.GET)
	public ModelAndView materialList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/supplierList");
		return mv;
	}

	@RequestMapping(value = "/supplierList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supplierList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_supplier_table supplier left join uc_user user on user.userid=supplier.construct_supplier_charger where 1=1 ");

		String construct_supplier_name = request.getParameter("construct_suppliername");
		if (StringUtils.hasText(construct_supplier_name)) {
			sbf.append(" and construct_supplier_name like'%" + construct_supplier_name
					+ "%'");
		}

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = materialService.materialList(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	/**
	 * 供应商保存
	*/
	@RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSupplier(HttpServletRequest request) throws Exception {
		Supplier supplier=new Supplier();
		supplier.setConstruct_supplier_addr(request.getParameter("construct_supplier_addr"));
		supplier.setConstruct_supplier_id(Integer.valueOf(request.getParameter("construct_supplier_id").toString().isEmpty()?"0":request.getParameter("construct_supplier_id").toString()));
		supplier.setConstruct_supplier_tel(request.getParameter("construct_supplier_tel"));
		supplier.setConstruct_supplier_name(request.getParameter("construct_supplier_name"));
		supplier.setConstruct_supplier_charger(Integer.valueOf(request.getParameter("construct_supplier_charger").toString()==""?"0":request.getParameter("construct_supplier_charger").toString()));

		supplierService.saveSupplier(supplier);

		return null;
	}
	
	/**
	 * 供应商删除
	*/
	@RequestMapping(value="/deleSupplier" , method = RequestMethod.POST)
	@ResponseBody
	public String deleSupplier(HttpServletRequest request, Model model) {
		Map<String, Object> map=new HashMap<String, Object>();
		String ids =request.getParameter("ids");
		ids=ids.substring(1);
		String[] id=ids.split("]");
		map.put("what",id[0]);        
	    map.put("where", "construct_supplier_table");
	    supplierService.deleSupplier(map);
		return null;
	}
	
	/**
	*  供应商专用-材料采购查询
	*/
	
	@RequestMapping(value = "/suppPurchaseList", method = RequestMethod.GET)
	public ModelAndView suppPurchaseList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("supplierMod/suppPurchaseList");
		return mv;
	}
	
	@RequestMapping(value = "/suppPurchaseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> suppPurchaseList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		User user=(User) request.getSession().getAttribute("loginUser");
		String username = user.getUsername();
		sbf.append(" construct_purchase_head_class pur left join construct_project_table pro on pur.construct_purchase_projectId=pro.construct_project_id  "
				+ "where 1=1  and construct_purchase_status > 4 and construct_purchase_supplier='"+username+"'");

		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = finalPurchaseService
				.finalPurchaseList(pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}
	


	
	
	
	/**
	 * 初始化台账
	*/
	@RequestMapping(value="/toMaterialPrice" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toMaterialPrice(HttpServletRequest request) {
		
		return supplierService.toMaterialPrice();
	
	}

}
