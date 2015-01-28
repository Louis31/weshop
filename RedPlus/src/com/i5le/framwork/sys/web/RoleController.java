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
import com.i5le.framwork.sys.entity.Role;
import com.i5le.framwork.sys.service.RoleAuthService;
import com.i5le.framwork.sys.service.RoleService;

@Controller
@RequestMapping(value = "/admin/sys/role")
public class RoleController extends BaseCRUDController<Role, Long> {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleAuthService roleAuthService;

	@RequestMapping(value = "")
	public String index() {
		return "/admin/sys/role/index";
	}

	@RequestMapping(value = "auth/{id}", method = RequestMethod.GET)
	@RequiresPermissions("/admin/sys/role:auth")
	public String auth(@PathVariable("id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/admin/sys/role/auth";
	}

	@RequestMapping(value = "auth/{id}", method = RequestMethod.POST)
	@RequiresPermissions("/admin/sys/role:auth")
	@ResponseBody
	public String auth(@PathVariable("id") Long id, @RequestParam(value = "jsonRole") String jsonRole) {
		Role role = this.mapper.fromJson(jsonRole, Role.class);
		this.roleService.authRole(role);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions("/admin/sys/role:list")
	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String list(ServletRequest request) {
		return super.list(request);
	}

	@RequiresPermissions("/admin/sys/role:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid Role role) {
		this.roleService.save(role);
		return mapper.toJson(successResult);
	}

	// 特别设定多个ReuireRoles之间为Or关系，而不是默认的And.
	@RequiresPermissions(value = { "/admin/sys/role:update", "/admin/sys/role:auth" }, logical = Logical.OR)
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String editFrom(@PathVariable("id") Long id) {
		Role role = this.roleService.findOne(id);
		return mapper.toJson(role);
	}

	@RequiresPermissions("/admin/sys/role:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("role") Role role) {
		this.roleService.save(role);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions("/admin/sys/role:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids) {
		this.roleService.delete(ids);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions(value = { "/admin/sys/user:update", "/admin/sys/user:add" }, logical = Logical.OR)
	@RequestMapping(value = "roleCheckList")
	@ResponseBody
	@LogAnnotation(value = true, writeRespBody = false)// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String roleCheckList() {
		List<Role> roles = this.roleService.findAll();
		List<ListItem> list = new ArrayList<ListItem>();
		for (Role role : roles) {
			list.add(new ListItem(role.getName(), role.getId().toString()));
		}
		return mapper.toJson(list);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("role", this.roleService.findOne(id));
		}
	}

	@Override
	protected BaseService<Role, Long> getServcie() {
		return this.roleService;
	}

}
