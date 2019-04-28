package com.casd.controller.manage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.casd.controller.web.utils.DownloadUtil;
import com.casd.entity.manage.AchReview;
import com.casd.entity.manage.Supplierform;
import com.casd.entity.uc.User;
import com.casd.service.manage.SupplierformService;

@Controller
@RequestMapping("/admin")
public class SupplierformController {

	@Autowired
	private SupplierformService supplierformService;
	
	
	/**
	 *  列表
	 * 
	 * */
	@RequestMapping(value = "/supplierformList", method = RequestMethod.GET)
	public ModelAndView supplierformList() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Calendar now = Calendar.getInstance();
		int yearNum = now.get(Calendar.YEAR)-2016;
		for (int j = 0; j < yearNum+1; j++) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("yearMon", 2016+j);
			list.add(map);
		}
		
		mv.addObject("yearMon", list);
		mv.setViewName("manage/supplierformList");
		return mv;

	}
	
	@RequestMapping(value = "/supplierformList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supplierformList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		StringBuilder sBuilder = new StringBuilder();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int pageIndex = Integer.parseInt(request.getParameter("page")) - 1;
		Ref<Integer> record = new Ref<Integer>();
		String fields = "*";
		sBuilder.append(" manage_supplierform ");
		sBuilder.append(" where 1=1");
		
		String supplierform_date = request.getParameter("supplierform_date");
		if (StringUtils.hasText(supplierform_date)) {
			sBuilder.append(" and supplierform_date like '%" + supplierform_date + "%'");
		}
		List<Map<String, Object>> data = supplierformService.supplierformList(pageIndex,
				pageSize, record, fields, sBuilder.toString());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object>footMap = new HashMap<String, Object>();
           list.add(footMap);
		jsonMap.put("footer", list);
		jsonMap.put("rows", data);
		jsonMap.put("total", record.getValue());
		
		return jsonMap;
		}
	/**
	 *  查看界面
	 * 
	 * */
	@RequestMapping(value = "/supplierformView", method = RequestMethod.GET)
	public ModelAndView supplierformView(HttpServletRequest request ,Model model) {
		int supplierform_id = Integer.valueOf(request.getParameter("bizId").toString());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierform_id", supplierform_id);
		List<Map<String, Object>> data=supplierformService.getData(map);
		model.addAttribute("data", data.get(0));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manage/supplierformView");
		return mv;
	}
	
	
	/**
	 *  新增，修改合同
	 * 
	 * */
	@RequestMapping(value = "/supplierformNew", method = RequestMethod.GET)
	public ModelAndView supplierformNew(HttpServletRequest request ,Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		String supplierform_id = request.getParameter("supplierform_id");
		Map<String, Object> supplierform=new HashMap<String, Object>();
		String type="";
		if(supplierform_id.equals("")){
			supplierform.put("supplierform_id", 0);
			type="'new'";
		}else {
			supplierform =supplierformService.getData(supplierform_id);
			type = request.getParameter("type");
		}
		mv.addObject("type", type);
		mv.addObject("supplierform", supplierform);
		mv.setViewName("manage/supplierformNew");
		return mv;

	}
	
	
	/**
	 * 方法说明：上传
	 * 
	 * 
	 * */
	@RequestMapping(value = "/uup", method = RequestMethod.POST)
	@ResponseBody
	public Object uup(MultipartFile pic,HttpServletRequest request,HttpServletResponse response) {
		
			Map<String, Object> json = new HashMap<String, Object>();
			int supplierform_id =Integer.valueOf(request.getParameter("supplierform_id").toString()==""?"0":request.getParameter("supplierform_id").toString());
			try {
				// 获取原始文件名
				String fileName = pic.getOriginalFilename();
				if (StringUtils.isEmpty(fileName)) {
					json.put("Success", false);
					json.put("Msg", "请选择文件");
					return json;
				}

				if (!fileName.substring(fileName.lastIndexOf(".")).equals(".pdf")) {
					json.put("Success", false);
					json.put("Msg", "请选择文件格式为.pdf");
					return json;
				}
		
				File file2 = new File("e:/uploadfile/photo/LaborDivision");
				if (!file2.exists()) {
					file2.mkdirs();
				}
				User user = (User) request.getSession().getAttribute("loginUser");
				HashMap<String, Object> map=new HashMap<String, Object>();
				String fields="*";
				StringBuffer sBuffer=new StringBuffer();
				sBuffer.append(" uc_center where center_id = "+user.getCenter_id()+" ");
				map.put("fields", fields);
				map.put("where", sBuffer.toString());
				List<Map<String, Object>> centerList=supplierformService.getCenter(map);
				
				Date date=new Date(System.currentTimeMillis());
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String createdate = sdf.format(date);	    
				Supplierform supplierform=new Supplierform();
				supplierform.setSupplierform_fileway(fileName);
				supplierform.setSupplierform_date(createdate);
				supplierform.setSupplierform_id(supplierform_id);
				supplierform.setSupplierform_loadcenter(centerList.get(0).get("center_name").toString());
				supplierformService.saveSupplierform(supplierform);
				// 定义文件路径
				String fileUploadBasePath =file2.toString();
				File newFile = new File(fileUploadBasePath +"/"+ fileName);
				pic.transferTo(newFile); // 写入文件到服务器目录
				json.put("Success", true);
				json.put("Msg", "上传成功");
				if (supplierform_id==0) {
					json.put("ID", supplierform.getSupplierform_id());

				}else {
					json.put("ID", supplierform_id);

				}
				
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "上传失败" + e);
			}
			return json;
		}



	/**
	 *  删除
	 * 
	 * */
	@RequestMapping(value = "/delete_Supplierform", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_Supplierform(HttpServletRequest request)
			throws Exception {
		String supplierform_id = request.getParameter("supplierform_id");
		
		String supplierform_fileway = request.getParameter("supplierform_fileway");

		File file = new File("e:/uploadfile/photo/LaborDivision/" + supplierform_fileway);
		if (file.exists() && file.isFile())
			file.delete();
		supplierformService.delete_Supplierform(supplierform_id);
		return null;
		
	}
}
