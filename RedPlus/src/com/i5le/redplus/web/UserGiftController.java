package com.i5le.redplus.web;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.UserGiftService;
import com.i5le.redplus.service.UserInfoService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/usergift")
public class UserGiftController extends BaseCRUDController<UserGift, Long> {

	@Autowired
	private UserGiftService userGiftService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}


	@RequestMapping(value = "list")
	@ResponseBody
	public String index(HttpServletRequest rq) {
		return super.listIter(rq);
	}

	

	@RequestMapping(value = "find")
	@ResponseBody
	public String byUserId(@RequestParam("uid") int uid) {
		
		 AjaxResult<UserGift> userAjaxResult = new AjaxResult<UserGift>();
		 userAjaxResult.setSuccess(true);
 
 
 userAjaxResult.setList(this.userGiftService.findByUserId(uid));
 return GsonUtil.transferByEntity(userAjaxResult);
 
 
	}
	
	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userGiftService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserGift, Long> getServcie() {
		return this.userGiftService;
	}

}
