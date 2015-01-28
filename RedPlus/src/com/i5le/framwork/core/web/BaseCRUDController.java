package com.i5le.framwork.core.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;

import com.i5le.framwork.core.entity.AjaxResult;
import com.i5le.framwork.core.entity.ShiroUser;
import com.i5le.framwork.core.mapper.JsonMapper;
import com.i5le.framwork.core.poi.Column;
import com.i5le.framwork.core.poi.XLSExcelWriter;
import com.i5le.framwork.core.service.BaseService;
import com.redplus.view.utils.GsonUtil;

public abstract class BaseCRUDController<T, ID extends Serializable> {

	protected static JsonMapper mapper = JsonMapper.nonNullMapper();

	protected static AjaxResult ajxlist = new AjaxResult();

	protected abstract BaseService<T, ID> getServcie();

	protected final static AjaxResult successResult = new AjaxResult(true,
			"操作成功！");
	protected final static AjaxResult errorResult = new AjaxResult(false,
			"操作失败！");

	protected final static AjaxResult uploadPaseResult = new AjaxResult(true,
			"解析成功！");
	protected final static AjaxResult uploadPaseErrResult = new AjaxResult(
			false, "解析失败！");

	protected final static AjaxResult paramseErrResult = new AjaxResult(false,
			"参数错误！");
	protected final static String RESULT_OK;

	protected final static String RESULT_ERROR;

	protected final static String PA_RESULT_OK;

	protected final static String PA_RESULT_ERROR;

	protected final static String PARAMS_ERROR;
	static {
		RESULT_OK = mapper.toJson(successResult);
		RESULT_ERROR = mapper.toJson(errorResult);
		PA_RESULT_OK = mapper.toJson(uploadPaseResult);
		PA_RESULT_ERROR = mapper.toJson(uploadPaseErrResult);
		PARAMS_ERROR = mapper.toJson(paramseErrResult);
	}

	public Page<T> list(PageRequest pageRequest, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<T> page = this.getServcie()
				.searchByPage(searchParams, pageRequest);
		return page;
	}
	
	public String list(ServletRequest request) {
		PageRequest pageRequest = PageRequestBulider.getPageRequest(request);
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<T> page = this.getServcie()
				.searchByPage(searchParams, pageRequest);

	
		return mapper.toJson(page);
	}

	public String listIter(ServletRequest request) {
		PageRequest pageRequest = PageRequestBulider.getPageRequest(request);
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<T> page = this.getServcie()
				.searchByPage(searchParams, pageRequest);

		ajxlist.setSuccess(true);

		ajxlist.setList(page.getContent());
		
		return GsonUtil.transferByEntity(ajxlist);
	}

	public List<T> listAll(ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		List<T> page = this.getServcie().search(searchParams);

		return page;
	}

	public Long count(ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Long page = this.getServcie().count(searchParams);

		return page;

	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	protected Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.getId();
	}

	protected void exportExcel(String fileName, List<Column> columnList,
			List dataList, HttpServletResponse response) throws IOException {
		fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1")
				+ ".xls";

		XLSExcelWriter xls = new XLSExcelWriter();
		xls.addColumns(columnList);

		xls.setData(dataList);
		xls.build();

		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);

		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		xls.writeOut(response.getOutputStream());
		response.getOutputStream().flush();

	}

}
