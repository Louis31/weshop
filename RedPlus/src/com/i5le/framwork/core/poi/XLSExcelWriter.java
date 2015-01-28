package com.i5le.framwork.core.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.i5le.framwork.core.utils.JPBeanUtil;



public class XLSExcelWriter<T> implements ExcelWriter{

	private List<Column> cols = null;
	private List data;
	private HSSFWorkbook workbook;
	
	public XLSExcelWriter(){
		this.cols = new ArrayList<Column>();
	}
	
	public void addColumns(List<Column> cols) {
		this.cols.addAll(cols);
	}
	
	public void addColumn(Column col){
		this.cols.add(col);
	}

	public void setData(List data) {
		this.data = data;
	}

	public void writeOut(OutputStream output) throws IOException {
		if(workbook != null)
		{
			workbook.write(output);
			output.flush();
			output.close();
		}
	}

	public void build() {
		workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("sheet1");
	    
	    buildHead(sheet);
	    buildData(sheet);
	}
	
	/**
	 * 创建标题
	 */
	private void buildHead(HSSFSheet sheet){
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle headStyle = getHeadStyle();
		Column col = null;
		for (int i = 0; i < cols.size(); i++) {
			col = cols.get(i);
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(col.getTitle());
			cell.setCellStyle(headStyle);
			sheet.setColumnWidth(i, col.getWidth()*256);
		}
		
		
	}
	
	/**
	 * 创建内容行
	 */
	private void buildData(HSSFSheet sheet){
		//取得泛型类型
		
		Object rowData = null;
		for (int j = 0; j < data.size(); j++) {
			rowData = data.get(j);
			HSSFRow row = sheet.createRow(j+1);
			setOneRowVal(row, rowData);
		}
		
	}
	
	private void setOneRowVal(HSSFRow row,Object rowData){
		for (int i = 0; i < cols.size(); i++) {
			Column col = cols.get(i);
			
			HSSFCell cell = row.createCell(i);
			Object valObj = JPBeanUtil.getValue(rowData, col.getField());
			if(col.getStyle() == null)
			{
				col.setStyle(getCellStyle(col.getType()));
			}
			cell.setCellStyle(col.getStyle());
			if(valObj==null){
				cell.setCellValue("");
				continue;
			}

			switch(col.getType()){
	    		case ColumnTypeConstant.TYPE_INTEGER:
	    		case ColumnTypeConstant.TYPE_PRICE:
	    		case ColumnTypeConstant.TYPE_DOUBLE:
		    			Integer valInt = col.renderer(Integer.parseInt(valObj.toString()));
		    			cell.setCellValue(valInt);
		    			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    			break;
	    		default:
	    			String valStr = col.renderer(valObj);
	    			cell.setCellValue(valStr);
	    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			break;
			}
			
		}
	}
	
	private HSSFCellStyle getHeadStyle(){
		HSSFFont font = workbook.createFont();
	    font.setFontName("宋体");
	    font.setBoldweight((short) 200);
	    font.setFontHeight((short) 250);
	    font.setColor(HSSFColor.WHITE.index);
	    
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    // 设置边框
	    style.setBottomBorderColor(HSSFColor.BLACK.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setFont(font);
	    
	    return style;
	}
	
	private HSSFCellStyle getCellStyle(int celltype){
		HSSFFont font = workbook.createFont();
	    font.setFontName("宋体");
	    font.setColor(HSSFColor.BLACK.index);
	    
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    //style.setFillForegroundColor(HSSFColor.WHITE.index);
	    //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    // 设置边框
	    style.setBottomBorderColor(HSSFColor.BLACK.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setFont(font);
	    
	    switch(celltype){
	    	case ColumnTypeConstant.TYPE_STRING:
	    		break;
	    	case ColumnTypeConstant.TYPE_INTEGER:
	    		style.setDataFormat(workbook.createDataFormat().getFormat("0"));
	    		break;
	    	case ColumnTypeConstant.TYPE_DOUBLE:
	    		style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
	    		break;
	    	case ColumnTypeConstant.TYPE_PRICE:
	    		style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
	    		break;
	    	case ColumnTypeConstant.TYPE_DATE:
	    		style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-m-d"));
	    		break;
	    	case ColumnTypeConstant.TYPE_BOOLEAN:
	    		break;
	    }
	    
	    return style;
	}
}
