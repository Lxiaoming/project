package com.casd.controller.uc;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.activiti.DataProcinst;
import com.casd.entity.uc.Costapp;
import com.casd.entity.uc.User;
import com.casd.service.hr.ActivitiService;
import com.casd.service.manage.SupOpinionService;
import com.casd.service.uc.CostappService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/admin")
public class CostappController<costapp> {

	@Autowired
	private CostappService costappService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private SupOpinionService supOpinionService;
	@Autowired
	private RuntimeService runtimeService;
	

	@RequestMapping(value = "/costappList", method = RequestMethod.GET)
	public ModelAndView costappList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("uc/costappList");
		return mv;

	 }

	@RequestMapping(value = "/costappLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> costappList(HttpServletRequest request)
			throws Exception {
		
		User user = (User) request.getSession().getAttribute("loginUser");
		
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));

		StringBuilder sBuilder = new StringBuilder();
		Ref<Integer> record = new Ref<Integer>();
		String fields = "*";
		sBuilder.append(" uc_costapp ");
		sBuilder.append(" where 1 = 1 ");
		sBuilder.append(" and userid = '"+user.getUserid()+"' ");
		String costapp_company = request.getParameter("costapp_company");
		if (StringUtils.hasText(costapp_company)) {
			sBuilder.append(" and costapp_company like '%" + costapp_company
					+ "%'");
		}
		sBuilder.append(" order by costapp_id desc ");
	
		// 部门列表
		List<Map<String, Object>> data = costappService.costappList(page,
				pageSize, record, fields, sBuilder.toString());
		
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;

	}

	/**
	 * 费用申请新增界面,修改界面
	 * 
	 * @throws Exception
	 * 
	 * */
	@RequestMapping(value = "/costappNew", method = RequestMethod.GET)
	public ModelAndView costappNew(HttpServletRequest request, Model model)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();


		if (request.getParameter("costapp_id") != "") {
			int costapp_id = Integer.valueOf(request.getParameter("costapp_id")
					.toString());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costapp_id", costapp_id);
			data = costappService.getData(map);
			model.addAttribute("data", data.get(0));
			mv.addObject("edit", false);
		} else {
			mv.addObject("edit", true);
		}
		// mv.addObject("userList", userList);
		Object startForm = activitiService.getRenderedStartForm("costappView:4:590017");
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		String[] temp = new String[]{};
		temp = new String[] {"申请人", "专业公司总经理", "总裁", "董事长", "诚安资本(出纳)"};				
		for (String t : temp) {
				Map<String, Object> activityMap = new HashMap<String, Object>();
				activityMap.put("name", t);
				activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		mv.addObject("taskName", "申请人");
		mv.addObject("startForm",startForm);
		mv.setViewName("uc/costappNew");
		return mv;

	}

	/**
	 * 申请查看界面
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping(value = "/costappView{taskid}", method = RequestMethod.GET)
	public ModelAndView costappView(HttpServletRequest request,String taskid,String taskName) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		Object startForm = null;
		List<Map<String, Object>> historyList;
		double costapp_amount = 0;
		if(taskid == null){
			int costapp_id = Integer.valueOf(request.getParameter("bizId")
					.toString());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costapp_id", costapp_id);
			List<Map<String, Object>> data = costappService.getData(map);
			mv.addAllObjects(data.get(0));
			String beyId = "costappView"; // 流程实例key 请勿改动
			historyList = activitiService.viewHisComments(String.valueOf(costapp_id), beyId);
//			startForm = activitiService.getRenderedStartForm("costappView:3:542510");
		}else{
			String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号
			String[] strs = bizkey.split("\\.");
			String bizId = null; // 业务编号
			for (int i = 0, len = strs.length; i < len; i++) {
				bizId = strs[i].toString();
			}
			String fields="*";
			String where=" uc_costapp c left join uc_user u on c.userid = u.userid where costapp_id="+bizId;
			Map<String, Object> param=costappService.findCostapp(fields, where);		
			startForm = activitiService.getRenderedTaskForm(taskid); // 获取表单
			mv.addAllObjects(param);
			costapp_amount = Double.valueOf(String.valueOf(param.get("costapp_amount")));
			historyList = activitiService.getProcessComments(taskid);
		}
		// 审核历史意见
	    JSONArray jsonObject = new JSONArray();
		if (historyList!=null) {
			jsonObject=JSONArray.fromObject(historyList);
			
		}
		//流程线
		List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();// 获取节点
		String[] temp = new String[]{};
		if(costapp_amount >= 10000){
			temp = new String[] {"申请人", "专业公司总经理", "总裁", "董事长", "诚安资本(出纳)"};				
		}else {
			temp = new String[] {"申请人", "专业公司总经理", "总裁", "诚安资本(出纳)"};				
		}
		for (String t : temp) {
				Map<String, Object> activityMap = new HashMap<String, Object>();
				activityMap.put("name", t);
				activityList.add(activityMap);
		}
		mv.addObject("activityList", activityList);
		mv.addObject("history", jsonObject);		
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
		mv.addObject("startForm",startForm);
		mv.setViewName("uc/costappView");
		return mv;
	}

	/**
	 * 费用申请删除
	 * 
	 * */
	@RequestMapping(value = "/delect_costapp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delect_costapp(HttpServletRequest request) {
	
		Map<String, Object> json = new HashMap<String, Object>();

		try {
			String costapp_id = request.getParameter("costapp_id");
			System.out.println(costapp_id);
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuffer sbf = new StringBuffer();
			sbf.append(" uc_costapp where costapp_id=" + costapp_id);
			map.put("where", sbf);
			costappService.delete_data(map);
            activitiService.deleteRecord(costapp_id, "costappView");
			json.put("Success", true);
			json.put("Msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "保存失败");
        }

		return json;

	}

	/**
	 * 申请保存
	 * 
	 * */
	@RequestMapping(value = "/startCostapp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> startCostapp(HttpServletRequest request,Costapp costapp)
			throws Exception {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			costappService.startCostapp(request, costapp);
			json.put("Success", true);
			json.put("Msg", "保存成功");
		    }catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "保存失败");
		  }
		return json;
	   }

	/**
	 * 申请办理
	 * 
	 * */
	@RequestMapping(value = "/Costapp_pass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> Costapp_pass(HttpServletRequest request)
			throws Exception {

		Map<String, Object> json = new HashMap<String, Object>();
		try {
			costappService.Costapp_pass(request);
			json.put("Success", true);
			json.put("Msg", "保存成功");
		    }catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "保存失败");
		  }
		return json;
	   }
	
	
	@RequestMapping(value = "/costappAudit", method = RequestMethod.GET)
	public ModelAndView costappAudit(String taskid,String taskName) {
		ModelAndView mv = new ModelAndView();
		
		String bizkey = activitiService.getBusinessBizId(taskid);// 获取业务编号

		String[] strs = bizkey.split("\\.");
		String bizId = null; // 业务编号
		for (int i = 0, len = strs.length; i < len; i++) {
			bizId = strs[i].toString();
		}
		String fields="*";
		String where=" uc_costapp where costapp_id="+bizId;
		Map<String, Object> param=costappService.findCostapp(fields, where);		
		List<Map<String, Object>> userList = supOpinionService.supOpinionUser(
				taskName, "costappView"); //查询审核人	
		
		List<String> bot = activitiService.findOutComeListByTaskId(taskid);// 查看当前有几条线	
		
		mv.addAllObjects(param);
		
		mv.addObject("taskid", taskid);
		mv.addObject("taskName", taskName);
	    mv.addObject("userList",userList);  
	    mv.addObject("bot",bot);  
		mv.setViewName("uc/costappAudit");
		return mv;
	  }
	
	     /**
	      * 费用申请审核
	      * 
	      * */
    	@RequestMapping(value = "/pass_costapp", method = RequestMethod.POST)
    	@ResponseBody
	  public Map<String, Object> pass_costapp(HttpServletRequest request,Costapp costapp) {
    		Map<String, Object> json=new HashMap<String, Object>();
    		try {
			    Map<String, Object> map=new HashMap<String, Object>();
    			String taskName=request.getParameter("taskName");
    			if (!taskName.equals("申请人")&&!taskName.equals("管理公司")){
	    			User user=(User) request.getSession().getAttribute("loginUser");//当前办理人
				    String auditor=request.getParameter("auditor").toString();
			    	String param=request.getParameter("param").toString()+"--"
			    			+user.getUsername()+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
	
			    	String costapp_id=request.getParameter("costapp_id").toString();
			    	
				    StringBuffer stringBuffer=new StringBuffer();
				    stringBuffer.append( auditor+" = '"+param +"' where costapp_id="+costapp_id+" ");
				    map.put("what", stringBuffer);
    			}
    	       costappService.pass_costapp(request, costapp,map);
    	   	json.put("Success", true);
			json.put("Msg", "已审核!");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("Success", false);
			json.put("Msg", "程序出错,请联系技术员");
	    	}
			return json;
	    }
	  
    	
    	/**
    	 * 打印费用申请表
    	 * @throws DocumentException 
    	 * @throws IOException 
    	 * 
    	 * */
    	@RequestMapping(value = "/costappPrint", method = RequestMethod.GET)
        public void costappPrint(HttpServletResponse response,String biz,HttpServletRequest request) throws IOException, DocumentException{
    		
    		OutputStream out=null;
    		ByteArrayOutputStream baos=null;
    	try {
		
    		String fields="us.username,cst.costapp_company,cst.costapp_application,";
    		fields +="cst.costapp_appitem,cst.costapp_amount,cst.costapp_majoyview,";
    		fields +="cst.costapp_managerview,cst.costapp_financeview,";
    		fields +="cst.costapp_assistant,cst.costapp_chairmanview,";
    		fields +="cst.costapp_time ";
    		

    		StringBuffer sbf=new StringBuffer();
    		sbf.append(" uc_costapp cst LEFT JOIN  uc_user us on cst.userid=us.userid");
    		sbf.append(" WHERE cst.costapp_id="+biz);
    		System.out.println(fields);
    		System.out.println(sbf);
    		Map<String, Object> dataMap=costappService.findCostapp(fields,sbf.toString());
    		
    		String beyId = "costappView"; // 流程实例key 请勿改动

  			List<Map<String, Object>> historyList = activitiService
  				.viewHisComments(biz, beyId);
    		System.out.println(historyList);
  			baos=this.simplePDF(request,dataMap,historyList); 
		//设置请求返回类型 
		String fileName=System.currentTimeMillis()+".pdf";
		response.setContentType("application/pdf"); 
		response.setHeader("Content-Disposition", "attachment;filename="+fileName); 
		response.setContentLength(baos.size()); 
		 out = response.getOutputStream(); 
		baos.writeTo(out); 
		out.flush(); 
		out.close(); 
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (out!=null) {
				out.close(); 
			}if(baos!=null){
				baos.close();
			}
			
			
			
		}
    	  
    	  

    	 
		}
    	
    	
    	
         //下载输出
	public ByteArrayOutputStream simplePDF(HttpServletRequest request,Map<String, Object> dataMap,List<Map<String, Object>> historyList) {
		System.out.println(JSONObject.fromObject(dataMap));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,baos);
			document.open();

			PdfPTable table = new PdfPTable(4); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			// Set Column widths
			float[] columnWidths = { 1f, 1f, 1f,1f};
			table.setWidths(columnWidths);

			String realPath = request.getSession().getServletContext().getRealPath("/");
			String fontPath =realPath+"res/print/simsun.ttc";
	        BaseFont bfChinese = BaseFont.createFont(fontPath+",1",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	  
	        Font yahei1px = FontFactory.getFont(fontPath+",1", BaseFont.IDENTITY_H, 20);
	        Font yahei10px = new Font(bfChinese, 12.0F, 0);
			
			PdfPCell cell = cell("费用报销", yahei1px, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE);
			cell.setColspan(2);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(4);
			table.addCell(cell);
	        
			PdfPCell cell1 = new PdfPCell(cell("申请人",yahei10px));
			table.addCell(cell1);
			
            PdfPCell cell2 = new PdfPCell(cell(dataMap.get("username")+"",yahei10px));
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(cell("申请时间",yahei10px));
            table.addCell(cell3);
            
            PdfPCell cell4 = new PdfPCell(cell(dataMap.get("costapp_time")+"",yahei10px));
            table.addCell(cell4);
            
            PdfPCell cell21 = new PdfPCell(cell("公司/部门",yahei10px));
            table.addCell(cell21);
            
            PdfPCell cell22 = new PdfPCell(cell(dataMap.get("costapp_company")+"",yahei10px));
            cell22.setColspan(3);
            table.addCell(cell22);
            
            PdfPCell cell31 = new PdfPCell(cell("申请类型",yahei10px));
            table.addCell(cell31);
            
            PdfPCell cell32 = new PdfPCell(cell(dataMap.get("costapp_application")+"",yahei10px));
            table.addCell(cell32);
            
            PdfPCell cell33 = new PdfPCell(cell("费用金额",yahei10px));
            table.addCell(cell33);
            
            PdfPCell cell34 = new PdfPCell(cell(dataMap.get("costapp_amount")+"",yahei10px));
            table.addCell(cell34);
            
            PdfPCell cell41 = new PdfPCell(cell("费用金额(大写)",yahei10px));
            table.addCell(cell41);
            
            PdfPCell cell42 = new PdfPCell(cell(digitUppercase(Double.parseDouble(dataMap.get("costapp_amount")+"")),yahei10px));
            cell42.setColspan(3);
            table.addCell(cell42);
            
            PdfPCell cell51 = new PdfPCell(cell("申请事项",yahei10px));
            table.addCell(cell51);
            
            PdfPCell cell52 = new PdfPCell(new Phrase(dataMap.get("costapp_appitem")+"",yahei10px));
            cell52.setColspan(3);
            cell52.setBorderColor(new BaseColor(196, 196, 196));
            cell52.setPadding(5.0f);
            cell52.setMinimumHeight(50);  
            cell52.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell52.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell52);
            
            for (int i = 0; i < historyList.size(); i++) {
	            PdfPCell cell61 = new PdfPCell(cell("申请人".equals(historyList.get(i).get("name_"))?"申请人请款":historyList.get(i).get("name_")+"意见",yahei10px));
	            table.addCell(cell61);
	            
	            PdfPCell cell62 = new PdfPCell(new Phrase(historyList.get(i).get("MESSAGE_")+"",yahei10px));
	            cell62.setColspan(3);
	            cell62.setBorderColor(new BaseColor(196, 196, 196));
	            cell62.setPadding(5.0f);
	            cell62.setMinimumHeight(50);  
	            cell62.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell62.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell62);
            }

			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos;
	}
    
    	//
     private static PdfPCell cell(String content, Font font) {
  	        PdfPCell cell = new PdfPCell(new Phrase(content, font));
  	        cell.setBorderColor(new BaseColor(196, 196, 196));
  	        cell.setPadding(5.0f);
  	        cell.setMinimumHeight(50);  
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

  	        
  	        cell.setPaddingTop(1.0f);
  	      /*  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
  	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
*/  	        return cell;
  	    }

  	    private static PdfPCell cell(String content, Font font, int hAlign, int vAlign) {
  	        PdfPCell cell = new PdfPCell(new Phrase(content, font));
  	        cell.setBorderColor(new BaseColor(196, 196, 196));
  	        cell.setVerticalAlignment(vAlign);
  	        cell.setHorizontalAlignment(hAlign);
  	        cell.setPadding(5.0f);
  	        cell.setPaddingTop(1.0f);
  	        return cell;
  	    }
  	
  	//金额转换大小写
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

}
