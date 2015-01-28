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
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.UserInfoService;
import com.i5le.redplus.service.UserMoneyService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/userMoney")
public class UserMoneyController extends BaseCRUDController<UserMoney, Long> {

	@Autowired
	private UserMoneyService userMoneyService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}

	@RequestMapping(value = "getMoney")
	@ResponseBody
	public String getMoney(@RequestParam("uid") Long uid) {
		
		AjaxResult<UserMoney> userAjaxResul= new AjaxResult<UserMoney>();
	  UserMoney userMoney
	   = this.userMoneyService.findOne(uid);
	  userAjaxResul.setSuccess(true);
	  userAjaxResul.setObj(userMoney);
	  
		return GsonUtil.transferByEntity(userAjaxResul);
	}

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userMoneyService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserMoney, Long> getServcie() {
		return this.userMoneyService;
	}

}
