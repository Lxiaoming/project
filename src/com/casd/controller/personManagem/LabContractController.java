package com.casd.controller.personManagem;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.utils.PrintUtil;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Controller
@RequestMapping("/admin")
public class LabContractController {

	/**
	 * 页面跳转
	 */

	@RequestMapping(value = "/labContract", method = RequestMethod.GET)
	public ModelAndView labContract(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("personManagem/labContract");
		return mv;
	}

	/**
	 * 打印
	 */
	@RequestMapping(value = "print", method = RequestMethod.POST)
	@ResponseBody
	private String print(HttpServletRequest request) {

		String company = request.getParameter("company");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex").equals("1")?"男":"女";
		String birth_date = request.getParameter("birth_date");	
		String workTime = request.getParameter("workTime");
		String end_time = request.getParameter("end_time");
		String idCard = request.getParameter("idCard");
		String nowLived = request.getParameter("nowLived");
		String phone_number = request.getParameter("phone_number");
		String department = request.getParameter("department");
		String position = request.getParameter("position");
		String workPlace =request.getParameter("workPlace");
		String fixedTerm = request.getParameter("fixedTerm");
		String startContractTime = request.getParameter("startContractTime");
		String endContractTime = request.getParameter("endContractTime");
		String probationary = request.getParameter("probationary");
		String proWages = request.getParameter("proWages");
		String endProWages = request.getParameter("endProWages");
		String registeredAdd = request.getParameter("registeredAdd");
		
		PrintUtil printUtil = new PrintUtil();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		// 模板路径
		realPath = realPath.replaceAll("\\\\", "/"); 
		String templatePath = realPath+"res/print/contract.pdf";
		
		//String newPDFPath = realPath+"res/print/print.pdf";
		
		String newPDFPath = "C:/Users/Administrator/Desktop/print.pdf";
		
		PdfReader reader;
		FileOutputStream out = null;
		ByteArrayOutputStream bos = null;
		PdfStamper stamper;
		int numberOfPages=0;
		try {
			out = new FileOutputStream(newPDFPath); //   输出流
			reader = new PdfReader(templatePath); // 读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			numberOfPages = reader.getNumberOfPages();
			
			String fontPath =realPath+"res/print/simsun.ttc";
			BaseFont bf = BaseFont.createFont(fontPath+",1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);// 加载字体
			java.util.Iterator<String> it = form.getFields().keySet()
                    .iterator();
            while (it.hasNext()){
                String n = it.next().toString();
                System.out.println(n);
            }          
			form.setFieldProperty("company", "textfont", bf, null);
			form.setField("company", company);
			form.setFieldProperty("name", "textfont", bf, null);
			form.setField("name", name);
			form.setFieldProperty("sex", "textfont", bf, null);
			form.setField("sex", sex);
			form.setFieldProperty("birth_date", "textfont", bf, null);
			form.setField("birth_date", birth_date);
			form.setFieldProperty("workTime", "textfont", bf, null);
			form.setField("workTime", workTime);
			/*form.setFieldProperty("end_time", "textfont", bf, null);
			form.setField("end_time", end_time);*/
			form.setFieldProperty("idCard", "textfont", bf, null);
			form.setField("idCard", idCard);
			form.setFieldProperty("nowLived", "textfont", bf, null);
			form.setField("nowLived", nowLived);
			form.setFieldProperty("phone_number", "textfont", bf, null);
			form.setField("phone_number", phone_number);
			form.setFieldProperty("department", "textfont", bf, null);
			form.setField("department", department);
			form.setFieldProperty("position", "textfont", bf, null);
			form.setField("position", position);
			form.setFieldProperty("workPlace", "textfont", bf, null);
			form.setField("workPlace", workPlace);
			form.setFieldProperty("fixedTerm", "textfont", bf, null);
			form.setField("fixedTerm", fixedTerm);
			form.setFieldProperty("startContractTime", "textfont", bf, null);
			form.setField("startContractTime", startContractTime);
			form.setFieldProperty("endContractTime", "textfont", bf, null);
			form.setField("endContractTime", endContractTime);
			form.setFieldProperty("probationary", "textfont", bf, null);
			form.setField("probationary", probationary);
			form.setFieldProperty("proWages", "textfont", bf, null);
			form.setField("proWages", proWages);
			form.setFieldProperty("endProWages", "textfont", bf, null);
			form.setField("endProWages", endProWages);
			form.setFieldProperty("registeredAdd", "textfont", bf, null);
			form.setField("registeredAdd", registeredAdd);
			stamper.setFormFlattening(true); // 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		printUtil.fillTemplate(out, bos,numberOfPages);
		//printUtil.print(newPDFPath);
		return "";

	}
}
