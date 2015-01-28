package com.i5le.redplus.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.redplus.entity.UserTransInfo;
import com.i5le.redplus.service.UserTransInfoService;

@Controller
@RequestMapping(value = "/userTransInfo")
public class UserTransInfoController extends
		BaseCRUDController<UserTransInfo, Long> {

	@Autowired
	private UserTransInfoService userTransInfoService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public String list(HttpServletRequest request) {

		return super.listIter(request);

	}

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userTransInfoService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserTransInfo, Long> getServcie() {
		return this.userTransInfoService;
	}

}
