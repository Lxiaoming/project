package com.casd.controller.web.common.excel;



import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

public class ReportExcel {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// 创建工作簿
	private HSSFWorkbook workBook;

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	// 创建sheet页
	HSSFSheet sheet;
	// 标题行样式
 

	public HSSFCellStyle getTitleStyle() {
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 22);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		style.setFont(font);
		return style;
	}

	public HSSFCellStyle getHeaderStyle() {

		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);     
		style.setFont(font);
		return style;
	}

	private List<Column> columns;

	/**
	 * 创建sheet页
	 * 
	 * @param sheetName
	 *            sheet页名称
	 */
	public HSSFSheet createSheet(String sheetName) {

		HSSFSheet sheet = workBook.createSheet(sheetName);
		return sheet;

	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	/**
	 * 添加数据
	 * 
	 * @param data
	 */
	public void addData(List<Map<String, Object>> data, HSSFSheet sheet) {

		if (columns == null) {

		}
		if (null == sheet) {

			sheet = workBook.createSheet();
		}

		if (null != this.title) {
			sheet.shiftRows(0, sheet.getLastRowNum(), 1, true, false);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
					columns.size() - 1));
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 800);
			HSSFCell cell = row.createCell(0, 0);
			cell.setCellStyle(this.getTitleStyle());
			HSSFRichTextString text = new HSSFRichTextString(this.title);
			cell.setCellValue(text);

		}

		int column_index = 0;
		int rowNo = sheet.getLastRowNum();
		HSSFRow rows = sheet.createRow(1);
		for (Column column : columns) {
			sheet.setColumnWidth(column_index, column.getWidth());
			HSSFCell cell = rows.createCell(column_index++);
			cell.setCellType(column.getCellType());
			cell.setCellStyle(this.getHeaderStyle());
			HSSFRichTextString text = new HSSFRichTextString(column.getHeader());
			cell.setCellValue(text);
		}

		for (Map<String, Object> row : data) {
			int index = 0;

			int rowindex = sheet.getLastRowNum();
			HSSFRow datarow = sheet.createRow(rowindex + 1);
		 
			for (Column column : columns) {
				Object v = null;
				if (column.hanlder != null)
					v = column.hanlder.hanlder(row, column.getField());
				else
					v = row.get(column.getField());
				if (null == v) {
					v = "";
				}

				HSSFCell datacelll = datarow.createCell(index++);
				datacelll.setCellType(column.getCellType());
				HSSFRichTextString text = new HSSFRichTextString(v + "");

				HSSFCellStyle style = column.getColumnStyle();

				style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
				datacelll.setCellStyle(column.getColumnStyle());
				datacelll.setCellValue(text);		 

			}
		}
		System.gc();
	}

	/**
	 * 添加普通字符串列数据
	 * 
	 * @param headerMap
	 *            表头
	 * @throws IOException
	 */
	public void addColumn(String headStr, String field, int width) {

		if (columns == null) {
			columns = new ArrayList<Column>();
		}
		HSSFDataFormat format = this.workBook.createDataFormat();

		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setDataFormat(format.getFormat("@"));
		style.setFont(font);

		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);
		column.setCellType(HSSFCell.CELL_TYPE_STRING);
		columns.add(column);
	}

	/**
	 * 添加布尔类型数据
	 * 
	 * @param colmun
	 * @param filed
	 * @param map
	 */
	public void setBolleanValue(String headStr, String field, int width,
			IRowBindHanlder hanlder) {
		if (columns == null) {
			columns = new ArrayList<Column>();
		}

		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中

		style.setFont(font);

		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);

		column.setCellType(HSSFCell.CELL_TYPE_STRING);
		column.setHanlder(hanlder);
		columns.add(column);

	}

	/**
	 * double类型数据插入
	 * 
	 * @param headStr
	 * @param field
	 * @param width
	 */
	public void setMoneyVlue(String headStr, String field, int width) {

		if (columns == null) {
			columns = new ArrayList<Column>();
		}
		HSSFDataFormat format = this.workBook.createDataFormat();
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setFont(font);
		style.setDataFormat(format.getFormat("#,##0.00"));

		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);

		column.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		columns.add(column);
	}

	/**
	 * double类型数据插入
	 * 
	 * @param headStr
	 * @param field
	 * @param width
	 */
	public void setDoubleVlue(String headStr, String field, int width) {

		if (columns == null) {
			columns = new ArrayList<Column>();
		}
		HSSFDataFormat format = this.workBook.createDataFormat();
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setFont(font);
		style.setDataFormat(format.getFormat("###0.00 "));
		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);

		column.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		columns.add(column);
	}

	/**
	 * 插入整形数据；
	 * 
	 * @param headStr
	 * @param field
	 * @param width
	 * @param hanlder
	 */
	public void setIntegert(String headStr, String field, int width) {
		if (columns == null) {
			columns = new ArrayList<Column>();
		}

		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setFont(font);
		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);
		column.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		columns.add(column);
	}

	/**
	 * 插入键值对形式的数据
	 * 
	 * @param headStr
	 *            表头
	 * @param field
	 *            表头的字符串
	 * @param width
	 *            宽度
	 * @param hanlder
	 *            需要自己去实现该类的方法
	 */
	public void addMapValue(String headStr, String field, int width,
			IRowBindHanlder hanlder) {

		if (columns == null) {
			columns = new ArrayList<Column>();
		}
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		style.setFont(font);
		Column column = new Column();
		column.setColumnStyle(style);
		column.setHeader(headStr);
		column.setField(field);
		column.setWidth(width);
		column.setCellType(HSSFCell.CELL_TYPE_STRING);
		column.setHanlder(hanlder);
		columns.add(column);

	}

	public void out(String url, String name) throws IOException {

		FileOutputStream out = new FileOutputStream(url + name);
		workBook.write(out);
		out.close();
	}

	class Column {
		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public IRowBindHanlder getHanlder() {
			return hanlder;
		}

		public void setHanlder(IRowBindHanlder hanlder) {
			this.hanlder = hanlder;
		}

		private int cellType;

		public int getCellType() {
			return cellType;
		}

		public HSSFCellStyle getColumnStyle() {
			return columnStyle;
		}

		public void setColumnStyle(HSSFCellStyle columnStyle) {
			this.columnStyle = columnStyle;
		}

		public void setCellType(int cellType) {
			this.cellType = cellType;
		}

		private HSSFCellStyle columnStyle;
		private String header;
		private String field;
		private int width;
		private IRowBindHanlder hanlder;
	}
}
