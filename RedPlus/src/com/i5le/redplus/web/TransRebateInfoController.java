package com.i5le.redplus.web;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.entity.AjaxResult;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.TransRebate;
import com.i5le.redplus.entity.TransRebateInfo;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.TransRebateInfoService;
import com.i5le.redplus.service.TransRebateService;
import com.i5le.redplus.service.UserInfoService;

@Controller
@RequestMapping(value = "/rabateInfo")
public class TransRebateInfoController extends BaseCRUDController<TransRebateInfo, Long> {

	@Autowired
	private TransRebateInfoService rebateInfoService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}
	

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.rebateInfoService.findOne(id));
		}
	}

	@Override
	protected BaseService<TransRebateInfo, Long> getServcie() {
		return this.rebateInfoService;
	}

}
