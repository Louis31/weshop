package com.i5le.framwork.sys.web;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.annotation.LogAnnotation;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.Resource;
import com.i5le.framwork.sys.service.ResourceService;

@Controller
@RequestMapping(value = "/admin/sys/resource")
public class ResourceController extends BaseCRUDController<Resource, Long> {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "")
	public String index() {
		return "/admin/sys/resource/index";
	}

	@RequiresPermissions(value={"/admin/sys/resource:list","/admin/sys/role:update"}, logical = Logical.OR)
	@RequestMapping(value = "resourceTree")
	@ResponseBody
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String resourceTree() {
		return this.mapper.toJson(this.resourceService.getRsourceList());
	}

	@RequiresPermissions("/admin/sys/resource:list")
	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String list(ServletRequest request) {
		return super.list(request);
	}

	// 特别设定多个ReuireResources之间为Or关系，而不是默认的And.
	@RequiresPermissions("/admin/sys/resource:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String editFrom(@PathVariable("id") Long id) {
		Resource resource = this.resourceService.findOne(id);
		return mapper.toJson(resource);
	}
	
	
	@RequiresPermissions("/admin/sys/resource:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid Resource resource){
		//如果Parent的ID为空，则需要把Parent设置为空，否则会报错
		if(resource.getParent()!=null && (resource.getParent().getId()==null || resource.getParent().getId()==0)){
			resource.setParent(null);
		}
		this.resourceService.save(resource);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions("/admin/sys/resource:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("resource") Resource resource){
		//如果Parent的ID为空，则需要把Parent设置为空，否则会报错
		if(resource.getParent()!=null && (resource.getParent().getId()==null || resource.getParent().getId()==0)){
			resource.setParent(null);
		}
		this.resourceService.save(resource);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions("/admin/sys/resource:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids) {
		this.resourceService.delete(ids);
		return mapper.toJson(successResult);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getResource(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("resource", this.resourceService.findOne(id));
		}
	}

	@Override
	protected BaseService<Resource, Long> getServcie() {
		return this.resourceService;
	}

}
