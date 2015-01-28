package com.i5le.redplus.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.redplus.entity.Type_map;
import com.i5le.redplus.service.TypeMapService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/typemap")
public class TypeMapController extends BaseCRUDController<Type_map, Long> {

	@Autowired
	private TypeMapService typeMapService;
	
	@Override
	protected BaseService<Type_map, Long> getServcie() {
		return this.typeMapService;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public String list() {
		List<Type_map> ar = this.typeMapService.findAll();
		ajxlist.setSuccess(true);
		ajxlist.setList(ar);
		return GsonUtil.transferByEntity(ajxlist);
	}
	
	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.typeMapService.findOne(id));
		}
	}
}
