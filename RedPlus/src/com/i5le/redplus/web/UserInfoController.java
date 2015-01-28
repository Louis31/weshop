package com.i5le.redplus.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.i5le.framwork.core.entity.AjaxResult;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.core.web.PageRequestBulider;
import com.i5le.framwork.core.web.Servlets;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.Collect;
import com.i5le.redplus.entity.DealerInfo;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.repository.DealerInfoRepository;
import com.i5le.redplus.service.CollectService;
import com.i5le.redplus.service.DealerInfoService;
import com.i5le.redplus.service.UserInfoService;
import com.i5le.redplus.service.UserMoneyService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController extends BaseCRUDController<UserInfo, Long> {

	private transient static final String CAN_LOGIN = "1";
	AjaxResult<String> ajaxResultstr = new AjaxResult<String>();

	@Autowired
	private DealerInfoService dealerInfoService;
	@Autowired
	private UserMoneyService moneyService;
	private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CollectService collectService;


	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	@Override
	public String list(ServletRequest request) {
		AjaxResult<UserInfo> ajaxResult = new AjaxResult<UserInfo>();
		try {
			PageRequest pageRequest = PageRequestBulider
					.getPageRequest(request);

			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");

			Page<UserInfo> page = this.getServcie().searchByPage(searchParams,
					pageRequest);

			ajxlist.setSuccess(true);

			ajxlist.setList(page.getContent());

			return GsonUtil.transferByEntity(ajxlist);
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}

	}

	@RequestMapping(value = "getCollect")
	@ResponseBody
	public String getCollect(@RequestParam("host_id") Long uid) {

		AjaxResult<UserInfo> ajaxResult = new AjaxResult<UserInfo>();

		List<UserInfo> list = this.userInfoService.findCollectionAll(uid);
		try {
			
		
		if (list!=null) {

			ajaxResult.setList(list);

			ajaxResult.setSuccess(true);
		} else {

			ajaxResult.setSuccess(false);
		}
		    return GsonUtil.transferByEntity(ajaxResult);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			return GsonUtil.transferByEntity(ajaxResult);
		}

	}
	
	@RequestMapping(value = "saveCollect")
	@ResponseBody
	public String saveCollect(Collect collect) {

		AjaxResult<Collect> ajaxResult = new AjaxResult<Collect>();
		
		try {
			if(this.collectService.findOneCollect(collect.getHost_id(), collect.getUser_id())==null){
				
				this.collectService.save(collect);
				ajaxResult.setSuccess(true);
			}else {
				ajaxResult.setSuccess(false);
			}

		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
	
		return GsonUtil.transferByEntity(ajaxResult);
	}
	
	@RequestMapping(value = "deleteCollect")
	@ResponseBody
	public String deleteCollect(Collect collect) {

		AjaxResult<Collect> ajaxResult = new AjaxResult<Collect>();
		
		try {
			Collect collect2 = this.collectService.findOneCollect(collect.getHost_id(), collect.getUser_id());
			if(collect2!=null){
				this.collectService.delete(collect2);
				ajaxResult.setSuccess(true);
			}else {
				ajaxResult.setSuccess(false);
			}

		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
	
		return GsonUtil.transferByEntity(ajaxResult);
	}
	
	
	@RequestMapping(value = "downLine")
	@ResponseBody
	public String downLine(@RequestParam("uid") Long uid,
			@RequestParam("online") int onlne) {

		AjaxResult<UserInfo> ajaxResult = new AjaxResult<UserInfo>();

		UserInfo userInfo = this.userInfoService.findOne(uid);
		if (userInfo != null) {

			userInfo.setOnline(onlne);

			this.userInfoService.save(userInfo);

			ajaxResult.setSuccess(true);
		} else {

			ajaxResult.setSuccess(false);
		}
		return GsonUtil.transferByEntity(ajaxResult);

	}

	@RequestMapping(value = "serach")
	@ResponseBody
	public String serach(@RequestParam("gender") String gender,
			@RequestParam("age") String age,
			@RequestParam("height") String height,
			@RequestParam("weight") String width,
			@RequestParam("area") String areaName,
			@RequestParam("type") String type) {
		AjaxResult<UserInfo> ajaxResult = new AjaxResult<UserInfo>();
		ajaxResult.setSuccess(true);
		List<UserInfo> userInfos = this.userInfoService.searchByNativeSql(age,
				height, width, areaName, gender,type);
		ajaxResult.setList(userInfos);
		return GsonUtil.transferByEntity(ajaxResult);

	}

	@RequestMapping(value = "upload")
	@ResponseBody
	public String addUser(@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletRequest request) {

		String dateT = df.format(new Date());
		for (int i = 0; i < files.length; i++) {
			System.out.println("fileName---------->"
					+ files[i].getOriginalFilename());

			if (!files[i].isEmpty()) {
				String basePath = request.getRealPath("/");

				basePath += dateT + File.separator;

				File f = new File(basePath);
				if (!f.exists()) {
					f.mkdir();

				}

				try {
					IOUtils.copy(
							files[i].getInputStream(),
							new FileOutputStream(basePath
									+ files[i].getOriginalFilename()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return RESULT_ERROR;
				}
			}
		}
		return RESULT_OK;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public String Testlogin(@RequestParam("loginname") String loginname,
			@RequestParam("passwd") String passwd) {
		AjaxResult<UserInfo> ajaxResult = new AjaxResult<UserInfo>();
		try {

			UserInfo user = this.userInfoService.findByLoginname(loginname);
			if (user == null || !user.getPasswd().equals(passwd)
					|| user.getResv1() == null
					|| !user.getResv1().equals(CAN_LOGIN)) {

				return RESULT_ERROR;
			}

			UserMoney userMoney = moneyService.findOne(user.getId());
			user.setMoney(userMoney != null ? userMoney.getMoney() : 0);
			user.setGold(userMoney != null ? userMoney.getGold() : 0);
			ajaxResult.setSuccess(true);
			ajaxResult.setObj(user);

			return GsonUtil.transferByEntity(ajaxResult);
			// this.userInfoService.findByLoginName(searchParams)
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;
			// TODO: handle exception
		}

	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam("loginname") String loginname,
			@RequestParam("passwd") String passwd) {
		try {

			UserInfo user = this.userInfoService.findByLoginname(loginname);
			if (user == null || !user.getPasswd().equals(passwd)
					|| user.getResv1() == null
					|| !user.getResv1().equals(CAN_LOGIN)) {

				return RESULT_ERROR;
			}
			user.setMoney(moneyService.findOne(user.getId()).getMoney());
			successResult.setMsg(mapper.toJson(user));
			return mapper.toJson(successResult);
			// this.userInfoService.findByLoginName(searchParams)
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;
			// TODO: handle exception
		}

	}

	// @RequestMapping(value = "regeister", method = RequestMethod.POST)
	// @ResponseBody
	// public String regeister(UserInfo userInfo) {
	// try {
	// UserInfo user = this.userInfoService.findByLoginname(userInfo
	// .getRefreeUser().getLoginname());
	// if (user == null)
	// return RESULT_ERROR;
	// userInfo.setRefreeUser(user);
	//
	// userInfo = this.userInfoService.save(userInfo);
	// UserMoney userMoney = new UserMoney();
	// userMoney.setUserId(userInfo.getId());
	// userMoney.setMoney(0L);
	// userMoney.setRemark("新注册用户");
	// this.moneyService.save(userMoney);
	// return RESULT_OK;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return RESULT_ERROR;
	//
	// }
	//
	// }

	// passwd=q, loginname=qwrtyu, area=0, weight=31,
	// height=141, age=1, hobby=fdd, gender=0, telpone=18665654216,
	// iconimg=//20141123124217/1949060062.png, refreeUser.loginname=admin
	@RequestMapping(value = "regeister", method = RequestMethod.POST)
	@ResponseBody
	public String regeister(@RequestParam("passwd") String passwd,
			@RequestParam("loginname") String loginname,
			@RequestParam("area") String area,
			@RequestParam("weight") int weight,
			@RequestParam("height") int height, @RequestParam("age") int age,

			@RequestParam("hobby") String hobby,

			@RequestParam("gender") int gender,

			@RequestParam("telpone") String telpone,
			@RequestParam("iconimg") String iconimg,
			@RequestParam("refreeUser") String referName,
			@RequestParam("nickName") String nickName

	) {
		try {
			DealerInfo user = this.dealerInfoService.findOne(referName);
			if (user == null)
				return RESULT_ERROR;

			UserInfo userInfo = new UserInfo();
			userInfo.setLoginname(loginname);
			userInfo.setArea(area);
			userInfo.setWeight(weight);
			userInfo.setHeight(height);
			userInfo.setAge(age);
			userInfo.setHobby(hobby);
			userInfo.setGender(gender);
			userInfo.setTelpone(telpone);
			userInfo.setIconimg(iconimg);
			userInfo.setRefreeUser(referName);
			userInfo.setNickname(nickName);
			userInfo.setResv1("0");
			userInfo.setPasswd(passwd);
			userInfo.setResv2("0");
			userInfo.setResv3("0");
			userInfo.setType_map(1);
			userInfo.setOnline(1);
			userInfo = this.userInfoService.save(userInfo);
			UserMoney userMoney = new UserMoney();
			userMoney.setUserId(userInfo.getId());
			userMoney.setMoney(0L);
			userMoney.setRemark("新注册用户");
			this.moneyService.save(userMoney);
			return RESULT_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;

		}

	}

	@RequestMapping(value = "checkname")
	@ResponseBody
	public String checkname(@RequestParam("loginname") String loginname) {
		try {
			UserInfo user = this.userInfoService.findByLoginname(loginname);
			if (user == null)
				return RESULT_OK;
			return RESULT_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;

		}

	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid UserInfo userInfo) {
		try {
			this.userInfoService.save(userInfo);
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}

		return RESULT_OK;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("entity") UserInfo userInfo) {
		try {
			this.userInfoService.save(userInfo);
		} catch (Exception e) {
			return RESULT_ERROR;
			// TODO: handle exception
		}

		return RESULT_OK;
	}

	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam(value = "ids[]") Long[] ids) {
		try {
			this.userInfoService.delete(ids);
		} catch (Exception e) {
			return RESULT_ERROR;

		}
		return RESULT_OK;
	}

	@ModelAttribute
	public void getDictionary(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userInfoService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserInfo, Long> getServcie() {
		return this.userInfoService;
	}

	public String uploadIcon() {
		return null;

	};

}
