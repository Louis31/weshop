package com.i5le.back.web;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.redplus.entity.DealerInfo;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.DealerInfoService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.UserInfoService;

@Controller
@RequestMapping("/back/giftInfo")
public class RedPlusGiftInfoController extends
		BaseCRUDController<GiftInfo, Long> {

	@Autowired
	GiftInfoService giftInfoService;


	
	@ResponseBody
	@RequestMapping(value = "list")
	public String list(ServletRequest request) {
		return super.list(request);
	}



	@Override
	protected BaseService<GiftInfo, Long> getServcie() {
		// TODO Auto-generated method stub
		return giftInfoService;
	}

	
}
