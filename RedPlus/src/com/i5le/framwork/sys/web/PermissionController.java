package com.i5le.framwork.sys.web;

import java.util.ArrayList;
import java.util.List;

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

import com.i5le.framwork.commom.bui.vo.ListItem;
import com.i5le.framwork.core.annotation.LogAnnotation;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.Permission;
import com.i5le.framwork.sys.service.PermissionService;

@Controller
@RequestMapping(value = "/admin/sys/permission")
public class PermissionController  extends BaseCRUDController<Permission, Long>{

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "")
	public String index() {
		return "/admin/sys/permission/index";
	}
	@RequiresPermissions("/admin/sys/permission:list")
	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String list(ServletRequest request) {
		return super.list(request);
	}
	
	@RequiresPermissions(value = {"/admin/sys/resource:update","/admin/sys/role:update"}, logical= Logical.OR)
	@RequestMapping(value = "permissionCheckList")
	@ResponseBody
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String permissionCheckList(){
		List<Permission> permissions = this.permissionService.findAll();
		List<ListItem> list = new ArrayList<ListItem>();
		for(Permission permission : permissions){
			list.add(new ListItem(permission.getName(), permission.getId().toString()));
		}
		return mapper.toJson(list);
	}
	
	// 特别设定多个ReuireRoles之间为Or关系，而不是默认的And.
	@RequiresPermissions("/admin/sys/permission:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String editFrom(@PathVariable("id") Long id) {
		Permission permission = this.permissionService.findOne(id);
		return mapper.toJson(permission);
	}
	
	@RequiresPermissions("/admin/sys/permission:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid Permission permission){
		this.permissionService.save(permission);
		return mapper.toJson(successResult);
	}
	
	@RequiresPermissions("/admin/sys/permission:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("permission") Permission permission){
		this.permissionService.save(permission);
		return mapper.toJson(successResult);
	}
	
	@RequiresPermissions("/admin/sys/permission:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids){
		this.permissionService.delete(ids);
		return mapper.toJson(successResult);
	}
	

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getPermission(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("permission", this.permissionService.findOne(id));
		}
	}

	
	@Override
	protected BaseService<Permission, Long> getServcie() {
		return this.permissionService;
	}

}
