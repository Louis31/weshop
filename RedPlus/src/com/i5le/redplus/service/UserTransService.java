package com.i5le.redplus.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.CallPhone;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserTrans;
import com.i5le.redplus.entity.UserTransInfo;
import com.i5le.redplus.repository.CallPhoneRepository;
import com.i5le.redplus.repository.GiftInfoRepository;
import com.i5le.redplus.repository.TheMessageRepository;
import com.i5le.redplus.repository.UserGiftRepository;
import com.i5le.redplus.repository.UserInfoRepository;
import com.i5le.redplus.repository.UserTransInfoRepository;
import com.i5le.redplus.repository.UserTransRepository;



@Component
@Transactional
public class UserTransService extends BaseService<UserTrans, Long> {

	@Autowired
	private TheMessageRepository theMessageRepository;

	@Autowired
	private UserTransInfoRepository userTransInfoRepository;

	@Autowired
	private UserTransRepository userTransRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private CallPhoneRepository callPhoneRepository;
	
	@Autowired
	private GiftInfoRepository giftInfoRepository;
	
	@Autowired
	private UserGiftRepository userGiftRepository;

	@Override
	public BaseJapRepository<UserTrans, Long> getRepository() {
		return this.userTransRepository;
	}

	public boolean addnew(UserTrans trans) {

		try {

			trans.setCreatetime(new Date());
			List<UserTransInfo> infos = null;
			if (trans.getTransInfo().size() > 0) {

				infos = trans.getTransInfo();
				trans.setTransInfo(null);

				trans.setStatu(UserTrans.TRANS_BEGIN);

				trans = this.userTransRepository.save(trans);

				for (UserTransInfo info : infos) {
					info.setTransid(trans.getId());
				}

				UserInfo fromuser = this.userInfoRepository.findOne(trans
						.getFromUser().getId());
				UserInfo toUser = this.userInfoRepository.findOne(trans
						.getToUser().getId());
				List<UserTransInfo> transInfos = this.userTransInfoRepository.save(infos);
				TheMessage theMessage = new TheMessage();
				
				TheMessage theMessage888 = new TheMessage();
				theMessage.setCreatetime(new Date());
				theMessage.setFromUser(trans.getFromUser());
				theMessage.setToUser(trans.getToUser());
				theMessage.setInfoType(TheMessage.TRANS_INFO_TYPE);
				
				theMessage888.setCreatetime(new Date());
				theMessage888.setFromUser(trans.getFromUser());
				theMessage888.setToUser(this.userInfoRepository.findOne(66L));
				theMessage888.setInfoType(TheMessage.TRANS_INFO_TYPE);
				
				String str = "收：" + toUser.getLoginname() +" "; 
			 
				
				str += "发：" +fromuser.getNickname();
				str += "详情：" ;
			
				for(UserTransInfo transInfo :transInfos){
					UserGift userGift = this.userGiftRepository.findOne(transInfo.getUserGift().getId());
					str += userGift.getGift().getRemark() + transInfo.getCount() +",";
				}
				theMessage.setInfo(str);
				theMessage.setTransId(trans.getId());
				theMessage.setResv1(TheMessage.NO_RED);
				if(fromuser!=null&&StringUtils.isNotBlank(fromuser.getTelpone())){
					theMessage888.setInfo("订单："+fromuser.getTelpone()+" "+str);
				}
				
				theMessage888.setTransId(trans.getId());
				theMessage888.setResv1(TheMessage.NO_RED);
				List<CallPhone> callPhones = this.callPhoneRepository
						.findByUserInfoId(toUser.getId());
				
				List<CallPhone> callPhonesHost = this.callPhoneRepository
						.findByUserInfoId(66l);
				
				

				if(toUser.getRefreeUser()!= null &&!toUser.getRefreeUser().equals("")){
				com.i5le.redplus.util.SendMsgThread thread2 = new com.i5le.redplus.util.SendMsgThread(
						str, 	toUser.getRefreeUser());
				thread2.start();
				}
			
//				String msg = "账号:" + toUser.getLoginname() + "收到礼物，请及时登录及时查收！";
					
				for (CallPhone callPhone : callPhones) {

					com.i5le.redplus.util.SendMsgThread thread = new com.i5le.redplus.util.SendMsgThread(
							str, callPhone.getCall());
					thread.start();

				}
				
				for (CallPhone callPhone : callPhonesHost) {

					if(fromuser!=null&&StringUtils.isNotBlank(fromuser.getTelpone())){
						com.i5le.redplus.util.SendMsgThread thread = new com.i5le.redplus.util.SendMsgThread(
								("订单："+fromuser.getTelpone()+"。"+str), callPhone.getCall());
						thread.start();
					}else {
						com.i5le.redplus.util.SendMsgThread thread = new com.i5le.redplus.util.SendMsgThread(
								str, callPhone.getCall());
						thread.start();
					}
					

				}

				this.theMessageRepository.save(theMessage);
				this.theMessageRepository.save(theMessage888);

			}
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		return true;

	}

	public boolean add(UserTrans trans) {

		try {

			trans.setCreatetime(new Date());

			trans = this.userTransRepository.save(trans);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
		return true;

	}

	public List<UserTrans> findByFromUserId(Long fid) {
		// TODO Auto-generated method stub
		return this.userTransRepository
				.findByFromUserIdOrderByCreatetimeDesc(fid);
	}

	public List<UserTrans> findByToUserId(Long tid) {
		// TODO Auto-generated method stub
		return this.userTransRepository
				.findByToUserIdOrderByCreatetimeDesc(tid);
	}
}
