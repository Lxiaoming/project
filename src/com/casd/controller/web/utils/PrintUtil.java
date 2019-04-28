package com.casd.controller.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PrintUtil {
	
	 /**
	 * 调用打印机打印pdf
	 */
	public void print(String pathname) {
		File file = new File(pathname);
		// 构建打印请求属性集
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// 设置打印格式，因为未确定类型，所以选择autosense
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 查找所有的可用的打印服务
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		// 定位默认的打印服务
		PrintService defaultService = PrintServiceLookup
				.lookupDefaultPrintService();
		// 显示打印对话框
		/*
		 * PrintService service = ServiceUI.printDialog(null, 200, 200,
		 * printService, defaultService, flavor, pras);
		 */

		if (defaultService != null) {
			try {
				DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
				FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
				DocAttributeSet das = new HashDocAttributeSet();
				Doc doc = new SimpleDoc(fis, flavor, das);
				job.print(doc, pras);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 模板填充数据
	 */
	public void fillTemplate(FileOutputStream out, ByteArrayOutputStream bos,
			int pageNum) {
		try {
			Document doc = new Document();

			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			// for(int i=1;i<pageNum+1;i++){
			PdfImportedPage importPage = copy.getImportedPage(
					new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			// }
			doc.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}



	/**
	 * 填充模板
	 * */
	public static void fillData(AcroFields fields, Map<String, Object> data)
			throws IOException, DocumentException {
		for (String key : data.keySet()) {
			String value = data.get(key).toString();
			fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
		}
	}

	

}