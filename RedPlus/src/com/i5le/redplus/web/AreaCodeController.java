package com.i5le.redplus.web;

import java.util.List;

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
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.UserInfoService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/areaCode")
public class AreaCodeController extends BaseCRUDController<AreaCode, Long> {

	@Autowired
	private AreaCodeService areacodeService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}

	public void clearP(AreaCode a) {
		
		

		

	}

	@RequestMapping(value = "root")
	@ResponseBody
	public String root(HttpServletRequest request) {
		return super.listIter(request);
//		List<AreaCode> ar = this.areacodeService.findRoot();
//
//		for (AreaCode a : ar) {
//
//			clearP(a);
//		}
//
//		ajxlist.setSuccess(true);
//		ajxlist.setList(ar);
//
//		return GsonUtil.transferByEntity(ajxlist);

	}

	@RequestMapping(value = "list")
	@ResponseBody
	public String list() {

		List<AreaCode> ar = this.areacodeService.findAll();

		

		ajxlist.setSuccess(true);
		ajxlist.setList(ar);

		return GsonUtil.transferByEntity(ajxlist);
	}

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.areacodeService.findOne(id));
		}
	}

	@Override
	protected BaseService<AreaCode, Long> getServcie() {
		return this.areacodeService;
	}

}
