package com.casd.controller.ownCenter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.ownCenter.OwnPurchaseEntry;
import com.casd.entity.ownCenter.OwnPurchaseHead;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.ownCenter.OwnHeadService;

@Controller
@RequestMapping("/admin")
public class OwnPurchaseController {
	
	@Autowired
	private OwnHeadService ownHeadService;
	
	@Autowired
	private ActivitiService  activitiService;
	
	@Autowired
	private SupOpinionService supOpinionService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@RequestMapping(value = "/ownHeadList", method = RequestMethod.GET)
	public ModelAndView ownHeadList() {
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ownCenter/ownHeadList");
		return mv;

	}
	@RequestMapping(value = "/ownHeadLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>  ownHeadList(HttpServletRequest request) throws Exception {
		
		User user = (User) request.getSession().getAttribute("loginUser");
		
		Ref<Integer> record = new Ref<Integer>();
		
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
	
		String company_name=request.getParameter("company_name");
		String own_purchase_planMan=request.getParameter("own_purchase_planMan");
		String construct_project_name=request.getParameter("construct_project_name");

		
		String fields = "head.*,pro.construct_project_name,cpy.company_name";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" own_purchase_head head");
		sbf.append(" left join construct_project_table pro on head.own_purchase_projectId=pro.construct_project_id");
		sbf.append(" LEFT JOIN uc_company cpy ON cpy.company_id=head.own_purchase_companyId");
		sbf.append(" where ");
		sbf.append(" head.own_purchase_planMan like '%"+user.getUsername()+"%' ");
		if (StringUtils.isNotBlank(company_name)) {
			sbf.append(" AND cpy.company_name LIKE'%"+company_name+"%'");
		}
		if (StringUtils.isNotBlank(own_purchase_planMan)) {
			sbf.append(" AND head.own_purchase_planMan LIKE'%"+own_purchase_planMan+"%'");
		}
		if (StringUtils.isNotBlank(construct_project_name)) {
			sbf.append(" AND pro.construct_project_name LIKE'%"+construct_project_name+"%'");
		}
		  sbf.append(" order by head.own_purchase_id desc");
		
