package com.casd.controller.construct;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.common.excel.ImportExcel;
import com.casd.entity.construct.CoQuantities;
import com.casd.service.construct.CoQuantitieService;
import com.casd.service.construct.ConstructService;

@Controller
@RequestMapping("/admin")
public class ManageController {

	@Autowired
	private ConstructService constructService;

	@Autowired
	private CoQuantitieService coQuantitieService;

	private String project_id;
	/**
	 * 建设公司项目list
	 */
	@RequestMapping(value = "/build_manaConList", method = RequestMethod.GET)
	public ModelAndView build_manaConList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/manaConList");
		return mv;
	}

	@RequestMapping(value = "/build_manaConList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> build_manaConList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_project_table pro left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep where dep.constuct_project_dep_company=1  ");
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and pro.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		
		
		
		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}
	
	/**
	 * 发展公司运营项目list
	 */
	@RequestMapping(value = "/deve_manaConList", method = RequestMethod.GET)
	public ModelAndView deve_manaConList(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/manaConList");
		return mv;
		
	}

	@RequestMapping(value = "/deve_manaConList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deve_manaConList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_project_table pro left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep where dep.constuct_project_dep_company=2  ");
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and pro.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}
	
	
	
	/**
	 * 发展公司所有项目list
	 */
	@RequestMapping(value = "/deveManaConList", method = RequestMethod.GET)
	public ModelAndView deveManaConList(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("construct/deveManaConList");
		return mv;
		
	}

	@RequestMapping(value = "/deveManaConList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deveManaConList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_project_table pro left join construct_project_dep dep on dep.constuct_project_dep_id=pro.construct_project_dep where   dep.constuct_project_dep_company=3 ");
		String construct_project_name = request.getParameter("construct_project_name");
		if (StringUtils.hasText(construct_project_name)) {
			sbf.append(" and pro.construct_project_name like '%" + construct_project_name
					+ "%'");
		}
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;

	}
	
	
	/**
	 * 合同工程量list
	 */
	@RequestMapping(value = "/conMaterialList", method = RequestMethod.GET)
	public ModelAndView conMaterialList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		project_id = request.getParameter("construct_project_id");
		mv.addObject("project_id", project_id);
		mv.setViewName("construct/conMaterialList");
		return mv;
	}

	@RequestMapping(value = "/conMaterialList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> conMaterialList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		String projectIdString = request.getParameter("construct_project_id");
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(" ( select qua.construct_project_quantities_conId,qua.construct_project_quantities_id, qua.construct_project_quantities_modelId, qua.construct_project_quantities_num,qua.construct_project_quantities_auxiliaryNum,qua.construct_project_quantities_price,qua.construct_project_quantities_remarks, ");
		sbf.append(" model.construct_material_model_name,model.construct_material_model_unit,material.construct_material_name,series.construct_material_seriesName ,series.construct_material_seriesId,(concat(ifNULL(qua.construct_project_quantities_name,''),'-',ifNULL(qua.construct_project_quantities_model,''))) oldMaterial");
		sbf.append(" ,( select sum(entry.construct_purchase_applyNum) from construct_purchase_entry entry where entry.construct_purchase_quantitiesId= qua.construct_project_quantities_id ) purNum ");
		sbf.append(" from ");
		sbf.append(" construct_project_quantities qua ");
		sbf.append( " left join construct_material_model model on qua.construct_project_quantities_modelId=model.construct_material_model_id ");
		sbf.append( " left join construct_material_table material on material.construct_material_id=model.construct_material_model_parentid ");
		sbf.append( " left join construct_material_series series on series.construct_material_seriesId=material.construct_material_seriesId ");

		sbf.append( "  where construct_project_quantities_conId="+projectIdString+"");
		
		
		String construct_project_quantities_name = request.getParameter("construct_project_quantities_name");
		if (StringUtils.hasText(construct_project_quantities_name)) {
			sbf.append(" and material.construct_material_name like '%" + construct_project_quantities_name
					+ "%'");
		}
		String construct_material_seriesName = request.getParameter("construct_material_seriesName");
		if (StringUtils.hasText(construct_material_seriesName)) {
			sbf.append(" and material.construct_material_seriesName like '%" + construct_material_seriesName
					+ "%'");
		}
		
		String construct_project_quantities_model = request.getParameter("construct_project_quantities_model");
		if (StringUtils.hasText(construct_project_quantities_model)) {
			sbf.append(" and material.construct_material_model_name like '%" + construct_project_quantities_model
					+ "%'");
		}
		
		sbf.append("  order BY  series.construct_material_seriesId,material.construct_material_name ");
		sbf.append(" ) tableAll where 1=1 ");
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	// 选择材料
	@RequestMapping(value = "/materialListCheck", method = RequestMethod.GET)
	public ModelAndView materialListCheck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("checkBox/materialListCheck");
		return mv;
	}

	@RequestMapping(value = "/materialListCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> materialListCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		Map<String, Object> json = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" construct_material_series series left join  construct_material_table cmt on series.construct_material_seriesID=cmt.construct_material_seriesId left join construct_material_model cmm on cmt.construct_material_id=cmm.construct_material_model_parentid where 1=1  ");
		String construct_material_name=request.getParameter("construct_material_name");
		String construct_material_model_name=request.getParameter("construct_material_model_name");
		String construct_material_seriesName=request.getParameter("construct_material_seriesName");

		if (StringUtils.hasText(construct_material_seriesName)) {
			sbf.append(" and series.construct_material_seriesName like'%"+construct_material_seriesName+"%'");
		}
		if (StringUtils.hasText(construct_material_name)) {
			sbf.append(" and cmt.construct_material_name like'%"+construct_material_name+"%'");
		}
		if (StringUtils.hasText(construct_material_model_name)) {
			sbf.append(" and cmm.construct_material_model_name like'%"+construct_material_model_name+"%'");
		}
		
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;

		List<Map<String, Object>> data = constructService.constructList(
				pageIndex, pageSize, record, sbf.toString());

		json.put("rows", data);
		json.put("total", record.getValue());
		return json;
	}

	/**
	 * 新增合同材料
	 * */
	@RequestMapping(value = "/add_quantities", method = RequestMethod.POST)
	@ResponseBody
	public Object add_quantities(HttpServletRequest request, CoQuantities cq) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
		Integer conId=Integer.parseInt(request.getParameter("conId"));
		Integer id=Integer.parseInt(request.getParameter("id"));
		Integer  modelId=Integer.parseInt(request.getParameter("modelId"));

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("conId", conId);
		map.put("modelId", modelId);
		int existNum=0;
		if (id==0) {
			existNum=coQuantitieService.existQuan(map);

		}
		if (existNum==0) {
			String name=request.getParameter("name");
			String model=request.getParameter("model");
			String unit=request.getParameter("unit");
			BigDecimal num=BigDecimal.valueOf(Double.valueOf(request.getParameter("num").toString()));
			BigDecimal auxiliaryNum=BigDecimal.valueOf(Double.valueOf(request.getParameter("auxiliaryNum").toString()));
			BigDecimal price=BigDecimal.valueOf(Double.valueOf(request.getParameter("price")));
			String remarks=request.getParameter("remarks");

			cq.setConstruct_project_quantities_conId(conId);
			cq.setConstruct_project_quantities_id(id);
			cq.setConstruct_project_quantities_name(name);	
			cq.setConstruct_project_quantities_model(model);
			cq.setConstruct_project_quantities_modelId(modelId);
			cq.setConstruct_project_quantities_unit(unit);	
			cq.setConstruct_project_quantities_num(num);
			cq.setConstruct_project_quantities_auxiliaryNum(auxiliaryNum);
			cq.setConstruct_project_quantities_price(price);
			cq.setConstruct_project_quantities_remarks(remarks);
			coQuantitieService.addquantities(cq);
			json.put("Success", 0);
			json.put("Msg", "操作成功");
		}else if(existNum==1) {
			json.put("Success", 1);
			json.put("Msg", "存在相同材料，数量是否合并！");
		}else if (existNum>1) {
			json.put("Success", 2);
			json.put("Msg", "旧数据，存在多条相同材料，请先处理再添加！");
		}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", 2);
			json.put("Msg", "操作失败！"+e);
			
		}
		return json;
	}
	
	/**
	 * 合并合同材料
	 * */
	@RequestMapping(value = "/sum_quantities", method = RequestMethod.POST)
	@ResponseBody
	public Object sum_quantities(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
		Integer conId=Integer.parseInt(request.getParameter("conId"));
		Integer num=Integer.parseInt(request.getParameter("num"));
		Integer auxiliaryNum=Integer.parseInt(request.getParameter("auxiliaryNum"));
		Integer  modelId=Integer.parseInt(request.getParameter("modelId"));

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("conId", conId);
		map.put("modelId", modelId);
		map.put("num", 0);
		map.put("auxiliaryNum", auxiliaryNum);
		coQuantitieService.sum_quantities(map);
		json.put("Success", true);
		json.put("Msg", "添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "操作失败！"+e);
			
		}
		return json;
	}
	

	@RequestMapping(value = "/delete_quantities", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_quantities(HttpServletRequest request) throws Exception {
		   
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cid = request.getParameter("cid");
			if(coQuantitieService.isExistPur(cid)==0){
				StringBuffer sbf = new StringBuffer();
				sbf.append(" where construct_project_quantities_id = "+cid+" ");
				coQuantitieService.deleteCoQuantitie(sbf.toString());
			}else {
				map.put("msg", "已存在采购单，不可删！");
			}
		} catch (Exception e) {
			map.put("msg", "删除失败,请联系管理员！");
		}

		return map;
		
	
		/*try {	
		String cid = request.getParameter("cid");
		cid = cid.substring(1);
		String[] ids = cid.split("]");
		StringBuffer sbf = new StringBuffer();
		sbf.append(" where construct_project_quantities_id in(" + ids[0] + ")");
		coQuantitieService.deleteCoQuantitie(sbf.toString());
		} catch (Exception e) {
			json.put("msg", "删除失败！");
			e.printStackTrace();
		}
		return null;*/
	}
	
	
	/**
	 * 方法说明：将Excel表格数据
	 * 导入construct_project_quantities表中
	 * 
	 * */
		@RequestMapping(value="/cp_quantities_exl" , method = RequestMethod.POST)
		@ResponseBody
		public  Map<String, Object> cp_quantities_exl(MultipartFile pic, HttpServletRequest request) throws IOException {
			Map<String, Object> json = new HashMap<String, Object>();
			
		   try {

	            // 获取原始文件名  
	            String fileName = pic.getOriginalFilename();  
	           
	            String quantities_conId = request.getParameter("quantities_conId");
	              
			
				List<List<Object>> list = new ArrayList<List<Object>>();
				ImportExcel ie = new ImportExcel();
				
				if(StringUtils.isEmpty(fileName)){
					json.put("Success", false);
					json.put("Msg", "请选择要导入的文件");
					return json;
				}
				
				if(!fileName.substring(fileName.lastIndexOf(".")).equals(".xlsx")){
					json.put("Success", false);
					json.put("Msg", "请选择Excel2007以上版本文件.xlsx");
					return json;
				}
		     
	  

	        	File file2=new File("e:/uploadfile");	
	            if(!file2.exists()) {  
	            	file2.mkdirs();  
	            }  
	            
	            //定义文件路径
	            String fileUploadBasePath = "e:/uploadfile/";
	            File newFile = new File(fileUploadBasePath + System.currentTimeMillis() + fileName);
	           
	            pic.transferTo(newFile);  // 写入文件到服务器目录
	          
	        	list = ie.read2007Excel(newFile);  //读取exl表格数据
	            

				//读取完信息删除上传文件 
			    // 判断文件存在,删除  
			    if (newFile.exists()) {
			    	newFile.delete();
			    }
		
			    List<Map<String, Object>> stockRecord = new ArrayList<Map<String,Object>>();
			    for (int i = 0; i < list.size()-1; i++) {
			    	List<Object> eobj = list.get(i);
			 
			    	
			  //有标题 所以必须i>3 去掉标题行
			      if (i>3) {
			    	/*Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("quantities_name", eobj.get(2));//材料名称
			    	map.put("quantities_model", eobj.get(3));//型号规格
			    	map.put("quantities_num", eobj.get(6));//数量
			    	map.put("quantities_unit", eobj.get(5));//计量单位
			    	map.put("quantities_price", eobj.get(8));//单价
			    	map.put("quantities_conId", quantities_conId) ;//项目编号
			   	    stockRecord.add(map);*/
				}
			    
			    }
			    
			 coQuantitieService.insert(stockRecord);

				json.put("Success", true);
				json.put("Msg", "导入成功");
		   } catch (Exception e) {
			    json.put("Success", false);
				json.put("Msg", "导入失败"+e);
			}
		    return json;
		}

}
