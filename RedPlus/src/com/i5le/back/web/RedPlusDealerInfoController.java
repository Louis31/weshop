package com.i5le.back.web;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.DealerInfo;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.DealerInfoService;
import com.i5le.redplus.service.UserInfoService;

@Controller
@RequestMapping("/back/dealerInfo")
public class RedPlusDealerInfoController extends
		BaseCRUDController<DealerInfo, String> {

	@Autowired
	DealerInfoService dealerInfoService;

	@RequestMapping(value = "")
	public String index(ServletRequest request) {
		return "/admin/redplus/dealerInfo/index";

	}

	@RequiresPermissions("/back/dealerInfo:list")
	@ResponseBody
	@RequestMapping(value = "list")
	public String list(ServletRequest request) {
		return super.list(request);
	}
	
	@RequiresPermissions("/back/dealerInfo:distributor")
	@ResponseBody
	@RequestMapping(value = "distributor")
	public String distributor(ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();//获取当前用户 
		User user=(User) currentUser.getSession(true).getAttribute("current_login");
		if(user!=null){
			
		}
		//dealerInfoService.
		return super.list(request);
	}

	@RequiresPermissions("/back/dealerInfo:add")
	@ResponseBody
	@RequestMapping(value = "add")
	public String add(@Valid DealerInfo userInfo) {

		try {

			this.dealerInfoService.save(userInfo);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}
	}

	@RequiresPermissions("/back/dealerInfo:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") String[] ids) {

		try {

			this.dealerInfoService.delete(ids);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}

	}

	@RequiresPermissions("/back/dealerInfo:update")
	@ResponseBody
	@RequestMapping(value = "update")
	public String edit(@Valid @ModelAttribute("entity") DealerInfo userInfo) {
		try {

			this.dealerInfoService.save(userInfo);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}
	}

	@ModelAttribute
	public void getUser(
			@RequestParam(value = "id", defaultValue = "-1") String id,
			Model model) {
		if (id != null) {
			model.addAttribute("entity", this.dealerInfoService.findOne(id));
		}
	}

	@Override
	protected BaseService<DealerInfo, String> getServcie() {
		// TODO Auto-generated method stub
		return dealerInfoService;
	}
}
