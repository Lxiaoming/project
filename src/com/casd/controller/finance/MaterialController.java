package com.casd.controller.finance;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.common.excel.ImportExcel;
import com.casd.entity.construct.ConstructDep;
import com.casd.entity.finance.Material;
import com.casd.entity.finance.MaterialModel;
import com.casd.entity.finance.MaterialSeries;
import com.casd.service.construct.PurchaseService;
import com.casd.service.finance.MaterialService;

@Controller
@RequestMapping("/admin")
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private PurchaseService purchaseService;
	/**
	 * 材料系列资料
	 */

	@RequestMapping(value = "/materialSeries", method = RequestMethod.GET)
	public ModelAndView materialSeries(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/materialSeries");
		return mv;
	}

	@RequestMapping(value = "/materialSeries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialSeries(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_material_series where 1=1 ");

		String searchName = request
				.getParameter("construct_material_seriesName");
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and construct_material_seriesName like'%" + searchName
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
	 * 材料系列保存
	 * */
	@RequestMapping(value = "/saveSeries", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveSeries(HttpServletRequest request,
			MaterialSeries materialSeries, String rowData) throws Exception {
		JSONObject json = new JSONObject();
		try {
			rowData = rowData.substring(1);
			String[] ids = rowData.split("]");
			materialSeries.setConstruct_material_seriesSuppliers(ids[0]);
			materialService.saveSeries(materialSeries);
		} catch (Exception e) {
			json.put("msg", "操作有误!");

			e.printStackTrace();
		}
		return json;

	}

	// 修改数据初始化
	@RequestMapping(value = "/selectData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryId_dep(HttpServletRequest request,
			Model model) throws Exception {

		StringBuffer sbf = new StringBuffer();
		String cid = request.getParameter("cid");
		Map<String, Object> json = new HashMap<String, Object>();
		String fields = " * ";
		sbf.append(" construct_material_series  WHERE construct_material_seriesID="
				+ cid);
		List<Map<String, Object>> dataList = materialService.selectData(fields,
				sbf.toString());

		if (StringUtils.hasText(dataList.get(0)
				.get("construct_material_seriesSuppliers").toString())) {
			fields = " construct_supplier_id,construct_supplier_name";
			sbf.delete(0, sbf.length());
			sbf.append(" construct_supplier_table where construct_supplier_id in("
					+ dataList.get(0).get("construct_material_seriesSuppliers")
					+ ")");
			List<Map<String, Object>> listMap = materialService.selectData(
					fields, sbf.toString());
			json.put("listMap", listMap);
		}
		return json;
	}

	// 材料系列列表删除
	@RequestMapping(value = "/delete_Series", method = RequestMethod.POST)
	@ResponseBody
	public Object delete_Series(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		String seriesID = request.getParameter("seriesID");

		try {
			Map<String, Object> map=new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			sbf.append(" where construct_material_seriesID=" + seriesID);
			map.put("where", sbf);
			map.put("seriesID", seriesID);
			materialService.delete_Series(map);

		} catch (Exception e) {
			json.put("msg", "删除失败!");
			e.printStackTrace();
		}

		return json;

	}

	/**
	 * 材料基础资料
	 */

	@RequestMapping(value = "/materialList", method = RequestMethod.GET)
	public ModelAndView materialList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String seriesID = request.getParameter("construct_material_seriesID");
		mv.addObject("seriesID", seriesID);
		mv.setViewName("finance/materialList");
		return mv;
	}

	@RequestMapping(value = "/materialList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String seriesID = request.getParameter("construct_material_seriesID");

		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_material_table where 1=1  and construct_material_seriesId="
				+ seriesID + "");

		String searchName = request.getParameter("construct_material_name");
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and construct_material_name like'%" + searchName
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
	 * 材料规格列表
	 * 
	 * @author Administrator
	 * **/
	@RequestMapping(value = "/materialhome", method = RequestMethod.GET)
	public ModelAndView materialmodelList(HttpServletRequest request, String cid) {
		ModelAndView mv = new ModelAndView();
		cid = request.getParameter("construct_material_id");
		mv.addObject("cid", cid);
		mv.setViewName("finance/materialhome");
		return mv;

	}

	@RequestMapping(value = "/materialhome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialmodelList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Ref<Integer> record = new Ref<Integer>();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		String cid = request.getParameter("construct_material_id");
		StringBuilder sbf = new StringBuilder();
		sbf.append(" construct_material_model a");
		sbf.append(" JOIN construct_material_table b ON a.construct_material_model_parentid=b.construct_material_id");
		sbf.append(" where 1=1");
		sbf.append(" and a.construct_material_model_parentid=" + cid);
		String model_name = request
				.getParameter("construct_material_model_name");
		if (StringUtils.hasText(model_name)) {
			sbf.append(" and a.construct_material_model_name like'%"
					+ model_name + "%'");
		}
		params.put("where", sbf.toString());
		List<Map<String, Object>> data = materialService.getMaterialById(
				pageIndex, pageSize, record, sbf.toString());
		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}

	// 材料编辑
	@RequestMapping(value = "/editMaterial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editMaterial(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String model_name = request.getParameter("model_name");
		String model_remarks = request.getParameter("model_remarks");
		String model_unit = request.getParameter("model_unit");
		String model_parentid = request.getParameter("model_parentid");
		String model_id = request.getParameter("model_id") == "" ? "0"
				: request.getParameter("model_id");
		String construct_materail_model_num = request.getParameter("construct_materail_model_num") == "" ? "0"
				: request.getParameter("construct_materail_model_num");
		
		try {
			MaterialModel mtm = new MaterialModel();
			mtm.setConstruct_material_model_id(Integer.parseInt(model_id));
			mtm.setConstruct_material_model_name(model_name);
			mtm.setConstruct_material_model_remarks(model_remarks);
			mtm.setConstruct_material_model_unit(model_unit);
			mtm.setConstruct_material_model_parentid(Integer
					.parseInt(model_parentid));
			mtm.setConstruct_materail_model_num(Integer
					.parseInt(construct_materail_model_num));
			materialService.editmaterial(mtm);
		} catch (Exception e) {
			map.put("msg", "修改失败");
		}
		return map;

	}

	/**
	 * 删除材料
	 * */
	@RequestMapping(value = "/deleteMaterial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Map<String, Object> deleMap=new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			String menuids = request.getParameter("menuids");
			/*menuids = menuids.substring(1);
			String[] ids = menuids.split("]");*/
			sbf.append(" where construct_material_model_id = "+menuids+" ");
			deleMap.put("where", sbf.toString());
			deleMap.put("menuids",  menuids);
			String msg=materialService.delete(deleMap);
			if (msg!="") {
				map.put("msg", msg);
			}
		} catch (Exception e) {
			map.put("msg", "删除失败,请联系管理员！");
		}

		return map;

	}

	@RequestMapping(value = "/materialset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialset(HttpServletRequest request) {

		Map<String, Object> json = new HashMap<String, Object>();
		String material_id = request.getParameter("material_id") == "" ? "0"
				: request.getParameter("material_id");
		try {
			// 材料录入
			String material_name = request.getParameter("material_name");
			String material_remarks = request.getParameter("material_remarks");
			String seriesID = request.getParameter("seriesID");
			String construct_material_num = request.getParameter("construct_material_num") == "" ? "0"
					: request.getParameter("construct_material_num");
			Material mtl = new Material();
			mtl.setConstruct_material_id(Integer.parseInt(material_id));
			mtl.setConstruct_material_name(material_name);
			mtl.setConstruct_material_remarks(material_remarks);
			mtl.setConstruct_material_seriesId(Integer.valueOf(seriesID));
			mtl.setConstruct_material_num(Integer.valueOf(construct_material_num));
			materialService.addMaterial(mtl);
		} catch (Exception e) {
			json.put("msg", "保存失败,请联系管理员！");

		}
		return json;
	}

	/**
	 * 
	 * 删除材料信息
	 * */
	@RequestMapping(value = "/deletejoin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletejoin(HttpServletRequest request) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			String material_id = request.getParameter("material_id");
			StringBuffer sbf = new StringBuffer();
			sbf.append(" construct_material_table LEFT JOIN construct_material_model");
			sbf.append(" ON construct_material_table.construct_material_id = construct_material_model.construct_material_model_parentid");
			sbf.append(" WHERE construct_material_id = " + material_id);
			map.put("where", sbf.toString());
			map.put("material_id", material_id);
			materialService.deletemt(map);
		} catch (Exception e) {
			jsonMap.put("msg", "操作失败！");
		}
		return jsonMap;

	}

	/**
	 * 方法说明：将Excel表格数据 导入
	 * 
	 * */
	@RequestMapping(value = "/cp_material_exl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  cp_material_exl(MultipartFile pic,
			HttpServletRequest request) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();

		try {

			// 获取原始文件名
			String fileName = pic.getOriginalFilename();

			//String construct_material_seriesId = request.getParameter("construct_material_seriesId");

			List<List<Object>> list = new ArrayList<List<Object>>();
			ImportExcel ie = new ImportExcel();

			if (StringUtils.isEmpty(fileName)) {
				json.put("Success", false);
				json.put("Msg", "请选择要导入的文件");
				return json;
			}

			if (!fileName.substring(fileName.lastIndexOf(".")).equals(".xlsx")) {
				json.put("Success", false);
				json.put("Msg", "请选择Excel2007以上版本文件.xlsx");
				return json;
			}

			File file2 = new File("e:/uploadfile");
			if (!file2.exists()) {
				file2.mkdirs();
			}

			// 定义文件路径
			String fileUploadBasePath = "e:/uploadfile/";
			File newFile = new File(fileUploadBasePath
					+ System.currentTimeMillis() + fileName);

			pic.transferTo(newFile); // 写入文件到服务器目录

			list = ie.read2007Excel(newFile);

			// 读取完信息删除上传文件
			// 判断文件存在,删除
			if (newFile.exists()) {
				newFile.delete();
			}
			
			materialService.cp_material_exl(list);
			
			json.put("Success", true);
			json.put("Msg", "导入成功");
		} catch (Exception e) {
			json.put("Success", false);
			json.put("Msg", "导入失败" + e);
		}
		return json;
	}
	
	
	/**
	*  采购报表
	*/
	@RequestMapping(value = "/matPurReport", method = RequestMethod.GET)
	public ModelAndView matPurReport() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/matPurReport");
		return mv;
	}
	
	
	@RequestMapping(value = "/matPurReports", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> matPurReport(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		
		
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		

		StringBuffer sbf = new StringBuffer();
		String filds = "*";
		sbf.append(" (select pur.*,pro.*,dep.*,SUM(entry.construct_purchase_purchaseTotal) total from ");
		
		sbf.append(" construct_purchase_head pur left join construct_project_table pro on pur.construct_purchase_projectId=pro.construct_project_id  ");
		sbf.append(" left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep ");
		sbf.append(" LEFT JOIN construct_purchase_entry entry on pur.construct_purchase_id=entry.construct_purchase_parentId where 1=1 ");

		
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and  pro.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		
		String constuct_project_dep_company = request.getParameter("constuct_project_dep_company");
		if (StringUtils.hasText(constuct_project_dep_company)) {
			sbf.append(" and  dep.constuct_project_dep_company = " + constuct_project_dep_company+ "");
		}
		
		String construct_purchase_status = request.getParameter("construct_purchase_status");
		if (StringUtils.hasText(construct_purchase_status)) {
			sbf.append(" and  pur.construct_purchase_status = " + construct_purchase_status+ "");
		}
		
		sbf.append(" GROUP BY pur.construct_purchase_id ) tableAll ORDER BY tableAll.construct_purchase_creatTime DESC");

	
		
		List<Map<String, Object>> data = purchaseService.purchaseList(filds,
				pageIndex, pageSize, record, sbf.toString());
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
		
		
		
	}
	
}
