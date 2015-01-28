package com.i5le.redplus.web;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.i5le.framwork.sys.service.UserService;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.RedplusMoney;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.entity.UserTrans;
import com.i5le.redplus.entity.UserTransInfo;
import com.i5le.redplus.entity.UserTransfer;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.RedplusMoneyService;
import com.i5le.redplus.service.TheMessageService;
import com.i5le.redplus.service.UserInfoService;
import com.i5le.redplus.service.UserMoneyService;
import com.i5le.redplus.service.UserTransInfoService;
import com.i5le.redplus.service.UserTransService;
import com.i5le.redplus.service.UserTransferService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/userTransfer")
public class UserTransferController extends
		BaseCRUDController<UserTransfer, Long> {

	@Autowired
	private RedplusMoneyService redplusMoneyService;
	@Autowired
	private UserTransService userTransService;

	@Autowired
	private UserTransferService userTransferService;

	@Autowired
	private UserMoneyService userMoneyService;

	@Autowired
	private TheMessageService theMessageService;

	@Autowired
	private UserInfoService userInfoService;

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

	// @RequestMapping(value = "updateTrans")
	// @ResponseBody
	// public String toUserTrans(@RequestParam("tid") Long tid ,@RequestMapp) {
	// AjaxResult<UserTrans> ajaxResult = new AjaxResult<UserTrans>();
	// ajaxResult.setSuccess(true);
	// ajaxResult.setList(this.userTransService.findByToUserId(tid));
	//
	// return GsonUtil.transferByEntity(ajaxResult);
	//
	// }

	// @ResponseBody
	// @RequestMapping(value = "actionTrans")
	// public String actionTrans(@RequestParam("id") Long tid,
	// @RequestParam("statu") int statu) {
	//
	// UserTrans trans = this.userTransService.findOne(tid);
	// UserMoney fmoney = this.userMoneyService.findByUserId(
	// trans.getFromUser().getId());
	// UserMoney tmoney = this.userMoneyService.findByUserId(
	// trans.getToUser().getId());
	//
	// return null;
	// }
	@ResponseBody
	@RequestMapping(value = "transfer")
	public String add(@RequestParam("fromUserid") Long id,
			@RequestParam("tousername") String loginName,
			@RequestParam("long") Long money) {
		AjaxResult<UserTransfer> ajaxResult = new AjaxResult<UserTransfer>();

		UserInfo fromuserInfo = this.userInfoService.findOne(id);
		UserInfo toUserInfo = this.userInfoService.findByLoginname(loginName);

		ajaxResult.setSuccess(false);

		if (fromuserInfo != null && toUserInfo != null) {

			UserMoney formuserMoney = this.userMoneyService
					.findByUserId(fromuserInfo.getId());
			if (formuserMoney == null) {

				ajaxResult.setMsg("请先充值！");
				return GsonUtil.transferByEntity(ajaxResult);
			}

			UserMoney toUserMoney = this.userMoneyService
					.findByUserId(toUserInfo.getId());
			
			
			if (formuserMoney.getMoney() > money) {
				if ((fromuserInfo.getGender() == 1)
						&& (toUserInfo.getGender() == 1)) {
					formuserMoney.setMoney(formuserMoney.getMoney() - money);
					if(toUserMoney == null){
						
						toUserMoney  = new UserMoney();
					
						 toUserMoney.setUserId(toUserInfo.getId());
						 toUserMoney.setMoney(money);
					}else{
					toUserMoney.setMoney(toUserMoney.getMoney() == null ? money :toUserMoney.getMoney() + money);
					}

					this.userMoneyService.save(formuserMoney);
					this.userMoneyService.save(toUserMoney);

					/**
					 * 总部应该得多少
					 */

					TheMessage thsMessage = new TheMessage();
					TheMessage thsMessage888 = new TheMessage();
					thsMessage.setToUser(fromuserInfo);
					thsMessage.setCreatetime(new Date());
					thsMessage.setInfoType(TheMessage.SYSTEM_WORD_TYPE);

					thsMessage.setInfo("你转账给" + toUserInfo.getNickname() + ":"
							+ money);
					
					BeanUtils.copyProperties(thsMessage, thsMessage888);
					UserInfo user888 = new UserInfo();
					user888.setId(66l);
					thsMessage888.setToUser(user888);
					this.theMessageService.save(thsMessage);
					this.theMessageService.save(thsMessage888);
					TheMessage thsMessage2 = new TheMessage();
					TheMessage thsMessage2888 = new TheMessage();
					thsMessage2.setToUser(toUserInfo);
					thsMessage2.setCreatetime(new Date());
					thsMessage2.setInfoType(TheMessage.SYSTEM_WORD_TYPE);
					thsMessage2.setInfo(fromuserInfo.getNickname() + "转账给你"
							+ ":" + money);
					BeanUtils.copyProperties(thsMessage2, thsMessage2888);
					this.theMessageService.save(thsMessage2);
					thsMessage2888.setToUser(user888);
					this.theMessageService.save(thsMessage2888);
					UserTransfer userTransfer = new UserTransfer();
					userTransfer.setFromUser(fromuserInfo);
					userTransfer.setToUser(toUserInfo);
					userTransfer.setCreatetime(new Date());
					userTransfer.setMoneny(money);
					this.userTransferService.save(userTransfer);
					ajaxResult.setSuccess(true);
					ajaxResult.setMsg("转账成功！");

				} else {
					ajaxResult.setMsg("只允许男同学之间转账!");

				}
			} else {
				ajaxResult.setMsg("余额不足!");

			}

		} else {

			ajaxResult.setMsg("转出账号不存在！");

		}
		return GsonUtil.transferByEntity(ajaxResult);
	}

	@ResponseBody
	@RequestMapping(value = "actionTrans")
	public String actionTrans(@RequestParam("tid") Long id,
			@RequestParam("statu") int statu) {
		AjaxResult<UserTrans> ajaxResult = new AjaxResult<UserTrans>();
		UserTrans userTrans = userTransService.findOne(id);
		if (userTrans == null) {
			ajaxResult.setSuccess(false);

			return GsonUtil.transferByEntity(ajaxResult);
		}
		UserInfo fromUser = userTrans.getFromUser();
		UserInfo toUser = userTrans.getToUser();

		Long money = userTrans.getCountMoney();
		UserMoney formuserMoney = userMoneyService.findByUserId(userTrans
				.getFromUser().getId());
		ajaxResult.setSuccess(true);
		TheMessage theMessage = new TheMessage();
		TheMessage theMessage888 = new TheMessage();
		
		theMessage.setCreatetime(new Date());
		theMessage.setFromUser(fromUser);
		theMessage.setToUser(toUser);
		theMessage.setTransId(id);
		theMessage.setInfoType(TheMessage.TRANS_INFO_TYPE);
		
		UserMoney toUserMoney = userMoneyService.findByUserId(userTrans
				.getToUser().getId());
		if (statu == UserTrans.TRANS_OK) {
			if (formuserMoney.getMoney() < money) {
				ajaxResult.setSuccess(false);
				userTrans.setStatu(UserTrans.TRANS_BAD);
				theMessage.setInfo("余额不足不够送礼:" + money);
				this.userTransService.save(userTrans);
			} else {
				formuserMoney.setMoney(formuserMoney.getMoney() - money);
				userTrans.setStatu(UserTrans.TRANS_OK);
				this.userMoneyService.save(formuserMoney);
				this.userTransService.save(userTrans);
				theMessage.setInfo("此次送礼已经确认:" + money);

			}

		} else if (statu == UserTrans.TRANS_OVER) {
			userTrans.setStatu(UserTrans.TRANS_OVER);
			toUserMoney.setMoney(toUserMoney.getMoney() != null ? toUserMoney
					.getMoney() + money : money);

			theMessage.setInfo("此次送礼已经完美结束，总共金额:" + money);

			this.userTransService.save(userTrans);
			this.userMoneyService.save(toUserMoney);

		} else if (statu == UserTrans.TRANS_BAD) {
			userTrans.setStatu(UserTrans.TRANS_BAD);
			theMessage.setInfo("拒绝此次送礼");
			this.userTransService.save(userTrans);
		}
		BeanUtils.copyProperties(theMessage, theMessage888);
		UserInfo user888 = new UserInfo();
		user888.setId(66l);
		theMessage888.setToUser(user888);
		this.theMessageService.save(theMessage);
		this.theMessageService.save(theMessage888);
		
		return GsonUtil.transferByEntity(ajaxResult);

	}

	@ResponseBody
	@RequestMapping(value = "addT")
	public String addT(@Valid UserTrans trans) {

		return userTransService.add(trans) ? RESULT_OK : RESULT_ERROR;
	}

	// @RequestMapping(value = "add")
	// @ResponseBody
	// // public String add(@RequestParam("formId") Long
	// foomId,@RequestParam("toId") Long toId,@RequestParam("ids") String ids) {
	// //
	// //
	// //
	// // }

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userTransService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserTransfer, Long> getServcie() {
		// TODO Auto-generated method stub
		return userTransferService;
	}

}
