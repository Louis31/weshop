package com.i5le.back.web;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.redplus.entity.UserTrans;
import com.i5le.redplus.service.DealerInfoService;
import com.i5le.redplus.service.UserTransService;


@Controller
@RequestMapping("/back/userTrans")
public class RedPlusUserTransInfoController   extends
BaseCRUDController<UserTrans, Long>{

	
	@Autowired
	private UserTransService userTransService;
	@Override
	protected BaseService<UserTrans, Long> getServcie() {
		// TODO Auto-generated method stub
		return userTransService;
	}

	@RequestMapping(value = "")
	public String index(ServletRequest request) {
		return "/admin/redplus/userTrans/index";

	}

	@RequiresPermissions("/back/userTrans:list")
	@ResponseBody
	@RequestMapping(value = "list")
	public String list(ServletRequest request) {
		return super.list(request);
	}
	

}
