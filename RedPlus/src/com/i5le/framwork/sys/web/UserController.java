/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.i5le.framwork.sys.web;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import com.i5le.framwork.sys.entity.Role;
import com.i5le.framwork.sys.entity.User;
import com.i5le.framwork.sys.service.UserService;

@Controller
@RequestMapping(value = "/admin/sys/user")
public class UserController extends BaseCRUDController<User, Long> {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String index() {
		return "/admin/sys/user/index";
	}

	// URL /admin/sys/user/list?start=0&limit=15&pageIndex=0&
	// field=id&direction=DESC&search_LIKE_name=&search_LIKE_email=&
	// search_EQ_status=&search_GTE_createTime_D=&search_LTE_createTime_D=
	@RequiresPermissions("/admin/sys/user:list")
	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	@LogAnnotation(value = true, writeRespBody = false)
	// 写日志但是不打印请求的params,但不打印ResponseBody的内容
	public String list(ServletRequest request) {
		return super.list(request);
	}

	// 从数据库中获取user，如http://localhost/admin/system/update/1,则返回用户ID为1的用户
	@RequiresPermissions("/admin/sys/user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String editFrom(@PathVariable("id") Long id) {
		User user = this.userService.findOne(id);
		return mapper.toJson(user);
	}
	
	@RequiresPermissions(value = "/admin/sys/user:add")
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	//远程校验登录名是否重复
	public String checkLoginName(@RequestParam String loginName) {
		User user = this.userService.findByLoginName(loginName);
		if (user == null)
			return "";
		else 
			return "该登录名已经被使用";
	}

	@RequiresPermissions(value = "/admin/sys/user:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid User user,
			@RequestParam(value = "roleIds") List<Long> roleIds) {
		for (Long roleId : roleIds) {
			Role role = new Role(roleId);
			user.getRoles().add(role);
		}
		user.setCreateTime(new Date());
		User creater = new User();
		creater.setId(this.getCurrentUserId());
		user.setCreater(creater);
		this.userService.save(user);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions(value = "/admin/sys/user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "roleIds") List<Long> roleIds) {
		user.setPlainPassword(null);// 强制将密码设置为空，避免有人越权把密码传回来进行修改
		// 清除从数据库load出来的角色列表
		user.getRoles().clear();
		for (Long roleId : roleIds) {
			Role role = new Role(roleId);
			user.getRoles().add(role);
		}
		this.userService.save(user);
		return mapper.toJson(successResult);
	}

	@RequiresPermissions("/admin/sys/user:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids) {
		this.userService.delete(ids);
		return mapper.toJson(successResult);
	}

	@RequiresRoles("Admin")
	@RequestMapping(value = "changePasswd", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword(@Valid @ModelAttribute("user") User user) {
		this.userService.save(user);
		return mapper.toJson(successResult);
	}



	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("user", this.userService.findOne(id));
		}
	}

	@Override
	protected BaseService<User, Long> getServcie() {
		return this.userService;
	}

}
