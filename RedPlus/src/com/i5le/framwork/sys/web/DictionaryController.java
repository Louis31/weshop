package com.i5le.framwork.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.commom.bui.vo.ListItem;
import com.i5le.framwork.core.annotation.LogAnnotation;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.core.web.Servlets;
import com.i5le.framwork.sys.entity.Dictionary;
import com.i5le.framwork.sys.service.DictionaryService;

@Controller
@RequestMapping(value = "/admin/sys/dictionary")
public class DictionaryController extends BaseCRUDController<Dictionary, Long>{

	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "")
	public String index() {
		return "/admin/sys/dictionary/index";
	}
	
	
	@RequiresPermissions("/admin/sys/dictionary:list")
	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String list(ServletRequest request) {
		return super.list(request);
	}
	
	
	/**
	 * 请求地址：
	 * /admin/sys/dictionary/search?search_EQ_dictionaryType.code=city
	 * @param request
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "search")
	@ResponseBody
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String listByDictionaryTypeId(ServletRequest request){
		Sort sort = new Sort(Sort.Direction.ASC, "orderId");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<ListItem> list = this.dictionaryService.searchListItem(searchParams, sort);
		return this.mapper.toJson(list);
	}
	

	// 特别设定多个ReuireRoles之间为Or关系，而不是默认的And.
	@RequiresPermissions("/admin/sys/dictionary:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String editFrom(@PathVariable("id") Long id) {
		Dictionary dictionary = this.dictionaryService.findOne(id);
		return mapper.toJson(dictionary);
	}
	
	@RequiresPermissions("/admin/sys/dictionary:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid Dictionary dictionary){
		this.dictionaryService.save(dictionary);
		return mapper.toJson(successResult);
	}
	
	@RequiresPermissions("/admin/sys/dictionary:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("dictionary") Dictionary dictionary){
		this.dictionaryService.save(dictionary);
		return mapper.toJson(successResult);
	}
	
	@RequiresPermissions("/admin/sys/dictionary:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids){
		this.dictionaryService.delete(ids);
		return mapper.toJson(successResult);
	}
	

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getDictionary(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("dictionary", this.dictionaryService.findOne(id));
		}
	}

	
	@Override
	protected BaseService<Dictionary, Long> getServcie() {
		return this.dictionaryService;
	}

}
