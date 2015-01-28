package com.i5le.back.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.net.www.content.image.gif;

import com.i5le.back.vo.ParamsVo;
import com.i5le.framwork.core.entity.AjaxResult;
import com.i5le.framwork.core.mapper.JsonMapper;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.redplus.entity.DealerInfo;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.DealerInfoService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.UserGiftService;
import com.i5le.redplus.service.UserInfoService;

@Controller
@RequestMapping("/back/userGift")
public class RedPlusUserGiftController {

	protected static JsonMapper mapper = JsonMapper.nonNullMapper();

	protected static AjaxResult ajxlist = new AjaxResult();

	protected final static AjaxResult successResult = new AjaxResult(true,
			"操作成功！");
	protected final static AjaxResult errorResult = new AjaxResult(false,
			"操作失败！");

	protected final static AjaxResult uploadPaseResult = new AjaxResult(true,
			"解析成功！");
	protected final static AjaxResult uploadPaseErrResult = new AjaxResult(
			false, "解析失败！");

	protected final static AjaxResult paramseErrResult = new AjaxResult(false,
			"参数错误！");
	protected final static String RESULT_OK;

	protected final static String RESULT_ERROR;

	protected final static String PA_RESULT_OK;

	protected final static String PA_RESULT_ERROR;

	protected final static String PARAMS_ERROR;
	static {
		RESULT_OK = mapper.toJson(successResult);
		RESULT_ERROR = mapper.toJson(errorResult);
		PA_RESULT_OK = mapper.toJson(uploadPaseResult);
		PA_RESULT_ERROR = mapper.toJson(uploadPaseErrResult);
		PARAMS_ERROR = mapper.toJson(paramseErrResult);
	}
	@Autowired
	private GiftInfoService giftInfoService;

	@Autowired
	private UserGiftService userGiftService;

	@RequestMapping("{userid}")
	public String index(@PathVariable("userid") long userid, Model model) {

		model.addAttribute("userGift",
				mapper.toJson(userGiftService.findByUserId((int) userid)));
		model.addAttribute("userId", userid);

		return "/admin/redplus/userGift/index";
	}

	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestParam("userId") long userid,
			@RequestParam("ids[]") long[] ids,
			@RequestParam("prices[]") long[] prices) {

		//userGiftService.deletByUserId((int) userid);

		List<UserGift> userGifts = new ArrayList<UserGift>();
		for (int i = 0, b = ids.length; i < b; i++) {
			Long id = ids[i];
			UserGift gift = this.userGiftService.findByUserIdAndGiftId(userid,
					id);

			gift = gift == null ? new UserGift() : gift;
			GiftInfo giftInfo = this.giftInfoService.findOne(id);
			gift.setUserId((int) userid);
			gift.setGift(giftInfo);
			gift.setPrice((int) prices[i]);
			gift.setOrder(i);
			gift.setResv1(" ");
			userGifts.add(gift);

		}

		this.userGiftService.save(userGifts);
		return RESULT_OK;

	}

	@RequestMapping("giftlist")
	@ResponseBody
	public String listGift() {

		return mapper.toJson(giftInfoService.findAll());

	}

	@RequestMapping("giftlist/{id}")
	@ResponseBody
	public String ListForUserid(@PathVariable("id") int id) {

		return null;

	}

}
