package com.i5le.framwork.core.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageRequestBulider {
	

	
	/**
	 * 页面索引，一般从0开始
	 */
	private static String pageIndexPropertie = "pageIndex";
	/**
	 * 页面大小
	 */
	private static String pageSizePropertie = "limit";
	
	/**
	 * 排序字段
	 */
	private static String sortFieldPropertie = "field";
	/**
	 * 排序方法 ASC OR DESC
	 */
	private static String sortDirPropertie = "direction";


	
	public static void setPageIndexPropertie(String pageIndexPropertie) {
		PageRequestBulider.pageIndexPropertie = pageIndexPropertie;
	}

	public static void setPageSizePropertie(String pageSizePropertie) {
		PageRequestBulider.pageSizePropertie = pageSizePropertie;
	}
	

	public static PageRequest getPageRequest(ServletRequest request) {
		int page = 0;
		int size =10;

		String[] sortFields = null;
		String[] dirFiles = null;
		
		
		Enumeration<String> paramNames = request.getParameterNames();
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if(paramName.endsWith(pageIndexPropertie)){
				String[] values = request.getParameterValues(paramName);
				if(values!=null && values.length>0){
					page = Integer.parseInt(values[0]);
				}
			}
			if(paramName.equals(pageSizePropertie)){
				String[] values = request.getParameterValues(paramName);
				if(values!=null && values.length>0){
					size = Integer.parseInt(values[0]);
				}
			}
			if(paramName.equals(sortFieldPropertie)){
				sortFields = request.getParameterValues(paramName);
			}
			if(paramName.equals(sortDirPropertie)){
				dirFiles = request.getParameterValues(paramName);
			}
		}
		
		Sort sort = null;
		if(sortFields!= null && sortFields.length>0){
			List<Sort.Order> orders = new ArrayList<Sort.Order>();
			for(int i=0;i<sortFields.length;i++){
				if(dirFiles!=null && (dirFiles.length+1) >= i ){
					
					if(!sortFields[i].equals(""))
					orders.add(new Sort.Order(Direction.fromString(dirFiles[i]), sortFields[i]));
				}else{
					if(!sortFields[i].equals(""))
					orders.add(new Sort.Order(dirFiles[i]));
				}
			}
			if(orders.size() > 0)
			sort = new Sort(orders);
		}
		PageRequest pageRequest = new PageRequest(page, size, sort);
		
		return pageRequest;
	}
}