		List<Map<String, Object>> ListData =ownHeadService.ownHeadlist(page, pageSize, record, fields, sbf.toString());
		  JSONArray jsonArray=JSONArray.fromObject(ListData);
		  
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
      }
	
	  //跳转 新增 、 编辑页面
	@RequestMapping(value = "/ownHeadView", method = RequestMethod.GET)
	public ModelAndView ownHeadView(HttpServletRequest request)
			throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		JSONArray jsonArray = new JSONArray();

		Object startForm = null;
		List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> entryList = new ArrayList<Map<String, Object>>();
		if (type.equals("save")) {

			startForm = formService.getRenderedStartForm("ownHeadView:5:590009");
			Map<String, Object> ownHead = new HashMap<String, Object>();
			ownHead.put("own_purchase_status", "-1");
			mv.addAllObjects(ownHead);
			mv.addObject("taskName", "申请人");
		} else {
			// 查询单头
			String bizid = request.getParameter("bizId");
			String fields = "head.*,pro.construct_project_name,cmy.company_name";
			StringBuilder sbf = new StringBuilder();
			sbf.append(" own_purchase_head head LEFT JOIN  construct_project_table pro");
			sbf.append(" on head.own_purchase_projectId=pro.construct_project_id");
			sbf.append(" LEFT JOIN uc_company  cmy ON  cmy.company_id=head.own_purchase_companyId");
			sbf.append(" WHERE head.own_purchase_id=" + bizid);
			Map<String, Object> ownHead = ownHeadService.findOwnHead(fields,sbf.toString());
			// 查询采购单据
			String fields2 = "*";
			String where = " own_purchase_entry entry where entry.own_purchase_parentId=" + bizid;
			entryList = ownHeadService.findOwnEntry(fields2, where);
			jsonArray = JSONArray.fromObject(entryList);
			mv.addAllObjects(ownHead);
			String beyId = "ownHeadView"; // 流程实例key 请勿改动
			historyList = activitiService.viewHisComments(bizid, beyId);
		}
		
		mv.addObject("jsonObject", jsonArray);
		jsonArray = JSONArray.fromObject(historyList);
		mv.addObject("historyList", jsonArray);

		// 根据流程定义ID读取外置表单

		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
				
		String[] temp = new String[] {"申请人", "人力资源中心经理", "总裁/专业公司总经理", "董事长"};				
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		
		mv.addObject("startForm", startForm);
		mv.setViewName("ownCenter/ownHeadView");
		return mv;

	}
	
	@RequestMapping(value = "/saveOwnHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOwnHead(OwnPurchaseHead ownHead,HttpServletRequest request) {
		Map<String, Object> json=new HashMap<String, Object>();
	try {
		 Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		ownHead.setOwn_purchase_time(dateString);
		
		
	ownHeadService.saveOwnHead(ownHead, request);	
		
		json.put("Success", true);
		json.put("Msg", "已提交!");
	} catch (Exception e) {
		e.printStackTrace();
		json.put("Success", false);
		json.put("Msg", "程序出错,请联系技术员");
	}
				
		return json;

	}
	
	
	
	
	@RequestMapping(value = "/ownHeadView{taskid}", method = RequestMethod.GET)
	public ModelAndView ownHeadAudit(String taskid, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String taskName = request.getParameter("taskName");
		String bizid = activitiService.getBusinessBizId(taskid);// 获取业务编号
		if (StringUtils.isNotBlank(bizid)) {
			bizid = bizid.split("\\.")[1];
		}
		List<Map<String, Object>> historyList = activitiService.getProcessComments(taskid);// 查询审批记录

		JSONArray jsonArray = new JSONArray();
		// 查询单头
		String fields = "head.*,pro.construct_project_name,cmy.company_name";
		StringBuilder sbf = new StringBuilder();
		sbf.append(" own_purchase_head head LEFT JOIN  construct_project_table pro");
		sbf.append(" on head.own_purchase_projectId=pro.construct_project_id");
		sbf.append(" LEFT JOIN uc_company  cmy ON  cmy.company_id=head.own_purchase_companyId");
		sbf.append(" WHERE head.own_purchase_id=" + bizid);
		Map<String, Object> ownHead = ownHeadService.findOwnHead(fields,sbf.toString());
		// 查询采购单据
		String fields2 = "*";
		String where = " own_purchase_entry entry where entry.own_purchase_parentId="
				+ bizid;
		List<Map<String, Object>> entryList = ownHeadService.findOwnEntry(fields2, where); // 查询单据
		jsonArray = JSONArray.fromObject(entryList);
		mv.addAllObjects(ownHead);
		mv.addObject("jsonObject", jsonArray);
		Object renderedTaskForm = formService.getRenderedTaskForm(taskid); // 获取表单
		jsonArray = JSONArray.fromObject(historyList);
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
				
		String[] temp = new String[] {"申请人", "人力资源中心经理", "总裁/专业公司总经理", "董事长"};				
		for (String t : temp) {
			Map<String, Object> activityMap = new HashMap<String, Object>();
			activityMap.put("name", t);
			activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		mv.addAllObjects(ownHead);
		mv.addObject("historyList", jsonArray);
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.addObject("startForm", renderedTaskForm);
		mv.setViewName("ownCenter/ownHeadAudit");
		return mv;

	}
	
	@RequestMapping(value = "/ownHeadPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  ownHeadPass(OwnPurchaseHead head,HttpServletRequest request) {
		
      Map<String, Object> json=new HashMap<String, Object>();
			try {
			
				ownHeadService.ownHeadPass(head, request);
				
				json.put("Success", true);
				json.put("Msg", "已提交!");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");
			}
		return json;
	
		
	}
	
	@RequestMapping(value = "/deleteOwnEntry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  deleteOwnEntry(String headId,String entryId,String type) {
		Map<String, Object> json=new HashMap<String, Object>();
		StringBuffer sbf=new StringBuffer();
		try {
			
	
		if (type.equals("head")) {
		   //级联删除
			sbf.append("own_purchase_head where own_purchase_id ="+headId);		
			ownHeadService.deleteEntry(sbf.toString());//级联删除 内容
			String where=" own_purchase_entry  where own_purchase_parentId="+headId;
			ownHeadService.deleteEntry(where);//级联删除 内容
			activitiService.deleteRecord(headId, "ownHeadView");//级联删除流程信息
			
		  }else if(type.equals("entry")){
			sbf.append("own_purchase_entry where own_purchase_entryId ="+entryId);
		    ownHeadService.deleteEntry(sbf.toString());
		 }
		
		json.put("Success", true);
		json.put("Msg", "已删除!");
	} catch (Exception e) {
		e.printStackTrace();
		json.put("Success", false);
		json.put("Msg", "程序出错,请联系技术员");
	}
		return json;
	}
		
	
	/**
	 * 导出单据信息 修改人：Jerry_木子 修改时间：2019年1月15日 方法描述：@param response 方法描述：@param
	 * request 方法描述：@throws Exception
	 * @throws IOException 
	 */
	@RequestMapping(value = "/ownpurchaseExcel", method = RequestMethod.GET)
	@ResponseBody
	public void ownpurchaseExcel(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		
		List<Map<String, Object>> entryList=new ArrayList<Map<String,Object>>();
	   	OutputStream ous = null;
					try {   
		   String bizid=request.getParameter("bizId");
					String fields="head.*,pro.construct_project_name,cmy.company_name";
					StringBuilder sbf=new StringBuilder();
					sbf.append(" own_purchase_head head LEFT JOIN  construct_project_table pro");
					sbf.append(" on head.own_purchase_projectId=pro.construct_project_id");
					sbf.append(" LEFT JOIN uc_company  cmy ON  cmy.company_id=head.own_purchase_companyId");
					sbf.append(" WHERE head.own_purchase_id="+bizid);
				Map<String, Object> ownHead =ownHeadService.findOwnHead(fields, sbf.toString());
				   // 查询采购单据
				   String fields2="*";
				   String where=" own_purchase_entry entry where entry.own_purchase_parentId="+bizid;
			       entryList= ownHeadService.findOwnEntry(fields2, where);
			       
			       
			    
			   	HSSFWorkbook wb = new HSSFWorkbook(); // 定义一个新的工作簿
				HSSFSheet sheet = wb.createSheet("打卡记录"); // 创建第一个Sheet页
				HSSFCellStyle style = wb.createCellStyle();
				HSSFFont font = wb.createFont();
				HSSFRow row = null; // 创建一个行
				HSSFCell cell = null;// 创建列
				font.setFontHeightInPoints((short) 12);// 字号
				font.setFontName("宋体");
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
				style.setFont(font);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边	
				//样式二
				HSSFCellStyle style1 = wb.createCellStyle();
				style1.setFont(font);
				style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
				style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
				style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
				style1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
				style1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边
				
				row = sheet.createRow(0);
				
				for (int i = 0; i < 12; i++) {
					if (i == 0) {
						cell = row.createCell(i);
						cell.setCellValue("普通采购订单");
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(i);
						
						cell.setCellStyle(style);
					}
				}
			       sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); // 单元格合并
			       
			        row = sheet.createRow(1);
					this.addCulek(row, cell,"公司信息："+ownHead.get("company_name"),style1);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11)); // 单元格合并
					 row = sheet.createRow(2);
					String  construct_project_name= ((ownHead.get("construct_project_name")==null)?"":(ownHead.get("construct_project_name")+""));
					this.addCulek(row, cell,"项目信息："+construct_project_name,style1);
					 row = sheet.createRow(3);
					 sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 11)); // 单元格合并
					this.addCulek(row, cell,"材料清单信息：",style1);
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 11)); // 单元格合并
					 row = sheet.createRow(4);
					for (int i = 0; i < 12; i++) {
						if (i==0) {
							cell = row.createCell(i);
							cell.setCellValue("计划日期");
							cell.setCellStyle(style);
						}else if (i==2) {
							cell = row.createCell(i);
							cell.setCellValue(ownHead.get("own_purchase_planDate")+"");
							cell.setCellStyle(style);
						}else if (i==4) {
							cell = row.createCell(i);
							cell.setCellValue("希望送达时间");
							cell.setCellStyle(style);
						}else if (i==7) {
							cell = row.createCell(i);
							cell.setCellValue(ownHead.get("own_purchase_arriveDate")+"");
							cell.setCellStyle(style);
						}else {
							cell = row.createCell(i);
							
							cell.setCellStyle(style);
						}
						sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 6)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 11)); // 单元格合并
					}
					 row = sheet.createRow(5);
					for (int i = 0; i < 12; i++) {
						if (i==0) {
							cell = row.createCell(i);
							cell.setCellValue("材料计划人");
							cell.setCellStyle(style);
						}else if (i==2) {
							cell = row.createCell(i);
							cell.setCellValue(ownHead.get("own_purchase_planMan")+"");
							cell.setCellStyle(style);
						}else if (i==4) {
							cell = row.createCell(i);
							cell.setCellValue("材料品牌");
							cell.setCellStyle(style);
						}else if (i==7) {
							cell = row.createCell(i);
							cell.setCellValue(ownHead.get("own_purchase_brand")+"");
							cell.setCellStyle(style);
						}else {
							cell = row.createCell(i);
							
							cell.setCellStyle(style);
						}
						sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 3)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 6)); // 单元格合并
						sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 11)); // 单元格合并
					}
					 row = sheet.createRow(6);
						for (int i = 0; i < 12; i++) {
							if (i==0) {
								cell = row.createCell(i);
								cell.setCellValue("主管公司");
								cell.setCellStyle(style);
							}else if (i==8) {
								cell = row.createCell(i);
								cell.setCellValue("管理公司");
								cell.setCellStyle(style);
							}else {
								cell = row.createCell(i);
								
								cell.setCellStyle(style);
							}
							sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 7)); // 单元格合并
							sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 10)); // 单元格合并
							
						}
						String str[]={"序号","材料名称","","型号规格","单位","合同工程量","库存",
								      "计划采购量","最低单价","本次单价","小计","注明"};
					
							 row = sheet.createRow(7);
							 for (int j = 0; j < str.length; j++) {
									cell = row.createCell(j);
									cell.setCellValue(str[j]);
									cell.setCellStyle(style);
						         }
							 sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 2)); // 单元格合并
						int i;	 
						double total = 0;
					for (i = 0; i <entryList.size(); i++) {
						    row = sheet.createRow(i+8);
						  double own_purchase_applyNum=  Double.valueOf(entryList.get(i).get("own_purchase_applyNum")+"");
						  double own_purchase_price=Double.valueOf(entryList.get(i).get("own_purchase_price")+"");
							cell = row.createCell(0);
							cell.setCellValue(i+1);
							cell.setCellStyle(style);
							cell = row.createCell(1);
							cell.setCellValue(entryList.get(i).get("own_purchase_material")+"");
							cell.setCellStyle(style);
							cell = row.createCell(2);
							cell.setCellStyle(style);
							cell = row.createCell(3);
							cell.setCellValue(entryList.get(i).get("own_purchase_model")+"");
							cell.setCellStyle(style);
							cell = row.createCell(4);
							cell.setCellValue(entryList.get(i).get("own_purchase_unit")+"");
							cell.setCellStyle(style);
							cell = row.createCell(5);
							cell.setCellValue(entryList.get(i).get("own_purchase_quantities")+"");
							cell.setCellStyle(style);
							cell = row.createCell(6);
							cell.setCellStyle(style);
							cell = row.createCell(7);
							cell.setCellValue(own_purchase_applyNum);
							cell.setCellStyle(style);
							cell = row.createCell(8);
							cell.setCellValue(entryList.get(i).get("own_purchase_leastPrice")+"");
							cell.setCellStyle(style);
							cell = row.createCell(9);
							cell.setCellValue(own_purchase_price);
							cell.setCellStyle(style);
							cell = row.createCell(10);
						
							cell.setCellValue((own_purchase_applyNum*own_purchase_price));
							cell.setCellStyle(style);
							cell = row.createCell(11);
							cell.setCellValue(entryList.get(i).get("own_purchase_remarks")+"");
							cell.setCellStyle(style);
							 sheet.addMergedRegion(new CellRangeAddress(i+8, i+8, 1, 2)); // 单元格合并
							total+=(own_purchase_applyNum*own_purchase_price);
					} 
					//合计
					row = sheet.createRow(i+8);
					for (int j = 0; j < 12; j++) {

						if (j==0) {
							cell = row.createCell(j);
							cell.setCellValue("合计");
							cell.setCellStyle(style);
						}else if (j==10) {
							cell = row.createCell(j);
							cell.setCellValue(total);
							cell.setCellStyle(style);
						}else {
							cell = row.createCell(j);
							cell.setCellStyle(style);
						}
					}
					 sheet.addMergedRegion(new CellRangeAddress(i+8, i+8, 0, 9)); // 单元格合并
			
				String filename = "普通采购订单.xls";// 设置下载时客户端Excel的名称
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename="
						+ java.net.URLEncoder.encode(filename, "UTF-8"));
				ous = new BufferedOutputStream(response.getOutputStream());

				wb.write(ous);
				ous.flush();
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
				// 抛出异常
				response.sendError(500, e.getMessage());
			} finally {
				if (ous != null) {
					ous.close();
				}

			}
				
	}	
	public void addCulek(HSSFRow row,HSSFCell cell,String value,HSSFCellStyle style) {
		
		for (int  i= 0; i < 12; i++) {
			cell = row.createCell(i);
			if (i== 0) {
				cell.setCellValue(value);
				cell.setCellStyle(style);
			}else {
				cell.setCellValue("");
				cell.setCellStyle(style);
			}
	      }
	}
	
	@RequestMapping(value = "/updateOwnPurchaseEntry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  updateOwnPurchaseEntry(OwnPurchaseEntry entry) {
		
      Map<String, Object> json=new HashMap<String, Object>();
			try {
				ownHeadService.updateOwnPurchaseEntry(entry);
				
				json.put("Success", true);
				json.put("Msg", "已修改!");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "程序出错,请联系技术员");
			}
		return json;
	
		
	}
	
}
