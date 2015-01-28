package com.i5le.back.web;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import com.i5le.framwork.core.utils.SortBean;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.Type_map;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.TypeMapService;
import com.i5le.redplus.service.UserInfoService;
import com.i5le.redplus.service.UserMoneyService;

@Controller
@RequestMapping("/back/userInfo")
public class RedPlusUserInfoController extends
		BaseCRUDController<UserInfo, Long> {

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private AreaCodeService areacodeService;
	
	@Autowired
	private UserMoneyService  userMoneyService;
	
	@Autowired
	private TypeMapService typeService;
	@Override
	protected BaseService<UserInfo, Long> getServcie() {
		// TODO Auto-generated method stub
		return userInfoService;
	}


	@RequestMapping(value = "")
	public String index(ServletRequest request,Model model) {
		List<AreaCode> ar = this.areacodeService.findAll();
		List<Type_map> art = this.typeService.findAll();
		model.addAttribute("areacode", ar);
		model.addAttribute("type", art);
		return "/admin/redplus/userInfo/index";

	}
	@RequiresPermissions("/back/userInfo:list")
	@ResponseBody
	@RequestMapping(value = "list")
	public String list(ServletRequest request) {
	    List<UserInfo> list = userInfoService.findAll();
	    SortBean sb = new SortBean();
	    list.sort(sb);
	    Subject currentUser = SecurityUtils.getSubject();//获取当前用户 
		if(currentUser.hasRole("Admin")){
			return super.list(request);
		}else{
			User user=(User) currentUser.getSession(true).getAttribute("current_login");	
			return null;
		}
		
	}
	
	@RequiresPermissions("/back/userInfo:add")
	@ResponseBody
	@RequestMapping(value = "add")
	public String add(@Valid UserInfo userInfo,@Valid Long gold,@Valid Long update_money) {

		try {
            if(StringUtils.isBlank(userInfo.getIconimg())){
            	userInfo.setIconimg("//20141224002719/0.jpg");
            }
            if(userInfo.getSort_value()==null){
            	userInfo.setSort_value(Integer.valueOf(userInfoService.getUserCount()+""));;
            }
			this.userInfoService.save(userInfo);
			UserMoney money = new UserMoney();
			if(gold==null){
				gold=0L;
			}
			if(update_money==null){
				update_money=0L;
			}
			money.setMoney(update_money);
			money.setGold(gold);
			money.setUserId(userInfo.getId());
			userMoneyService.save(money);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}
	}
	
	@RequiresPermissions("/back/userInfo:del")
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids) {
		
		try {
			userMoneyService.delete(ids);
			this.userInfoService.delete(ids);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}
		
		
	}

	@RequiresPermissions("/back/userInfo:update")
	@ResponseBody
	@RequestMapping(value = "update")
	public String edit(@Valid @ModelAttribute("entity") UserInfo userInfo,@Valid Long gold,@Valid Long update_money) {
		try {
			if(StringUtils.isBlank(userInfo.getIconimg())){
            	userInfo.setIconimg("//20141224002719/0.jpg");
            }
			 if(userInfo.getSort_value()==null){
	            	userInfo.setSort_value(Integer.valueOf(userInfoService.getUserCount()+""));;
	            }
			this.userInfoService.save(userInfo);
			UserMoney userMoney = userMoneyService.findOne(userInfo.getId());
			if(userMoney==null){
				userMoney = new UserMoney();
				userMoney.setUserId(userInfo.getId());
			}
			if(gold==null){
				gold=0L;
			}
			if(update_money==null){
				update_money=0L;
			}
			
			userMoney.setMoney(update_money);
			userMoney.setGold(gold);
			userMoneyService.save(userMoney);
			return RESULT_OK;
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}
	}

	@ModelAttribute
	public void getUser(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userInfoService.findOne(id));
		}
	}
}
